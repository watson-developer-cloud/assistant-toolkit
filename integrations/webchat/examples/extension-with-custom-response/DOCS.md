To display information from an extension in a custom response, follow these steps.

1. Set up the extension using these instructions: [Building a custom extensions](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-build-custom-extension)
2. Assign the data from the extension to a session variable. You can find documentation for this at [Accessing extension response data](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-call-extension#extension-access-response)
3. Add a step to your action that returns a `user_defined` response. Use the JSON editor to send the following response.
```json
{
  "generic": [
    {
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

TODO: Fill out this file with instructions that can be copied to the main Watson Assistant docs. Once that has been done, then delete this file.