Note: This document is in a temporary location until this content is moved into the [main Watson Assistant documentation](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-overview) tutorials section.

To use a custom element to change the size or position of web chat, follow the steps below.

Web chat uses the custom element you provide as the host location for web chat. It places both the main web chat window and the launcher element in this one host (if you are not using a custom launcher). When you use a custom element, web chat assumes that you will take control of showing and hiding the element when it is opened or closed. This allows you the opportunity to apply additional effects to the process such as animating in or out the element. Because the built-in launcher is also contained inside the custom element, you will need to show and hide the main window only using the `WACWidget` class name.

1. The first step is to define the custom element where you want web chat to be displayed. You can do this in any way you want using any framework you want but a simple example is to use an empty element with an ID on it.
```html
<div id="WebChatContainer"></div>
```
2. You'll need to get a reference to the element so you can provide it to web chat. This can be done using whatever mechanism makes sense for the library you are using. You can save a reference if you had used the `document.createElement` function or you can look up the element from the DOM using using the document query functions. This example will look up the element using the ID we assigned to it.
```javascript
const customElement = document.querySelector('#WebChatContainer');
```
3. In the embed snippet for web chat, set the `element` property to the custom element.
```javascript
window.watsonAssistantChatOptions = {
  integrationID: "YOUR_INTEGRATION_ID",
  region: "YOUR_REGION",
  serviceInstanceID: "YOUR_SERVICE_INSTANCE_ID",

  // The important piece.
  element: customElement,

  onLoad: function(instance) {
    instance.render();
  }
};
```
4. Make sure that by default, the web chat window is hidden. It will be made visible when it is opened. Do this inside of the `onLoad` function and after `render` has been called. You will also need to add handlers to listen for the open and close events.
```javascript
function onLoad(instance) {
  instance.render();
  instance.on({ type: 'window:close', handler: closeHandler });
  instance.on({ type: 'window:open', handler: openHandler });
  customElement.querySelector('.WACWidget').style.display = 'none';
}
```
5. Create handlers that will hide and show web chat when it is opened and closed. The full example demonstrates how to do this with animations.
```javascript
function closeHandler() {
  const wacWidgetElement = customElement.querySelector('.WACWidget');
  wacWidgetElement.style.display = 'none';
}

function openHandler() {
  const wacWidgetElement = customElement.querySelector('.WACWidget');
  wacWidgetElement.style.display = '';
}
```

For complete working code see [the example in our GitHub repository](https://github.com/watson-developer-cloud/assistant-toolkit/tree/master/integrations/webchat/examples/custom-element).