// Authorization codes storage (code -> expireTime)
const authCodes = new Map();
export const accessTokensCustomApikey = new Map();
accessTokensCustomApikey.set('custom', 0)
const refreshToken = 'custom-refresh-token';

function issueAccessToken() {
  // Issue a new access_token
  const accessToken = Math.random().toString(36).slice(-8)
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

    // TODO
    // Custom validation logic
    // if (req.body.apikey_id !== API_KEY_ID || req.body.apikey_secret !== API_KEY_SECRET) {
    //   return res.status(401).send('invalid or missing apikey credentials')
    // }

    // Only check the apikey_secret due to issue: https://github.ibm.com/watson-engagement-advisor/wea-backlog/issues/60227
    if (req.body.apikey_secret !== process.env.OAUTH_CUSTOM_APIKEY_SECRET) {
      return res.status(401).send('invalid or missing apikey credentials')
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