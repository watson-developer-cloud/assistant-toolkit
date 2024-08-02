// import dependencies and initialize the express router
import express from 'express';
import * as testController from '../controllers/test-controller.js';

const streamRouter = express.Router();

// define routes
streamRouter.post('/', testController.streamTest);
streamRouter.post('/error', testController.streamErrorTest);
streamRouter.post('/timeout', testController.streamTimeoutTest);

export default streamRouter;

