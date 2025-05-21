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
import axios from "axios";
import express, { Request, Response } from "express";
import * as https from "https";

// Load local dev .env file before starting the app
import * as dotenv from "dotenv";

dotenv.config();
const PORT = process.env.PORT || 8080;
const VERIFICATION_TOKEN = process.env.VERIFICATION_TOKEN;
const WEBHOOK_URL = process.env.WEBHOOK_URL;

if (!WEBHOOK_URL) {
  console.error("❌ WEBHOOK_URL is not configured. Exiting...");
  process.exit(1);
}
const agent = new https.Agent({
  rejectUnauthorized: false,
});

const app = express();
app.use(express.json());

async function processInboundMessage(req: Request, res: Response) {
  const token = req.header("x-watson-byop-verification-token");
  if (!token || token !== VERIFICATION_TOKEN) {
    return res.status(401).send("Unauthorized");
  }

  const payload = req.body;
  res.sendStatus(200);
  console.log(`Incoming SMS message:\n`, JSON.stringify(payload));

  setTimeout(async () => {
    try {
      const response = await axios.post(
        WEBHOOK_URL!,
        {
          ...payload,
          from: payload.to,
          to: payload.from,
        },
        {
          headers: {
            "x-watson-byop-verification-token": VERIFICATION_TOKEN,
          },
          httpsAgent: agent,
        },
      );
      console.log("✅ Callback response:", response.status);
    } catch (err) {
      console.error("❌ Failed to send callback:", err);
    }
  }, 4000);
}

app.post("/byop-sms", (req: Request, res: Response) => {
  processInboundMessage(req, res);
});

app.listen(PORT, () => {
  console.log(`🚀 [byop sms server]: Listening on http://localhost:${PORT}`);
});
