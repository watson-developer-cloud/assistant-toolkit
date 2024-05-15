const express = require('express');

const router = express.Router();

/**
 * This function authenticates the user and creates a session token cookie to represent the user.
 */
function authenticate(request, response) {
  // This is where you would generally take a username and password from the user and authenticate that user against
  // some database. But for this example, we're just going to hardcode a user. In a normal production environment,
  // the session cookie would just be a secure session ID that's not accessible on the client. But for the purposes
  // of this example, we're just going to put the user info in the cookie so the client has easy access to the user
  // info.
  const userInfo = {
    userName: 'Cade',
    customUserID: 'cade-id',
  };

  // In a normal environment you would also want to include "httpOnly: true" here is keep this info safe. But for
  // this demo, we're going to have the client use this data.
  response.cookie('SESSION_INFO', JSON.stringify(userInfo), { encode: String });

  response.send('Ok');
}

router.get('/', authenticate);

module.exports = router;
