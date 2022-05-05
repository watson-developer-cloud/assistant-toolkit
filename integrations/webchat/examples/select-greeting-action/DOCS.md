To change the greeting action used by the assistant follow these steps.

1. The first step is to determine what page the user is on. How this is done can vary a lot by application and you can customize this however is appropriate for your application but a simple mechanism is to check for a URL parameter.

```javascript
const page = new URLSearchParams(window.location.search).get('page');
```

2. To change the greeting action when web chat is opened, you'll need to add a handler that is called when web chat sends a message. Here we use `once` since we only need this to happen when the greeting message is sent which is the first message.
```javascript
instance.once({ type: 'pre:send', handler: preSendHandler});
```

3. When a message is sent you can check to see if it is the greeting message and if so customize the text based on the page the user is visiting.

```javascript
function preSendHandler(event) {
  if (event.data.input?.text === '') {
    if (page === 'cards') {
      event.data.input.text = 'Credit Cards';
    }
  }
}
```

For complete working code see [the example in our GitHub repository](https://github.com/watson-developer-cloud/assistant-toolkit/tree/master/integrations/webchat/examples/select-greeting-action).