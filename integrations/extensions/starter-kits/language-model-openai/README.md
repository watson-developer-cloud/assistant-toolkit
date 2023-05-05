# OpenAI language model starter kit

OpenAI is an AI research and deployment company, which is best known for the [ChatGPT](https://openai.com/blog/chatgpt) conversational application. OpenAI provides an [API](https://platform.openai.com/docs/api-reference) for interacting with their generative language models. You can use this starter kit to connect IBM Watson Assistant to the OpenAI API.

This starter kit does not provide any examples of anything that you can _do_ with this extension.  Instead, we have two general-purpose kits that show _use cases_ generative language models, and we may add more in the future.  The two use case starter kits we have now are:

- [language-model-personalization](../language-model-personalization)
- [language-model-summarization](../language-model-summarization)

The OpenAPI specification in the starter kit describes two endpoints, and a few of the most important of the configuration options that these endpoints provide.

| Endpoint | Description |
| --- | --- |
| Create completion | Used with the OpenAI text completion models such as `text-davinci-003` and `text-curie-001`. You provide text as a prompt and it returns the text that follows that prompt. |
| Create chat completion | Used with the OpenAI chat models such as `gpt-3.5-turbo` and `gpt-4`. You provide a chat transcript consisting of an optional system instruction and then a partial chat transcript. It then returns the next response in that chat. This starter kit uses this API in an extremely simple way. It doesn't send a system instruction. It sends a single text prompt to the model as a single user utterance to get a single response from the model. For more complex use cases, you might want to include multiple utterances in the API call to provide some additional context for a request. |

## Prerequisites

### Create an API key

1. Create an account with [OpenAI](https://openai.com/).
1. Manage your account to [create a new API Key](https://platform.openai.com/account/api-keys). Save this API key somewhere safe and accessible. You can't view it again in your OpenAI account. If you lose this secret key, you need to generate a new one.

### Create an assistant

This starter kit requires that you use the [new Watson Assistant](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-welcome-new-assistant).

Create a new, empty assistant that you can use to test this starter kit. For more information, see [Adding more assistants](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-assistant-add).

## Connect your assistant to OpenAI

You connect your assistant by using an OpenAPI specification to add a custom extension.

### Download the OpenAPI specification

Download the OpenAPI specification file from the extension starter kit: [openAI.openapi.json](./openAI.openapi.json).

You use this specification file to create and add the extension to your assistant.

### Build and add extension

1.  In your assistant, on the **Integrations** page, click **Build custom extension** and use the OpenAPI specification file to build a custom extension named `OpenAI`. For general instructions on building any custom extension, see [Building the custom extension](/docs/watson-assistant?topic=watson-assistant-build-custom-extension#building-the-custom-extension).

1. After you build the OpenAI extension and it appears on your **Integrations** page, click **Add** to add it to your assistant. For general instructions on adding any custom extension, see [Adding an extension to your assistant](/docs/watson-assistant?topic=watson-assistant-add-custom-extension).

1. In **Authentication**, choose **Bearer auth**. Copy and paste your OpenAI API key into the **Token** field.

For more information about the extension parameters, see the OpenAI API Reference:
- [Create completion](https://platform.openai.com/docs/api-reference/completions)
- [Create chat completion](https://platform.openai.com/docs/api-reference/chat)

If you need any capabilities that are in the documentation but not in the OpenAPI specification that we provided, feel free to add them to the OpenAPI specification.  The specification in the kit is intended to be a simple example of how to get started, not a comprehensive encoding of everything that API can do.

## Upload sample actions

The starter kit includes a JSON file with sample actions that are configured to use the OpenAI extension.

| Action | Description |
| --- | --- |
| Invoke GPT Chat Completion API | Connects to OpenAI with the `gpt-3.5-turbo` model, which provides a level of quality that is nearly as good as `text-davinci-003` at a much lower cost. |
| Invoke GPT Completion API | Connects to OpenAI with the `text-davinci-003` model, which is more expensive. For more information, see [OpenAI Pricing](https://openai.com/pricing). |

Note that both of these actions include a step that invokes an extension and includes a parameter named `model`.  You can edit that extension call to set a different value for model.  For example, `Invoke GPT Chat Completion API` can be updated to point call `gpt-4` instead of `gpt-3.5-turbo`, which will make it much more expensive but potentially higher quality; note though that at the time that we are publishing this kit, `gpt-4` has only limited availability so you will need to request access from OpenAI before using this value for `model`.  You can see which models work with the Chat Competion API and which models work with the Completion API on [OpenAI's model compatibility page](https://platform.openai.com/docs/models/model-endpoint-compatibility).

## Do not use this as a fallback strategy

You can get either of these actions to just respond to _anything_ a user says by updating "No action matches" (and optionally "Fallback") built-in actions in Watson Assistant to set `model_prompt` to `input.text`, call one of these actions, and then put the `model_response` in "Assistant says".  With more work, you can even keep track of things the assistant and the user have said to each other and put them into the prompt too, ideally as separate entries in the `messages` list of the chat completion API as described in the [chat completion API documentation](https://platform.openai.com/docs/api-reference/chat); the result would be something similar to the ChatGPT experience in which you would have an ongoing conversation with the model.  This can be a fun thing to try out, but we strongly recommend that you do not do this for any serious chatbot that you use to represent your enterprise, since you would no control over what it will say.
