Note: This document is in a temporary location until this content is moved into the [main Watson Assistant documentation](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-overview) tutorials section.

This will cover how to extend Watson Assistant web chat to be able to use the Watson Speech service to convert speech to text and text to speech.

These steps assume that you have already created a Watson Speech service instance.

This example requires access to the [Watson Speech SDK](https://github.com/watson-developer-cloud/speech-javascript-sdk). You can also read more about [Watson Text to Speech](https://www.ibm.com/cloud/watson-text-to-speech) and [Watson Speech to Text](https://www.ibm.com/cloud/watson-speech-to-text).

If you are using a simple HTML file, you can download the SDK from the link above and include it using a `<script>` tag.
```html
<script src="watson-speech.min.js"></script>
```
Or if you are using `npm`, you can import it using
```javascript
import WatsonSpeech from 'watson-speech'
```

You will need to add code for getting an authorization token to use with the speech services. See the linked example for details on how to do that.

For the first part of this tutorial, we will explain how to enable text to speech for web chat.

1. Create a function that can convert the received message into the text we wish to speak. This example only demonstrates how to handle text responses but it can be modified to handle other responses as well.
```javascript
function generateTextFromMessage(message) {
  return message.output.generic.map(message => message.text).join(' ');
}
```
2. Create a handler that can listen for messages received from Watson Assistant by web chat.
```javascript
instance.on({ type: 'receive', handler: handleMessageReceive })
```
3. In the receive handler, convert the message to text and then send the text to the text to speech service. When the service has converted to speech, it will automatically play the speech from the browser.
```javascript
function handleMessageReceive(event) {
  const synthText = generateTextFromMessage(event.data);
  const audio = WatsonSpeech.TextToSpeech.synthesize({
    text: synthText,
    accessToken: ttsAuthToken,
  });
}
```

For the second part, we will explain how to use speech to text.

1. Create a function that will send the final text to Watson Assistant after it is converted to speech.
```javascript
function sendTextToAssistant(text) {
  const sendObject = { input: { text } };
  webChatInstance.send(sendObject)
}
```
2. Create a function that will start listening for speech and send that speech to the Watson Speech service.
```javascript
function onStartRecord() {
  const stream = WatsonSpeech.SpeechToText.recognizeMicrophone({
    accessToken: sttAuthToken,
    model: 'en-US_BroadbandModel',
    objectMode: true,
  });
  stream.on('data', function (data) {
    if (data.results[0] && data.results[0].final) {
      stream.stop();
      currentStream = null;
      setButtonState(false);
      const text = data.results[0].alternatives.map(message => message.transcript).join(' ');
      sendTextToAssistant(text);
    }
  });
  currentStream = stream;
}
```
3. Add functions that can respond to button clicks.
```javascript
function onStopRecord() {
  if (currentStream) {
    currentStream.stop();
  }
  currentStream = null;
}

function setButtonState(localIsRecording) {
  isRecording = localIsRecording;
  const label = isRecording ? 'Stop' : 'Start';
  document.querySelector('.RecordButton').innerHTML = `${label} recording`;
}

function onButtonClick() {
  setButtonState(!isRecording);
  if (isRecording) {
    onStartRecord();
  } else {
    onStopRecord();
  }
}
```
4. Use the writeable element feature of web chat to add a start/stop record button above the input field.
```javascript
function addRecordButton(instance) {
  const button = document.createElement('button');
  button.classList.add('RecordButton');
  button.innerHTML = 'Start recording';
  button.addEventListener('click', onButtonClick);
  instance.writeableElements.beforeInputElement.appendChild(button);
}
```

For complete working code see [the example in our GitHub repository](https://github.com/watson-developer-cloud/assistant-toolkit/tree/master/integrations/webchat/examples/speech-and-text).