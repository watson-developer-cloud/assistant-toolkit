// Copyright 2024 davidamid
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     https://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

// load environment variables from .env file
dotenv.config();

// import dependencies and initialize express

import express from 'express';
import path from 'path';
import bodyParser from 'body-parser';

import {createBluepointsSkill} from '../bluepoints/BluepointsSkillFactory.js';

import morgan from "morgan";

import dotenv from 'dotenv';


const app = express();

// enable access log
app.use(morgan('dev'));

// enable parsing of http request body
app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());

// access to static files
app.use(express.static(path.join('public')));

// routes and api calls

const bluepointsSkill = await createBluepointsSkill('en'); 

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



