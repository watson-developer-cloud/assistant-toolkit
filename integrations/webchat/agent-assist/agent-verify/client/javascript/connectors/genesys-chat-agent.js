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

import { ChatAgentInterface } from "../chat-agent-interface.js";

// Obtain a reference to the platformClient object
let platformClient = null;
let client = null;
let usersApi = null;
let conversationsApi = null;
let notificationsApi = null;

// Implicit Grant credentials
// #424 changed from const to variables
let clientId = ''; // 'fac46d7e-5e1c-42d3-aa74-f7de1a7c6485';
let environment = ''; // 'mypurecloud.de';

// API instances

let userId = '';
let chatConversations = []; // Chat conversations handled by the user
let activeChatId = ''; // Chat that is in focus on the UI
let activeCommunicationId = '';
let channel = {};
let callChannel = {};
let agentAssistConversationID = '';

let chatTopic = 'chatTopic';
let engagementId = '0.0.0.0-20200101T120000.000Z';
let engagementTopic = 'engagementTopic';
let rtSessionId = '10.0.0.1-20200101T120000.000Z';
let topic = 'topic';
let custId = 'customerId';
let compBranch = 'companyBranch';
let customerDetails = [{ type: 'ctmrinfo', customerId: custId, companyBranch: compBranch }];
//* below out for PII

let visitorInfoData = {
    visitorId: "",
    visitorName: "",
    IpAddress: "0.0.0.0",
    visitorEmail: "",
    visitStartTime: new Date().toISOString()
};

let agentInfoData = {
    accountId: "IBM Research",
    agentName: "",
    agentId: "",
    //* agentNickname: "John",
    agentEmail: ""
};

let chatTranscript = {
    "lines": {
        "onSuccess": null,
        "notifyWhenDone": null
    }
};
let chatHistory = [];

let callTranscripts = [];
let transcriptionSender = null;

let setAgentInfo = (genesysData) => {
    agentInfoData.agentName = genesysData.name;
    agentInfoData.agentId = genesysData.userId;
};

let setVisitorInfo = (genesysData) => {
    visitorInfoData.visitorId = genesysData.attributes["context.customerId"];
    visitorInfoData.visitorEmail = genesysData.attributes["context.email"];
    visitorInfoData.visitorName = genesysData.name;
};

let showRecommendations = (sourceParam, text) => {
    if (chatTranscript.lines.onSuccess !== null && text) {
        // pass across last utterance
        let utterance = {
            html: text,
            source: sourceParam,
            subType: "REGULAR",
            text: text,
            time: Date.now()
        };
        chatHistory.push(utterance);
        let lastUtteranceEvent = {
            'key': 'chatTranscript.lines',
            'newValue': [chatHistory[chatHistory.length - 1]]
        };

        chatTranscript.lines.onSuccess(lastUtteranceEvent);
    }
};
/**
 * Callback function for 'message' and 'typing-indicator' events.
 * For this sample, it will merely display the chat message into the page.
 * 
 * @param {Object} data the event data  
 */
let onMessage = (data) => {
    switch (data.metadata.type) {
        case 'typing-indicator':
            break;
        case 'message':
            // Values from the event
            let eventBody = data.eventBody;
            let message = eventBody.body;
            let convId = eventBody.conversation.id;
            let senderId = eventBody.sender.id;

            // Conversation values for cross reference
            let conversation = chatConversations.find(c => c.id == convId);
            let participant = conversation.participants.find(p => p.chats[0].id == senderId);
            let purpose = participant.purpose;

            // Get agent communication ID
            if (purpose == 'agent') {
                stackedText = '';
                setAgentInfo(participant);
                showRecommendations('agent', message);
            }
            // Get some recommended replies
            if (purpose == 'customer') {
                stackedText += message;
                setVisitorInfo(participant);
                showRecommendations('visitor', message);
            }

            break;
    }
};


/**
 * Callback function for transcription 'message' events.
 * @param {Object} data the event data  
 */
