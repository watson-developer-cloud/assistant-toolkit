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

class IBMChatAgent extends ChatAgentInterface {

    ux_window = window.parent;
    ux_widget_window = null;
    rtSessionId = '10.0.0.1-20200101T120000.000Z';
    engagementId = '0.0.0.0-20200101T120000.000Z';
    chatTopic = 'chatTopic';
    customerId = 'customerId';
    companyBranch = 'companyBranch';
    customerDetails = [{ type: 'ctmrinfo', customerId: this.customerId, companyBranch: this.companyBranch }];
    topic = 'topic';
    engagementTopic = 'engagementTopic';
    chathistory = [];
    ipObj = { 'ip': '0.0.0.0' };
    chatTranscript = {
        "lines": {
            "onSuccess": null,
            "notifyWhenDone": null
        }
    };

    visitorInfo = {
        "chattingVisitorState": {
            "onSuccess": null,
            "notifyWhenDone": null
        }
    };

    //* below out for PII
    visitorInfoData = {
        visitorId: "0987654321",
        visitorName: "Jim",
        IpAddress: "0.0.0.0",
        visitStartTime: new Date().toISOString()
    };

    //* below out for PII
    agentInfoData = {
        accountId: "IBM Research",
        //* agentName: "John Smith",
        agentId: "1234567890"
        //* agentNickname: "John",
        //* agentEmail: "john@ibm.com"
    };

    chatSDKInfo = {
        startChat: {

        }
    };

    constructor() {
        super();
    }

    getIPAddress() {
        let http = new XMLHttpRequest();
        let url = location.protocol + "//api.ipify.org/?format=json";
        http.open("GET", url);
        let self = this;
        http.onload = function () {
          try {
            self.ipObj = JSON.parse(http.response);
          } catch (err) {
            // service is not available, avoid syntax error
            self.ipObj = {};
            self.ipObj.ip = "10.10.10.10";
          }
            self.setEngagementID();
            self.setEngagementTopic();
            return self.ipObj.ip;
        };
        http.onerror = function () {
            // leave ipObj as initialized
            self.setEngagementID();
            self.setEngagementTopic();
        };
        http.send();
    }

    setEngagementID() {
        let now = new Date();
        let month = '' + (now.getUTCMonth() + 1);
        let day = '' + now.getUTCDate();
        let year = now.getUTCFullYear();
        let hour = '' + now.getUTCHours();
        let min = '' + now.getUTCMinutes();
        let sec = '' + now.getUTCSeconds();
        let milli = '' + now.getUTCMilliseconds();
        if (month.length < 2) { month = '0' + month; }
        if (day.length < 2) { day = '0' + day; }
        if (hour.length < 2) { hour = '0' + hour; }
        if (min.length < 2) { min = '0' + min; }
        if (sec.length < 2) { sec = '0' + sec; }
        if (milli.length === 1) { milli = '00' + milli; } else if (milli.length === 2) { milli = '0' + milli; }
        this.engagementId = this.ipObj.ip + "-" + year + month + day + 'T' + hour + min + sec + '.' + milli + 'Z';
        console.log("engagementId set to " + this.engagementId);
        // this is to ensure a unique conversation id in Test Rig
        this.rtSessionId = this.engagementId;
        return this.engagementId;
    }

