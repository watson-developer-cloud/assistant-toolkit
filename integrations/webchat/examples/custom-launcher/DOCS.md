To display a custom launcher for web chat follow the steps below.

1. First you will need to create the element that will be your custom launcher. This can be anything you want on your webpage and does not necessarily have to be a button. But for this example we are just going to use a simple button.
```html
<button id="Launcher" type="button">Open web chat</button>
```

2. Add a click handler to your button that will open web chat.
```javascript
const launcherElement = document.getElementById('.Launcher');
launcherElement.addEventListener('click', openWindow);

function openWindow() {
  if (webChatInstance) {
    webChatInstance.openWindow();
  }
}
```

3. If you want to hide your launcher button while web chat is open you can listen for the `window:open` and `window:close` events to let you know when web chat has been opened or closed.
```javascript
function onLoad(instance) {
  webChatInstance = instance;
  instance.render();
  instance.on({ type: 'window:close', handler: closeHandler });
  instance.on({ type: 'window:open', handler: openHandler });
}

function closeHandler() {
  launcherElement.style.display = '';
}

function openHandler() {
  launcherElement.style.display = 'none';
}
```

For complete working code see [the example in our GitHub repository](https://github.com/watson-developer-cloud/assistant-toolkit/tree/master/integrations/webchat/examples/custom-launcher).