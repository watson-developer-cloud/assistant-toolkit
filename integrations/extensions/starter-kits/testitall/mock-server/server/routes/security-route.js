// import dependencies and initialize the express router
import express from 'express';
import * as securityController from '../controllers/security-controller.js';

const router = express.Router();

// define routes
router.use('/basic', securityController.basicAuthMiddleware);
router.post('/basic', securityController.basicTest);
router.use('/bearer', securityController.bearerAuthMiddleware);
router.post('/bearer', securityController.bearerTest);
router.use('/apikey', securityController.apiKeyAuthMiddleware);
router.post('/apikey', securityController.apiKeyTest);
export default router;