let onTranscriptionMessage = (data) => {
    if (data && data.eventBody && data.eventBody.transcripts && data.eventBody.transcripts.length > 0) {
        let transcripts = data.eventBody.transcripts;
        transcripts.forEach(t => {
            let utterance = null;
            if (t.alternatives && t.alternatives.length > 0) {
                let sourceParam = null;
                if (t.channel === 'INTERNAL') {
                    sourceParam = 'agent';
                } else {
                    sourceParam = 'visitor';
                }
                let text = t.alternatives[0].transcript;
                utterance = {
                    html: text,
                    source: sourceParam,
                    subType: "REGULAR",
                    text: text,
                    time: Date.now()
                };
                //Adding utterance to chathistory
                chatHistory.push(utterance);
            }
        });
        let lastUtteranceEvent = {
            'key': 'chatTranscript.lines',
            'newValue': [chatHistory[chatHistory.length - 1]]
        };
        chatTranscript.lines.onSuccess(lastUtteranceEvent);
    }
};

// Contains the calback functions for the subscribed topics 
// in the notifications channel.
// <topic name>:<callback function>
let topicCallbackMap = {
    'channel.metadata': () => {
        console.log('Notification heartbeat.');
    }
};

// Messages of the client that are sent in a straight series.
let stackedText = '';

/**
 * Calback function to when a chat conversation event occurs 
 * for the current user
 * @param {Object} event The Genesys Cloud event
 */
function onChatConversationEvent(event) {
    let conversation = event.eventBody;
    let participants = conversation.participants;
    let conversationId = conversation.id;

    //Just processing the events for current conversation ID
    if (agentAssistConversationID === conversationId) {
        console.log(conversation);

        // Get the last agent participant. This happense when a conversation
        // has multiple agent participants, we need to get the latest one.
        let agentParticipant = participants.slice().reverse().find(
            p => p.purpose == 'agent');
        let customerParticipant = participants.find(
            p => p.purpose == 'customer');
        // Value to determine if conversation is already taken into account before
        let isExisting = chatConversations.map((conv) => conv.id)
            .indexOf(conversationId) != -1;

        // Once agent is connected subscribe to the conversation's messages 
        if (agentParticipant.state == 'connected' &&
            customerParticipant.state == 'connected' &&
            !isExisting) {
            // Add conversationid to existing conversations array
            return registerConversation(conversation.id)
                .then(() => {
                    // Add conversation to tab
                    let participant = conversation.participants.filter(
                        participant => participant.purpose === "customer")[0];
                    //view.addCustomerList(participant.name, conversation.id, setActiveChat);
                    //TODO: make CAIRA CALL

                    return addSubscription(
                        `v2.conversations.chats.${conversationId}.messages`,
                        onMessage);
                });
        }

        // If agent has multiple interactions,
        // open the active conversation based on Genesys Cloud
        //if (agentParticipant.state == 'connected' && customerParticipant.state == 'connected' && agentParticipant.held == false) {
        //    setActiveChat(conversationId);
        //}

        // If chat has ended remove the tab and the chat conversation
        //if (agentParticipant.state == 'disconnected' && isExisting) {
        //view.removeTab(conversationId);
        //TODO: make CAIRA CALL
        //   chatConversations = chatConversations.filter(c => c.id != conversationId);
        //    if (chatConversations.length > 0) {
        //        setActiveChat(chatConversations[0].id);
        //    }
        //}
    }
}

/**
 * Add a subscription to the channel and store the 
 * callback function mapping to the global variable
 * @param {String} topic Genesys Cloud notification topic string
 * @param {Function} callback callback function to fire when the event occurs
 */
function addSubscription(topic, callback) {
    let body = [{ 'id': topic }];

    return notificationsApi.postNotificationsChannelSubscriptions(
        channel.id, body)
        .then((_data) => {
            topicCallbackMap[topic] = callback;
            console.log(`Added subscription to ${topic}`);
        });
}

function addCallSubscription(topic, callback) {
    let body = [{ 'id': topic }];

    return notificationsApi.postNotificationsChannelSubscriptions(
        callChannel.id, body)
        .then((_data) => {
            topicCallbackMap[topic] = callback;
            console.log(`Added subscription to ${topic}`);
        });
}

/**
 * Create the channel. If called multiple times,
 * the last one will be the active one.
 */
