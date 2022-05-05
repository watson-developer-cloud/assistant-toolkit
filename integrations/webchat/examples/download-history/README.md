# Download history for Watson Assistant web chat

TODO: Links
This code is for extending the Watson Assistant web chat. If you are new to developing with web chat, please start with the [Getting Started tutorial](https://ibm.com). The code in this folder is commented with links and references to the web chat APIs used.

This example demonstrates how to capture the conversation history and provide an option for the user to download it. It will add a custom menu option to the web chat header for the user to do this.

It demonstrates:

- How to use the [**pre:receive**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#prereceive) and [**pre:send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#presend) event handlers to capture the messages that are sent to and received from the assistant.
- How to use a [**history:begin**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#historybegin) event handler to capture messages that were loaded from session history.
- How to use the [**updateCustomMenuOptions**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#updatecustommenuoptions) instance function to add a "Download history" option to the header.
- How to convert [**Watson Assistant messages**](https://cloud.ibm.com/apidocs/assistant/assistant-v2#message) into a text format that can be downloaded.
- How to use the built-in `Blob` and `URL` objects to construct a downloadable link.

**For a full walk through of how this code works, please visit [the tutorial page](https://TODO.ibm.com) in the Watson Assistant documentation.**

## Running the Code

### Running the JavaScript Example

- Open the [client/javascript/index.html](client/javascript/index.html) file in a web browser.
- Open web chat and type "How are you doing?".
- Click "Download history" from the overflow menu that appears to the right of the assistant name in the header.

## Setting up your own assistant

This example is configured to use an existing assistant set up for common use by anyone running this example. If you want to set up your own assistant, you'll need to perform the steps below.

- Import the [actions.json](actions.json) file located in the repository for this example into your assistant.
- Modify the `integrationID`, `region`, `serviceInstanceID` and `subscriptionID` (only for enterprise accounts) in the `watsonAssistantChatOptions` used in this example to match those in the web chat embed code for your assistant.