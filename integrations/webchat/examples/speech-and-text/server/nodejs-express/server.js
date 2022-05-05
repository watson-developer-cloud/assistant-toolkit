/**
 * This file starts up a basic NodeJS server with express, adds some routes, and adds some error handling. This is
 * mostly boilerplate content that can be adjusted as needed.
 *
 * The server defaults to being available on port 3001.
 */

const { createServer } = require('http');

const cookieParser = require('cookie-parser');
const cors = require('cors');
const createError = require('http-errors');
const express = require('express');

const PORT = 3001;

const app = express();

// Enable all CORS requests. In a production environment, you will likely want to remove this or set it to something
// more restrictive.
app.use(cors({ origin: '*' }));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());

// The routes needed by the application.
const getAuthTokenRouter = require('./routes/getAuthToken');

app.use('/getAuthToken/', getAuthTokenRouter);

// Catch 404 and forward to error handler
app.use(function (req, res, next) {
  next(createError(404));
});

// Error handler
app.use(function (error, request, response, next) {
  // set locals, only providing error in development
  response.locals.message = error.message;
  response.locals.error = request.app.get('env') === 'development' ? error : {};

  // render an error
  response.status({ error });
});

// Get port from environment and store in Express.
app.set('port', PORT);

// Create HTTP server.
const server = createServer(app);

// Listen on provided port, on all network interfaces.
server.listen(PORT);
server.on('error', onError);
server.on('listening', onListening);

/**
 * Event listener for HTTP server "error" event.
 */
function onError(error) {
  if (error.syscall !== 'listen') {
    throw error;
  }

  // Handle specific listen errors with friendly messages
  switch (error.code) {
    case 'EACCES':
      console.error(`${PORT} requires elevated privileges`);
      process.exit(1);
      break;
    case 'EADDRINUSE':
      console.error(`${PORT} is already in use`);
      process.exit(1);
      break;
    default:
      throw error;
  }
}

/**
 * Event listener for HTTP server "listening" event.
 */
function onListening() {
  console.log(`Listening on ${server.address().port}`);
}
