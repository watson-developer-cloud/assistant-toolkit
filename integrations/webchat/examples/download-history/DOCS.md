In order to provide the ability for your customers to download a history or transcript of their conversation with the assistant, you will need to perform the following steps. This will add a custom menu option to the header of the web chat.

1. Registers listeners for the `send` and `receive` events that can save the messages in a list so they can be downloaded later. This will also capture any pre-existing messages that were loaded from session history using the `history:begin` event.
```javascript
instance.on({ type: 'send', handler: saveMessage });
instance.on({ type: 'receive', handler: saveMessage });
instance.on({ type: 'history:begin', handler: saveHistory });
```
The handlers for these events will save the messages into a variable.
```javascript
const messages = [];

function saveMessage(event) {
  messages.push(event.data);
}

function saveHistory(event) {
  messages.push(...event.messages);
}
```
2. Create a function that can convert the messages saved from the previous step into the format you would like the customers to receive when they download the history. For example, this could be to convert the messages into a CSV file format that could be opened directly in a tool such as Excel. You will need to decide how to convert the different types of messages that are available from your assistant such as text, images, options (buttons and dropdowns), connect to agent, etc. See the linked example for complete code.
```javascript
function createDownload() {
  const content = [];
  messages.forEach(message => {
    content.push(...);
  });
  return content.join('\n');
}
```
3. Add some code that can trigger a file download of the converted messages. This will call the `createDownload` function above to generate the actual content to be downloaded.
```javascript
function doDownload() {
  const downloadContent = createDownload();
  const blob = new Blob([downloadContent], { type: 'text/csv' });
  const url = URL.createObjectURL(blob);

  const a = document.createElement('a');
  a.setAttribute('href', url);
  a.setAttribute('download', `Chat History.csv`);
  a.click();
}
```
4. Add a custom menu option to the web chat header to display the download option to users.
```javascript
instance.updateCustomMenuOptions('bot', [{ text: 'Download history', handler: doDownload }]);
```

For complete working code see [the example in our GitHub repository](https://github.com/watson-developer-cloud/assistant-toolkit/tree/master/integrations/webchat/examples/download-history).