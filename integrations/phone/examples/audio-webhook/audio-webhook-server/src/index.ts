/*
 Copyright 2025 IBM Corporation

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

      https://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
import express, {
  NextFunction,
  Request,
  RequestHandler,
  Response
} from "express";
import multer from "multer";
import dotenv from "dotenv";
import * as IBM from "ibm-cos-sdk";
import morgan from 'morgan';

import * as fs from 'fs';
import jwt from 'jsonwebtoken';

dotenv.config();

const app = express();
const port = process.env.PORT || 8080
app.use(morgan('combined')); // Logging format


app.use((err: Error, req: Request, res: Response, next: NextFunction) => {
  const status = (err as any).status || 500; // Type assertion for status property
  res.status(status).json({
    message: err.message || "Internal Server Error",
    stack: process.env.NODE_ENV === 'production' ? null : err.stack
  });
});


const UPLOAD_DIRECTORY = 'uploads/';

fs.mkdirSync(UPLOAD_DIRECTORY, {
  recursive: true
})

// Set to false to store files to disk
const UPLOAD_TO_DISK = process.env.UPLOAD_TO_DISK === "true";
const UPLOAD_TO_COS = process.env.UPLOAD_TO_COS === "true";


let upload;
let cosClient: IBM.S3;
if (!UPLOAD_TO_DISK) {
  const memoryStorage = multer.memoryStorage();
  upload = multer({
    storage: memoryStorage,
  })

} else {
  const diskStorage = multer.diskStorage({
    destination: (req, file, cb) => {
      cb(null, UPLOAD_DIRECTORY); // Specify the destination folder
    },
    filename: (req, file, cb) => {
      console.error(`Stored ${file.originalname} to disk`)
      cb(null, file.originalname); // Preserve the original file name
    },
  });

  upload = multer({
    storage: diskStorage
  });

}
const verifyToken = (req: Request, res: Response, next: NextFunction) => {
  const token = req.header("Authorization");

  if (!token) {
    res.status(401).json({
      message: "Access Denied. No token provided."
    });
    return;
  }

  try {
    const secretKey = process.env.AUDIO_WEBHOOK_SECRET as string; // Ensure you store your secret in environment variables
    jwt.verify(token.replace("Bearer ", ""), secretKey);
    next();
  } catch (error) {
    res.status(400).json({
      message: "Invalid Token"
    });
  }
  return;
};

if (UPLOAD_TO_COS) {

  const config = {
    endpoint: process.env.COS_ENDPOINT,
    apiKeyId: process.env.COS_API_KEY_ID,
    serviceInstanceId: process.env.COS_SERVICE_INSTANCE_ID,
  };
  cosClient = new IBM.S3(config);

}


app.post('/audiowebhook', verifyToken, upload.single('audio_recording'), async function (req, res, next) {

  try {
    const {
      metadata
    } = req.body;

    if (metadata) {
      console.log(`Received Metadata = ${metadata}`);
    }

    // When using memory storage, handle the file, see https://github.com/expressjs/multer?tab=readme-ov-file#file-information for documentation
    // On the File object.
    if (req.file) {
      if (cosClient) {
        await cosClient
          .putObject({
            Bucket: process.env.COS_BUCKET!,
            Key: decodeURIComponent(req.file.originalname),
            Body: req.file.buffer,
            Metadata: JSON.parse(metadata),
          })
          .promise();
      }
      console.log(req.file);
      res.end();

    } else {
      next(new Error('unable to save file'));
    }
  } catch (error) {
    next(error);
  }
});

app.listen(port, function () {
  console.log(`[audio webhook server]: Listening on http://localhost:${port}`)
});
