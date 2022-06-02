# Change launcher and home screen text for Watson Assistant web chat

This code is for extending the Watson Assistant web chat. If you are new to developing with web chat, please start with the [web chat development overview](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-develop). The code in this folder is commented with links and references to the web chat APIs used.

This example uses the current page that the user is on to customize the content that appears in the launcher as well as the home screen.

It demonstrates:

- How to determine what page the user is on and how to use a condition for customizing the launcher and home screen.
- How to use [**updateLauncherGreetingMessage**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#updateLauncherGreetingMessage) to change the text that is displayed in the launcher.
- How to use [**updateHomeScreenConfig**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#homescreen) to change the greeting text as well as the starter buttons that are displayed on the home screen.

**For a full walk through of how this code works, please visit [the tutorial page](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-overview) in the Watson Assistant documentation.**

## Running the Code

### Running the JavaScript Example

1. Open the [client/javascript/index.html](examples/change-launcher-and-home-screen-text/client/javascript/index.html) file in a web browser.
2. Wait for the launcher to appear to see the default launcher message.
3. Open web chat to see the default home screen.
4. Click the "here" link at the top of the page to switch to the "credit cards" page.
5. You'll see a customized launcher message that is specific to credit cards.
6. Open web chat again. You will see a different home screen that has topics specific to credit cards.

## Setting up your own assistant

This example is configured to use an existing assistant set up for common use by anyone running this example. If you want to set up your own assistant, you'll need to perform the following steps.

- Import the [actions.json](examples/change-launcher-and-home-screen-text/actions.json) file located in the repository for this example.
- Modify the `integrationID`, `region`, `serviceInstanceID` and `subscriptionID` (only for enterprise accounts) in the `watsonAssistantChatOptions` used in this example to match those in the web chat embed code for your assistant.