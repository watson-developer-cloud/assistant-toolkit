# Page interactions for Watson Assistant web chat

This code is for extending the Watson Assistant web chat. If you are new to developing with web chat, please start with the [Getting Started tutorial](https://ibm.com). The code in this folder is commented with links and references to the web chat APIs used.

This example will show you how to use a custom response to display a button within web chat that can locate fields in the host page and fill in values to them.

It demonstrates:

- How to use the [**pre:receive**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#prereceive) event handler to add a custom response to message returned from the assistant so you can display a custom button.
- How to use a [**customResponse**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#customresponse) event handler to display a custom button which, when clicked, will interact with elements on the host page.
- How to use the [**ibm-web-chat--default-styles**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-render#helper_classes) CSS helper class to make a custom button look like a default web chat button.

**For a full walk through of how this code works, please visit [the tutorial page](https://TODO.ibm.com) in the Watson Assistant documentation.**

## Running the Code

### Running the JavaScript Example

1. Open the [client/javascript/index.html](client/javascript/index.html) file in a web browser.
2. Open web chat and click the button.

## Setting up your own assistant

This example is configured to use an existing assistant set up for common use by anyone running this example. If you want to set up your own assistant, you'll need to perform the following steps.

- Import the [actions.json](actions.json) file located in the repository for this example.
- Modify the `integrationID`, `region`, `serviceInstanceID` and `subscriptionID` (only for enterprise accounts) in the `watsonAssistantChatOptions` used in this example to match those in the web chat embed code for your assistant.