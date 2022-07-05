Note: This document is in a temporary location until this content is moved into the [main Watson Assistant documentation](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-overview) tutorials section.

To display information from an extension in a custom response, follow these steps.

TODO: Should steps 1 and 2 (which are about using an extension to assign action variables) be separated as a seperate tutorial from steps 3+ (which are about using a custom response to show action variables)?

1. Set up the extension using these instructions: [Building a custom extensions](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-build-custom-extension)
2. Assign the data from the extension to a session variable. You can find documentation for this at [Accessing extension response data](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-call-extension#extension-access-response)
3. Add a step to your action that returns a `user_defined` response. Use the JSON editor to send the following response.
```json
{
  "generic": [
    {
      "user_defined": {},
      "response_type": "user_defined"
    }
  ]
}
```
4. Register a listener for the custom response event. When web chat receives a `user_defined` response from the assistant, it will fire this event.
```javascript
instance.on({ type: 'customResponse', handler: customResponseHandler });
```
5. Create the handler that will display the information from the extension.
```javascript
function customResponseHandler(event) {
  const { fullMessage, element } = event.data;
  const variables = fullMessage.context.skills['actions skill'].skill_variables;

  element.innerHTML = `<div>The temperature is ${variables.temperature}</div>`;
}
```

6. To save the state of a custom response in session history as the user interacts with it, call the `updateHistoryUserDefined` instance function. Note there is a limit of 20 calls per message for this function.
```javascript
instance.updateHistoryUserDefined(fullMessage.id, { selectedTemperature });
```

For complete working code see [the example in our GitHub repository](https://github.com/watson-developer-cloud/assistant-toolkit/tree/master/integrations/webchat/examples/custom-response).