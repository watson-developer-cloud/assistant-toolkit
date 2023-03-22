/**
 *----------------------------------------------------------------------
 * IBM Confidential
 * OCO Source Materials
 * 2020-1414 Agent Assistance
 *
 * © Copyright 2020-2022 IBM Corp. All rights reserved.
 *
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has been
 * deposited with the U.S. Copyright Office.
 *----------------------------------------------------------------------
 */

// Obtain a reference to the platformClient object
let platformClient = null;
let client = null;
let notificationsApi = null;

// Implicit Grant credentials
let clientId = ''; // 'fac46d7e-5e1c-42d3-aa74-f7de1a7c6485';
let environment = ''; // 'mypurecloud.de';

let callChannel = {};
let agentAssistConversationID = '';

/**
 * Callback function for transcription 'message' events.
 * @param {Object} data the event data  
 */
let onTranscriptionMessage = (data) => {
    if (data?.eventBody?.transcripts?.length > 0) {
        let transcripts = data.eventBody.transcripts;
        transcripts.forEach(t => {
            if (t?.alternatives?.length > 0) {
                let text = t.alternatives[0].transcript;

                if (t.channel === 'INTERNAL') {
                    window.watsonAssistantChat.agentAssist.onAgentSaid(text);
                } else {
                    window.watsonAssistantChat.agentAssist.onUserSaid(text);
                }
            }
        });
    }
};

function addCallSubscription(topic) {
    let body = [{ 'id': topic }];

    return notificationsApi.postNotificationsChannelSubscriptions(
        callChannel.id, body)
        .then((_data) => {
            console.log(`postNotificationsChannelSubscriptions complete`);
        });
}

/**
 * Create the call channel. If called multiple times,
 * the last one will be the active one.
 */
function createCallChannel() {
    return notificationsApi.postNotificationsChannels()
        .then(data => {
            console.log('---- Created Notifications Channel ----');
            console.log(data);

            callChannel = data;
            let ws = new WebSocket(callChannel.connectUri);
            ws.onmessage = (event) => {
                let dataObj = JSON.parse(event.data);
                onTranscriptionMessage(dataObj);
            };
        });
}

/**
 * Set-up the channel for call conversations
 */
function setupCallChannel(conversationID) {
    return createCallChannel()
        .then(data => {
            // Subscribe to incoming chat conversations
            return addCallSubscription(`v2.conversations.${conversationID}.transcription`);
        });
}


function getRedirectURL() {
    let redirectUrl = new URL(document.location.href);
    redirectUrl.searchParams.delete('conversationID');
    return redirectUrl.href;
}
    
function initConnector() {
    console.log('initConnector: TOP');

    const urlSearchParams = new URLSearchParams(window.location.search);
    const params = Object.fromEntries(urlSearchParams.entries());

    // Get the parameters needed to work with the Genesys APIs from query parameters
    clientId =  params.clientID || null;
    environment =  params.environment || null;
    agentAssistConversationID = params.conversationID || null;

    // Obtain a reference to the platformClient object
    platformClient = require('platformClient');
    client = platformClient.ApiClient.instance;

    // API instances
    notificationsApi = new platformClient.NotificationsApi();
    
    client.setEnvironment(environment);
    client.setPersistSettings(true, 'test_app');

    client.loginImplicitGrant(clientId,
        getRedirectURL(),
        { state: agentAssistConversationID })
        .then(data => {
            console.log(data);
            // Create the channel for call notifications
            return setupCallChannel(agentAssistConversationID);
        }).then(_data => {
            console.log('Finished Setup');
            // Error Handling
        }).catch(e => console.log(e));
}

initConnector();
