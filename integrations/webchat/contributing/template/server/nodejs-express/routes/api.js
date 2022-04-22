const express = require('express');

const router = express.Router();

router.get('/', function (request, response, next) {
  response.json({ message: 'Hello World' });
});

module.exports = router;
