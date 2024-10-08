let webChatInstance;
let currentTextToVerify;
let currentTextElementToVerify;
let currentNextButton;
let currentAgentUtterance;
let currentUserElement;

/**
 * Installs web chat.
 */
function installWebChat() {
  // This is the standard web chat configuration object. You can modify these values with the embed code for your
  // own assistant if you wish to try this example with your assistant. You can find the documentation for this at
  // https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-configuration#configurationobject.
  const integrationID = 'e147ea66-03be-4165-9c3e-b4347acc4f2a';
  const region = 'us-east';
  const serviceInstanceID = '920dfdd7-fff9-4840-ac91-6cacfe609b37';

  if (!integrationID || !region || !serviceInstanceID) {
    console.error('Cannot load web chat! The integrationID, region, and serviceInstanceID parameters are required.');
  }

  window.watsonAssistantChatOptions = {
    integrationID,
    region,
    serviceInstanceID,

    openChatByDefault: true,
    disableSessionHistory: true,
    hideCloseButton: true,
    showLauncher: false,
    element: document.getElementById('AgentAssist__WebChatContainer'),

    debug: true,

    onLoad: (instance) => {
      // Save this instance so we can use it in our handlers.
      webChatInstance = instance;

      // This method is used to handle the read-verbatim custom comment (which is currently the only custom command
      // supported).
      instance.on({ type: 'userDefinedResponse', handler: userDefinedResponseHandler });

      // We will need to listen for the "pre:receive" event so we can convert the custom_command to user_defined
      // responses.
      instance.on({ type: 'pre:receive', handler: preReceiveHandler });

      // We will need to listen for the "post:send" to always clear the custom_command for the next turn.
      instance.on({ type: 'pre:send', handler: preSendHandler });

      instance.render();
    },
  };

  window.watsonAssistantChat = {
    agentAssist: {
      onUserSaid,
      onAgentSaid,
    },
  };

  setTimeout(function () {
    const t = document.createElement('script');
    t.src = `https://web-chat.global.assistant.watson.appdomain.cloud/versions/latest/WatsonAssistantChatEntry.js`;
    document.head.appendChild(t);
  });
}

/**
 * This handler is called each time a message is sent. This is used to clear the read-verbatim custom_command for
 * the next turn.
 */
function preSendHandler(event) {
  if (currentNextButton) {
    // Whenever a message is sent, disable any current next button that we previously displayed.
    currentNextButton.disabled = true;
  }

  currentTextToVerify = '';
  currentTextElementToVerify = null;
  currentNextButton = null;
  currentAgentUtterance = '';
  currentUserElement = null;

  if (event.data.context?.skills?.['actions skill']?.skill_variables?.custom_command) {
    // Remove the custom command if it's set.
    event.data.context.skills['actions skill'].skill_variables.custom_command = null;
  }
}

/**
 * This will convert responses that contain buttons into user defined responses so we can render them using custom code.
 */
function preReceiveHandler(event) {
  const message = event.data;

  if (message.context?.skills?.['actions skill']?.skill_variables?.custom_command === 'read-verbatim') {
    message.output.generic.forEach((messageItem) => {
      // Message items that contain buttons have a response type of "option".
      if (messageItem.response_type === 'text') {
        // This will turn this response into a user defined response that will be rendered by our user defined response handler.
        messageItem.response_type = 'user_defined';
      } else if (messageItem.response_type === 'option') {
        // This will turn this response into a user defined response that will be rendered by our user defined response handler.
        messageItem.response_type = 'user_defined';
      }
    });
  }
}

/**
 * This function is called when a user defined response should be rendered in web chat. This will use the data inside the
 * message to render custom content - in this case a simple weather card.
 */
function userDefinedResponseHandler(event) {
  const { message, element } = event.data;

  if (message.text) {
    // Create the custom element that will hold that text that is to be verified.
    const { text } = message;

    element.innerHTML = `<div class="AgentAssist__TextToVerify AgentAssist__TextToVerify--notVerified">${text}</div><br/>`;

    currentTextToVerify = text;

    currentTextElementToVerify = element.querySelector('.AgentAssist__TextToVerify');
  } else if (message.options) {
    // Here's where we create our custom buttons.
    message.options.forEach((messageItem) => {
      const nextButton = document.createElement('button');
      nextButton.innerHTML = messageItem.label;
      nextButton.className = 'AgentAssist__NextButton';

      nextButton.addEventListener('click', () => markVerified());

      element.className = 'ibm-web-chat--default-styles';
      element.appendChild(nextButton);

      currentNextButton = nextButton;
    });
  }
}

