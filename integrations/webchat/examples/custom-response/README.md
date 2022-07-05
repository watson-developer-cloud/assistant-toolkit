# Download history for Watson Assistant web chat

**For a full walk through of how this code works, please visit [the tutorial page](DOCS.md) in the Watson Assistant documentation.**

This code is for extending the Watson Assistant web chat. If you are new to developing with web chat, please start with the [web chat development overview](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-develop). The code in this folder is commented with links and references to the web chat APIs used.

This example demonstrates how to use a custom response to render a custom UI widget of a card displaying information about the weather at a location provided by the user. The weather information is retrieved using an extension and the data is stored as action variables.

It demonstrates:

- How to use a custom extension to fetch weather data from a 3rd party service.
- How to assign data from the extension response to action variables.
- How to return a custom response (`user_defined`) from an action.
- How to use a [**customResponse**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#customresponse) event handler to display a custom weather card.
- How to use the [**updateHistoryUserDefined**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#updateHistoryUserDefined) instance function to record the state of a toggle button that appears on the custom response.
- How to access action variables in a custom response.

## Running the Code

### Running the JavaScript Example

- Open the [client/javascript/index.html](client/javascript/index.html) file in a web browser.
- Open web chat and click or type "What is the weather?".
- Enter the name of a city to get the weather for.
- Click the toggle to switch between celsius and fahrenheit. If you reload the page, web chat will remember the last state for each message.

## Setting up your own assistant

This example is configured to use an existing assistant set up for common use by anyone running this example. If you want to set up your own assistant, you'll need to perform the steps below.

- This example is based on the [MetaWeather custom extension starter kit](https://github.com/watson-developer-cloud/assistant-toolkit/tree/master/integrations/extensions/starter-kits/metaweather). You'll need to follow the instructions there for setting up a custom extension in your assistant.
- A modified version of the actions export has been checked into this repository that returns a custom response. You can find the export in [actions.json](actions.json) which  you will need to import into your assistant. You will need to modify the actions after being imported to enable the custom extension; instructions on how to do this can be found in the MetaWeather starter kit linked above.
- Modify the `integrationID`, `region`, `serviceInstanceID` and `subscriptionID` (only for enterprise accounts) in the `watsonAssistantChatOptions` used in this example to match those in the web chat embed code for your assistant.
