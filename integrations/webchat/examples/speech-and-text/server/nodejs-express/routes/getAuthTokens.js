const IamTokenManager = require('@ibm-functions/iam-token-manager');
const express = require('express');
const fs = require('fs');
const path = require('path');

const router = express.Router();

// These two files contain the credentials from your Watson Speech service instance.
const STT_CREDENTIALS = loadCredentials('serviceCredentialsSTT.json');
const TTS_CREDENTIALS = loadCredentials('serviceCredentialsTTS.json');

/**
 * This is the function that handles "getAuthToken" requests. It will use the provided API keys for the speech to text
 * and text to speech services and return authorization tokens that can be used by the client (browser) to
 * communicate with the service. You can host this code in your own server or you can put it in an IBM cloud function.
 * You can also find more information about the Speech SDK here:
 * https://github.com/watson-developer-cloud/speech-javascript-sdk.
 */
async function getAuthTokens(request, response) {
  const ttsManager = new IamTokenManager({
    iamApikey: TTS_CREDENTIALS.apikey,
  });
  const ttsToken = await ttsManager.getToken();

  const sttManager = new IamTokenManager({
    iamApikey: STT_CREDENTIALS.apikey,
  });
  const sttToken = await sttManager.getToken();

  response.json({ ttsToken, ttsURL: TTS_CREDENTIALS.url, sttToken, sttURL: STT_CREDENTIALS.url });
}

/**
 * Reads the API key from the given file.
 */
function loadCredentials(fileName) {
  const filePath = path.join(__dirname, `../keys/${fileName}`);
  if (fs.existsSync(filePath)) {
    const data = String(fs.readFileSync(filePath));
    const json = JSON.parse(data);
    if (!json.apikey || !json.url) {
      throw new Error(
        `The file ${filePath} does not contain the expected service credentials. I expected "apikey" and "url" properties.`,
      );
    }
    return json;
  }
  throw new Error(
    `This example requires that you provide service credentials for access to your Watson Speech service. Please make sure the appropriate credentials can be found in keys/${fileName}.`,
  );
}

router.get('/', getAuthTokens);

module.exports = router;
