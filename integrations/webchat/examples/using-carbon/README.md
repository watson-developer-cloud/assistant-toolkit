# Using Carbon for IBM watsonx Assistant web chat

**For a full walk through of how this code works, please visit the [tutorial page](DOCS.md) in the IBM watsonx Assistant documentation.**

This code is for extending the IBM watsonx Assistant web chat. If you are new to developing with web chat, please start with the [web chat development overview](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-develop). The code in this folder is commented with links and references to the web chat APIs used.

This example demonstrates how to use the Carbon Design System with web chat. There is both a vanilla JavaScript example using web components and a React example.

It demonstrates:

- How to use a [**userDefinedResponse**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#userDefinedResponse) event handler to render an accordion using Carbon.

## Running the Code

### Running the JavaScript Example

1. `cd client/javascript`
2. `npm install`
3. `npm run start`
4. Open a web browser to `localhost:3000`.
5. Open web chat and click or type "Show me a Carbon component".

### Running the React Example

1. `cd client/react`
2. `npm install`
3. `npm run start`
4. Open a web browser to `localhost:3000`.
5. Open web chat and click or type "Show me a Carbon component".

## Setting up your own assistant

This example is configured to use an existing assistant set up for common use by anyone running this example. If you want to set up your own assistant, you'll need to perform the following steps.

- Import the [actions.json](actions.json) file located in the repository for this example.
- Modify the `integrationID`, `region`, `serviceInstanceID` and `subscriptionID` (only for enterprise accounts) in the `watsonAssistantChatOptions` used in this example to match those in the web chat embed code for your assistant.