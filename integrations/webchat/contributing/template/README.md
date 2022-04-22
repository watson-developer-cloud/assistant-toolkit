# TODO: XXXXXXX for Watson Assistant web chat

This code is for extending the Watson Assistant web chat. If you are new to developing with web chat, please start with the [Getting Started tutorial](https://ibm.com). The code in this folder is commented with links and references to the web chat APIs used.

<!---

Add a brief description of what the code in this folder covers.

Something like...
----
TODO: The code in this folder will specifically cover how to extend Watson Assistant web chat to be able to show custom dashboard from the Queequatch API dashboard service.

**For a full walk through of how this code works, please visit [the tutorial page](https://TODO.ibm.com) in the Watson Assistant documentation.**
----

If there is a matching tutorial in the documentation, please reference that link here. If there is not yet a tutorial page in the Watson Assistant documentation (likely), you can skip including that until it is added. If you are a non-IBMer adding this code example, don't fret! The web chat team will write a corresponding tutorial for this content!

--->

## Running the Code

TODO: Fill out each of the appropriate sections as needed and delete the unneeded sections. Only use one of the two JavaScript sections that correspond to the template you are using.

### Running the JavaScript Example (TODO: For the "javascript" template)

1. Open the [client/javascript/index.html](client/javascript/index.html) file in a web browser.

### Running the JavaScript Example (TODO: For the "javascript-with-build" template)

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

A simple server that serves requests from `http://localhost:3001/api`.

1. `cd server/nodejs-express`
2. `npm install`
3. `npm run start`
4. The server will be available at `http://localhost:3001`.

To view the code, start from [server/nodejs-express/server.js](server/nodejs-express/server.js)

## Setting up your own assistant

This example is configured to use an existing assistant set up for common use by anyone running this example. If you want to set up your own assistant, you'll need to perform the following steps.

- Import the [actions.json](actions.json) file located in the repository for this example.
- Modify the `integrationID`, `region`, `serviceInstanceID` and `subscriptionID` (only for enterprise accounts) in the `watsonAssistantChatOptions` used in this example to match those in the web chat embed code for your assistant.