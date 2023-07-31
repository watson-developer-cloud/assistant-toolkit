# Custom buttons for Watson Assistant web chat

**For a full walk through of how this code works, please visit the [tutorial page](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-develop-custom-buttons) in the Watson Assistant documentation.**

This code is for extending the Watson Assistant web chat. If you are new to developing with web chat, please start with the [web chat development overview](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-develop). The code in this folder is commented with links and references to the web chat APIs used.

This example demonstrates how to use a custom response to change the appearance of the buttons that are displayed when the assistant returns and options response.

It demonstrates:

- How to use a [**pre:receive**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#prereceive) event handler to convert a built-in response to a custom response so it can be customized.
- How to use a [**customResponse**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#customresponse) event handler to create custom buttons.
- How to use the [**send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#send) instance method to send a message to the assistant when a button is clicked.
- How to use the [**updateHistoryUserDefined**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#updateHistoryUserDefined) instance method to record which button was clicked in session history so it will be remembered when web chat is reloaded.
- How to use data stored in session history to change how a custom response is displayed when web chat is reloaded.
- How to use the [Carbon Design System](https://v10.carbondesignsystem.com/) inside of web chat. The examples here cover both the vanilla JavaScript version (using web components) and the React version.

## Running the Code

### Running the JavaScript Example

1. Open the [client/javascript/index.html](client/javascript/index.html) file in a web browser and click or type "Show me credit cards".

## Setting up your own assistant

This example is configured to use an existing assistant set up for common use by anyone running this example. If you want to set up your own assistant, you'll need to perform the following steps.

- Import the [actions.json](actions.json) file located in the repository for this example.
- Modify the `integrationID`, `region`, `serviceInstanceID` and `subscriptionID` (only for enterprise accounts) in the `watsonAssistantChatOptions` used in this example to match those in the web chat embed code for your assistant.