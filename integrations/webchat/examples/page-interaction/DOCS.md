Note: This document is in a temporary location until this content is moved into the [main Watson Assistant documentation](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-overview) tutorials section.

To interact with the host page using a custom response or a pre-receive event, follow the steps below.

1. Register a listener for the custom response and pre-receive events. When web chat receives a `user_defined` response from the assistant, it will fire the custom response event.

```javascript
instance.on({type: 'customResponse', handler: customResponseHandler});
instance.on({type: 'receive', handler: receiveHandler});
```
2. Create the handler that display the custom button and create the click handler for it.
```javascript
function customResponseHandler(event) {
  const { element } = event.data;

  const button = document.createElement('button');
  button.type = 'button';
  button.innerHTML = 'Fill in account number';
  button.addEventListener('click', () => {
    // Look for the account number element in the document and fill in the account number.
    document.querySelector('#account-number').value = '1234567';
  });

  element.appendChild(button);
}
```
3. The greeting action includes a `user_defined` response type (a custom response) item that is detected by the code above for displaying the "Fill in account number" button. This can be added using the JSON editor on your action and adding the following item to the `generic` array.
```json
{
  "user_defined": {
    "user_defined_type": "fill_account_number"
  },
  "response_type": "user_defined"
}
```
4. The "fill phone number" action uses a `user_defined` payload object that web chat can see inside the `receive` handler. You can use the JSON editor to add the `user_defined` object to your existing `text` response item.
```json
    {
      "values": [
        {
          "text_expression": {
            "concat": [
              {
                "scalar": "I have looked up your phone number but this time, I have filled it in for you without asking."
              }
            ]
          }
        }
      ],
      "user_defined": {
        "user_defined_type": "fill_phone_number"
      },
      "response_type": "text",
      "selection_policy": "sequential"
    }
```

For complete working code see [the example in our GitHub repository](https://github.com/watson-developer-cloud/assistant-toolkit/tree/master/integrations/webchat/examples/page-interaction).