/**
 * This will mark the last message as having been verified.
 */
function markVerified() {
  if (!currentTextElementToVerify) {
    return;
  }

  // Swap the classes that indicate the text has been verified.
  currentTextElementToVerify.classList.remove('AgentAssist__TextToVerify--notVerified');
  currentTextElementToVerify.classList.add('AgentAssist__TextToVerify--verified');

  // Send a message to the assistant with the label of the button.
  webChatInstance.send(currentNextButton?.innerText || 'Next');
}

/**
 * This function handles when an utterance from a user is submitted. This will continue to append this content to
 * the existing user message until an utterance from the agent is encountered.
 */
function onUserSaid(text) {
  console.log(`onUserSaid:${text}`);

  // Switching from the agent to the user so clear the agent's text.
  currentAgentUtterance = '';

  addUserSaid(text);
}

/**
 * This function handles when an utterance from an agent is submitted. This will append this content to the current
 * agent says text until an utterance from the user is received. The current combined text from the agent will be
 * checked against the most recent text to verify.
 */
function onAgentSaid(text) {
  // Switching from the user to the agent so clear the current user element.
  currentUserElement = null;

  if (currentTextToVerify) {
    currentAgentUtterance = `${currentAgentUtterance} ${text.toLowerCase().trim()}`;
    const verbatimText = currentTextToVerify.toLowerCase();
    const { stringSimilarity } = window;

    console.log(`onAgentSaid: currentAgentUtterance: ${currentAgentUtterance}`);
    console.log(`onAgentSaid: verbatimText: ${verbatimText}`);

    const similarity = stringSimilarity.compareTwoStrings(verbatimText, currentAgentUtterance);
    console.log(`onAgentSaid: similarity: ${similarity}`);

    if (similarity > 0.7) {
      markVerified();
    }
  }
}

/**
 * Creates a new message in the "user said" area.
 */
function addUserSaid(text) {
  const container = document.getElementById('AgentAssist__UserSaidContainer');

  if (!currentUserElement) {
    // If we don't have a current user element, then create a new one and add it to the container. Otherwise, we'll
    // just append the new text to the existing one.
    const localUserElement = document.createElement('div');
    localUserElement.innerHTML = `
    <div class="AgentAssist__UserSaidMessage">
      <cds-btn class="AgentAssist__UserSaidCopyButton" kind="primary" icon-layout="" size="sm">
        <svg id="icon" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 32 32" slot="icon">
          <polygon points="6 18 7.41 19.41 15 11.83 15 30 17 30 17 11.83 24.59 19.41 26 18 16 8 6 18"/>
          <path d="M6,8V4H26V8h2V4a2,2,0,0,0-2-2H6A2,2,0,0,0,4,4V8Z"/>
          <rect fill="none" width="32" height="32"/>
        </svg>
      </cds-btn>
      <div class="AgentAssist__UserSaidText"></div>
    </div>
    `;

    const copyButton = localUserElement.querySelector('.AgentAssist__UserSaidCopyButton');
    copyButton.addEventListener('click', () => {
      const textElement = localUserElement.querySelector('.AgentAssist__UserSaidText');
      webChatInstance.elements.getMessageInput().setValue(textElement.textContent.trim());
      webChatInstance.elements.getMessageInput().getHTMLElement().focus();
    });

    container.appendChild(localUserElement);
    currentUserElement = localUserElement;
  }

  // Set the textContent property to escape any HTML that might be in the message.
  const textElement = currentUserElement.querySelector('.AgentAssist__UserSaidText');
  const currentContent = textElement.textContent;
  textElement.textContent = `${currentContent} ${text.trim()}`;

  console.log(`addUserSaid: text: ${textElement.textContent}`);

  setTimeout(() => {
    container.scrollTop = container.scrollHeight;
  });
}

/**
 * Sets up the agent assist area that contains both web chat and the "user said" area.
 */
function setupArea() {
  const container = document.getElementById('AgentAssistContainer');
  container.innerHTML = `
    <div id="AgentAssist__WebChatContainer"></div>
    <div id="AgentAssist__UserSaidContainer"></div>
  </div>
  `;
}

setupArea();
installWebChat();
