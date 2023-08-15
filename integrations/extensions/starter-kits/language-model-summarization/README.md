# Language Model Summarization starter kit

This starter kit uses a generative language model to collect and summarize a customer's answers via the Watson Assistant [session_history variable](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-publish-overview#publish-overview-environment-settings-session-history). It sends the summary to a customer service agent when the assistant escalates to an agent. This can enable a customer service agent who enters a chat session to have an overview of what has been said in the chat so far without having to read an entire transcript.

This starter kit includes examples of how to configure different language models with Watson Assistant for summarization.

1. The first example shows how to use [OpenAI for summarization](#example-1-connect-your-assistant-to-openai-using-a-custom-extension)
1. The second example shows how to use [watsonx for summarization](#example-2-connect-your-assstant-to-watsonx-via-a-custom-extensions)

## Prerequisites

This starter kit requires that you use the [new Watson Assistant](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-welcome-new-assistant).

Create a new, empty assistant that you can use to test this starter kit. For more information, see [Adding more assistants](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-assistant-add).

## Example 1: Connect your assistant to OpenAI using a custom extension

You connect your assistant by using an OpenAPI specification to add a custom extension. You can see an example of how to do this in the [OpenAI starter kit](../language-model-openai), which shows how to connect to OpenAI models like GPT 3.5 and 4. However, you can also do the same thing with any other generative language model that has a REST API using the same approach. For details on how to use an alternative language model, see below.

## Upload sample actions

Use **Actions Global Settings** to upload the [summarization-openai-actions.json](summarization-openai-actions.json) file in this kit to your assistant. For more information, see [Uploading](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-admin-backup-restore#backup-restore-import). You may also need to refresh the action Preview chat after uploading in order to get all the session variables initialized before these actions will work correctly.

The starter kit includes a JSON file with these sample actions:

| Action                         | Description                                                                                                                                                                                                                                                                                                     |
| ------------------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Check order status             | A simple action to check the status of a customer order.                                                                                                                                                                                                                                                        |
| Escalate to Agent              | Simple example of how OpenAI can be used to provide a summary of a customer conversation. Connects to OpenAI using the `Invoke GPT Chat Completion API` action and shows the summary to the user. It also triggers an escalation to a customer service agent with the summary in the message sent to the agent. |
| Invoke GPT Chat Completion API | Connects to OpenAI with the `gpt-3.5-turbo` model, which provides a level of quality that is nearly as good as `text-davinci-003` at a much lower cost.                                                                                                                                                         |

To use the sample actions:

1. Download the sample actions from this starter kit: [summarization-openai-actions.json](summarization-openai-actions.json).

1. Use **Actions Global Settings** to upload the JSON file to your assistant. For more information, see [Uploading](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-admin-backup-restore#backup-restore-import).

## Preview the sample actions

To preview the sample actions, you'll use the `Check order status` action to start a conversation, which WA will then escalate to an agent to see a summary provided by OpenAI.

1. On the **Actions** page, click **Preview**.

1. Enter `Check order status`.

1. Enter an order number when prompted by the assistant.

1. The assistant will report that there is a problem checking the order status and ask if it's ok to escalate to a human agent.

1. If the user response is yes, the system escalates to a human agent with a summary of the interaction.

An example response might look something like this: `The user asks about the status of their order. The chatbot asks for the order number. The user provides the order number, but the chatbot tells them there is a problem checking the status. The chatbot offers to escalate the issue to a human agent and asks if the user wants to do this. The user agrees to escalate the issue.`

1. After showing the summary to the user, it then initiates a transfer to a human agent. Unless you have configured a contact center, this will not work, but it is included in the kit to show how it would be invoked if you did have one configured. See details in the next section.

1. If the user declines to escalate to an agent, the transaction ends.

## Technical Details

The interaction between the assistant and the user is recorded in the [session_history_variable](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-publish-overview#publish-overview-environment-settings-session-history).Here is an example of the session history for the check order status use case: `[{"a":"Welcome, how can I assist you?"},{"u":"order status","n":true},{"a":"To get started, please enter your order number"},{"u":"1234"},{"a":"Thanks! There is a problem checking the status for your order 1234\n\n
\n\n\n\nWould you like me to escalate to an agent?\noption: ["Yes","No"]"},{"u":"Yes"}]`.

This use case has configured the human agent escalation process to use the language model to summarize the session and present the summary to both the user and the human agent. You can see that the model response is sent to the agent by going to step 3 of the `Escalate to Agent` action and clicking on "Edit settings" in the "Connect to agent" block at the bottom of the step. You should see this:

<img src="./assets/connect.png" width="500"/>

As you can see, the "Message to agent" field is set to the `model_response`, i.e., the summary that the language model generated.

In the starter kit, this is not configured to a real contact center, so no actual human agent is contacted. See [Adding contact center support](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-deploy-web-chat-haa) in the Watson Assistant documentation for details on how to connect to a real contact center. Here is what the output looks like in the Preview page of Watson Assistant (including the error message because no contact center has been configured):

<img src="./assets/order-status-summarization.png" width="300"/>
