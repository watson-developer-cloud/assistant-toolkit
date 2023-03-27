# Agent Verify

## Background
This agent-assist starter kit combines Watson Assistant's webchat with a wraper application that monitors real-time caller/agent transcriptions to provide the following features:

1. Agent prompting based on customer input.
2. Verification that agent speaks certain prompts back to the caller verbatim.
3. The ability to use callers speech transcriptions as input to Watson Assistant.

The real-time transcriptions are delivered to the agent-verify application. This sample does NOT include Watson speech support for delivering the real-time transcriptions.

The client application that comes with this starter kit can be embedded into the following agent dashboards:

1. Genesys Cloud CX: This version of the client application relies on Genesys voice transcriptions to drive the Watson Assistant conversational flow.
2. Dashboard Similutor - The starter kit includes a simulator application that can be used for testing outside the chosen CCaaS.

## Structure of the Starter Kit

Below this directory you will find the following two directories:

1. `client` - This directory includes both the HTML and JavaScript files that are used to launch the embedded client applciation.
2. `sample` - This directory contains a sample Watson Assistant actions skill that can be used to demonstrate the fuctionality of this starter kit.

Here is a quick rundown of all the various files:

- `.\client\genesys-agent-assist-verify.html` - Loads the agent-verify app along with the genesys connector. Can be loaded into the genesys agent dashboard.
- `.\client\simulator-agent-assist-verify.html` - Loads both a simulator and the agent-verify application. Can be loaded directly into a browser to locally test the application. 
- `.\client\javascript\agent-verify.js` - Contains the wrapper for the Watson Assistant webchat app. It handles the real-time transcription events published from the various connectors.
- `.\client\javascript\agent-verify.css` - Used to render the agent-verify app.
- `.\client\javascript\connectors\genesys-transcription-connector.js` - This is the code that interfaces with the Genesys notification APIs to receive voice transcription events. It publishes these events directly to the agent-verify application.
- `.\sample\agent-assistant-atlas-airlines.actions.json` - Sample Action skill that can be used to test the agent-verify app.
- `.\sample\aa_transcript.md` - A caller/agent transcript that can be used to test the Atlas Airline skill.

## Pre-Requisites

To test this application with a particular Contact Center as a Server (CCaaS) provider requires an account with that provider. It may also require specific permissions within the account. In addition, you will need a Watson Assistant instance.

### Genesys
For Genesys, you will need a Cloud CX 3 license to have access to the features needed to execute this demo. You will also need to setup and configure a webserver to serve up the static HTML pages, CSS and javascript contained in this sample project. A simple weebserver that is easy to setup and run locally is the Node.js htt-server. See details below on how to setup a webserver on localhost for testing.

#### Auth Info
The Genesys connector included with this sample relise on **Token Implicit Grant** browser authentication. The parameters needed to perform this authentication are passed to the agent-verify application through query parameters.

#### Agent Groups
You must define a group that will contain agents within the Genesys Admin dashboard. Please refer to Genesys documentation for how to set this up. Note that you will need this group when configuring the agent-verify Interation Widget.

## Genesys Setup
There are two parts to the Genesys setup. The first part covers setting up the queue and creating the integration. This includes all the setup needed to embed the agent-verify application into the Genesys agent dashboard. This README will walk you step by step through this setup. 

The second part of the Genesys setup is to define the inbound call flow to reach the call queue setup in part 1 of this setup guide. The second part of the Genesys setup is not covered in this README. You will need to consult the Genesys documentation for details on how to setup the call flow. Note that there are several different options for this including:

- the use of a Genesys provider to setup a phone number
- the use of an external SIP trunking provider such as Twilio
- the use of a Genesys call simulator

### Step 1: Login to your Genesys account and navigate to the admin page

![Genesys Admin Queue Page](images/admin-page-queue.jpg)

From the Admin panel click on the `Queues` link to create a new queue that will be used to associated the agent-verify Interaction Widget with the agent dashboard that will be loaded whenever a call is answered off the queue.

### Step 2: Create a new queue

![Genesys Queue Page](images/create-queue-page.png)

Click create queue and specify a name for the new queue. Read about queue options [here](https://help.mypurecloud.com/articles/create-queues).

### Step 3: Configure the queue

![Genesys Queue Page](images/queue-config-page.png)

After specifying the members or groups associated with the new queue, you can simply take the defaults for the queue configuration with the exception of the `Voice Transcription` field outlined in red in the above image which needs to be set to On.


### Step 1: Go to the Integrations page from the Admin panel

![Genesys Admin Page](images/admin-page.png)

From the Admin panel click on the `Integrations` link to create a new integration.

### Step 2: Create a new integration

![Genesys Integrations Page](images/integrations-page.png)

Add a new integration by clicking the + sign in the top right of the page.

### Step 3: Install the Interaction Widget

![Genesys Interaction Widget Page](images/interaction-widget-page.png)

The Interaction Widget is needed to embed third-party webapps via an iframe in the Agent UI.

### Step 4: Configure the Interaction Widget

![Genesys Interaction Widget Config Page](images/interaction-widget-config-page.png)

After you provide a name for your widget on the Details tab, you will need to navigate to the Configuration tab. From here you will need to configure both the `Application URL`, the `Iframe Sandbox Options` and `Group Filtering`.

#### Application URL
The `Application URL` should take the following form:

```
http://<webserver>/genesys-agent-assist-verify.html?clientID=<genesys client id>&environment=mypurecloud.com&conversationID={{pcConversationId}}
```
`webserver`: host address and port for the webserver that is serving up the client application files included in this sample application.

`clientID`: generated when you create the OAuth credentials to be used for the implicit token login (see below).

`environment`: based on the location of your Genesys account. The environment shown works for North American accounts.

`conversationID`: generated by Genesys at runtime and the field will be populated by the agent application.

#### Iframe Sandbox Options
The `Iframe Sandbox Options` should be filled in with these options:

```
allow-scripts,allow-same-origin,allow-forms,allow-modals,allow-popups,allow-presentation,allow-downloads
```
#### Group Filtering
The `Group Filtering` section is selected from a pre-configured group that can be created from the Admin page under Directory->Groups. You need to make sure that all agents that require the new agent-verify widget are listed within the chosen group.

### Step 5: Configure OAuth for the Interaction Widget

![Genesys Interaction Widget Config Page](images/oauth-client-page.png)
Start by going to `OAuth` under Integrations and it `Add Client` to create a new OAuth client.

![Genesys Interaction Widget Config Page](images/oauth-client-details-top-page.png)

```
http://localhost:8080/genesys-agent-assist-verify.html?clientID=xxxxx&environment=mypurecloud.com
```

![Genesys Interaction Widget Config Page](images/oauth-client-details-bottom-page.png)

## Test Simulator Setup


## Using the Agent-Verify application


