

## Examples

This folder contains a set of examples that are intended to demonstrate how to use various features of the Watson Assistant phone channel. Below is a list of these examples, along with a brief explanation of each and the different features that each example demonstrates.


### [Common Interactions](./common-interactions/)

This example contains Watson Assistant phone integration actions that demonstrate how to:

- How to use the phone and SMS channel for 2-factor authentication
- How to tune the Watson Speech-To-Text parameters in the Greetings action for background noise (`background_audio_suppression`) and increase the speech capture time (`end_of_phrase_silence_time`).
- How to disable and enable Text-to-Speech barge-in
- How to handle the input timeout from the phone channel via the `vgwPostResponseTimeout` keyword
- How to disconnect the call when the caller is done.


### [Amazon Connect](./amazon-connect/)

These instructions provide an example of how to build a custom contact control panel (CCP) that grabs the data from an Amazon Connect flow and uses it to display the conversation history with Watson Assistant. It can be used as a reference for more complex and customer-specific use cases. 

The example used in these instructions is based on this [blog](https://aws.amazon.com/blogs/contact-center/perform-an-external-screen-pop-with-amazon-connect/). 
