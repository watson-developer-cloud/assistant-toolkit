Note: This document is in a temporary location until this content is moved into the [main IBM watsonx Assistant documentation](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-overview) tutorials section.

For complete working code see [the example in our GitHub repository](https://github.com/watson-developer-cloud/assistant-toolkit/tree/master/integrations/webchat/examples/agents-unavailable).

To take custom action, such as filing a ticket in an external system, when no live agents are available, follow the steps below.

1. Register a listener for the `agent:areAnyAgentsOnline` event. This event is fired after every time it asks a service desk integration whether any agents are online/available. The event contains the result of that question.
```javascript
instance.on({ type: 'agent:areAnyAgentsOnline', handler: agentsOnlineHandler });
```

2. Create a handler that will send a silent message to the assistant that will trigger an action to start the process for creating a ticket. The details of that process can be whatever you need and can create inside your actions. You can use an extension if you want to file a ticket in an external system.
```javascript
function agentsOnlineHandler(event, instance) {
  if (event.areAnyAgentsOnline === 'offline') {
    instance.send('NO_AGENTS_FILE_TICKET', { silent: true });
  }
}
```
