# Examples

This folder contains a set of examples that are intended to demonstrate how to use various features of Watson Assistant web chat. Below is a list of these examples, along with a brief explanation of each and the different features that each example demonstrates.

## Change launcher and home screen text

This example uses the current page that the user is on to customize the content that appears in the launcher as well as the home screen.

It demonstrates:

- How to determine what page the user is on and how to use a condition for customizing the launcher and home screen.
- How to use [**updateLauncherGreetingMessage**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#updateLauncherGreetingMessage) to change the text that is displayed in the launcher.
- How to use [**updateHomeScreenConfig**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#homescreen) to change the greeting text as well as the starter buttons that are displayed on the home screen.

## Custom buttons

This example demonstrates how to use a custom response to change the appearance of the buttons that are displayed when the assistant returns and options response.

It demonstrates:

- How to use a [**pre:receive**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#prereceive) event handler to convert a built-in response to a custom response so it can be customized.
- How to use a [**customResponse**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#customresponse) event handler to create custom buttons.
- How to use the [**send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#send) instance function to send a message to the assistant when a button is clicked.
- How to use the [**updateHistoryUserDefined**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#updateHistoryUserDefined) instance function to record which button was clicked in session history so it will be remembered when web chat is reloaded.
- How to use data stored in session history to change how a custom response is displayed when web chat is reloaded.

## Custom responses

This example demonstrates how to use a custom response to render a custom UI widget of a card displaying information about the weather at a location provided by the user. The weather information is retrieved using an extension and the data is stored as action variables.

It demonstrates:

- How to use a custom extension to fetch weather data from a 3rd party service.
- How to assign data from the extension response to action variables.
- How to return a custom response (`user_defined`) from an action.
- How to use a [**customResponse**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#customresponse) event handler to display a custom weather card.
- How to access action variables in a custom response.

## Download history

This example demonstrates how to capture the conversation history and provide an option for the user to download it. It will add a custom menu option to the web chat header for the user to do this.

It demonstrates:

- How to use the [**pre:receive**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#prereceive) and [**pre:send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#presend) event handlers to capture the messages that are sent to and received from the assistant. 
- How to use a [**history:begin**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#historybegin) event handler to capture messages that were loaded from session history.
- How to use the [**updateCustomMenuOptions**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#updatecustommenuoptions) instance function to add a "Download history" option to the header.
- How to convert [**Watson Assistant messages**](https://cloud.ibm.com/apidocs/assistant/assistant-v2#message) into a text format that can be downloaded.
- How to use the built-in `Blob` and `URL` objects to construct a downloadable link.

## Select greeting action

This example demonstrates how to change the greeting request that is sent to the assistant when web chat is first opened so that it will trigger a specific action instead of the default greeting action.

It demonstrates:

- How to determine what page the user is on and how to use a condition for deciding what action to trigger.
- How to use a [**pre:send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#presend) event handler to customize the greeting request that is sent to the assistant.

## Set context

This example demonstrates how to set variables using context so those variables are available within an action.

It demonstrates:

- How to use a [**pre:send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#presend) event handler to set a context variable when a message is sent.
- How to use a variable in an action.

## Speech and text

This example demonstrates how to use the Watson Speech services to convert speech to text and text to speech. It will show how to get web chat to speak the text that it receives from the assistant and how to use a record button to listen to a user for speech that will be converted to text and sent to the assistant from web chat.

It demonstrates:

- How to use a NodeJS Express server for creating authorization tokens from your API keys that are required when making calls to Watson Speech.
- How to use the [**pre:receive**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#prereceive) and [**pre:send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#presend) event handlers to capture the messages that are sent to and received from the assistant so they can be sent to the text to speech service.
- How to use the [**Watson Speech JS SDK**](https://github.com/watson-developer-cloud/speech-javascript-sdk) To convert text to speech.
- How to use the [**writeableElements**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#writeableelements) instance property to add a custom button above the web chat input field that can be used to record speech.
- How to use the [**Watson Speech JS SDK**](https://github.com/watson-developer-cloud/speech-javascript-sdk) To convert the recorded speech to text.
- How to use the [**send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#send) instance function to send the converted text to the assistant.

## Web chat security

This example demonstrates how to enable security with web chat. It will show how to create a JWT that can be used to securely authorize a webpage to access your web chat.

It demonstrates:

- How to generate public and private keys that are required for creating JWTs.
- How to use a NodeJS Express server for creating a JWT from your private key.
- How to use an [**identityTokenExpired**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#identityexpired) event handler to request a new JWT from your server.
- How to set the user ID used by web chat.
- How to encrypt a payload in a JWT so that secret information can be sent to the assistant.