function createChannel() {
    return notificationsApi.postNotificationsChannels()
        .then(data => {
            console.log('---- Created Notifications Channel ----');
            console.log(data);

            channel = data;
            let ws = new WebSocket(channel.connectUri);
            ws.onmessage = () => {
                let dataObj = JSON.parse(event.data);

                topicCallbackMap[dataObj.topicName](dataObj);
            };
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
            ws.onmessage = () => {
                let dataObj = JSON.parse(event.data);

                topicCallbackMap[dataObj.topicName](dataObj);
            };
        });
}


/**
 * Set-up the channel for chat conversations
 */
function setupChatChannel() {
    return createChannel()
        .then(_data => {
            // Subscribe to incoming chat conversations
            return addSubscription(
                `v2.users.${userId}.conversations.chats`, onChatConversationEvent);
        });
}


/**
 * Calback function to when a call conversation event occurs 
 * for the current user
 * @param {Object} event The Genesys Cloud event
 */
function onCallConversationEvent(event) {
    let conversation = event.eventBody;
    let participants = conversation.participants;
    let conversationId = conversation.id;

    //Just processing the events for current conversation ID
    if (agentAssistConversationID === conversationId) {
        console.log(conversation);

        // Get the last agent participant. This happense when a conversation
        // has multiple agent participants, we need to get the latest one.
        let agentParticipant = participants.slice().reverse().find(
            p => p.purpose == 'agent');
        let customerParticipant = participants.find(
            p => p.purpose == 'customer');
        // Value to determine if conversation is already taken into account before
        let isExisting = chatConversations.map((conv) => conv.id)
            .indexOf(conversationId) != -1;

        // Once agent is connected subscribe to the conversation's call 
        if (agentParticipant.state == 'connected' &&
            customerParticipant.state == 'connected' &&
            !isExisting) {
            // Add conversationid to existing conversations array
            return registerConversation(conversation.id)
                .then(() => {
                    // Add conversation to tab
                    let participant = conversation.participants.filter(
                        participant => participant.purpose === "customer")[0];
                    //view.addCustomerList(participant.name, conversation.id, setActiveChat);
                    //TODO: make CAIRA CALL

                    return addSubscription(
                        `v2.conversations.${conversationId}.transcription`,
                        onTranscriptionMessage);
                });
        }

        // If agent has multiple interactions,
        // open the active conversation based on Genesys Cloud
        //if (agentParticipant.state == 'connected' && customerParticipant.state == 'connected' && agentParticipant.held == false) {
        //    setActiveChat(conversationId);
        //}

        // If chat has ended remove the tab and the chat conversation
        //if (agentParticipant.state == 'disconnected' && isExisting) {
        //view.removeTab(conversationId);
        //TODO: make CAIRA CALL
        //    chatConversations = chatConversations.filter(c => c.id != conversationId);
        //    if (chatConversations.length > 0) {
        //        setActiveChat(chatConversations[0].id);
        //    }
        //}
    }
}

/**
 * Set-up the channel for call conversations
 */
function setupCallChannel(conversationID) {
    return createCallChannel()
        .then(data => {
            // Subscribe to incoming chat conversations
            return addCallSubscription(
                `v2.conversations.${conversationID}.transcription`, onTranscriptionMessage);
            //return addCallSubscription(
            //    `v2.users.${userId}.conversations.calls`, onCallConversationEvent)
        });
}

/**
 * Get already existing chat conversations, subscribe the conversations to the 
 * notifications and display each name on the tab menu
 * @returns {Promise} 
 */
function processExistingChats() {
    return conversationsApi.getConversationsChats()
        .then((data) => {
            let promiseArr = [];

            data.entities.forEach((conv) => {
                //Just processing existing messages of current conversation ID
                if (agentAssistConversationID === conv.id) {
                    promiseArr.push(registerConversation(conv.id));

                    addSubscription(
                        `v2.conversations.chats.${conv.id}.messages`,
                        onMessage);
                }
            });

            //view.populateActiveChatList(data.entities, setActiveChat);
            //TODO: make CAIRA CALL


            return Promise.all(promiseArr);
        })
        .then(() => {
            // Set the first one as the active one
            if (chatConversations.length > 0) {
                setActiveChat(chatConversations[0].id);
            }
        });
}

