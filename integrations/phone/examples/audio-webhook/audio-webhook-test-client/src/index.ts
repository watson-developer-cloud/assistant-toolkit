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
import dotenv from "dotenv";
import FormData from "form-data";
import axios, {
  AxiosRequestConfig
} from "axios";
import * as fs from 'fs';
import jwt from 'jsonwebtoken';
import {
  v4 as uuidv4
} from 'uuid';

dotenv.config();

export const AUDIO_WEBHOOK_METADATA_FIELD_NAME = "metadata";

export const AUDIO_WEBHOOK_FILE_FIELD_NAME = "audio_recording";

export type RecordMetadata = {
  assistant_id: string;
  environment_id: string;
  session_id: string;
  recording_id: string;
  start_timestamp: string;
  stop_timestamp: string;
};


async function callAudioWebhook() {
  const url = process.env.AUDIO_WEBHOOK_URL;

  if (!url) {
    throw new Error('Please specify the "AUDIO_WEBHOOK_URL" url in your .env file. For example, "AUDIO_WEBHOOK_URL=https://example.com/audio-webhook"')
  }

  const startDatetime = new Date();
  startDatetime.setSeconds(startDatetime.getSeconds() - 10);

  const recordingID = uuidv4();
  const metadata: RecordMetadata = {
    assistant_id: uuidv4(),
    environment_id: uuidv4(),
    session_id: uuidv4(),
    recording_id: recordingID,
    start_timestamp: startDatetime.toISOString(),
    stop_timestamp: new Date().toISOString(),
  };

  const recording = fs.readFileSync(`./src/assets/sample.wav`);

  const multipartForm = new FormData();

  multipartForm.append(
    AUDIO_WEBHOOK_METADATA_FIELD_NAME,
    JSON.stringify(metadata),
  );

  multipartForm.append(
    AUDIO_WEBHOOK_FILE_FIELD_NAME,
    recording, {
      filename: `${recordingID}.wav`,
    },
  );

  const axiosRequest: AxiosRequestConfig = {
    method: "POST",
    url,
    data: multipartForm.getBuffer(),
    headers: {
      ...multipartForm.getHeaders(),
      'Authorization': `Bearer ${generateJwtToken()}`,
    },
  };
  console.log(axiosRequest.headers)

  console.log("Submitting request...\n ", multipartForm.getBuffer().toString());
  try { 
    await axios(axiosRequest)


    console.log("Audio Webhook Called Successfully")

  } catch (error) {
    console.log("Audio Webhook failed")
    console.error(error);
  }
}


function generateJwtToken(): string {
  const secret = process.env.AUDIO_WEBHOOK_SECRET || "";
  let token = "";

  try {
    const jwtExpirationInMillis = 30000; // 30 seconds
    const current_time = Date.now() / 1000;
    const expiration_time = Math.floor(
      current_time + jwtExpirationInMillis / 1000,
    );

    const jwtPayload = {
      sub: "user",
      iss: "test-client",
      exp: expiration_time,
    };

    token = jwt.sign(jwtPayload, secret, {
      algorithm: "HS256",
    });
  } catch (error) {
    console.error(error);
  }

  return token;
}


callAudioWebhook();