# Download history for Watson Assistant web chat

**For a full walk through of how this code works, please visit the [tutorial page](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-develop-download-transcript) in the Watson Assistant documentation.**

This code is for extending the Watson Assistant web chat. If you are new to developing with web chat, please start with the [web chat development overview](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-develop). The code in this folder is commented with links and references to the web chat APIs used.

This example demonstrates how to capture the conversation history for both Watson Assistant and for conversation with a human agent at a service desk and provide an option for the user to download them. It will add a custom menu option to the web chat header for the user to do this.

It demonstrates:

- How to use the [**receive**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#receive) and [**send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#send) event handlers to capture the messages that are sent to and received from the assistant.
- How to use the [**agent:receive**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#agentreceive) and [**agent:send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#agentsend) event handlers to capture the messages that are sent to and received from a human agent using a service desk integration.
- How to use a [**history:begin**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#historybegin) event handler to capture messages that were loaded from session history.
- How to use the [**updateCustomMenuOptions**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#updatecustommenuoptions) instance method to add a "Download history" option to the header.
- How to convert [**Watson Assistant messages**](https://cloud.ibm.com/apidocs/assistant/assistant-v2#message) into a text format that can be downloaded.
- How to use the built-in `Blob` and `URL` objects to construct a downloadable link.

## Running the Code

### Running the JavaScript Example

- Open the [client/javascript/index.html](client/javascript/index.html) file in a web browser.
- Open web chat and type "How are you doing?".
- Click "Download history" from the overflow menu that appears to the right of the assistant name in the header.

## Setting up your own assistant

This example is configured to use an existing assistant set up for common use by anyone running this example. If you want to set up your own assistant, you'll need to perform the steps below.

- Import the [actions.json](actions.json) file located in the repository for this example into your assistant.
- Modify the `integrationID`, `region`, `serviceInstanceID` and `subscriptionID` (only for enterprise accounts) in the `watsonAssistantChatOptions` used in this example to match those in the web chat embed code for your assistant.