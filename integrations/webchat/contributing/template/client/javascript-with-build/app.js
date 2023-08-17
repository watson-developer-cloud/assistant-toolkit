import './styles.scss';

/**
 * Injects a web chat instance into the page.
 */
function addWebChat() {
  // This is the standard web chat configuration object. You can modify these values with the embed code for your
  // own assistant if you wish to try this example with your assistant. You can find the documentation for this at
  // https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-configuration#configurationobject.
  const options = {
    // TODO: Update this with the assistant configured for this example.
    integrationID: 'xyz',
    region: 'us-south',
    serviceInstanceID: '9a3613d2-3ce6-4928-8eb6-4d659d87ae68',
    onLoad: webChatOnLoad,
  };

  window.watsonAssistantChatOptions = options;

  const script = document.createElement('script');
  const version = options.clientVersion || 'latest';
  script.src = `https://web-chat.global.assistant.watson.appdomain.cloud/versions/${version}/WatsonAssistantChatEntry.js`;
  document.head.appendChild(script);
}

/**
 * This function is called when web chat has been loaded and is ready to be displayed.
 */
async function webChatOnLoad(instance) {
  // TODO: Your example code starts here.
  console.log('The application has been started!');
  await instance.render();
}

// Launch web chat as soon as this script has loaded.
addWebChat();
