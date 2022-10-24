Note: This document is in a temporary location until this content is moved into the [main Watson Assistant documentation](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-overview) tutorials section.

To display a custom form using a custom panel in web chat when the user opens or closes web chat, follow the steps below. When web chat is opened or closed it will fire an event that you can listen for. Inside a handler for these events, you can use the custom panels feature of web chat to display a panel with custom content for your users. If you want your custom panel to pause the opening or closing process of web chat (for example if you want to gather information from the user before triggering the greeting message in your action), then your handler will need to return a promise that is only resolved when the user completes your form and you close the custom panel.

These steps demonstrate how to create a pre-chat form but the same steps can be used for creating a post-chat form, you just need to listen to the `window:pre:close` event instead of the `window:open` event.

# JavaScript

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

# React

For the React version, use the `WebChatContainer` component from the `assistant-web-chat-react` library that renders web chat for you and provides access to the web chat instance object that you will need for displaying custom panels.

This will render a container for the pre-chat form once the web chat instance is ready.

```javascript
function App() {
  const [instance, setInstance] = useState(null);
  return (
    <>
      <WebChatContainer config={config} onBeforeRender={setInstance} />
      {instance && <FormContainer instance={instance} />}
    </>
  );
}
```

Then create a container that will manage the custom panel. This container will listen for window open events that will cause the custom panel to open and display the pre-chat form.

```javascript
function FormContainer({ instance }) {
  // We store the Promise that is created when the custom panel is opened. This Promise should be resolved whent the
  // panel is closed.
  const promiseResolveRef = useRef();
  const [isPanelOpen, setIsPanelOpen] = useState(false);

  useEffect(() => {
    instance.on({ type: 'window:open', handler: windowOpenHandler });
    return () => {
      instance.off({ type: 'window:open', handler: windowOpenHandler });
    };
  }, [instance]);

  const changePanelOpen = useCallback(
    (newPanelOpen) => {
      setIsPanelOpen(newPanelOpen);
      const customPanel = instance.customPanels.getPanel();
      if (newPanelOpen) {
        customPanel.open({ hidePanelHeader: true, disableAnimation: true });
      } else {
        if (promiseResolveRef.current) {
          promiseResolveRef.current();
          promiseResolveRef.current = null;
        }
        customPanel.close();
      }
    },
    [instance],
  );

  useEffect(() => {
    const customPanel = instance.customPanels.getPanel();
    if (isPanelOpen) {
      customPanel.open({ hidePanelHeader: true, disableAnimation: true });
    } else {
      // If there is a promise waiting for the custom panel to close, then resolve it now.
      if (promiseResolveRef.current) {
        promiseResolveRef.current();
        promiseResolveRef.current = null;
      }
      customPanel.close();
    }
  }, [isPanelOpen, instance]);

  function windowOpenHandler() {
    return new Promise((resolve) => {
      promiseResolveRef.current = resolve;
      changePanelOpen('pre-chat');
    });
  }

  return (
    <CustomPanelPortal hostElement={instance.customPanels.getPanel().hostElement}>
      {isPanelOpen && <PreChatForm changePanelOpen={changePanelOpen} />}
    </CustomPanelPortal>
  );
}

function CustomPanelPortal({ hostElement, children }) {
  return ReactDOM.createPortal(children, hostElement);
}
```

Refer to the linked example to see more details about how to create the actual pre-chat form as well as how to take the data from the form and pass it to the assistant when the user sends a message.

For complete working code see [the example in our GitHub repository](https://github.com/watson-developer-cloud/assistant-toolkit/tree/master/integrations/webchat/examples/pre-post-chat-forms).