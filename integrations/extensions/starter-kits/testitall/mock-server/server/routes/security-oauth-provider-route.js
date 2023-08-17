// import dependencies and initialize the express router
import express from 'express';
import {
    oauth2ProviderAuthCodeAuthorize,
    oauth2ProviderAuthCodeToken
} from "../controllers/oauth-providers/auth-code.js";
import {oauth2ProviderPasswordToken} from "../controllers/oauth-providers/password.js";
import {oauth2ProviderClientCredToken} from "../controllers/oauth-providers/client-cred.js";
import {oauth2ProviderCustomApikeyToken} from "../controllers/oauth-providers/custom-apikey.js";

const router = express.Router();

// define routes
router.get('/auth-code/authorize', oauth2ProviderAuthCodeAuthorize);
router.post('/auth-code/token', oauth2ProviderAuthCodeToken);

router.post('/password/token', oauth2ProviderPasswordToken);

router.post('/client-cred/token', oauth2ProviderClientCredToken);

router.post('/custom-apikey/token', oauth2ProviderCustomApikeyToken);

export default router;