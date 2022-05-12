const IamTokenManager = require('@ibm-functions/iam-token-manager');
const express = require('express');
const fs = require('fs');
const path = require('path');

const router = express.Router();

// These two files contain the API keys from your Watson Speech service instance.
const STT_KEY = loadAPIKey('apiKeySTT.txt');
const TTS_KEY = loadAPIKey('apiKeyTTS.txt');

/**
 * This is the function that handles "getAuthToken" requests. It will use the provided API keys for the speech to text
 * and text to speech services and return authorization tokens that can be used by the client (browser) to
 * communicate with the service. You can host this code in your own server or you can put it in an IBM cloud function.
 * You can also find more information about the Speech SDK here:
 * https://github.com/watson-developer-cloud/speech-javascript-sdk.
 */
async function getAuthToken(request, response) {
  const ttsManager = new IamTokenManager({
    iamApikey: TTS_KEY,
  });
  const ttsToken = await ttsManager.getToken();

  const sttManager = new IamTokenManager({
    iamApikey: STT_KEY,
  });
  const sttToken = await sttManager.getToken();

  response.json({ ttsToken, sttToken });
}

/**
 * Reads the API key from the given file.
 */
function loadAPIKey(fileName) {
  const filePath = path.join(__dirname, `../keys/${fileName}`);
  if (fs.existsSync(filePath)) {
    return String(fs.readFileSync(filePath)).trim();
  }
  throw new Error(
    `This example requires that you provide API keys for access to your Watson Speech service. Please make sure the appropriate key can be found in the keys/${fileName}.`,
  );
}

router.get('/', getAuthToken);

module.exports = router;
