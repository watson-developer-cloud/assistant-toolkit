// Authorization codes storage (code -> expireTime)
import crypto from "node:crypto";

export const accessTokensCustomApikey = new Map();
accessTokensCustomApikey.set('custom', 0)
const refreshToken = 'custom-refresh-token';

function issueAccessToken() {
  // Issue a new access_token
  const accessToken = crypto.randomUUID().toString();
  const accessExpireTime = Date.now() + 1000 * parseInt(process.env.OAUTH_ACCESS_TOKEN_EXPIRES, 10)
  accessTokensCustomApikey.set(accessToken, accessExpireTime)

  console.log(`[custom apikey] Issuing token: ${accessToken}, expires: ${new Date(accessExpireTime)}`)
  return accessToken
}

function issueRefreshToken() {
  // To make this demo simpler, we always return the same refresh_token
  return refreshToken
}

export function oauth2ProviderCustomApikeyToken(req, res, next) {
  // Identify the grant_type
  if (req.body.grant_type === 'custom_apikey') {
    console.log('[custom apikey] AUTH FLOW BEGINS; body.grant_type:', req.body.grant_type);
    if (req.body.apikey_id !== process.env.OAUTH_CUSTOM_APIKEY_ID) {
      return res.status(401).send('invalid or missing apikey_id')
    }
    if (req.body.apikey_secret !== process.env.OAUTH_CUSTOM_APIKEY_SECRET) {
      return res.status(401).send('invalid or missing apikey_secret')
    }

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
    console.log('[custom apikey] REFRESH FLOW BEGINS; body.grant_type:', req.body.grant_type);
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