/**
 * Should be called when there's a new conversation. 
 * Will store the conversations in a global array.
 * @param {String} conversationId Genesys Cloud conversationId
 */
function registerConversation(conversationId) {
    return conversationsApi.getConversation(conversationId)
        .then((data) => chatConversations.push(data));
}

/**
 * Set the focus to the specified chat conversation.
 * @param {String} conversationId conversation id of the new active chat
 */
function setActiveChat(conversationId) {
    let conversation = chatConversations.find(c => c.id == conversationId);

    // Store global references to the current chat conversation
    activeChatId = conversationId;
    let agentData = conversation.participants.slice().reverse().find(p => p.purpose == 'agent');
    if (agentData.chats && agentData.chats.length > 0) {
        activeCommunicationId = agentData.chats[0].id;
    } else if (agentData.calls && agentData.calls.length > 0) {
        activeCommunicationId = agentData.calls[0].id;
    }

    return conversationsApi.getConversationsChatMessages(conversationId)
        .then((data) => {
            // Get messages and display to page
            //view.makeTabActive(conversationId);
            //view.displayTranscript(data.entities, conversation);
            //TODO: make CAIRA CALL

            // Rebuild the stacked text string
            stackedText = '';
            let messages = data.entities;
            messages.forEach((msg) => {
                if (msg.hasOwnProperty("body")) {
                    let message = msg.body;

                    let senderId = msg.sender.id;
                    let purpose = conversation
                        .participants.find(p => p.chats[0].id == senderId)
                        .purpose;

                    if (purpose == 'customer') {
                        stackedText += message;
                    } else {
                        stackedText = '';
                    }
                }
            });

            // Show recommendations
            //showRecommendations(stackedText);
            //TODO: make CAIRA CALL
        });
}

/**
 * Agent assistant to send the selected recommended response
 * @param {String} message
 * @param {String} conversationId
 * @param {String} communicationId
 */
function sendMessage(message, conversationId, communicationId) {
    conversationsApi.postConversationsChatCommunicationMessages(
        conversationId, communicationId,
        {
            "body": message,
            "bodyType": "standard"
        }
    );
}

function getConversationDetails(conversationId) {
    conversationsApi.getConversation(conversationId)
        .then((conversationDetails) => {
            console.log('CONVERSATION DETAILS: ', conversationDetails);
            let customer = conversationDetails.participants.find(p => p.purpose == 'customer');
            //let communicationId = customer.calls[0].id;
            if (conversationDetails && conversationDetails.participants && conversationDetails.participants.length >= 2) {
                conversationDetails.participants.forEach(participant => {
                    let purpose = participant.purpose;
                    // Get agent communication ID
                    if (purpose == 'agent') {
                        setAgentInfo(participant);
                    }
                    if (purpose == 'customer') {
                        setVisitorInfo(participant);
                    }
                }
                );
            }
        });
}

class GenesysChatAgent extends ChatAgentInterface {

    constructor() {
        super();
    }

    preInit() {
        // Obtain a reference to the platformClient object
        platformClient = require('platformClient');
        client = platformClient.ApiClient.instance;

        // API instances
        usersApi = new platformClient.UsersApi();
        conversationsApi = new platformClient.ConversationsApi();
        notificationsApi = new platformClient.NotificationsApi();
    }

    getRedirectURL() {
        let redirectUrl = new URL(document.location.href);
        redirectUrl.searchParams.delete('conversationID');
        return redirectUrl.href;
    }
    

