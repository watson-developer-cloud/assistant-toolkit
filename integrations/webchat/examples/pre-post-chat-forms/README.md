# Pre and post-chat forms for Watson Assistant web chat

**For a full walk through of how this code works, please visit [the tutorial page](DOCS.md) in the Watson Assistant documentation.**

This code is for extending the Watson Assistant web chat. If you are new to developing with web chat, please start with the [web chat development overview](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-develop). The code in this folder is commented with links and references to the web chat APIs used.

This example demonstrates how to create pre and post-chat forms with web chat. You can use a pre-chat form to gather data from your user before the conversation starts or to display a custom disclaimer or other required panel. You can use a post-chat form to gather feedback from your user on the quality of the conversation. This example will ask the user for their full name before beginning the conversation. And when the user closes the window, it will ask the user to provide feedback.

It demonstrates:

- How to use the [**pre:receive**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#prereceive) event handler to send the data gathered from the pre-chat form to the assistant.
- How to use a [**window:open**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#windowclose) event handler to show a pre-chat form before web chat is opened.
- How to use a [**window:pre:close**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#windowclose) event handler to show a customUserID form when web chat is closed.
- How to use the [**customPanels**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#custompanels) instance property to display custom panels.

## Running the Code

### Running the JavaScript Example

1. Open the [client/javascript/index.html](client/javascript/index.html) file in a web browser.
2. Open web chat and fill out the pre-chat form that is displayed. The name you enter will be displayed as part of the greeting message.
3. Type "Say something" to send a message to the assistant (the post-chat form in this example is only displayed if the user has sent a message to the assistant).
4. Close web chat and fill out the post-chat form that is displayed.

### Running the React Example

1. `cd client/react`
2. `npm install`
3. `npm run start`
4. Open a web browser to `localhost:3000`
5. Open web chat and fill out the pre-chat form that is displayed. The name you enter will be displayed as part of the greeting message.
6. Type "Say something" to send a message to the assistant (the post-chat form in this example is only displayed if the user has sent a message to the assistant).
7. Close web chat and fill out the post-chat form that is displayed.

## Setting up your own assistant

This example is configured to use an existing assistant set up for common use by anyone running this example. If you want to set up your own assistant, you'll need to perform the following steps.

- Import the [actions.json](actions.json) file located in the repository for this example.
- Modify the `integrationID`, `region`, `serviceInstanceID` and `subscriptionID` (only for enterprise accounts) in the `watsonAssistantChatOptions` used in this example to match those in the web chat embed code for your assistant.