# Sharing location for IBM watsonx Assistant web chat

**For a full walk through of how this code works, please visit the [tutorial page](DOCS.md) in the IBM watsonx Assistant documentation.**

This code is for extending the IBM watsonx Assistant web chat. If you are new to developing with web chat, please start with the [web chat development overview](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-develop). The code in this folder is commented with links and references to the web chat APIs used.

This example demonstrates how to share the user's geographic location with the assistant and use that info to display an I-frame of a Google map for the nearest IBM office.

It demonstrates:

- How to use the [**receive**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#receive) event handlers to identify messages from the assistant that should trigger an action by the browser.
- How to use the [**send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#send) instance method to send messages to the assistant and how to set context/skill variables.
- How to use the browser's built-in geolocation functionality to obtain the user's current location.
- How to use a variable in an action to set the source/URL of an I-frame response.

## Running the Code

### Running the JavaScript Example

1. Open the [client/javascript/index.html](client/javascript/index.html) file in a web browser.
2. Type or click "Where is the nearest IBM office?".
3. When the browser asks if you want to share your location, click "Allow".

## Setting up your own assistant

This example is configured to use an existing assistant set up for common use by anyone running this example. If you want to set up your own assistant, you'll need to perform the following steps.

- Import the [actions.json](actions.json) file located in the repository for this example.
- Modify the `integrationID`, `region`, `serviceInstanceID` and `subscriptionID` (only for enterprise accounts) in the `watsonAssistantChatOptions` used in this example to match those in the web chat embed code for your assistant.