    /** --------------------------------------------------------------
     *                       INITIAL SETUP
     * -------------------------------------------------------------- */
    init(options) {
        this.preInit();
        this.genesys_window = options.iframeWindow.parent;
        if (!options.conversationID) {
            throw new Error('ERROR: Genesys conversation ID is required to init the Agent Assistance SDK...');
        }
        
        // #424 set values passed 
        clientId = options.clientID;
        environment = options.environment;
        
        client.setEnvironment(environment);
        client.setPersistSettings(true, 'test_app');
        agentAssistConversationID = options.conversationID;
        rtSessionId = agentAssistConversationID;
        client.loginImplicitGrant(clientId,
            this.getRedirectURL(),
            { state: agentAssistConversationID })
            .then(data => {
                console.log(data);
                // Get Details of current User
                return usersApi.getUsersMe();
            }).then(userMe => {
                userId = userMe.id;
                // Create the channel for call notifications
                return setupCallChannel(agentAssistConversationID);
            }).then(_data => {
                // Create the channel for chat notifications
                return setupChatChannel();
            }).then(_data => {
                // Get current chat conversations
                return processExistingChats();
            }).then(_data => {
                return getConversationDetails(agentAssistConversationID);
            }).then(_data => {
                console.log('Finished Setup');
                // Error Handling
            }).catch(e => console.log(e));

    }

    get(key, cbSuccess, _cbError) {
        if (key === 'visitorInfo') {
            return cbSuccess(visitorInfoData);
        } else if (key === 'agentInfo') {
            return cbSuccess(agentInfoData);
        } else if (key === 'engagementInfo.engagementId') {
            return cbSuccess(engagementId);
        } else if (key === 'engagementInfo.engagementTopic') {
            return cbSuccess(engagementTopic);
        } else if (key === 'engagementInfo.topic') {
            return cbSuccess(topic);
        } else if (key === 'chatInfo.rtSessionId') {
            return cbSuccess(rtSessionId);
        } else if (key === 'chatInfo.chatTopic') {
            return cbSuccess(chatTopic);
        } else if (key === 'authenticatedData.customerDetails') {
            return cbSuccess(customerDetails);
        } else if (key === 'chatTranscript.lines') {
            return cbSuccess(chatHistory);
        }
    }

    set(object, cbSuccess, cbError) {
        let obj = {};
        try {
            obj = JSON.parse(object);
            if (obj.key == null || obj.data == null) {
                let err = {
                    text: "set object's key or data is missing or null."
                };
                cbError(err);
            }
        } catch (Error) {
            cbError(Error);
        }
        if (obj.key === 'visitorInfo') {
            this.visitorInfoData = obj.data;
            return cbSuccess(this.visitorInfoData);
        } else if (obj.key === 'agentInfo.agentId') {
            this.agentInfoData.agentId = obj.data;
            return cbSuccess(this.agentInfoData);
        } else if (obj.key === 'engagementInfo.engagementTopic') {
            this.engagementTopic = obj.data;
            return cbSuccess(this.engagementTopic);
        } else if (obj.key === 'engagementInfo.topic') {
            this.topic = obj.data;
            return cbSuccess(this.topic);
        } else if (obj.key === 'chatInfo.chatTopic') {
            this.chatTopic = obj.data;
            return cbSuccess(this.chatTopic);
        }
        let error = {
            text: "set object unrecognized key: " + obj.key
        };
        return cbError(error);
    }

    bind(key, cbSuccess, cbNotifyWhenDone) {
        if (key === "chatTranscript.lines") {
            // subscription for newest utterance to be sent to iframe
            chatTranscript.lines.onSuccess = cbSuccess;
            chatTranscript.lines.notifyWhenDone = cbNotifyWhenDone;
        }
    }

    unbind(key, cbSuccess, _cbNotifyWhenDone) {
        if (key === 'visitorInfo.chattingVisitorState') {
            return cbSuccess(true);
        } else if (key === 'chatTranscript.lines') {
            chatTranscript.lines.onSuccess = null;
            chatTranscript.lines.notifyWhenDone = null;
            return cbSuccess(true);
        }
    }

    dispose() {
        // nothing to do
    }

    command(cmdName, data, cbNotifyWhenDone, convertHTML) {
        if (cmdName === 'Write ChatLine') {
            //This method send the message to both participants (agent / client)
            let htmlPayload = {
                'html': data.text
            };
            convertHTML(htmlPayload)
                .then(function (response) {
                    if (response && response.text) {
                        sendMessage(response.text, activeChatId, activeCommunicationId);
                    }
                })
                .catch(function (err) {
                    console.error('Error /feedback', err);
                });
            cbNotifyWhenDone();
        }
    }

}

let genesysChatAgentID = () => {
    return 'GenesysChatAgent';
};

export { GenesysChatAgent, genesysChatAgentID };