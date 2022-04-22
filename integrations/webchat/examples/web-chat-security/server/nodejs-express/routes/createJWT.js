const fs = require('fs');
const RSA = require('node-rsa');
const jwtLib = require('jsonwebtoken');
const express = require('express');
const path = require('path');

const router = express.Router();

// If you want to generate your own private/public key pair, you can use commands like the following.
//
// ssh-keygen -t rsa -b 4096 -m PEM -f jwtRS256.key
// openssl rsa -in jwtRS256.key -pubout -outform PEM -out jwtRS256.key.pub

// *** DO NOT USE THE PUBLIC AND PRIVATE KEYS FROM THIS EXAMPLE FOR PRODUCTION USE! ***

// This is your private key that you will keep on your server. This is used to sign the jwt. You will paste your public
// key into the appropriate field on the Security tab of the web chat settings page. Watson Assistant will use your
// public key to validate the signature on the jwt.
const PRIVATE_KEY = fs.readFileSync(path.join(__dirname, '../keys/jwtRS256.key'));

// This is IBM's public key and can be found on the Security tab of the web chat settings page. This is only necessary
// if you wish to encrypt a user payload in your jwt. The code below will use this key to encrypt the user payload
// inside of the JWT.
const IBM_PUBLIC_KEY = fs.readFileSync(path.join(__dirname, '../keys/ibmPublic.key.pub'));

/**
 * Generates a signed JWT. This example hardcodes information about the user the JWT represents but in a real production
 * environment, the server environment should have access to information about an authenticated user. Usually this is
 * accomplished by having the user login to the site and then storing a token in a cookie. That cookie can be used to
 * look up the user's profile. You can access the cookie from the request object that is available from the Express
 * application and passed in here.
 */
function createJWT() {
  // This is the content of the JWT. You would normally look up the user information from a user profile.
  const jwtContent = {
    // This is the subject of the JWT which will be the ID of the user.
    sub: 'some_user_id',
    // This object is optional and contains any data you wish to include as part of the JWT. This data will be
    // encrypted using IBM's public key so it will not be visible to your users. Watson Assistant will decrypt this
    // data and make it available to use in your assistant.
    user_payload: {
      name: 'Someone',
      email: 'someone@lendyr-demo.ibm.com',
      custom_message: 'This is a secret, encrypted message!',
    },
  };

  // Encrypt the user payload. This is optional.
  const rsaKey = new RSA(IBM_PUBLIC_KEY);
  jwtContent.user_payload = rsaKey.encrypt(jwtContent.user_payload, ['base64']);

  // Now sign the jwt content to make the actual jwt. We are giving this a very short expiration time (10 seconds)
  // to demonstrate the web chat capability of fetching a new token when it expires. In a production environment,
  // you would likely want to set this to a much higher value or leave it out entirely.
  const jwtString = jwtLib.sign(jwtContent, PRIVATE_KEY, {
    algorithm: 'RS256',
    expiresIn: '10s',
  });

  return jwtString;
}

router.get('/', function (request, response) {
  response.send(createJWT());
});

module.exports = router;
