To interact with the host page using a custom response, follow the steps below.

1. Register a listener for the custom response event. When web chat receives a `user_defined` response from the assistant, it will fire this event.
```javascript
instance.on({ type: 'customResponse', handler: customResponseHandler });
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

For complete working code see [the example in our GitHub repository](https://github.com/watson-developer-cloud/assistant-toolkit/tree/master/integrations/webchat/examples/page-interaction).