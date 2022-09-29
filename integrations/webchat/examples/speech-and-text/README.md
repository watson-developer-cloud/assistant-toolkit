# Watson Speech and Text for Watson Assistant web chat

**For a full walk through of how this code works, please visit [the tutorial page](DOCS.md) in the Watson Assistant documentation.**

This code is for extending the Watson Assistant web chat. If you are new to developing with web chat, please start with the [web chat development overview](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-develop). The code in this folder is commented with links and references to the web chat APIs used.

This example demonstrates how to use the Watson Speech services to convert speech to text and text to speech. It will show how to get web chat to speak the text that it receives from the assistant and how to use a record button to listen to a user for speech that will be converted to text and sent to the assistant from web chat.

It demonstrates:

- How to use a NodeJS Express server for creating authorization tokens from your API keys that are required when making calls to Watson Speech.
- How to use the [**pre:receive**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#prereceive) and [**pre:send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#presend) event handlers to capture the messages that are sent to and received from the assistant so they can be sent to the text to speech service.
- How to use the [**Watson Speech JS SDK**](https://github.com/watson-developer-cloud/speech-javascript-sdk) To convert text to speech.
- How to use the [**writeableElements**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#writeableelements) instance property to add a custom button above the web chat input field that can be used to record speech.
- How to use the [**Watson Speech JS SDK**](https://github.com/watson-developer-cloud/speech-javascript-sdk) To convert the recorded speech to text.
- How to use the [**send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#send) instance function to send the converted text to the assistant.

## Running the Code

### Running the JavaScript Example

1. Open the [client/javascript/index.html](client/javascript/index.html) file in a web browser.
2. Open web chat and you should hear it speak the greeting message.
3. Click the "Start recording" button and say "How are you doing?"
4. The first time you do this the browser will ask you for permission to use your microphone. Make sure to allow this. Also, if your computer has access to multiple microphone devices, make sure your browser settings are using the correct microphone.
5. Click "Stop recording" or just wait a moment after you stop speaking and your message should be sent as text to the assistant.

### Running the Server

The server is required for generating authorization tokens from your API keys. It serves requests from `http://localhost:3001/getAuthToken`.

The important code for this example can be found at [getAuthTokens.js](server/nodejs-express/routes/getAuthTokens.js).

1. Copy your service credentials for the text to speech and speech to text services into the `serviceCredentialsSTT.json` and `serviceCredentialsTTS.json` files under `server/nodejs-express/keys`.
2. `cd server/nodejs-express`
3. `npm install`
4. `npm run start`
5. The server will be available at `http://localhost:3001`.

## Setting up your own assistant

This example is configured to use an existing assistant set up for common use by anyone running this example. If you want to set up your own assistant, you'll need to perform the following steps.

- Import the [actions.json](actions.json) file located in the repository for this example.
- Modify the `integrationID`, `region`, `serviceInstanceID` and `subscriptionID` (only for enterprise accounts) in the `watsonAssistantChatOptions` used in this example to match those in the web chat embed code for your assistant.