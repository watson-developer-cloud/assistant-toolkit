This tutorial will explain how to replace the default buttons rendered in web chat for options responses.

1. The first step is to create a `pre:receive` handler that will look for `option` responses and convert them into custom responses (`user_defined`).
```javascript
function preReceiveHandler(event) {
  const message = event.data;
  if (message.output.generic) {
    message.output.generic.forEach(messageItem => {
      if (messageItem.response_type === 'option') {
        messageItem.response_type = 'user_defined';
      }
    })
  }
}
```
2. Create a custom response handler that can render our custom buttons.
```javascript
function customResponseHandler(event) {
  const { message, element, fullMessage } = event.data;
  message.options.forEach((messageItem, index) => {
    const button = document.createElement('button');
    button.innerHTML = messageItem.label;
    button.classList.add('CardButton');
    button.addEventListener('click', () => onClick(messageItem, button, fullMessage, index));
    element.appendChild(button);
  });
}
```
3. You will need a click handler to respond to the user clicking on the button. In this case, the handler will send a message to the assistant using the label of the button.
```javascript
function onClick(messageItem, button, fullMessage, itemIndex) {
  webChatInstance.send({ input: { text: messageItem.label }});
  button.classList.add('CardButton--selected');
}
```

The steps above add the class `CardButton--selected` to the button when it is clicked so that it appears selected. You will probably want the button to continue to appear selected if the user reloads the page or switches to another page and web chat loads from session history. You can store history state to track this.

4. In the `onClick` function, add the following which will record a variable in session history that indicates which button was clicked.
```javascript
webChatInstance.updateHistoryUserDefined(fullMessage.id, { selectedIndex: itemIndex });
```

5. Then in your `customResponseHandler`, you can read this value to set the initial state of the button.
```javascript
if (fullMessage.history?.user_defined?.selectedIndex === index) {
  button.classList.add('CardButton--selected');
}
```

For complete working code see [the example in our GitHub repository](https://github.com/watson-developer-cloud/assistant-toolkit/tree/master/integrations/webchat/examples/custom-buttons). The example also demonstrates how you can make your buttons disabled after a user has sent a message so the user can't use the buttons to send a message out-of-order.