import express from "express";
import * as webhookController from "../controllers/webhook-controller.js";

const router = express.Router();

router.post('/pre_run', webhookController.preRun);
router.post('/post_run', webhookController.postRun);
router.post('/prewebhook', webhookController.preWebhook);
router.post('/postwebhook', webhookController.postWebhook);
router.post('/error/non-json', webhookController.webhookErrorResponseNonJSON);
router.post('/error/timeout', webhookController.webhookErrorTimeout);
router.post('/error/code', webhookController.webhookErrorCode);
router.post('/error/code/:http_code', webhookController.webhookErrorCode);
router.post('/logs', webhookController.createLog);
router.get('/logs', webhookController.getLogs);

export default router;
