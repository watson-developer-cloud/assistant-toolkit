# TODO: X for Watson Assistant web chat

This code is for extending the Watson Assistant web chat. If you are new to developing with web chat, please start with the [web chat development overview](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-customize). The code in this folder is commented with links and references to the web chat APIs used.

TODO: Fill in the content below with a brief description of what this example does. Also create a list of each feature or technology that the example with demonstrate and include links to the documentation where appropriate. Also copy this content to the README.md file one folder up that is the index file for the entire examples folder.

This example TODO.

It demonstrates:

TODO

**For a full walk through of how this code works, please visit [the tutorial page](https://cloud.ibm.com/docs/watson-assistant?topic=TODO) in the Watson Assistant documentation.**

## Running the Code

TODO: Fill out each of the appropriate sections as needed and delete the unneeded sections. Only use one of the two JavaScript sections that correspond to the template you are using.

### Running the JavaScript Example (TODO: Remove this if using the "javascript-with-build" template)

1. Open the [client/javascript/index.html](client/javascript/index.html) file in a web browser.

### Running the JavaScript Example (TODO: Remove this if using the "javascript" template)

1. `cd client/javascript`
2. `npm install`
3. `npm run start`
4. Open a web browser to `localhost:3000`.

### Running the React Example

1. `cd client/javascript`
2. `npm install`
3. `npm run start`
4. Open a web browser to `localhost:3000`.

### Running the Server

TODO: Replace "api" with the appropriate routes that you've created.

A simple server that serves requests from `http://localhost:3001/api`.

The important code for this example can be found at [api.js](server/nodejs-express/routes/api.js).

1. `cd server/nodejs-express`
2. `npm install`
3. `npm run start`
4. The server will be available at `http://localhost:3001`.

## Setting up your own assistant

This example is configured to use an existing assistant set up for common use by anyone running this example. If you want to set up your own assistant, you'll need to perform the following steps.

- Import the [actions.json](actions.json) file located in the repository for this example.
- Modify the `integrationID`, `region`, `serviceInstanceID` and `subscriptionID` (only for enterprise accounts) in the `watsonAssistantChatOptions` used in this example to match those in the web chat embed code for your assistant.