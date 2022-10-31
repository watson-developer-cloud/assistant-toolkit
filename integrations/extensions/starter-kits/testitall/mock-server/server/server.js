// import dependencies and initialize express

import express from 'express';
import path from 'path';
import bodyParser from 'body-parser';

import {delayRouter} from './routes/delay-route.js';
import testRoutes from './routes/test-route.js';
import securityRoutes from './routes/security-route.js';
// require('dotenv').config();
const app = express();

// enable parsing of http request body
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

// access to static files
app.use(express.static(path.join('public')));

// routes and api calls

app.use('/delay', delayRouter);

app.use('/test', testRoutes);

app.use('/security', securityRoutes);

// start node server
const port = process.env.PORT || 4000;
app.listen(port, () => {
  console.log(`App available at http://localhost:${port}`);
});


