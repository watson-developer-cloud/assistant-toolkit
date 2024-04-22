import jwt from 'jsonwebtoken';

export function preWebhook(req, res) {
  let response_message = 'Sample value from API Server (pre-message)'
  let auth_result = 'No auth header provided'

  // Validate the JWT token (if provided)
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

  // Set the session variable
  const payload = req.body.payload;
  if (!payload.context.skills['actions skill'].skill_variables) {
    payload.context.skills['actions skill'].skill_variables = {};
  }
  payload.context.skills['actions skill'].skill_variables.pre_webhook_message = `${response_message}\n${auth_result}`;

  return res.json(req.body);
}

export function postWebhook(req, res) {
  let response_message = 'Sample value from API Server (post-message)'
  let auth_result = 'No auth header provided'

  // Validate the JWT token (if provided)
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

  // Set the session variable
  const payload = req.body.payload;
  if (!payload.context.skills['actions skill'].skill_variables) {
    payload.context.skills['actions skill'].skill_variables = {};
  }
  payload.context.skills['actions skill'].skill_variables.post_webhook_message = `${response_message}\n${auth_result}`;

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