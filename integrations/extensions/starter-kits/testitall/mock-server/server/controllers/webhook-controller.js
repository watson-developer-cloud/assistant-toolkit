import jwt from 'jsonwebtoken';
import { generateHighEntropyString } from '../utils.js';

const logs= [];
const MAX_LOGS = 20;

const PRE_MESSAGE_WEBHOOK_VARIABLE = 'pre_webhook_message';
const POST_MESSAGE_WEBHOOK_VARIABLE = 'post_webhook_message';

export function createLog(req, res) {
  if (logs.length > MAX_LOGS) {
    logs.shift(); // remove the first element
  }
  logs.push({body : req.body, headers: req.headers});
  res.status(201).json({message: 'Log created'});
}

export function getLogs(req, res) {
  res.json(logs);
}

export function preRun(req, res) {
  let payload = req.body?.payload;

  if (payload?.message?.content) {
    payload.message.content += ' and also can you tell me what is 2+2 and also can you ECHO BACK THE EXACT WORDS "modified by pre-run webhook"';
  } else {
    payload = {
      message: {
        content: 'faked by pre-run and translated'
      }
    };
  }

  res.json({payload});
}

export function postRun(req, res) {
  let payload = req.body?.payload;
  console.log('postRun:', JSON.stringify(payload));
  console.log('type ', typeof payload);

  /** <streaming enabled> */
  if (payload?.data?.delta && typeof payload.data.delta === 'string') {
    console.log('delta ', payload.data.delta);
    payload.data.delta += ' translated by post_run webhook.\n';
    console.log('delta after ', payload.data.delta);
  }

  let wxaOutput = payload?.message?.run?.result?.data?.message?.content ?? [];
  console.log('wxaOutput1', JSON.stringify(wxaOutput));
  if (wxaOutput.length > 0) {
    console.log('modifying wxa response');
    for (const item of wxaOutput) {
      if (item.text != null) {
        item.text += ' translated by post_run webhook.\n'; 
      }
    }
    console.log('modified resp', JSON.stringify(wxaOutput))
  }
  /** <message created for final_event: true, stream_events: false> */
  if (payload?.content && typeof payload.content === 'string') {
    payload.content += ' translated by final event, stream_events:false post_run webhook';
   }
 
   wxaOutput = payload?.additional_properties?.wxa_message?.output?.generic ?? [];
   console.log('wxaOutput2', JSON.stringify(wxaOutput));
   if (wxaOutput.length > 0){
     console.log('modifying a final event, stream_events:false wxa response');
   
   for (const item of wxaOutput) {
     if ( item.text != null ) {
       item.text += ' translated by final event, stream_events:false post_run webhook';
     }
   }
 
   console.log('modified resp by final event, stream_events:false post_run webhook: ', JSON.stringify(wxaOutput));
   }

  res.json({payload});
}

function setSessionVariable(payload, key, value) {
  if (!payload.context.skills) {
    payload.context.skills = {};
  }
  if (!payload.context.skills['actions skill']) {
    payload.context.skills['actions skill'] = {};
  }
  if (!payload.context.skills['actions skill'].skill_variables) {
    payload.context.skills['actions skill'].skill_variables = {};
  }
  payload.context.skills['actions skill'].skill_variables[key] = value;
}

export function preWebhook(req, res) {
  const payload = req.body.payload;
  
  let response_message = 'Sample value from API Server (pre-message)'
  let auth_result = 'No auth header provided'

  if (payload.input.text === 'skip') {
    // Set header X-Watson-Assistant-Webhook-Return if input is skip
    res.set('X-Watson-Assistant-Webhook-Return', 'yes');
    // Return a generic response that is send directly to the user
    return res.json({
      output: {
        generic: [
          {
            response_type: 'text',
            text: 'Response skipped by webhook'
          }
        ]
      }
    })
  }

  if (req.auth_result) { // Auth done by middleware
    auth_result = req.auth_result;
  } else { // Perform legacy auth check
    const token = req.headers.authorization;
    if (token && process.env.PRE_MESSAGE_SECRET) {
      try {
        jwt.verify(token, process.env.PRE_MESSAGE_SECRET);
        auth_result = 'JWT token verified';
      } catch (e) {
        if (token === process.env.PRE_MESSAGE_SECRET) {
          auth_result = 'Auth header matches secret value';
        
        } else {
          auth_result = `Invalid Auth Header value: ${token}`;
        }
      }
    }
  }

  // Set the session variable
  setSessionVariable(payload, PRE_MESSAGE_WEBHOOK_VARIABLE, `${response_message}\n${auth_result}`);

  return res.json(req.body);
}

export function postWebhook(req, res) {
  const payload = req.body.payload;

  let response_message = 'Sample value from API Server (post-message)'
  let auth_result = 'No auth header provided'

  if (req.auth_result) { // Auth done by middleware
    auth_result = req.auth_result;
  } else { // Perform legacy auth check
    const token = req.headers.authorization;
    if (token && process.env.POST_MESSAGE_SECRET) {
      try {
        jwt.verify(token, process.env.POST_MESSAGE_SECRET);
        auth_result = 'JWT token verified';
      } catch (e) {
        if (token === process.env.POST_MESSAGE_SECRET) {
          auth_result = 'Auth header matches secret value';
        
        } else {
          auth_result = `Invalid Auth Header value: ${token}`;
        }
      }
    }
  }

  // Set the session variable
  setSessionVariable(payload, POST_MESSAGE_WEBHOOK_VARIABLE, `${response_message}\n${auth_result}`);

  return res.json(req.body);
}

export function webhookErrorResponseNonJSON(req, res) {
  return res.send('Webhook non-JSON response');
}

export function webhookErrorTimeout(req, res) {
  // not sending response
}

export function webhookErrorCode(req, res) {
  let code = 500;

  try {
    const user_code = parseInt(req.params.http_code, 10);
    if (user_code >= 100 && user_code < 600) {
      code = user_code;
    }
  } catch (e) {
    // do nothing
    console.log(e);
  }

  return res.status(code).json({ error: 'Server Error', code });
}

export function webhookAlmostTooLargeResponse(req, res) {
  const payload = req.body.payload;

  const data = generateHighEntropyString(parseInt(process.env.WEBHOOK_RESPONSE_SIZE_LIMIT, 10) - 100000); // Default: 4MB - 100KB = 3.9MB
  // Set the session variable
  setSessionVariable(payload, POST_MESSAGE_WEBHOOK_VARIABLE, data);

  return res.json(req.body);
}

export function webhookTooLargeResponse(req, res) {
  const payload = req.body.payload;

  const data = generateHighEntropyString(parseInt(process.env.WEBHOOK_RESPONSE_SIZE_LIMIT, 10) + 100000); // Default: 4MB + 100KB = 4.1MB
  // Set the session variable
  setSessionVariable(payload, POST_MESSAGE_WEBHOOK_VARIABLE, data);

  return res.json(req.body);
}

export function webhookEchoHeaders(req, res) {
  const payload = req.body.payload;

  setSessionVariable(payload, POST_MESSAGE_WEBHOOK_VARIABLE, JSON.stringify(req.headers));

  return res.json(req.body);
}