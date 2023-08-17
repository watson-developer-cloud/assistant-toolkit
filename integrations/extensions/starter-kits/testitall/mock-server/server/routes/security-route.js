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

router.use('/oauth2-authCode', securityController.oauth2AuthCodeMiddleware);
router.post('/oauth2-authCode', securityController.oauth2AuthCodeTest);
router.use('/oauth2-password', securityController.oauth2PasswordMiddleware);
router.post('/oauth2-password', securityController.oauth2PasswordTest);
router.use('/oauth2-clientCred', securityController.oauth2ClientCredMiddleware);
router.post('/oauth2-clientCred', securityController.oauth2ClientCredTest);
router.use('/oauth2-customApikey', securityController.oauth2CustomApikeyMiddleware);
router.post('/oauth2-customApikey', securityController.oauth2CustomApikeyTest);

export default router;


