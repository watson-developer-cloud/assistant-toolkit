# Enabling security for Watson Assistant web chat

TODO: Links
This code is for extending the Watson Assistant web chat. If you are new to developing with web chat, please start with the [Getting Started tutorial](https://ibm.com). The code in this folder is commented with links and references to the web chat APIs used.

The code in this folder will cover how to enable the security feature for Watson Assistant web chat.

**For a full walk through of how this code works, please visit [the tutorial page](https://TODO.ibm.com) in the Watson Assistant documentation.**

## Running the Code

**DO NOT USE THE PUBLIC AND PRIVATE KEYS FROM THIS EXAMPLE FOR PRODUCTION USE!** You should generate your own public and private keys.

If you do not have a public and private key pair, you can generate one using the following commands:
```
ssh-keygen -t rsa -b 4096 -m PEM -f jwtRS256.key
openssl rsa -in jwtRS256.key -pubout -outform PEM -out jwtRS256.key.pub
```

### Running the JavaScript Example

- Open the [client/javascript/index.html](client/javascript/index.html) file in a web browser.
- Open web chat and click or type "Send something secure".

### Running the Server

The server is required for creating the JWTs that are required to enable security.

1. `cd server/nodejs-express`
2. `npm install`
3. `npm run start`
4. The server will be available at `http://localhost:3001`.

To view the code, start from [server/nodejs-express/server.js](server/nodejs-express/server.js)

## Setting up your own assistant

This example is configured to use an existing assistant set up for common use by anyone running this example. If you want to set up your own assistant, you'll need to perform the steps below.

- Import the [actions.json](actions.json) file located in the repository for this example into your assistant.
- Modify the `integrationID`, `region`, `serviceInstanceID` and `subscriptionID` (only for enterprise accounts) in the `watsonAssistantChatOptions` used in this example to match those in the web chat embed code for your assistant.
- Copy your public key into the file `server/nodejs-express/keys/jwtRS256.key.pub` and copy your private key into the file `server/nodejs-express/keys/jwtRS256.key`.
- Open the Security tab for the web chat settings page.
- Copy your public key into the "Your public key" field.
- Copy the "IBM provided public key" into the file `server/nodejs-express/keys/ibmPublic.key.pub`.
