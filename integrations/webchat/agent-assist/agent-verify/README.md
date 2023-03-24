# Agent Verify

## Background
This agent-assist starter kit combines Watson Assistant's webchat with a wraper application that monitors real-time caller/agent transcriptions to provide the following features:

1. Agent prompting based on customer input.
2. Verification that agent speaks certain prompts back to the caller verbatim.
3. The ability to use callers speech transcriptions as input to Watson Assistant.

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

## Pre-Requisite Steps
### Auth Info
Remove this section if the API does not require authentication.
```
Should include, at minimum:
- Which auth scheme(s) this API uses, and which ones are supported in your OpenAPI spec
- Numbered step-by-step guide on how to obtain any/all API keys for each scheme your OpenAPI spec supports
- Screenshots/GIFs (optional)
- Link to Auth reference page for this API
```

### Other Setup Info
Remove this section if no other setup is required.
```
Should include, at minimum:
- Step-by-step guide on how to setup the extension, on the extension/account side
- Any special custom variables you may have used in the starter kit
- Screenshots/GIFs (optional)
```

**Example:** <br> [HubSpot](../starter-kits/hubspot/README.md#pre-req-1:-getting-auth-keys)


## Using this Starter Kit
```
Should include, at minimum:
- Itemized list of actions provided in skill
    - Description of each action
    - Which extension operation/parameters are used, with corresponding variable names used by the skill
- A screenshot of a typical conversation that utilizes this skill/spec
- Any notes or limitations, known issues
```

**Example:** <br> [HubSpot](../starter-kits/hubspot/README.md#using-this-starter-kit)
