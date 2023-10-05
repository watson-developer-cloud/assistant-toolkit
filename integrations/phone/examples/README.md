

## Examples

This folder contains a set of examples that are intended to demonstrate how to use various features of the IBM watsonx Assistant phone channel. Below is a list of these examples, along with a brief explanation of each and the different features that each example demonstrates.


### [Common Interactions](./common-interactions/)

This example contains IBM watsonx Assistant phone integration actions that demonstrate:

- How to use the phone and SMS channel for two-factor authentication.
- How to tune the Watson Speech-To-Text parameters in the Greetings action for background noise (`background_audio_suppression`) and increase the speech capture time (`end_of_phrase_silence_time`).
- How to disable and enable Text-to-Speech barge-in.
- How to handle the input timeout from the phone channel via the `vgwPostResponseTimeout` keyword.
- How to disconnect the call when the caller is done.


### [Amazon Connect](./amazon-connect/)

This custom contact control panel (CCP) grabs data from an Amazon Connect flow and uses it to display the conversation history with IBM watsonx Assistant, and can be a reference for more complex and customer-specific cases.

The example in these instructions is based on this [blog](https://aws.amazon.com/blogs/contact-center/perform-an-external-screen-pop-with-amazon-connect/). 
