# Select greeting action for Watson Assistant web chat

**For a full walk through of how this code works, please visit [the tutorial page](DOCS.md) in the Watson Assistant documentation.**

This code is for extending the Watson Assistant web chat. If you are new to developing with web chat, please start with the [web chat development overview](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-develop). The code in this folder is commented with links and references to the web chat APIs used.

This example demonstrates how to change the greeting request that is sent to the assistant when web chat is first opened so that it will trigger a specific action instead of the default greeting action.

It demonstrates:

- How to determine what page the user is on and how to use a condition for deciding what action to trigger.
- How to use a [**pre:send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#presend) event handler to customize the greeting request that is sent to the assistant.

## Running the Code

### Running the JavaScript Example

1. Open the [client/javascript/index.html](examples/select-greeting-action/client/javascript/index.html) file in a web browser.
2. Open web chat to see the default greeting message.
3. Click the "here" link at the top of the page to switch to the "credit cards" page.
4. Open web chat again. You will see a different greeting message that was triggered by the message "Credit Cards".

## Setting up your own assistant

This example is configured to use an existing assistant set up for common use by anyone running this example. If you want to set up your own assistant, you'll need to perform the following steps.

- Import the [actions.json](examples/select-greeting-action/actions.json) file located in the repository for this example.
- Modify the `integrationID`, `region`, `serviceInstanceID` and `subscriptionID` (only for enterprise accounts) in the `watsonAssistantChatOptions` used in this example to match those in the web chat embed code for your assistant.