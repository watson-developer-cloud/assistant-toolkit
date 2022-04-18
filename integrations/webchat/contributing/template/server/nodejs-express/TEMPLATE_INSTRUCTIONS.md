# Template instructions for server/nodejs-express

This template uses a simplified version of the Express starter kit as its base. It provides the ability to import routes and some basic error fallback behavior. The entry point is [server.js](./server.js) and the server listens on port 3001.

## Using this template

1. Update the title in [package.json](./package.json).
2. Add your own routes to `http://localhost:3001/api/` as needed in [/routes/api](/routes/api).
3. Update the comment at the top of `server.js` to give a general overview of what is unique to this example where 
it says `ADD A COMMENT HERE ABOUT HOW TO FIND THE SPECIAL CODE YOU HAVE ADDED FOR THIS EXAMPLE.`.
4. Add the following block to the [README.md](../../README.md) at the root of this example under the `### Server` header:

```
#### Running the NodeJS Express Example

A simple server that serves requests from `http://localhost:3001/api`.

1. `cd server/nodejs-express`
2. `npm install`
3. `npm run start`
4. The server will be available at `http://localhost:3001`.

To view the code, start from [server/nodejs-express/server.js][./server/nodejs-express/server.js]

```

5. Delete this file.