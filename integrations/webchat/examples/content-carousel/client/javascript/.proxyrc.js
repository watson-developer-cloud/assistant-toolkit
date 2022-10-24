const serveStatic = require('serve-static');

// This code allows us to access the images we have stored under the assets folder.
module.exports = function (app) {
  app.use(serveStatic('assets'))
}