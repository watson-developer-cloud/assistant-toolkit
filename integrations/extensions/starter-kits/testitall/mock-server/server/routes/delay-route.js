// import dependencies and initialize the express router

import express from 'express';
import {delay} from '../controllers/delay-controller.js';

export const delayRouter = express.Router();

// define routes
delayRouter.post('', delay);

export default delayRouter;
