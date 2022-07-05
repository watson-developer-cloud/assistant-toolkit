Note: This document is in a temporary location until this content is moved into the [main Watson Assistant documentation](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-overview) tutorials section.

To use a custom response to display a content carousel, follow the steps below.

1. Add a step to your action that returns a `user_defined` response. Use the JSON editor to send the following response. This embeds the carousel data inside the `user_defined` object, but you could also put the data into skill variables that can be accessed by web chat from the context object.
```json
{
  "generic": [
    {
      "user_defined": {
        ... // Data for what to display in the carousel.
      },
      "response_type": "user_defined"
    }
  ]
}
```
2. Register a listener for the custom response event. When web chat receives a `user_defined` response from the assistant, it will fire this event.
```javascript
instance.on({ type: 'customResponse', handler: customResponseHandler });
```
3. Create the handler that will display the information from the assistant.
```javascript
function customResponseHandler(event) {
  const { message, element, fullMessage } = event.data;
  // You could also look up variables from context using something like "fullMessage.context.skills['actions skill'].skill_variables"
  const messageData = message.user_defined;
  element.innerHTML = `<div>...the carousel will go here</div>`;
  // Use a library such as Swiper to render the slides and provide the interactive functionality for the
  // carousel. The linked example will show the details for this.
}
```

For complete working code see [the example in our GitHub repository](https://github.com/watson-developer-cloud/assistant-toolkit/tree/master/integrations/webchat/examples/content-carousel).