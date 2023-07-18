## Phone Starter Kit

This example contains Watson Assistant phone integration actions that demonstrate how to:

- How to use the phone and SMS channel for 2-factor authentication via the [send a text message](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-phone-actions#phone-actions-sms.)
- How to tune the Watson Speech-To-Text parameters in the Greetings action for background noise (`background_audio_suppression`) and increase the speech capture time (`end_of_phrase_silence_time`) by using the [speech_to_text](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-phone-actions#phone-actions-speech-advanced) response type.
- How to disable and enable Text-to-Speech barge-in via the [text_to_speech](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-phone-actions#phone-actions-text-advanced) response type.
- How to handle the input timeout from the phone channel via the `vgwPostResponseTimeout` keyword.
- How to disconnect the call when the caller is done using the [end_session](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-phone-actions#phone-actions-hangup) response type.
- How to transfer a call via a SIP Transfer using the [Connect To Agent Resolver](#transferring-the-call-via-the-sip-transfer-option-in-the-connect-to-agent-resolver)

### Setting up your own assistant

This example is configured to use an existing assistant set up for common use by anyone running this example. If you want to set up your own assistant, you'll need to perform the following steps.

- Import the [actions.json](./action.json) file located in the repository for this example.
- Optionally, to utilize the phone and SMS channel for 2-factor authentication, please create an [SMS integration](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-deploy-sms)
- For user input timeouts to be handled as digressions, go to your `Actions Settings > Change Conversation Topic > Conversation Topic` and enable `Allow changing the conversation topic for all actions.`
- Go to the `Actions > All items > Created by you` and you will see a `Phone Utilities` folder with a list of actions that can be used for phone interactions.

<img src="./assets/phone-utilities-actions.png" />

### Using the phone channel

Once you've setup the starter kit, if you haven't provisioned a phone integration, you can deploy one following [these steps](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-deploy-phone).

Here are some example phrases you can do to trigger the different phone utilities while on the phone with your Watson Assistant:

| Utterance | Phone Behavior | Action Title |
| --- | --- | --- |
| `Goodbye` or `You've answered all of my questions` | Watson Assistant will disconnect the call. | `Call Completion` |
| `<<< SILENCE >>>` | Watson Assistant will reprompt on the phone. | `vgwPostResponseTimeout` |
| `can I speak to an agent` | Watson Assistant will attempt to transfer your call. | `Connect To Agent` |



### Starter Kit Details
#### Transferring the call via the SIP Transfer option in the Connect To Agent Resolver

For phone interactions, typically a SIP transfer is required for transferring the call to an upstream SIP provider. In the Watson Assistant actions, you can use the SIP Transfer Option in the Connect To Agent ressolver to achieve this. 

1. Go to the "Connect To Agent" action, click on the first Action Step and click on `Edit Settings` under the `Connect to Agent Resolver`

<img src="./assets/connect-to-agent-resolver.png" />

2. To add your SIP Transfer information you can edit the fields such as `uri` and add other SIP headers in the configuration options of the Connect To Agent Resolver:

<img src="./assets/sip-transfer-option.png" />

You can find  more details on call transfers on the [Transferring a call to a human agent](https://cloud.ibm.com/docs/assistant?topic=assistant-dialog-voice-actions#dialog-voice-actions-transfer) documentation.


#### Speech Recognition Settings

For phone interactions, there may be cases depending on the use case where you need to tune the speech recognition parameters an example of this can be seen in the `Greeting` action (`Actions > Set by Assistant > Greeting`), specifying a `speech_to_text` response type allows you to modify the Speech recognition settings for the interactions with Watson Assistant over the phone:

<img src="./assets/greeting-speech-to-text.png"/>

For more information on modifying the Speech recognition settings, you can find the documentation in the [docs for the service](https://cloud.ibm.com/docs/assistant?topic=assistant-dialog-voice-actions#dialog-voice-actions-speech-advanced)


