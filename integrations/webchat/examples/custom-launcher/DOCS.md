To display a custom launcher for web chat follow the steps below.

1. First you will need to create the element that will be your custom launcher. This can be anything you want on your webpage and does not necessarily have to be a button. But for this example we are just going to use a simple button.
```html
<button id="Launcher" type="button">Open web chat</button>
```

2. Add a click handler to your button that will open the web chat main window.
```javascript
const launcherElement = document.getElementById('.Launcher');
launcherElement.addEventListener('click', openMainWindow);

function openMainWindow() {
  webChatInstance?.changeView('mainWindow');
}
```

3. If you want to hide your launcher button while web chat is open you can listen for the `view:change` event to let you know when web chat has been opened or closed.
```javascript
function onLoad(instance) {
  webChatInstance = instance;
  instance.render();
  instance.on({ type: 'view:change', handler: viewChangeHandler });
}

function viewChangeHandler(event) {
  if (event.newViewState.mainWindow) {
    launcherElement.style.display = 'none';
  } else {
    launcherElement.style.display = '';
  }
}
```
