//
// Copyright (c) IBM Corporation and contributors. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//
// See the License for the specific language governing permissions and
// limitations under the License.
//

let webChatInstance;
let verbatimElement = null;
let verbatimText = null;
let nextButton = null;

/**
* This will convert responses that contain buttons into custom responses so we can render them using custom code.
*/
function preReceiveHandler(event) {
  const message = event.data;

  console.log("AgentAssistWebChat: preReceiveHandler");
  console.log("AgentAssistWebChat: " + message);

  if (typeof message.context.skills["actions skill"] !== 'undefined') {
    if (typeof message.context.skills["actions skill"].skill_variables.custom_command !== 'undefined' &&
      message.context.skills["actions skill"].skill_variables.custom_command === "read-verbatim") {

      message.output.generic.forEach(messageItem => {
        // Message items that contain buttons have a response type of "option".
        if (messageItem.response_type === 'text') {
          // This will turn this response into a custom response that will be rendered by our custom response handler.
          messageItem.response_type = 'user_defined';
        }
        else if (messageItem.response_type === 'option') {
          // This will turn this response into a custom response that will be rendered by our custom response handler.
          messageItem.response_type = 'user_defined';
        }
      });
    }
  }
}

/**
 * This function will be called when the user clicks on one of the buttons.
 */
function onClick(messageItem, button, fullMessage, itemIndex) {
  console.log("AgentAssistWebChat: onClick");

  // Set text to green.
  verbatimElement.style.color = "green";  // set to green

  button.disabled = true;
  button.style.backgroundColor = "#777777";
  button.style.color = "#ffffff";

  // Send a message to the assistant with the label of the button. You can also add "{ silent: true }" as the second
  // argument if you don't want this message to appear as a "sent" bubble from the user. See
  // https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods for more
  // information about the instance functions.
  webChatInstance.send(messageItem.label);

  verbatimElement = null;
  verbatimText = null;
}

/**
 * This function is called when a custom response should be rendered in web chat. This will use the data inside the
 * message to render custom content - in this case a simple weather card.
 */
function readVerbatimHandler(event) {

  const { message, element, fullMessage } = event.data;

  if (typeof message.text !== 'undefined') {
    //  Method that kicks off the read verbatim matching with the agent transcription
    startReadVerbatim(message.text, element);
  }
  else if (typeof message.options !== 'undefined') {
    // Here's where we create our custom buttons.
    message.options.forEach((messageItem, index) => {
      nextButton = document.createElement('button');
      nextButton.innerHTML = messageItem.label;

      nextButton.addEventListener('click', () => onClick(messageItem, nextButton, fullMessage, index));

      element.className = 'ibm-web-chat--default-styles';
      element.appendChild(nextButton);
    });
  }
}

/**
 * This handler is called each time a message is sent. This is used to clear the read-verbatim custom_command for the next turn.
 */
function preSendHandler(event) {
  console.log("AgentAssistWebChat: preSendHandler");
  console.log("AgentAssistWebChat: event.data:" + event.data);

  if (typeof event.data === 'undefined' ||
    typeof event.data.context === 'undefined' ||
    typeof event.data.context.skills === 'undefined') {
    return;
  }

  if (typeof event.data.context.skills["actions skill"] !== 'undefined') {
    if (typeof event.data.context.skills["actions skill"].skill_variables !== 'undefined' &&
      typeof event.data.context.skills["actions skill"].skill_variables.custom_command !== 'undefined') {
      // Remove the custom command if its not null
      event.data.context.skills["actions skill"].skill_variables.custom_command = null;
    }
  }
}

/**
 * This function will be called when the user clicks on one of the buttons.
 */
function startReadVerbatim(text, element) {

  //  Here we kick off the verbatim check by first turning the text to red and then looking for the agent response.
  verbatimElement = element;
  verbatimText = text.toLowerCase();

  console.log("AgentAssistWebChat: readVerbatimHandlerHandler");
  console.log("AgentAssistWebChat: text string = " + text);

  //  First display the text in red
  verbatimElement.style.color = "red";
  verbatimElement.innerHTML = `<div class="red-text">${text}</div>`;
  verbatimElement.innerHTML += "<br>";
}

window.watsonAssistantChatOptions = {
  openChatByDefault: true,
  disableSessionHistory: true,
  hideCloseButton: true,
  showLauncher: false,
  element: document.getElementById('agent-webchat-app'),
  integrationID: "e147ea66-03be-4165-9c3e-b4347acc4f2a", // The ID of this integration.
  region: "us-east", // The region your integration is hosted in.
  serviceInstanceID: "920dfdd7-fff9-4840-ac91-6cacfe609b37", // The ID of your service instance.

  agentUtteranceHandler: function (event) {
    console.log("AgentAssistWebChat: Agent Utterance Event: " + JSON.stringify(event));

    if (verbatimText != null){
      similarity = stringSimilarity.compareTwoStrings(verbatimText.toLowerCase(), event.utterance.toLowerCase());
      console.log("AgentAssistWebChat: Similarity = " + similarity);

      if (similarity > .8){
        nextButton.click();  
      }
    }
  },


  onLoad: function (instance) {
    // Save this instance so we can use it in our handlers.
    webChatInstance = instance;
    webChatInstance.restartConversation();

    // This method is used to handle the read-verbatim custom comment (which is currently the only custom command supported)
    instance.on({ type: 'customResponse', handler: readVerbatimHandler });

    // We will need to listen for the "pre:receive" event so we can convert the custom_command to user_defined responses.
    instance.on({ type: 'pre:receive', handler: preReceiveHandler });

    // We will need to listen for the "post:send" to always clear the custom_command for the next turn
    instance.on({ type: 'pre:send', handler: preSendHandler });

    instance.render();
  }
};

setTimeout(function () {
  const t = document.createElement('script');
  t.src = "https://web-chat.global.assistant.watson.appdomain.cloud/versions/" + (window.watsonAssistantChatOptions.clientVersion || 'latest') + "/WatsonAssistantChatEntry.js";
  document.head.appendChild(t);
});
