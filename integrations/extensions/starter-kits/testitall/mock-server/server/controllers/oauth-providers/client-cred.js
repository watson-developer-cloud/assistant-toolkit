// Authorization codes storage (code -> expireTime)
const authCodes = new Map();
export const accessTokensClientCred = new Map();
const refreshToken = 'client-cred-refresh-token';

function issueAccessToken() {
  // Issue a new access_token
  const accessToken = Math.random().toString(36).slice(-8)
  const accessExpireTime = Date.now() + 1000 * parseInt(process.env.OAUTH_ACCESS_TOKEN_EXPIRES, 10)
  accessTokensClientCred.set(accessToken, accessExpireTime)
  console.log('[client cred] Issuing token:', accessToken)

  return accessToken
}

function issueRefreshToken() {
  // To make this demo simpler, we always return the same refresh_token
  return refreshToken
}

export function oauth2ProviderClientCredToken(req, res, next) {
  // Check the client_id and client_secret is correct
  if (req.body.client_id !== process.env.OAUTH_CLIENT_ID || req.body.client_secret !== process.env.OAUTH_CLIENT_SECRET) {
    return res.status(401).send('invalid or missing client credentials')
  }

  // Identify the grant_type
  if (req.body.grant_type === 'client_credentials') {
    console.log('[client cred] AUTH FLOW BEGINS; body.grant_type:', req.body.grant_type);
    // Issue new access_token and refresh_token
    const accessToken = issueAccessToken()
    const refreshToken = issueRefreshToken()

    // Return the access_token
    return res.status(200).send({
      access_token: accessToken,
      refreshToken: refreshToken,
      expires_in: parseInt(process.env.OAUTH_ACCESS_TOKEN_EXPIRES, 10)
    });

  } else if (req.body.grant_type === 'refresh_token') {
    console.log('[client cred] REFRESH FLOW BEGINS; body.grant_type:', req.body.grant_type);
    // Check the refresh_token is correct
    if (req.body.refresh_token !== refreshToken) {
      return res.status(401).send('invalid refresh token or refresh token expired')
    }

    // Issue a new access_token
    const accessToken = issueAccessToken()

    // Return the new access_token and refresh_token
    return res.status(200).send({
      access_token: accessToken,
      refresh_token: issueRefreshToken(),
      expires_in: parseInt(process.env.OAUTH_ACCESS_TOKEN_EXPIRES, 10)
    });

  } else {
    return res.status(401).send('invalid or missing grant_type')
  }
}