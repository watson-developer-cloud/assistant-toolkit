// import dependencies and initialize the express router
import express from 'express';
import * as testController from '../controllers/test-controller.js';

const router = express.Router();

// define routes
router.delete('', testController.deleteTest);
router.get('', testController.getTest);
router.put('', testController.putTest);
router.post('', testController.postTest);
router.post('/error', testController.errorTest);
router.post('/content', testController.acceptAwareTest);
router.post('/params/:path_param', testController.postTest);
router.post('/postwebhook', testController.postWebhook);
router.post('/prewebhook', testController.preWebhook);
router.post('/arrays-root', testController.arraysRootTest);
router.post('/arrays-object', testController.arraysInObjectTest);
router.post('/advanced/properties-counter', testController.propertiesByCounterTest);
export default router;

