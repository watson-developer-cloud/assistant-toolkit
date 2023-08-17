import express from "express";
import * as webhookController from "../controllers/webhook-controller.js";

const router = express.Router();

router.post('/prewebhook', webhookController.preWebhook);
router.post('/postwebhook', webhookController.postWebhook);

export default router;