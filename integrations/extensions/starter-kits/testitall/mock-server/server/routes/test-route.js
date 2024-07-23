// import dependencies and initialize the express router
import express from 'express';
import * as testController from '../controllers/test-controller.js';

const router = express.Router();

// define routes
router.delete('', testController.deleteTest);
router.get('', testController.getTest);
router.put('', testController.putTest);
router.post('', testController.postTest);
router.patch('', testController.patchTest);
router.post('/error', testController.errorTest);
router.post('/params/:path_param', testController.postTest);
router.post('/auth_header', testController.authHeaderTest);
router.post('/arrays-root', testController.arraysRootTest);
router.post('/arrays-object', testController.arraysInObjectTest);
router.post('/context-too-large', testController.contextTooLargeTest);
router.post('/context-almost-too-large', testController.contextAlmostTooLargeTest);
router.post('/non-json-response', testController.successfulPostWithNonJSONResponse);

router.post('/advanced/properties-counter', testController.propertiesByCounterTest);

export default router;