    set(object, cbSuccess, cbError) {
        let obj = {};
        try {
            obj = JSON.parse(object);
            if (obj.key == null || obj.data == null) {
                let error = {
                    text: "set object's key or data is missing or null."
                };
                cbError(error);
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


    init(options) {
        this.ux_window = options.iframeWindow.parent;
        this.ux_widget_window = options.iframeWindow;
        let self = this;
        this.ux_widget_window.onmessage = function (e) { // NOSONAR Cognitive Complexity
            if (typeof e !== 'undefined' && e.data !== null) {
                let event = JSON.parse(e.data);
                switch (event.key) {
                    case "chatTranscript.lines": {
                        self.chathistory = event.data;
                        if (self.chatTranscript.lines.onSuccess !== null && self.chathistory.length != 0) {
                            // pass across last utterance
                            let lastUtteranceEvent = {
                                'key': 'chatTranscript.lines',
                                'newValue': [self.chathistory[self.chathistory.length - 1]]
                            };
                            self.chatTranscript.lines.onSuccess(lastUtteranceEvent);
                        }
                        break;
                    }
                    case "visitorInfo.chattingVisitorState": {
                        if (typeof self.visitorInfo.chattingVisitorState.onSuccess !== 'undefined' && self.visitorInfo.chattingVisitorState.onSuccess !== null) {
                            self.visitorInfo.chattingVisitorState.onSuccess(event);
                        }
                        break;
                    }
                    case "chatSDKInfo.startChat": {
                        if (typeof self.chatSDKInfo.startChat.conversationId !== 'undefined' && self.chatSDKInfo.startChat.conversationId !== null) {
                            self.engagementId = self.chatSDKInfo.startChat.conversationId;
                        } else {
                            self.setEngagementID();
                        }
                        if (typeof self.chatSDKInfo.startChat.visitorInfo !== 'undefined' && self.chatSDKInfo.startChat.visitorInfo !== null) {
                            self.visitorInfoData = self.chatSDKInfo.startChat.visitorInfo;
                        }
                        if (typeof self.chatSDKInfo.startChat.agentInfo !== 'undefined' && self.chatSDKInfo.startChat.agentInfo !== null) {
                            self.agentInfoData = self.chatSDKInfo.startChat.agentInfo;
                        }
                        break;
                    }
                    case "visitorInfo": //
                    case "agentInfo.agentId": //
                    case "engagementInfo.engagementTopic": //
                    case "engagementInfo.topic": // 
                    case "chatInfo.chatTopic": {
                        self.set(JSON.stringify(event), // param passed to set
                            (data) => { console.log("Set", data); }, // callback
                            (err) => { console.log("Error", err); }  // errback
                        );
                        break;
                    }
                }
            }
        };

        this.getIPAddress();
    }

    setEngagementTopic() {
        let event = {
            'type': 'setEngagementTopic'
        };
        this.ux_window.postMessage(JSON.stringify(event), "*");
    }


    dispose() {
        // nothing to do
    }

    get(key, cbSuccess, cbError) {
        if (key === 'visitorInfo') {
            return cbSuccess(this.visitorInfoData);
        } else if (key === 'agentInfo') {
            return cbSuccess(this.agentInfoData);
        } else if (key === 'engagementInfo.engagementId') {
            return cbSuccess(this.engagementId);
        } else if (key === 'engagementInfo.engagementTopic') {
            return cbSuccess(this.engagementTopic);
        } else if (key === 'engagementInfo.topic') {
            return cbSuccess(this.topic);
        } else if (key === 'chatInfo.rtSessionId') {
            return cbSuccess(this.rtSessionId);
        } else if (key === 'chatInfo.chatTopic') {
            return cbSuccess(this.chatTopic);
        } else if (key === 'authenticatedData.customerDetails') {
            return cbSuccess(this.customerDetails);
        } else if (key === 'chatTranscript.lines') {
            return cbSuccess(this.chathistory);
        }
    }

    bind(key, cbSuccess, cbNotifyWhenDone) {
        switch (key) {
            case "visitorInfo.chattingVisitorState": {
                // subscription for changes in visitor state to be sent to iframe
                this.visitorInfo.chattingVisitorState.onSuccess = cbSuccess;
                this.visitorInfo.chattingVisitorState.notifyWhenDone = cbNotifyWhenDone;
                break;
            }
            case "chatTranscript.lines": {
                // subscription for newest utterance to be sent to iframe
                this.chatTranscript.lines.onSuccess = cbSuccess;
                this.chatTranscript.lines.notifyWhenDone = cbNotifyWhenDone;
                break;
            }
        }
    }

    unbind(key, cbSuccess, cbNotifyWhenDone) {
        if (key === 'visitorInfo.chattingVisitorState') {
            this.visitorInfo.chattingVisitorState.onSuccess = null;
            this.visitorInfo.chattingVisitorState.notifyWhenDone = null;
            return cbSuccess(true);
        } else if (key === 'chatTranscript.lines') {
            this.chatTranscript.lines.onSuccess = null;
            this.chatTranscript.lines.notifyWhenDone = null;
            return cbSuccess(true);
        }
    }

    command(cmdName, data, cbNotifyWhenDone) {
        if (cmdName === 'Write ChatLine') {
            let event = {
                'type': 'writeAgentChat',
                'data': data
            };
            this.ux_window.postMessage(JSON.stringify(event), "*");
            cbNotifyWhenDone();
        }
    }


}

let ibmChatAgentID = () => {
    return 'IBMChatAgent';
};

export { IBMChatAgent, ibmChatAgentID };
