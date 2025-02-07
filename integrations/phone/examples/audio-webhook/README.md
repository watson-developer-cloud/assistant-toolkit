# Audio Webhook

## Introduction

This repo contains code to demonstrate how to process the `audio-webhook` for watsonx Assistant. The `audio-webhook` is a way for developers in watsonx Assistant to specify an endpoint of where to receive the audio recordings for the `record` step in an action in watsonx Assistant. 

Here is the representation of the HTTP POST request for the `audio-webhook`

```
POST /audio-webhook HTTP/1.1
Content-Type: multipart/form-data; boundary=----------3676416B-9AD6-440C-B3C8-FC66DDC7DB45
----------3676416B-9AD6-440C-B3C8-FC66DDC7DB45
Content-Disposition: form-data; name="metadata"
Content-Type: application/json
{
    "assistant_id": "dadf4b56-3b67-411a-b48d-079806b626d3",
    "environment_id": "6205aead-fe91-44af-bfe1-b4435015ba23",
    "session_id": "50989a59-9976-4b3f-9a98-af42adcad69a",
    "recording_id": "3daeb5d2-f52b-4c3e-a869-328b6fc6327c",
    "start_timestamp": "2024-10-21T17:22:07.789Z",
    "stop_timestamp": "2024-10-21T17:22:37.789Z"
}
----------3676416B-9AD6-440C-B3C8-FC66DDC7DB45
Content-Disposition: form-data; name="audio_recording"
Content-Type: audio/mulaw;rate=8000

<binary data>
```

## Setup

**Prerequisites**
- Latest [Version of NodeJS](https://nodejs.org/en/about/previous-releases)

Clone the repository:

```
git clone https://github.com/watson-developer-cloud/assistant-toolkit.git
```

### Run the Server Locally

1. Change directory to the `audio-webhook-server`
```
cd assistant-toolkit/integrations/phone/examples/audio-webhook/audio-webhook-server
```

2. Install the dependencies:

```
npm ci
```

3. Run the server

```
npm start
```

4. The server is now running at `http://localhost:8080`. You should see the message:
```
[audio webhook server]: Listening on http://localhost:8080
```

To allow Watson Assistant to access the API server, you need to expose the server to the internet. You can use tools
like [ngrok](https://ngrok.com/) to do that. After installing ngrok, run `ngrok http 8080` to expose the server to the
internet.

#### Configuration

| Configuration | Description |
| --- | --- |
| `UPLOAD_TO_DISK` | Defaults to `false`. Set to `true` to upload the files to disk. |
| `AUDIO_WEBHOOK_SECRET` | Specify the secret you want to use in order to verify the JWT signature from watsonx Assistant or the test client. |



### Run the Test Client

This repository also includes a test client that can be used to test your server implementation without needing to place calls to the watsonx Assistant Phone Integration. This client generates a random GUID for the Assistant ID, Environment ID, recording ID every time a request is made and submits the audio stored in `src/assets/sample.wav`

To run the test client follow these steps:

1. Change directory to `audio-webhook-test-client`
```
cd assistant-toolkit/integrations/phone/examples/audio-webhook/audio-webhook-test-client/
```

2. Install the dependencies

```
npm ci
```


3. Run the client

```
npm start
```

4. In the standard out of your terminal you should see a message and the payload of what was submitted.
```
"Audio Webhook Called Successfully"
```

| Configuration | Description |
| --- | --- |
| `AUDIO_WEBHOOK_URL` | Specify the full URL to send the request to for testing, for example `http://localhost:8080/audiowebhook` |
| `AUDIO_WEBHOOK_SECRET` |  Specify the secret you want to use in order to authenticate the client against the Audio Webhook server. |
