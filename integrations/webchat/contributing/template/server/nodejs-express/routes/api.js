const express = require('express');

const router = express.Router();

/**
 * This function implements the API route.
 */
function api(request, response, next) {
  response.json({ message: 'Hello World' });
}

router.get('/', api);

module.exports = router;
