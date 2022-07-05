# Content carousel for Watson Assistant web chat

**For a full walk through of how this code works, please visit [the tutorial page](DOCS.md) in the Watson Assistant documentation.**

This code is for extending the Watson Assistant web chat. If you are new to developing with web chat, please start with the [web chat development overview](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-develop). The code in this folder is commented with links and references to the web chat APIs used.

The example will display an interactive content carousel for data returned from Watson Assistant. The items in the carousel includes buttons that will send a message to the Assistant to provide additional information for each item.

This example uses the [Swiper](https://github.com/nolimits4web/Swiper) library for creating the UI elements of the carousel. It also uses the [parcel bundler](https://github.com/parcel-bundler/parcel) to compile the CSS and JavaScript.

It demonstrates:

- How to use a [**customResponse**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#customresponse) event handler to create the content carousel.
- How to use the [**send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#send) instance function to send a message to the assistant when a button is clicked.

## Running the Code

### Running the JavaScript Example

1. `cd client/javascript`
2. `npm install`
3. `npm run start`
4. Open a web browser to `localhost:3000`
5. Open web chat
6. Click or send the message "Show me a carousel"

## Setting up your own assistant

This example is configured to use an existing assistant set up for common use by anyone running this example. If you want to set up your own assistant, you'll need to perform the following steps.

- Import the [actions.json](actions.json) file located in the repository for this example.
- Modify the `integrationID`, `region`, `serviceInstanceID` and `subscriptionID` (only for enterprise accounts) in the `watsonAssistantChatOptions` used in this example to match those in the web chat embed code for your assistant.