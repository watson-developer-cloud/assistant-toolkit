import {accessTokensAuthCode} from "./oauth-providers/auth-code.js";
import {accessTokensClientCred} from "./oauth-providers/client-cred.js";
import {accessTokensCustomApikey} from "./oauth-providers/custom-apikey.js";
import {accessTokensPassword} from "./oauth-providers/password.js";

// Basic Auth Middleware
export function basicAuthMiddleware(req, res, next) {
  const auth = {login: process.env.AUTH_USERNAME, password: process.env.AUTH_PASSWORD} // change this
  const b64auth = (req.headers.authorization || '').split(' ')[1] || ''
  const [login, password] = Buffer.from(b64auth, 'base64').toString().split(':')
  // Verify login and password are set and correct
  if (login && password && login === auth.login && password === auth.password) {
    // Access granted...
    return next()
  }
  // Access denied...
  res.set('WWW-Authenticate', 'Basic realm="401"') // change this
  return res.status(401).send('Authentication required.') // custom message
}

export function basicTest(req, res) {
  return res.status(200).send({
    message: 'This is so basic!'
  });
}

// Bearer Auth Middleware
export function bearerAuthMiddleware(req, res, next) {
  // parse login and password from headers
  const authHeader = req.headers.authorization || ''
  const token = authHeader.split(' ')[1] || ''

  // Verify login and password are set and correct
  if (authHeader.toLowerCase().startsWith("bearer") && token === process.env.AUTH_TOKEN) {
    // Access granted...
    return next()
  }

  // Access denied...
  res.set('WWW-Authenticate', 'Bearer realm="401"') // change this
  return res.status(401).send('Authentication required.') // custom message
}

export function bearerTest(req, res, next) {
  return res.status(200).send({
    message: 'This is so bearrrrrrr!'
  });
}

// Api Key Auth Middleware
export function apiKeyAuthMiddleware(req, res, next) {
  // parse login and password from headers
  const key = (req.headers['x-api-key'] || '') || ''

  // Verify login and password are set and correct
  if (key === process.env.AUTH_APIKEY) {
    // Access granted...
    return next()
  }

  // Access denied...
  return res.status(401).send('Authentication required.') // custom message
}

export function apiKeyTest(req, res, next) {
  return res.status(200).send({
    message: 'This is so api key!'
  });
}

// OAuth2 Auth Middleware
function makeOAuth2AuthMiddleware(accessTokens) {
  return (req, res, next) => {
    // Check if the request has an Authorization header
    if (!req.headers.authorization) {
      return res.status(401).send('OAuth2 Authentication required.');
    }

    // Check if the Authorization header is valid
    const authHeader = req.headers.authorization.split(' ');
    if (authHeader[0] !== 'Bearer') {
      return res.status(401).send('Invalid authentication scheme.');
    }

    // Check if the token is valid
    const accessToken = authHeader[1];
    if (!accessTokens.has(accessToken) || Date.now() > accessTokens.get(accessToken)) {
      return res.status(401).send('Invalid access token or token has expired.');
    }

    // Access granted...
    return next()
  };
}

// OAuth2 - Authorization Code
export const oauth2AuthCodeMiddleware = makeOAuth2AuthMiddleware(accessTokensAuthCode);

export function oauth2AuthCodeTest(req, res, next) {
  res.status(200).send({
    message: 'This is so oauth2 auth code!'
  });
}

// OAuth2 - Client Credentials
export const oauth2ClientCredMiddleware = makeOAuth2AuthMiddleware(accessTokensClientCred);

export function oauth2ClientCredTest(req, res, next) {
  res.status(200).send({
    message: 'This is so oauth2 client cred!'
  });
}

// OAuth2 - Password
export const oauth2PasswordMiddleware = makeOAuth2AuthMiddleware(accessTokensPassword);

export function oauth2PasswordTest(req, res, next) {
  res.status(200).send({
    message: 'This is so oauth2 password!'
  });
}

// OAuth2 - Custom Example
export const oauth2CustomApikeyMiddleware = makeOAuth2AuthMiddleware(accessTokensCustomApikey);

export function oauth2CustomApikeyTest(req, res, next) {
  res.status(200).send({
    message: 'This is so oauth2 custom apikey!'
  });
}




