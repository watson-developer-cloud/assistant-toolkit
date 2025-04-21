import crypto from "node:crypto";

export const accessTokensPassword = new Map();
const refreshToken = 'password-refresh-token';

function issueAccessToken() {
  // Issue a new access_token
  const accessToken = crypto.randomUUID().toString();
  const accessExpireTime = Date.now() + 1000 * parseInt(process.env.OAUTH_ACCESS_TOKEN_EXPIRES, 10)
  accessTokensPassword.set(accessToken, accessExpireTime)
  console.log(`[password] Issuing token: ${accessToken}, expires: ${new Date(accessExpireTime)}`)

  return accessToken
}

function issueRefreshToken() {
  // To make this demo simpler, we always return the same refresh_token
  return refreshToken
}

export function oauth2ProviderPasswordToken(req, res, next) {
  // Check the client_id and client_secret is correct
  if (req.body.client_id !== process.env.OAUTH_CLIENT_ID || req.body.client_secret !== process.env.OAUTH_CLIENT_SECRET) {
    return res.status(401).send('invalid or missing client credentials')
  }

  // Identify the grant_type
  if (req.body.grant_type === 'password') {
    console.log('[password] AUTH FLOW BEGINS; body.grant_type:', req.body.grant_type);
    // Check the user_name and password is correct
    if (req.body.username !== process.env.OAUTH_USERNAME) {
      return res.status(401).send('invalid or missing username')
    }
    if (req.body.password !== process.env.OAUTH_PASSWORD) {
      return res.status(401).send('invalid or missing password')
    }

    // Issue new access_token and refresh_token
    const accessToken = issueAccessToken()
    const refreshToken = issueRefreshToken()

    // Return the access_token and refresh_token
    return res.status(200).send({
      access_token: accessToken,
      refresh_token: refreshToken,
      expires_in: parseInt(process.env.OAUTH_ACCESS_TOKEN_EXPIRES, 10)
    });

  } else if (req.body.grant_type === 'refresh_token') {
    console.log('[password] REFRESH FLOW BEGINS; body.grant_type:', req.body.grant_type)
    // Check the refresh_token is correct
    if (req.body.refresh_token !== refreshToken) {
      return res.status(401).send('invalid refresh token')
    }

    // Issue a new access_token
    const accessToken = issueAccessToken()

    // Return the new access_token and refresh_token
    return res.status(200).send({
      access_token: accessToken,
      refresh_token: req.body.refresh_token,
      expires_in: parseInt(process.env.OAUTH_ACCESS_TOKEN_EXPIRES, 10)
    });

  } else {
    return res.status(401).send('invalid or missing grant_type, must be one of "password" or "refresh_token"')
  }
}