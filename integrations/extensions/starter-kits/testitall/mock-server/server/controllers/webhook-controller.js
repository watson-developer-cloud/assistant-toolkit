import pkg from 'ibm-watson/auth/index.js';

export function preWebhook(req, res) {
  const payload = req.body.payload;
  if (!payload.context.skills['actions skill'].skill_variables) {
    payload.context.skills['actions skill'].skill_variables = {};
  }
  payload.context.skills['actions skill'].skill_variables.pre_webhook_message = 'Sample value from API Server (pre-message)';

  return res.json(req.body);
}
export function postWebhook(req, res) {
  const payload = req.body.payload;
  if (!payload.context.skills['actions skill'].skill_variables) {
    payload.context.skills['actions skill'].skill_variables = {};
  }
  payload.context.skills['actions skill'].skill_variables.post_webhook_message = 'Sample value from API Server (post-message)';

  return res.json(req.body);
}