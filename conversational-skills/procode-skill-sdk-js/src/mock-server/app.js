// import dependencies and initialize express

import express from 'express';
import bodyParser from 'body-parser';

import {createBluepointsSkill} from '../bluepoints/BluepointsSkillFactory.js';

const app = express();



// enable parsing of http request body
app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());


// routes and api calls

const bluepointsSkill = await createBluepointsSkill('en'); 

/**
 * This function lists the skills available by your provider. It will be called by the Assistant UI when selecting skills to create a code-based action.
 * It follows the OAS specification /conversational_skills Operation. See https://github.com/watson-developer-cloud/assistant-toolkit/blob/master/conversational-skills/procode-endpoints.md#oas
 * @param req - This is an express request. The request contains the following info `provider_id` ( path param ), assistant_id ( query param ), environment_id ( query param )
 * @param res - This is an express response. The json returned should adhere to the schema defined in the OAS specification `ListSkillsResponse` component
 * @param next 
 */
function listSkills(req,res,next) {
  const response = {
    "conversational_skills": [
      {
        "id": "7a161635-fc8b-420b-8112-dbd742131390",
        "name": "Example",
        /* Readable description of the Conversational Skill's purpose */
        "description": "sample bluepoints", 
        "created": new Date().toISOString(),
        "modified":new Date().toISOString(),
        /* Optional free-form metadata object for future usecases. */
        "metadata": { 
          "last_modified_by": "foo@bar.com"
        }
      },
    ] 
  }
  res.json(response);
}
app.post('/providers/:provider_id/conversational_skills/:conversational_skill_id/orchestrate', orchestrate);
app.get('/providers/:provider_id/conversational_skills', listSkills);

/**
 * This function implements the runtime contract between watsonx Assistant and the Provider
 * It follows the OAS specification /orchestrate Operation. See https://github.com/watson-developer-cloud/assistant-toolkit/blob/master/conversational-skills/procode-endpoints.md#oas
 * @param req - This is an express request. The `body` property adheres to the schema defined in the OAS specification `OrchestrationRequest` component
 * @param res - This is an express response. The json returned should adhere to the schema defined in the OAS specification `OrchestrationResponse` component
 * @param next 
 */
async function orchestrate(req,res,next) {
  const skillResponse = await bluepointsSkill.orchestrate({input:req.body.input,context:req.body.context, slots:req.body.slots, state: req.body.state, confirmation_event: req.body.confirmation_event});
  res.json(skillResponse);
}

// welcome page
app.get('/', (req, res) => {
  res.send('The mock procode server is up and running');
});

// start node server with port from environment variable or default 4000
const port = process.env.API_SERVER_PORT || 4000;
app.listen(port, () => {

  console.log(`App available at http://localhost:${port}`);
});



