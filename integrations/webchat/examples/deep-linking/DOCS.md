Note: This document is in a temporary location until this content is moved into the [main IBM watsonx Assistant documentation](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-overview) tutorials section.

To create a link to a page that will open web chat to a specific topic, follow these steps.

1. Define the messages you want web chat to send that will trigger the specific topics configured in your assistant and give each one a unique identifier. This identifier is what will appear in the link you give to your customers. It's a good idea to avoid IDs that are obvious for what they do to avoid customers trying to change them.

```javascript
window.watsonAssistantChatOptions = {
  integrationID: "YOUR_INTEGRATION_ID",
  region: "YOUR_REGION",
  serviceInstanceID: "YOUR_SERVICE_INSTANCE_ID",
  pageLinkConfig: {
    linkIDs: {
      'u35': {
        text: 'I would like to update my account'
      },
      'r23': {
        text: 'I need to reset my password'
      },
    }
  },
  onLoad: async (instance) => {
    await instance.render();
  }
};
```

2. Construct a URL that links to the web page where web chat will be loaded. Add a `wa_lid` parameter to that URL along with the ID of the topic you want that link to trigger. Below are a few examples of what such a URL might look like. The `wa_lid=u35` is the important part.

```
https://www.example.com?wa_lid=u35
```
```
https://www.example.com/help/faq.html?topic=general&wa_lid=u35
```

For complete working code see [the example in our GitHub repository](https://github.com/watson-developer-cloud/assistant-toolkit/tree/master/integrations/webchat/examples/deep-linking).