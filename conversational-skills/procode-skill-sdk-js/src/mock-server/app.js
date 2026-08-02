// import dependencies and initialize express

import express from 'express';
import bodyParser from 'body-parser';

import {createBluepointsSkill} from '../bluepoints/BluepointsSkillFactory.js';

const app = express();



// enable parsing of http request body
app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());


// routes and api calls

// NOTE: the skill is built per request, not once at module load. `Skill` holds
// `slotsInFlight`, and its `Slot` objects are mutated during orchestration (e.g.
// `slotInFlight.setError`). A single shared instance leaks that mutated state into
// every later turn -- across sessions and across users.

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

/**
 * Retrieve the skill data from your provider. This endpoint will be called by the Assistant UI when creating a code-based action. 
 * Implementation of the getSkill endpoint follows the OAS specification defined here https://github.com/watson-developer-cloud/assistant-toolkit/blob/master/conversational-skills/procode-endpoints.md#oas
 * **NOTE** This endpoint must be implemented in order to use the Assistant Variables feature on the UI.
 * @param req - This is an express request. The request contains the following info `provider_id` ( path param ), assistant_id ( query param ), environment_id ( query param )
 * @param res - This is an express response. The json returned should adhere to the schema defined in the OAS specification `ListSkillsResponse` component
 * @param next 
 */
function getSkill(req,res,next) {
  const response = {
    "id": "myTakeOutSkill",
    "name": "Order Takeout",
    "description": "Enables a user to place a takeout food order from a restaurant",
    "created": "2024-02-01T04:55:18.871Z",
    "modified": "2024-02-01T04:55:18.871Z",
    "metadata": { 
      "last_modified_by": "foo@bar.com"
    },
    "input": {
        "slots": [
            {
              "name": "restaurantName", 
              "description": "The name of the restaurant to place a takeout order from.", 
              "type": "string", 
            },
            {
              "name": "entréeName", 
              "description": "The name of the entrée or dish to order.", 
              "type": "string", 
            },
            {
              "name": "orderPickUpDate", 
              "description": "The date of the order pickup.", 
              "type": "date", 
            },
            {
              "name": "orderPickUpTime", 
              "description": "The time of the order pickup.", 
              "type": "time", 
            },
            {
              "name": "orderConfirmation", 
              "description": "Confirmation of the user's order.", 
              "type": "confirmation", 
            }
        ]
    },
  }
  res.json(response);
}

/**
 * Restores slot visibility and entity schemas that earlier turns produced.
 *
 * Because the skill is rebuilt per request, anything a previous turn did to a Slot
 * object (`show()`, `schema = ...`) is gone. watsonx round-trips `state` back to us
 * on every turn, so that is where such decisions have to live. This mirrors what the
 * Java SDK already does in SkillOrchestrator.initializeSlotHandlers, via the same
 * `visible_slots` and `<slot>_schema` keys.
 */
function rehydrateSlots(skill, state) {
  const localVars = state?.local_variables || {};

  for (const slotName of localVars.visible_slots || []) {
    const slot = skill.slotsInFlight?.find(slotName);
    if (!slot) continue;
    slot.show();
    const schema = localVars[`${slotName}_schema`];
    if (schema) slot.schema = schema;
  }
}

/**
 * Persists the inverse of rehydrateSlots, so the next turn can restore it.
 */
function persistSlots(skillResponse) {
  const slots = skillResponse.output?.generic?.find(item => item.response_type === 'slots')?.slots;
  if (!slots || !skillResponse.state) return;

  skillResponse.state.local_variables ??= {};
  skillResponse.state.local_variables.visible_slots = slots.map(slot => slot.name);
  for (const slot of slots) {
    if (slot.schema) skillResponse.state.local_variables[`${slot.name}_schema`] = slot.schema;
  }
}

/**
 * This function implements the runtime contract between watsonx Assistant and the Provider
 * It follows the OAS specification /orchestrate Operation. See https://github.com/watson-developer-cloud/assistant-toolkit/blob/master/conversational-skills/procode-endpoints.md#oas
 * @param req - This is an express request. The `body` property adheres to the schema defined in the OAS specification `OrchestrationRequest` component
 * @param res - This is an express response. The json returned should adhere to the schema defined in the OAS specification `OrchestrationResponse` component
 * @param next
 */
async function orchestrate(req,res,next) {
  try {
    const bluepointsSkill = await createBluepointsSkill(req.body.context?.global?.language || 'en');
    rehydrateSlots(bluepointsSkill, req.body.state);
    const skillResponse = await bluepointsSkill.orchestrate({input:req.body.input,context:req.body.context, slots:req.body.slots, state: req.body.state, confirmation_event: req.body.confirmation_event});
    persistSlots(skillResponse);
    res.json(skillResponse);
  } catch (error) {
    // Without this, a throw inside the skill becomes an unhandled rejection and
    // takes the whole process down -- killing every other user's session too.
    console.error('orchestrate failed:', error);
    res.status(500).json({error: error.message});
  }
}


app.post('/providers/:provider_id/conversational_skills/:conversational_skill_id/orchestrate', orchestrate);
app.get('/providers/:provider_id/conversational_skills', listSkills);
app.get('/providers/:provider_id/conversational_skills/:conversational_skill_id', getSkill);

// welcome page
app.get('/', (req, res) => {
  res.send('The mock procode server is up and running');
});

// start node server with port from environment variable or default 4000
const port = process.env.API_SERVER_PORT || 4000;
app.listen(port, () => {

  console.log(`App available at http://localhost:${port}`);
});



