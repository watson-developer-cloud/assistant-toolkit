const fs = require('fs');
const RSA = require('node-rsa');
const jwtLib = require('jsonwebtoken');
const express = require('express');
const path = require('path');
const { v4: uuid } = require('uuid');

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

// A time period of 45 days in milliseconds.
const TIME_45_DAYS = 1000 * 60 * 60 * 24 * 45;

/**
 * Generates a signed JWT. The JWT used here will always be assigned a user ID using the given anonymous user ID. If
 * the user is authenticated and we have session info, then info about the user will also be added to the JWT. We
 * always use the anonymous user ID even if the user is authenticated because Watson Assistant doesn't allow you to
 * change the user ID in the middle of a session.
 */
function createJWTString(anonymousUserID, sessionInfo) {
  // This is the content of the JWT. You would normally look up the user information from a user profile.
  const jwtContent = {
    // This is the subject of the JWT which will be the ID of the user.
    sub: anonymousUserID,
    // This object is optional and contains any data you wish to include as part of the JWT. This data will be
    // encrypted using IBM's public key so it will not be visible to your users. Watson Assistant will decrypt this
    // data and make it available to use in your assistant.
    user_payload: {
      custom_message: 'This is a secret, encrypted message!',
      name: 'Anonymous',
      custom_user_id: anonymousUserID,
    },
  };

  // If the user is authenticated, then add the user's real info to the JWT.
  if (sessionInfo) {
    jwtContent.user_payload.name = sessionInfo.userName;
    jwtContent.user_payload.custom_user_id = sessionInfo.customUserID;
  }

  console.log('Generating a JWT with info', jwtContent);

  // Encrypt the user payload.
  const rsaKey = new RSA(IBM_PUBLIC_KEY);
  jwtContent.user_payload = rsaKey.encrypt(jwtContent.user_payload, 'base64');

  // Now sign the jwt content to make the actual jwt. We are giving this a very short expiration time (10 seconds)
  // to demonstrate the web chat capability of fetching a new token when it expires. In a production environment,
  // you would likely want to set this to a much higher value or leave it out entirely.
  const jwtString = jwtLib.sign(jwtContent, PRIVATE_KEY, {
    algorithm: 'RS256',
    expiresIn: '10s',
  });

  return jwtString;
}

/**
 * Gets or sets the anonymous user ID cookie. This will also ensure that an existing cookie is updated with a new 45
 * day expiration time.
 */
function getOrSetAnonymousID(request, response) {
  let anonymousID = request.cookies['ANONYMOUS-USER-ID'];
  if (!anonymousID) {
    // If we don't already have an anonymous user ID, then create one. Normally you would want to use a full UUID,
    // but for the sake of this example we are going to shorten it to just five characters to make them easier to read.
    anonymousID = `anon-${uuid().substr(0, 5)}`;
  }

  // Here we set the value of the cookie and give it an expiration date of 45 days. We do this even if we already
  // have an ID to make sure that we update the expiration date to a new 45 days.
  response.cookie('ANONYMOUS-USER-ID', anonymousID, {
    expires: new Date(Date.now() + TIME_45_DAYS),
    httpOnly: true,
  });

  return anonymousID;
}

/**
 * Returns the session info for an authenticated user.
 */
function getSessionInfo(request) {
  // Normally the cookie would contain a session token that we would use to look up the user's info from something
  // like a database. But for the sake of simplicity in this example the session cookie directly contains the user's
  // info.
  const sessionInfo = request.cookies.SESSION_INFO;
  if (sessionInfo) {
    return JSON.parse(sessionInfo);
  }
  return null;
}

/**
 * Handles the createJWT request.
 */
function createJWT(request, response) {
  const anonymousUserID = getOrSetAnonymousID(request, response);
  const sessionInfo = getSessionInfo(request);
  response.send(createJWTString(anonymousUserID, sessionInfo));
}

router.get('/', createJWT);

module.exports = router;
