// Authorization codes storage (code -> expireTime)
const authCodes = new Map();
export const accessTokensAuthCode = new Map();
const refreshToken = 'auth-code-refresh-token';

function issueAccessToken() {
  // Issue a new access_token
  const accessToken = Math.random().toString(36).slice(-8)
  const accessExpireTime = Date.now() + 1000 * parseInt(process.env.OAUTH_ACCESS_TOKEN_EXPIRES, 10)
  accessTokensAuthCode.set(accessToken, accessExpireTime)
  console.log('[auth code] Issuing token:', accessToken)

  return accessToken
}

function issueRefreshToken() {
  // To make this demo simpler, we always return the same refresh_token
  return refreshToken
}

export function oauth2ProviderAuthCodeAuthorize(req, res, next) {
  // Check response_type is correct
  if (req.query.response_type !== 'code') {
    return res.status(401).send('invalid or missing response_type')
  }

  // Check the client_id is correct
  if (req.query.client_id !== process.env.OAUTH_CLIENT_ID) {
    return res.status(401).send('invalid or missing client_id')
  }

  // Issue a new authCode
  const authCode = Math.random().toString(36).slice(-6)
  const expireTime = Date.now() + 1000 * 60 * 10 // 10 minutes
  authCodes.set(authCode, expireTime)
  console.log('[auth code] one-time auth code issued:', authCode)

  // Generate the redirect_uri
  const redirectUrl = req.query.redirect_uri + '?code=' + authCode + '&state=' + encodeURIComponent(req.query.state);

  // Generate a page for user to grant access
  res.send(`
        <html>
            <head>
                <title>Mock OAuth2 Resource Owner</title>
            </head>
            <body>
                <h1>Mock OAuth2 Resource Owner</h1>
                <p><h3>Watson Assistant is requesting to access your data on this mock server.</h3></p>
                <button onclick="window.location.href='${redirectUrl}'"><h3>Grant Access</h3></button>
            </body>
        </html>
    `)
}

export function oauth2ProviderAuthCodeToken(req, res, next) {
  // Check the client_id and client_secret is correct
  if (req.body.client_id !== process.env.OAUTH_CLIENT_ID || req.body.client_secret !== process.env.OAUTH_CLIENT_SECRET) {
    return res.status(401).send('invalid or missing client credentials')
  }

  // Identify the grant_type
  if (req.body.grant_type === 'authorization_code') {
    console.log('[auth code] AUTH FLOW BEGINS; body.grant_type:', req.body.grant_type);
    // Check the auth_code is correct
    if (!authCodes.has(req.body.code) || Date.now() > authCodes.get(req.body.code)) {
      // Delete the auth_code if it is expired
      authCodes.delete(req.body.code)
      return res.status(401).send('invalid code or code expired')
    }

    // Issue new access_token and refresh_token
    const accessToken = issueAccessToken()
    const refreshToken = issueRefreshToken()

    // Delete the auth_code
    authCodes.delete(req.body.code)
    console.log('[auth code] one-time auth code used:', req.body.code)

    // Return the access_token and refresh_token
    return res.status(200).send({
      access_token: accessToken,
      refresh_token: refreshToken,
      expires_in: parseInt(process.env.OAUTH_ACCESS_TOKEN_EXPIRES, 10)
    });

  } else if (req.body.grant_type === 'refresh_token') {
    console.log('[auth code] REFRESH FLOW BEGINS; body.grant_type:', req.body.grant_type);
    // Check the refresh_token is correct
    if (req.body.refresh_token !== refreshToken) {
      return res.status(401).send('invalid refresh token or refresh token expired')
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
    return res.status(401).send('invalid or missing grant_type')
  }
}