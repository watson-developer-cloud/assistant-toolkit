// Obtain a reference to the platformClient object.
let platformClient;
let client;
let notificationsApi;

// Implicit Grant credentials.
let clientId = ''; // 'fac46d7e-5e1c-42d3-aa74-f7de1a7c6485';
let environment = ''; // 'mypurecloud.de';

let callChannel = {};
let agentAssistConversationID = '';

/**
 * Callback function for transcription 'message' events.
 *
 * @param {object} data The event data.
 */
function onTranscriptionMessage(data) {
  if (data?.eventBody?.transcripts?.length > 0) {
    const { transcripts } = data.eventBody;
    transcripts.forEach((transcript) => {
      if (transcript?.alternatives?.length > 0) {
        const text = transcript.alternatives[0].transcript;

        if (transcript.channel === 'INTERNAL') {
          window.watsonAssistantChat.agentAssist.onAgentSaid(text);
        } else {
          window.watsonAssistantChat.agentAssist.onUserSaid(text);
        }
      }
    });
  }
}

async function addCallSubscription(topic) {
  const body = [{ id: topic }];

  await notificationsApi.postNotificationsChannelSubscriptions(callChannel.id, body);
  console.log(`postNotificationsChannelSubscriptions complete`);
}

/**
 * Create the call channel. If called multiple times, the last one will be the active one.
 */
async function createCallChannel() {
  const data = await notificationsApi.postNotificationsChannels();
  console.log('---- Created Notifications Channel ----');
  console.log(data);

  callChannel = data;
  const ws = new WebSocket(callChannel.connectUri);
  ws.onmessage = (event) => {
    const dataObj = JSON.parse(event.data);
    onTranscriptionMessage(dataObj);
  };
}

/**
 * Set up the channel for call conversations.
 */
async function setupCallChannel(conversationID) {
  await createCallChannel();
  // Subscribe to incoming chat conversations.
  await addCallSubscription(`v2.conversations.${conversationID}.transcription`);
}

function getRedirectURL() {
  // Return the URL without the query parameters or hash.
  return `${document.location.origin}${document.location.pathname}`;
}

async function initConnector() {
  console.log('initConnector: TOP');

  const params = new URLSearchParams(window.location.search);

  // Get the parameters needed to work with the Genesys APIs from query parameters.
  clientId = params.get('clientID') || null;
  environment = params.get('environment') || null;
  agentAssistConversationID = params.get('conversationID') || null;

  // Obtain a reference to the platformClient object.
  // eslint-disable-next-line global-require
  platformClient = require('platformClient');
  client = platformClient.ApiClient.instance;

  // API instances.
  notificationsApi = new platformClient.NotificationsApi();

  client.setEnvironment(environment);
  client.setPersistSettings(true, 'test_app');

  const data = await client.loginImplicitGrant(clientId, getRedirectURL(), { state: agentAssistConversationID });
  console.log('Got login data', data);

  // Create the channel for call notifications.
  await setupCallChannel(data.state);

  console.log('Finished Setup');
}

initConnector().catch(console.error);
