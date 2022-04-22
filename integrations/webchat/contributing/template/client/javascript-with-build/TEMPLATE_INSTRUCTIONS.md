# Template instructions for client/javascript-with-build

This template is based off of a non-React javascript build using the [Parcel](https://parceljs.org/) library. Parcel will allow you to import `ts`, `js`, `css`, `scss`, etc files with no additional configuration. The entry point is [app.js](app.js) and the server listens on port 3000. By default, requests to `/api` are [proxied](https://github.com/http-party/http-server) to `http://localhost:3001/api` to avoid CORS issues if you are using a server for this example. You can edit this in [package.json](package.json).


## Using this template

**NOTE: You should only use the `javascript-with-build` or the `javascript` example, not both.**

1. Search for TODO in the example folder and replace with appropriate content.
2. Delete the existing [javascript][../javascript] folder and rename this folder to `javascript`.
3. Delete this file.