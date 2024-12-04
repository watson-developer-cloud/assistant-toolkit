// load environment variables from .env file
dotenv.config();

// import dependencies and initialize express

import express from 'express';
import path from 'path';
import bodyParser from 'body-parser';

import {delayRouter} from './routes/delay-route.js';
import testRoutes from './routes/test-route.js';
import testStreamRoutes from './routes/test-stream-route.js';
import securityRoutes from './routes/security-route.js';
import providerRoutes from './routes/security-oauth-provider-route.js';
import webhookRoutes from './routes/webhook-route.js';
import * as securityController from './controllers/security-controller.js';
import morgan from "morgan";

import dotenv from 'dotenv';


const app = express();

// enable access log
app.use(morgan('dev'));

// enable parsing of http request body
app.use(bodyParser.urlencoded({extended: true}));

// access to static files
app.use(express.static(path.join('public')));

// routes and api calls

// Skip body-parser for /test/stream, but apply all other global middleware
app.use('/test/stream', testStreamRoutes);

app.use(bodyParser.json());

app.use('/delay', delayRouter);

// General routes for /test 
app.use('/test', testRoutes);

app.use('/security', securityRoutes);
app.use('/oauth2-provider', providerRoutes);

app.use('/webhook', webhookRoutes);
app.use('/webhook/basic', securityController.basicAuthMiddleware, webhookRoutes);
app.use('/webhook/bearer', securityController.bearerAuthMiddleware, webhookRoutes);
app.use('/webhook/apikey', securityController.apiKeyAuthMiddleware, webhookRoutes);
app.use('/webhook/oauth2-authCode', securityController.oauth2AuthCodeMiddleware, webhookRoutes);
app.use('/webhook/oauth2-password', securityController.oauth2PasswordMiddleware, webhookRoutes);
app.use('/webhook/oauth2-clientCred', securityController.oauth2ClientCredMiddleware, webhookRoutes);
app.use('/webhook/oauth2-customApikey', securityController.oauth2CustomApikeyMiddleware, webhookRoutes);

// welcome page
app.get('/', (req, res) => {
  res.send('The mock API server is up and running');
});

// start node server with port from environment variable or default 4000
const port = process.env.API_SERVER_PORT || 4000;
app.listen(port, () => {
  console.log(`App available at http://localhost:${port}`);
});



