# Download history for Watson Assistant web chat

**For a full walk through of how this code works, please visit [the tutorial page](DOCS.md) in the Watson Assistant documentation.**

This code is for extending the Watson Assistant web chat. If you are new to developing with web chat, please start with the [web chat development overview](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-develop). The code in this folder is commented with links and references to the web chat APIs used.

This example demonstrates how to use a custom response to render a custom UI widget that will ask the user for information in order to preform a currency exchange and display the results. It will use an extension to obtain the exchange rate for the calculation.

It demonstrates:

- How to use a custom extension to fetch currency data from a 3rd party service.
- How to assign data from the extension response to action variables.
- How to return a custom response (`user_defined`) from an action.
- How to use a [**customResponse**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#customresponse) event handler to display a custom card.
- How to use the [**send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#send) instance function to send a message to the assistant and include context variables with user input.
- How to access action variables in a custom response.

## Running the Code

### Running the JavaScript Example

- Open the [client/javascript/index.html](client/javascript/index.html) file in a web browser.
- Open web chat and click or type "Exchange for Polish złoty".
- Select a currency and amount to exchange and click the "Exchange" button.

## Setting up your own assistant

This example is configured to use an existing assistant set up for common use by anyone running this example. If you want to set up your own assistant, you'll need to perform the steps below.

- The [OpenAPI specification](nbp.openapi.json) for the National Bank of Poland can be used to set up the custom extension in your assistant. For our full docs on how to create and customize extensions, visit [Building a Custom Extension](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-build-custom-extension).
- An actions export has been checked into this repository that returns the custom responses and uses the extension. You can find the export in [actions.json](actions.json) which  you will need to import into your assistant. You will need to modify the actions after being imported to enable the custom extension.
- Modify the `integrationID`, `region`, `serviceInstanceID` and `subscriptionID` (only for enterprise accounts) in the `watsonAssistantChatOptions` used in this example to match those in the web chat embed code for your assistant.
