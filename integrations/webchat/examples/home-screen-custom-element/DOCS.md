Note: This document is in a temporary location until this content is moved into the [main IBM watsonx Assistant documentation](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-overview) tutorials section.

To add custom UI elements to the home screen, follow these steps.

1. Assign custom element to the writeable element "homeScreenAfterStartersElement"
```javascript
  instance.writeableElements.homeScreenAfterStartersElement.innerHTML = 
    '<div class="MyCustomClass">This is my custom element</div>';
```

For complete working code see [the example in our GitHub repository](https://github.com/watson-developer-cloud/assistant-toolkit/tree/master/integrations/webchat/examples/home-screen-custom-element).