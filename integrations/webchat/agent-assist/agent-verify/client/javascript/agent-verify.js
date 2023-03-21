let webChatInstance;
let currentTextToVerify;
let currentTextElementToVerify;
let currentNextButton;

/**
 * Installs web chat.
 */
function installWebChat() {
  // Pull out the IDs required to load an instance of web chat from the URL.
  const urlParameters = new URLSearchParams(window.location.search);
  // const integrationID = urlParameters.get('integrationID');
  // const region = urlParameters.get('region');
  // const serviceInstanceID = urlParameters.get('serviceInstanceID');
  // const subscriptionID = urlParameters.get('subscriptionID');

  const integrationID = 'e147ea66-03be-4165-9c3e-b4347acc4f2a';
  const region = 'us-east';
  const serviceInstanceID = '920dfdd7-fff9-4840-ac91-6cacfe609b37';
  const subscriptionID = undefined;

  if (!integrationID || !region || !serviceInstanceID) {
    console.error('Cannot load web chat! The integrationID, region, and serviceInstanceID parameters are required.');
  }

  window.watsonAssistantChatOptions = {
    integrationID,
    region,
    serviceInstanceID,
    subscriptionID,

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
      instance.on({ type: 'customResponse', handler: customResponseHandler });

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

  currentTextToVerify = null;
  currentTextElementToVerify = null;
  currentNextButton = null;

  if (event.data.context?.skills?.['actions skill']?.skill_variables?.custom_command) {
    // Remove the custom command if it's set.
    event.data.context.skills['actions skill'].skill_variables.custom_command = null;
  }
}

/**
 * This will convert responses that contain buttons into custom responses so we can render them using custom code.
 */
function preReceiveHandler(event) {
  const message = event.data;

  if (message.context?.skills?.['actions skill']?.skill_variables?.custom_command === 'read-verbatim') {
    message.output.generic.forEach((messageItem) => {
      // Message items that contain buttons have a response type of "option".
      if (messageItem.response_type === 'text') {
        // This will turn this response into a custom response that will be rendered by our custom response handler.
        messageItem.response_type = 'user_defined';
      } else if (messageItem.response_type === 'option') {
        // This will turn this response into a custom response that will be rendered by our custom response handler.
        messageItem.response_type = 'user_defined';
      }
    });
  }
}

/**
 * This function is called when a custom response should be rendered in web chat. This will use the data inside the
 * message to render custom content - in this case a simple weather card.
 */
function customResponseHandler(event) {
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
 * This function handles when an utterance from a user is submitted.
 */
function onUserSaid(message) {
  addUserSaid(message);
}

/**
 * This function handles when an utterance from an agent is submitted.
 */
function onAgentSaid(text) {
  if (currentTextToVerify) {
    const verbatimText = currentTextToVerify.toLowerCase();
    const { stringSimilarity } = window;
    const similarity = stringSimilarity.compareTwoStrings(verbatimText, text.toLowerCase());

    if (similarity > 0.8) {
      markVerified();
    }
  }
}

/**
 * Creates a new message in the "user said" area.
 */
function addUserSaid(message) {
  const container = document.getElementById('AgentAssist__UserSaidContainer');
  const newElement = document.createElement('div');
  newElement.innerHTML = `
  <div class="AgentAssist__UserSaidMessage">
    <bx-btn class="AgentAssist__UserSaidCopyButton" kind="primary" icon-layout="" size="sm">
      <svg id="icon" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 32 32" slot="icon">
        <polygon points="6 18 7.41 19.41 15 11.83 15 30 17 30 17 11.83 24.59 19.41 26 18 16 8 6 18"/>
        <path d="M6,8V4H26V8h2V4a2,2,0,0,0-2-2H6A2,2,0,0,0,4,4V8Z"/>
        <rect fill="none" width="32" height="32"/>
      </svg>
    </bx-btn>
    <div class="AgentAssist__UserSaidText"></div>
  </div>
  `;

  // Set the textContent property to escape any HTML that might be in the message.
  const textElement = newElement.querySelector('.AgentAssist__UserSaidText');
  textElement.textContent = message;

  const copyButton = newElement.querySelector('.AgentAssist__UserSaidCopyButton');
  copyButton.addEventListener('click', () => {
    webChatInstance.elements.getMessageInput().setValue(message);
    webChatInstance.elements.getMessageInput().getHTMLElement().focus();
  });

  container.appendChild(newElement);
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
