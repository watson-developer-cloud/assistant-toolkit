# Custom launcher for IBM watsonx Assistant web chat

**For a full walk through of how this code works, please visit the [tutorial page](DOCS.md) in the IBM watsonx Assistant documentation.**

This code is for extending the IBM watsonx Assistant web chat. If you are new to developing with web chat, please start with the [web chat development overview](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-develop). The code in this folder is commented with links and references to the web chat APIs used.

This example demonstrates how to create your own launcher that can be used to open web chat.

- How to use the [**showLauncher**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-configuration#configurationobject) configuration property to hide the default launcher.
- How to use a [**view:change**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#viewchange) event handler to show or hide the custom launcher when the main web chat window is opened or closed.
- How to use the [**changeView**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#changeview) instance method to open the main window when the user clicks on the custom launcher.

## Running the Code

### Running the JavaScript Example

1. Open the [client/javascript/index.html](client/javascript/index.html) file in a web browser and click the custom launcher.

## Setting up your own assistant

This example is configured to use an existing assistant set up for common use by anyone running this example. If you want to set up your own assistant, you'll need to perform the following steps.

- Import the [actions.json](actions.json) file located in the repository for this example.
- Modify the `integrationID`, `region`, `serviceInstanceID` and `subscriptionID` (only for enterprise accounts) in the `watsonAssistantChatOptions` used in this example to match those in the web chat embed code for your assistant.