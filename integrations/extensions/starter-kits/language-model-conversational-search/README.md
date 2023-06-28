# Language Model Conversational Search starter kit

This starter kit has multiple examples of how to configure language models with Watson Assistant for conversational search.

1. The first example shows how to use [Watson Discovery search as input to the IBM watsonx tech preview language model](#example-1-connect-your-assistant-to-a-watsonx-language-model-via-custom-extensions).
1. The second example shows how to use [semantic search output as input to an OpenAI model](#example-2-connect-your-assistant-to-hugging-face-milvus-and-openai-via-custom-extensions)
1. The third example shows how to use [Watson Discovery search as input to an OpenAI model](#example-3-connect-your-assistant-to-watson-discovery-and-openai-via-custom-extensions)

The [prerequisite for a new Watson Assistant](#prerequisites) applies for all examples.

## Prerequisites

All examples in this starter kit require that you use the [new Watson Assistant](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-welcome-new-assistant).

Create a new, empty assistant that you can use to test this starter kit. For more information, see [Adding more assistants](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-assistant-add).

## Example 1: Connect your assistant to a watsonx language model via custom extensions

Before you upload the sample action for this starter kit, you first need to configure two custom extensions: [watsonx tech preview](../language-model-watsonx-tech-preview/README.md) and [Watson Discovery](../watson-discovery/README.md).

Follow the steps in the following two sections before proceeding.

### Configure watsonx tech preview extension

- Follow the steps specified [in the watsonx tech preview starter kit](../language-model-watsonx-tech-preview/README.md#connect-your-assistant-to-watsonx) to configure the extension.

### Configure Watson Discovery extension

- Follow the pre-requisite steps specified [in the Watson Discovery starter kit](../watson-discovery/README.md#setting-up-watson-discovery) to set up Watson Discovery. For this use case, use a custom extension, _not_ the built-in search integration.
- Download the OpenAPI specification ([`watson-discovery-query-openapi.json`](../watson-discovery/watson-discovery-query-openapi.json)) in from the Watson Discovery starter kit.
- [Add the extension to your assistant](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-add-custom-extension) using the API key you obtained in the pre-requisites above.
  - If you have an API key (as typical on IBM Cloud), select basic authentication, and it will ask for a username and password; for the username, enter `apikey` and for the password enter the API key you obtained in the pre-requisites above.
  - If you have a bearer token (as typical on Cloud Pak for Data), select bearer authentication, and it will ask for your bearer token.
  - Also fill in the portions of the URL for your instance _after_ `https://` in the `discovery_url` field.

### Upload sample action

The starter kit includes a JSON file with these sample actions:

| Action          | Description                                                                                                                                                                                                                                                                                                     |
| --------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Search          | Connects to Watson Discovery to search for documents related to the user query. The "No Action Matches" action has been configured to send all input to this action, so whatever the user enters will be used as the search input. It invokes the "Generate Answer" action to generate a response to the query. |
| Generate Answer | Connects to watsonx tech preview and, using as context the documents resulting from the search, asks the language model to generate an answer to the user query. It is not meant to be invoked directly, but rather by the "Search" action.                                                                     |

To use the sample actions:

1. **After having configured both extensions**, download the sample actions from this starter kit: [`discovery-watsonx-actions.json`](./discovery-watsonx-actions.json).

1. Use **Actions Global Settings** to upload the JSON file to your assistant. For more information, see [Uploading](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-admin-backup-restore#backup-restore-import).

1. Under "Variables"/"Created by you" (within the Actions page), set the `discovery_project_id` session variable using the project ID value you obtained [when configuring Watson Discovery above](#configure-watson-discovery-extension).

**NOTE**: If you import the actions _before_ configuring the extensions, you will see some errors on the actions because it could not find the extensions. Simply configure the extensions (as described [above](#prerequisites)) and re-import the action JSON file.

#### Session variables

These are the session variables used in this example.

- `answer`: The text generated by the model in response to the user prompt.
- `discovery_date_version` - Discovery date versions are documented in the [release notes](https://cloud.ibm.com/docs/discovery-data?topic=discovery-data-release-notes).
- `discovery_project_id`: You **MUST** set this value to be the project ID obtained [when configuring Watson Discovery above](#configure-watson-discovery-extension).
- `prompt`: You MAY change this to do prompt engineering, but a default will be used by watsonx if you don’t pass a prompt here.
- `query_text`: You MAY change this to pass queries to Watson Discovery. By default the Search action passes the user’s input.text directly.
- `search_results`: Response object from [Discovery query](https://cloud.ibm.com/apidocs/discovery-data#query).

### Language model

The advantage of the model chosen by default and the specific prompt used by the "Generate Answer" action is that it makes it easy to determine whether the query is not answerable. The downside, however, is that the generated answers tend to be short and terse.

If this does not suit your needs and you want to experiment with different prompts, do the following:

1. Go in the "Generate Answer" action and edit Step 5.

1. Edit the value of the `prompt` session variable.

1. Modify the text of the prompt passed to the language model.

Also, you can modify the `model_id` session variable to control which model is used. You can see which models are available on [the model compatibility page of the watsonx tech preview API](https://workbench.res.ibm.com/docs/models).

### Example 1 usage

Here is an example of how to use the `Search` action:

<img src="./assets/sample.png" width="300"/>

### Remarks

The prompt in the starter kit comes from one of the `squad_v2` [prompt templates from FLAN](https://github.com/google-research/FLAN/blob/e9e4ec6e2701182c7a91af176f705310da541277/flan/templates.py).

Since FLAN-UL2 is trained on FLAN, matching the prompt they use might make the model more effective.

SQuAD v2 has a lot of unanswerable questions, so the FLAN prompt for SQuAD v2 might be particularly good for enabling the model to recognize when it should not answer.

SQuAD (including v2) has very concise answers, which could be one of the reasons why FLAN-UL2 is so terse when using SQuAD v2 prompts.

## Example 2: Connect your assistant to Hugging Face, Milvus, and OpenAI via custom extensions

This example shows how to use Hugging Face to generate query embeddings for semantic search and use those results to generate an answer with the OpenAI model.

Before you upload the sample action for this starter kit, you first need to configure multiple custom extensions: [Hugging Face Hub Embeddings](https://huggingface.co/blog/getting-started-with-embeddings), [Milvus](https://milvus.io) for semantic search, and [OpenAI](../language-model-openai/README.md) for answer generation.

Follow the steps in the following sections for the custom extensions setup before uploading the sample action.

### Configure the Hugging Face Hub Embeddings extension

1. Sign up at [Hugging Face](https://huggingface.co/) and follow the instructions there to get an API key. Save this API key somewhere safe and accessible.

1. Download [the Hugging Face Hub OpenAPI specification file](hugging-face-hub-embed-openapi.json) from this starter kit. You use this specification file to create and add the Hugging Face Hub Embeddings extension to your assistant.

1. In your assistant, on the **Integrations** page, click **Build custom extension** and use the OpenAPI specification file to build a custom extension named `Hugging Face Hub Embeddings`. For general instructions on building any custom extension, see [Building the custom extension](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-build-custom-extension#building-the-custom-extension).

1. After you build the Hugging Face Hub Embeddings extension and it appears on your **Integrations** page, click **Add** to add it to your assistant. For general instructions on adding any custom extension, see [Adding an extension to your assistant](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-add-custom-extension).

1. In **Authentication**, choose **Bearer auth**. Copy and paste your Hugging Face API key into the **Token** field.

1. The setup of your extension is now complete. For more information about the extension parameters, see the [Hugging Face API Reference](https://huggingface.co/docs/hub/api).

### Configure the Milvus semantic search extension

Milvus is an open-source vector database for semantic search.

1. Follow the instructions at [Milvus](https://milvus.io) to [install it](https://milvus.io/docs/install_standalone-docker.md) somewhere that your assistant can access it. You will need the server variables (ip-address and REST port) to connect it with your assistant. One option for where to host is an [IBM virtual server](https://www.ibm.com/cloud/virtual-servers). Another is [IBM code engine](https://www.ibm.com/cloud/code-engine). However, it can also be installed on a separate machine as long as it is connected to the internet. Alternatively, Milvus can be run on a different cloud such as AWS. You need to record the IP address of the machine where it's installed Milvus and the port where it is running. Note that there are 2 Milvus ports. One port is for the REST API which is the one used for the assistant extension connection, and it defaults to 9091. The other port is for gRPC requests used for indexing the documents, and it defaults to 19530.

1. Add your content to Milvus. In this example, we use Milvus to search a document collection that we previously indexed with [this python script](index-with-milvus.py). We include this script as an example for reference, together with a [requirements.txt](requirements.txt) file that will install the required dependencies for the python script when you run the command `pip install -r requirements.txt`. In our example we use `.pdf` documents; the script can be modified to use other text formats. We have not included sample documents for search because we encourage you to use your own and modify the script accordingly.

1. Download [the Milvus OpenAPI specification file](milvus-openapi.json) from this starter kit. You use this specification file to create and add the Milvus extension to your assistant.

1. In your assistant, on the **Integrations** page, click **Build custom extension** and use the OpenAPI specification file to build a custom extension named `Milvus`. For general instructions on building any custom extension, see [Building the custom extension](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-build-custom-extension#building-the-custom-extension).

1. After you build the Milvus extension and it appears on your **Integrations** page, click **Add** to add it to your assistant. For general instructions on adding any custom extension, see [Adding an extension to your assistant](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-add-custom-extension).

1. In **Authentication**, choose **No authentication** and enter values for your Milvus server variables (i.e., ip-address:rest-port, e.g., `123.456.78.910:9091`) to create a valid generated URL for requests. However, if you configured Milvus to require basic authentication, then choose **Basic auth** and enter your Milvus credentials.

1. The setup of your Milvus extension for Watson Assistant is complete. For more information about the extension parameters, see the [Milvus API Reference](https://milvus.io/docs/search.md).

### Configure the OpenAI answer generation extension

Follow the steps in the starter kit for [language model openAI](../language-model-openai/README.md) to get an OpenAI api key and use it to connect with your assistant via a custom extension.

### Upload sample action

The starter kit includes [a JSON file with these sample actions](./vector-openai-actions.json):

| Action                         | Description                                                                                                                                                                                                                                                                                                                                         |
| ------------------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Search                         | Connects to Hugging Face to generate vectors for the user query, and to Milvus for semantic search of a collection. The "No Action Matches" action has been configured to send all input to this action, so whatever the user enters will be used as the search input. It invokes the "Generate Answer" action to generate a response to the query. |
| Generate Answer                | Configures the query prompt and document passages resulting from search, and calls the action "Invoke GPT Chat Completion API". It is not meant to be invoked directly, but rather by the "Search" action.                                                                                                                                          |
| Invoke GPT Chat Completion API | Connects to OpenAI and, using as context the documents resulting from the search, asks the language model to generate an answer to the user query. It is not meant to be invoked directly, but rather by the "Generate Answer" action.                                                                                                              |

To use the sample actions:

1. **After having configured all three extensions**, download the sample actions from this starter kit: [`vector-openai-actions.json`](./vector-openai-actions.json).

1. Use **Actions Global Settings** to upload the sample actions JSON file to your assistant. For more information, see [Uploading](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-admin-backup-restore#backup-restore-import). Once you have uploaded the actions, refresh the preview to make sure the assistant registers all of the variable assignments.

### Session variables

These are the session variables used in this example. Most of values are set in the process of setting up the starter kit. The user must set `collection_name` to identify the Milvus collection for the document search step.

- `collection_name`: This **MUST** be set to the name of the document collection in Milvus to be searched.
- `embedding_model_id` : ID of the model to use for generating embeddings for the query.
- `messages` : Input to the OpenAI model; includes the `search_results` and the `model_prompt`.
- `model_for_chat` : The OpenAI model used, defaults to `gpt-3.5-turbo`.
- `model_max_tokens` : The maximum number of text fragments to input to the model. The starter kit uses 200.
- `model_prompt`: You MAY change this to do prompt engineering, but a default will be used by the model if you don’t pass a prompt here.
- `model_response`: The text generated by the model in response to the `messages`.
- `passages` : Concatenation of top search results.
- `query_text`: By default the Search action passes the user’s input.text directly.
- `search_results` : Results from the semantic search for the query. These will be input to the OpenAI extension model.
- `snippet` : Top results from the semantic search.

### Language model

If you want to experiment with different prompts, do the following:

1. Go in the "Generate Answers" action and edit Step 5.

1. Edit the value of the `model_prompt` session variable.

1. Modify the text of the prompt passed to the language model.

You can also modify the `model_for_chat` session variable to control which model is used. This starter kit uses `gpt-3.5-turbo` because it performs well and reasonably fast. You can see the available models on [OpenAI language models](https://platform.openai.com/docs/models).

### Example 2 usage

Here is an example of how to use the `Search` action for this starter kit advanced conversational search example:

<img src="./assets/apr_for_preferred.png" width="300"/>

## Example 3: Connect your assistant to Watson Discovery and OpenAI via custom extensions

This starter kit example shows how to configure your assistant to use Watson Discovery for document search, and then use those search results as input context for an OpenAI large language model. The OpenAI LLM generates a natural language answer for the query based on the documents provided by the search.

Before you upload the sample action for this starter kit, you first need to configure two custom extensions: [Watson Discovery](../watson-discovery/README.md) and [OpenAI](../language-model-openai/README.md)).

Follow the steps in the following two sections to configure your extensions before proceeding.

### Configure Watson Discovery extension

Follow the steps [here](#configure-watson-discovery-extension) to configure the Watson Discovery extension.

### Configure the OpenAI answer generation extension

Follow the steps [here](#configure-the-openai-answer-generation-extension) to configure OpenAI as a custom extension.

### Upload sample action

The starter kit includes [a JSON file with these sample actions](./discovery-openai-actions.json):

| Action                         | Description                                                                                                                                                                                                                                                                                                     |
| ------------------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Search                         | Connects to Watson Discovery to search for documents related to the user query. The "No Action Matches" action has been configured to send all input to this action, so whatever the user enters will be used as the search input. It invokes the "Generate Answer" action to generate a response to the query. |
| Generate Answer                | Configures the query prompt and document passages resulting from search, and calls the action "Invoke GPT Chat Completion API". It is not meant to be invoked directly, but rather by the "Search" action.                                                                                                      |
| Invoke GPT Chat Completion API | Connects to OpenAI and, using as context the documents resulting from the search, asks the language model to generate an answer to the user query. It is not meant to be invoked directly, but rather by the "Generate Answer" action.                                                                          |

To use the sample actions:

1. **After having configured both extensions**, download the sample actions from this starter kit: [`discovery-openai-actions.json`](./discovery-openai-actions.json).

1. Use **Actions Global Settings** to upload the JSON file to your assistant. For more information, see [Uploading](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-admin-backup-restore#backup-restore-import).

1. Under "Variables"/"Created by you" (within the Actions page), set the `discovery_project_id` session variable using the project ID value you obtained [when configuring Watson Discovery above](#configure-watson-discovery-extension).

**NOTE**: If you import the actions _before_ configuring the extensions, you will see some errors on the actions because it could not find the extensions. Simply configure the extensions as described above and re-import the action JSON file.

#### Session variables

Below is a list of the session variables used in this example. Most of them are automatically set with defaults in the sample [discovery-openai-actions.json](discovery-openai-actions.json), so you do not need to set them yourself unless you want to make changes. You must, however, [set](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-manage-info#store-session-variable) the `discovery_project_id` to point to the project id for your Watson Discovery collection, as noted above.

- `discovery_date_version` - Discovery date versions are documented in the [release notes](https://cloud.ibm.com/docs/discovery-data?topic=discovery-data-release-notes).
- `discovery_project_id`: You **MUST** set this value to be the project ID obtained [when configuring Watson Discovery above](#configure-watson-discovery-extension).
- `messages` : Input to the OpenAI model, using the `model_prompt` from the sample actions file, for example `[{"role": "user", "content": “${model_prompt}”}]`
- `model_for_chat` : The OpenAI model used, defaults to `gpt-3.5-turbo`.
- `model_max_tokens` : The maximum number of text fragments to input to the model. The starter kit uses 200.
- `model_prompt`: You MAY change this to do prompt engineering, but a default will be used by the model if you don’t pass a prompt here.
- `model_response`: The text generated by the model in response to the `messages`.
- `passages` : Concatenation of top search results.
- `query_text`: You MAY change this to pass queries to Watson Discovery. By default the Search action passes the user’s input.text directly.
- `search_results`: Response object from [Discovery query](https://cloud.ibm.com/apidocs/discovery-data#query).
- `snippet` : Top results from the Watson Discovery document search.

### Language model

If you want to experiment with different prompts or language models for the OpenAI custom extension, see [these tips](#language-model-1)

### Example 3 usage

Here is an example of how to use the `Search` action for this starter kit advanced conversational search example:

<img src="./assets/discovery-openai-example.png" width="300"/>
