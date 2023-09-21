const fs = require('fs');
const jwtLib = require('jsonwebtoken');
const express = require('express');
const path = require('path');

const router = express.Router();

// If you want to generate your own private/public key pair, you can use commands like the following.
//
// ssh-keygen -t rsa -b 4096 -m PEM -f example-jwtRS256.key
// openssl rsa -in example-jwtRS256.key -pubout -outform PEM -out example-jwtRS256.key.pub

// *** DO NOT USE THE PUBLIC AND PRIVATE KEYS FROM THIS EXAMPLE FOR PRODUCTION USE! ***

// This is your private key that you will keep on your server. This is used to sign the jwt. You will paste your public
// key into the appropriate field on the Security tab of the web chat settings page. IBM watsonx Assistant will use your
// public key to validate the signature on the jwt.
const PRIVATE_KEY = fs.readFileSync(path.join(__dirname, '../keys/example-jwtRS256.key'));

/**
 * Handles the createJWT request.
 */
function createJWT(request, response) {
  // This is the content of the JWT. You would normally look up the user information from a user profile.
  const jwtContent = {
    // This is the subject of the JWT which will be the ID of the user. In a production environment, this code would
    // generally do something such as access a server session object that contains the already-authenticated user
    // information and place the current user's ID here.
    //
    // This user ID will be available under integrations.channel.private.user.id in dialog and
    // system_integrations.channel.private.user.id in actions.
    sub: 'user-id-cade',
  };

  // Now sign the jwt content to make the actual jwt. We are giving this a very short expiration time (10 seconds)
  // to demonstrate the web chat capability of fetching a new token when it expires. In a production environment,
  // you would likely want to set this to a much higher value or leave it out entirely.
  const jwtString = jwtLib.sign(jwtContent, PRIVATE_KEY, {
    algorithm: 'RS256',
    expiresIn: '10s',
  });

  response.send(jwtString);
}

router.get('/', createJWT);

module.exports = router;
