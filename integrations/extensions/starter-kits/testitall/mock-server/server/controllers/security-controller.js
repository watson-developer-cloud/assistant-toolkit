

export function basicAuthMiddleware(req, res, next) {
  // -----------------------------------------------------------------------
  // authentication middleware

  const auth = {login: 'david', password: '42'} // change this
  // console.log(req.headers.authorization);
  // parse login and password from headers
  const b64auth = (req.headers.authorization || '').split(' ')[1] || ''
  const [login, password] = Buffer.from(b64auth, 'base64').toString().split(':')
  // console.log(login);
  // console.log(password);
  // Verify login and password are set and correct
  if (login && password && login === auth.login && password === auth.password) {
    // Access granted...
    return next()
  }
  // Access denied...
  res.set('WWW-Authenticate', 'Basic realm="401"') // change this
  res.status(401).send('Authentication required.') // custom message
}

export function bearerAuthMiddleware(req, res, next) {
    // -----------------------------------------------------------------------
    // authentication middleware
  
    const expectedToken ='david42' // change this
  // console.log(req.headers.authorization);
    // parse login and password from headers
    const token = (req.headers.authorization || '').split(' ')[1] || ''
    
    // Verify login and password are set and correct
    if (req.headers.authorization.toLowerCase().startsWith("bearer") & token === expectedToken) {
      // Access granted...
      return next()
    }

  // Access denied...
  res.set('WWW-Authenticate', 'Bearer realm="401"') // change this
  res.status(401).send('Authentication required.') // custom message
}

export  function apiKeyAuthMiddleware(req, res, next) {
  // -----------------------------------------------------------------------
  // authentication middleware

  const expectedKey ='david42' // change this

  // parse login and password from headers
  const key = (req.headers['x-api-key'] || '') || ''

  // Verify login and password are set and correct
  if (key === expectedKey) {
    // Access granted...
    return next()
  }

// Access denied...
res.status(401).send('Authentication required.') // custom message
}

export function basicTest (req, res, next) {
  res.status(200).send({
    message: 'This is so basic!'
 });
};

export function bearerTest (req, res, next) {
  res.status(200).send({
    message: 'This is so bearrrrrrr!'
 });
};

export  function apiKeyTest (req, res, next) {
  res.status(200).send({
    message: 'This is so api key!'
 });
};



