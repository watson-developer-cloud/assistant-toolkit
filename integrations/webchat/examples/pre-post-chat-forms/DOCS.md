To display a custom form using a custom panel in web chat when the user opens or closes web chat, follow the steps below. When web chat is opened or closed it will fire an event that you can listen for. Inside a handler for these events, you can use the custom panels feature of web chat to display a panel with custom content for your users. If you want your custom panel to pause the opening or closing process of web chat (for example if you want to gather information from the user before triggering the greeting message in your action), then your handler will need to return a promise that is only resolved when the user completes your form and you close the custom panel.

These steps demonstrate how to create a pre-chat form but the same steps can be used for creating a post-chat form, you just need to listen to the `window:pre:close` event instead of the `window:open` event.

1. Register a listener that will call your handler when web chat is opened.
```javascript
instance.on({ type: 'window:open', handler: windowOpenHandler });
```
2. Create a handler function that will open a custom panel. This handler should make sure to return a Promise that can be resolved when the custom panel is closed. 
```javascript
function windowOpenHandler(event, instance) {
  return new Promise((resolve) => {
    // Save a reference to the resolve function so we can resolve this Promise later.
    promiseResolve = resolve;
    createOpenPanel(event, instance);

    const customPanel = instance.customPanels.getPanel();
    customPanel.open({ hidePanelHeader: true, disableAnimation: true });
  });
}
```
3. Create a function that creates the contents of the custom panel that you want displayed. Make sure to resolve the promise when the user closes the panel.
```javascript
function createOpenPanel(event, instance) {
  const customPanel = instance.customPanels.getPanel();
  const { hostElement } = customPanel;
  
  hostElement.innerHTML = `
    <div class="PreChatForm">
      ...
    </div>
  `;

  const okButton = hostElement.querySelector('#PreChatForm__OkButton')
  okButton.addEventListener('click', () => {
    customPanel.close();
    promiseResolve();
  });
}

```

For complete working code see [the example in our GitHub repository](https://github.com/watson-developer-cloud/assistant-toolkit/tree/master/integrations/webchat/examples/pre-post-chat-forms).