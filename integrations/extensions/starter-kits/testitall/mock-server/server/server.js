// load environment variables from .env file
dotenv.config();

// import dependencies and initialize express

import express from 'express';
import path from 'path';
import bodyParser from 'body-parser';

import {delayRouter} from './routes/delay-route.js';
import testRoutes from './routes/test-route.js';
import securityRoutes from './routes/security-route.js';
import providerRoutes from './routes/security-oauth-provider-route.js';
import webhookRoutes from './routes/webhook-route.js';
import morgan from "morgan";

import dotenv from 'dotenv';


const app = express();

// enable access log
app.use(morgan('dev'));

// enable parsing of http request body
app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());

// access to static files
app.use(express.static(path.join('public')));

// routes and api calls

app.use('/delay', delayRouter);

app.use('/test', testRoutes);

app.use('/security', securityRoutes);
app.use('/oauth2-provider', providerRoutes);

app.use('/webhook', webhookRoutes);

// welcome page
app.get('/', (req, res) => {
  res.send('The mock API server is up and running');
});

// start node server with port from environment variable or default 4000
const port = process.env.API_SERVER_PORT || 4000;
app.listen(port, () => {
  console.log(`App available at http://localhost:${port}`);
});


