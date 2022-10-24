# Examples

This folder contains a set of examples that are intended to demonstrate how to use various features of Watson Assistant web chat. Below is a list of these examples, along with a brief explanation of each and the different features that each example demonstrates.

Most of the examples only require downloading an individual index.html file and then opening that file in your web browser but other examples have more pieces to them. To run all the examples in this repository, download a clone of the repository and follow the instructions in each example.

## [Change launcher and home screen text](change-launcher-and-home-screen-text/README.md)

This example uses the current page that the user is on to customize the content that appears in the launcher as well as the home screen.

It demonstrates:

- How to determine what page the user is on and how to use a condition for customizing the launcher and home screen.
- How to use [**updateLauncherGreetingMessage**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#updateLauncherGreetingMessage) to change the text that is displayed in the launcher.
- How to use [**updateHomeScreenConfig**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#homescreen) to change the greeting text as well as the starter buttons that are displayed on the home screen.

## [Content carousel for Watson Assistant web chat](content-carousel/README.md)

The example will display an interactive content carousel for data returned from Watson Assistant. The items in the carousel includes buttons that will send a message to the Assistant to provide additional information for each item.

It demonstrates:

- How to use a [**customResponse**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#customresponse) event handler to create the content carousel.
- How to use the [**send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#send) instance function to send a message to the assistant when a button is clicked.

## [Custom buttons](custom-buttons/README.md)

This example demonstrates how to use a custom response to change the appearance of the buttons that are displayed when the assistant returns and options response.

It demonstrates:

- How to use a [**pre:receive**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#prereceive) event handler to convert a built-in response to a custom response so it can be customized.
- How to use a [**customResponse**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#customresponse) event handler to create custom buttons.
- How to use the [**send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#send) instance function to send a message to the assistant when a button is clicked.
- How to use the [**updateHistoryUserDefined**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#updateHistoryUserDefined) instance function to record which button was clicked in session history so it will be remembered when web chat is reloaded.
- How to use data stored in session history to change how a custom response is displayed when web chat is reloaded.
- How to use the [Carbon Design System](https://v10.carbondesignsystem.com/) inside of web chat. The examples here cover both the vanilla JavaScript version (using web components) and the React version.

## [Custom elements](custom-element/README.md)

This example demonstrates how to use a custom element to change the size and position of the main web chat window.

It demonstrates:

- How to use a [**window:open**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#windowclose) event handler to show the main web chat window when web chat is opened.
- How to use a [**window:close**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#windowclose) event handler to hide the main web chat window when web chat is closed.
- How to use the [**element**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-configuration#configurationobject) configuration property to specify which custom element to use.
- How to apply custom animation to the entrance and exit of web chat.

## [Custom launcher](custom-launcher/README.md)

This example demonstrates how to create your own launcher that can be used to open web chat.

- How to use the [**showLauncher**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-configuration#configurationobject) configuration property to hide the default launcher.
- How to use a [**window:open**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#windowopen) event handler to listen for when web chat is opened so you can hide your custom launcher.
- How to use a [**window:close**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#windowclose) event handler to listen for when web chat is closed so you can show your custom launcher.

## [Custom responses](custom-response/README.md)

This example demonstrates how to use a custom response to render a custom UI widget that will ask the user for information in order to preform a currency exchange and display the results. It will use an extension to obtain the exchange rate for the calculation.

It demonstrates:

- How to use a custom extension to fetch currency data from a 3rd party service.
- How to assign data from the extension response to action variables.
- How to return a custom response (`user_defined`) from an action.
- How to use a [**customResponse**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#customresponse) event handler to display a custom card.
- How to use the [**send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#send) instance function to send a message to the assistant and include context variables with user input.
- How to access action variables in a custom response.

## [Deep linking](deep-linking/README.md)

This example will show you how to create an external link that can be shared in other apps such as email that, when clicked, will open web chat to a specific conversation topic.

It demonstrates:

- The [**Creating links to web chat**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-configuration#pageLinks) feature.

## [Download history](download-history/README.md)

This example demonstrates how to capture the conversation history for both Watson Assistant and for conversation with a human agent at a service desk and provide an option for the user to download them. It will add a custom menu option to the web chat header for the user to do this.

It demonstrates:

- How to use the [**receive**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#receive) and [**send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#send) event handlers to capture the messages that are sent to and received from the assistant.
- How to use the [**agent:receive**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#agentreceive) and [**agent:send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#agentsend) event handlers to capture the messages that are sent to and received from a human agent using a service desk integration.
- How to use a [**history:begin**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#historybegin) event handler to capture messages that were loaded from session history.
- How to use the [**updateCustomMenuOptions**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#updatecustommenuoptions) instance function to add a "Download history" option to the header.
- How to convert [**Watson Assistant messages**](https://cloud.ibm.com/apidocs/assistant/assistant-v2#message) into a text format that can be downloaded.
- How to use the built-in `Blob` and `URL` objects to construct a downloadable link.

## [Home screen custom element](home-screen-custom-element/README.md)

This example will demonstrate how to add custom content to the home screen by using a custom writeable element. It will add a number of custom buttons which, when clicked, will send a message to the assistant.

It demonstrates:

- How to use the [**writeableElements**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#writeableelements) instance property to add a custom element to the home screen below the starters.
- How to use the [**send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#send) instance function to send a message to the assistant.
 
## [Page interaction](page-interaction/README.md)

This example will show you how to use a custom response to display a button within web chat that can locate fields in the host page and fill in values to them.

It demonstrates:

- How to use the [**pre:receive**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#prereceive) event handler to add a custom response to message returned from the assistant so you can display a custom button.
- How to use a [**customResponse**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#customresponse) event handler to display a custom button which, when clicked, will interact with elements on the host page.
- How to use the [**ibm-web-chat--default-styles**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-render#helper_classes) CSS helper class to make a custom button look like a default web chat button.

## [Pre-chat and post-chat forms](pre-post-chat-forms/README.md)

This example demonstrates how to create pre-chat and post-chat forms with web chat. You can use a pre-chat form to gather data from your user before the conversation starts or to display a custom disclaimer or other required panel. You can use a post-chat form to gather feedback from your user on the quality of the conversation. This example will ask the user for their full name before beginning the conversation. And when the user closes the window, it will ask the user to provide feedback.

It demonstrates:

- How to use the [**pre:receive**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#prereceive) event handler to send the data gathered from the pre-chat form to the assistant.
- How to use a [**window:open**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#windowclose) event handler to show a pre-chat form before web chat is opened.
- How to use a [**window:pre:close**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#windowclose) event handler to show a customUserID form when web chat is closed.
- How to use the [**customPanels**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#custompanels) instance property to display custom panels.


## [Select greeting action](select-greeting-action/README.md)

This example demonstrates how to change the greeting request that is sent to the assistant when web chat is first opened so that it will trigger a specific action instead of the default greeting action.

It demonstrates:

- How to determine what page the user is on and how to use a condition for deciding what action to trigger.
- How to use a [**pre:send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#presend) event handler to customize the greeting request that is sent to the assistant.

## [Set context](set-context/README.md)

This example demonstrates how to set variables using context so those variables are available within an action.

It demonstrates:

- How to use a [**pre:send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#presend) event handler to set a context variable when a message is sent.
- How to use a variable in an action.

## [Sharing location](set-context/README.md)

This example demonstrates how to share the user's geographic location with the assistant and use that info to display an I-frame of a Google map for the nearest IBM office.

It demonstrates:

- How to use the [**receive**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#receive) event handlers to identify messages from the assistant that should trigger an action by the browser.
- How to use the [**send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#send) instance function to send messages to the assistant and how to set context/skill variables.
- How to use the browser's built-in geolocation functionality to obtain the user's current location.
- How to use a variable in an action.

## [Speech and text](speech-and-text/README.md)

This example demonstrates how to use the Watson Speech services to convert speech to text and text to speech. It will show how to get web chat to speak the text that it receives from the assistant and how to use a record button to listen to a user for speech that will be converted to text and sent to the assistant from web chat.

It demonstrates:

- How to use a NodeJS Express server for creating authorization tokens from your API keys that are required when making calls to Watson Speech.
- How to use the [**pre:receive**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#prereceive) and [**pre:send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#presend) event handlers to capture the messages that are sent to and received from the assistant so they can be sent to the text to speech service.
- How to use the [**Watson Speech JS SDK**](https://github.com/watson-developer-cloud/speech-javascript-sdk) To convert text to speech.
- How to use the [**writeableElements**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#writeableelements) instance property to add a custom button above the web chat input field that can be used to record speech.
- How to use the [**Watson Speech JS SDK**](https://github.com/watson-developer-cloud/speech-javascript-sdk) To convert the recorded speech to text.
- How to use the [**send**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#send) instance function to send the converted text to the assistant.

## [Using Carbon](using-carbon/README.md)

This example demonstrates how to use the Carbon Design System with web chat. There is both a vanilla JavaScript example using web components and a React example.

It demonstrates:

- How to use a [**customResponse**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#customresponse) event handler to render an accordion using Carbon.

## [Web chat security](web-chat-security/README.md)

This example demonstrates how to enable security with web chat. It will show how to create a JWT that can be used to securely authorize a webpage to access your web chat. It also demonstrates how to use a new JWT for the use case when a user logs into to a site in the middle of a Watson Assistant chat session as well as how to send the user's ID to Watson Assistant so it can be used in an action or an extension.

It demonstrates:

- How to generate public and private keys that are required for creating JWTs.
- How to use a NodeJS Express server for creating a JWT from your private key.
- How to use an [**identityTokenExpired**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-events#identityexpired) event handler to request a new JWT from your server.
- How to use the [**updateIdentityToken**](https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods#updateidentity) instance function to send update web chat with a new JWT when the user has changed.
- How to set the user ID used by web chat.
- How to encrypt a payload in a JWT so that secret information can be sent to the assistant.
- How to use a custom variable to change the user ID in the middle of a session.

