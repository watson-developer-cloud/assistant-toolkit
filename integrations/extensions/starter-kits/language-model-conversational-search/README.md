# Language Model Conversational Search starter kit

You can use this starter kit to try a simple example of how the IBM watsonx tech preview language model and Watson Discovery search can be used to provide a personalized response to a customer.

## Prerequisites

This starter kit requires that you use the [new Watson Assistant](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-welcome-new-assistant).

Create a new, empty assistant that you can use to test this starter kit. For more information, see [Adding more assistants](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-assistant-add).

## Connect your assistant to a language model via custom extensions

Before you upload the sample action for this starter kit, you first need to configure two custom extensions: [watsonx tech preview](../language-model-watsonx-tech-preview) and [Watson Discovery](../watson-discovery).

Follow the steps in the following two sections before proceeding.

### Configure watsonx tech preview extension

- Follow the steps specified [in the watsonx tech preview starter kit](../language-model-watsonx-tech-preview/README.md#connect-your-assistant-to-watsonx) to configure the extension.

### Configure Watson Discovery extension

- Follow the pre-requisite steps specified [in the Watson Discovery starter kit](../watson-discovery/README.md#setting-up-watson-discovery) to set up Watson Discovery. For this use case, use a custom extension, *not* the built-in search integration.
- Download the OpenAPI specification ([`watson-discovery-query-openapi.json`](../watson-discovery/watson-discovery-query-openapi.json)) in from the Watson Discovery starter kit.
- [Add the extension to your assistant](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-add-custom-extension) using the API key you obtained in the pre-requisites above.
    - If you have an API key (as typical on IBM Cloud), select basic authentication, and it will ask for a username and password; for the username, enter `apikey` and for the password enter the API key you obtained in the pre-requisites above.
    - If you have a bearer token (as typical on Cloud Pak for Data), select bearer authentication, and it will ask for your bearer token.
    - Also fill in the portions of the URL for your instance _after_ `https://` in the `discovery_url` field.

## Upload sample action

The starter kit includes a JSON file with these sample actions:

| Action             | Description                                                                                                                                                      |
|--------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Search             | Connects to Watson Discovery to search for documents related to the user query.                                                                                  |
| Generate Answer    | Connects to watsonx tech preview and, using as context the documents resulting from the search, asks the language model to generate an answer to the user query. |

To use the sample actions:

1. Download the sample actions from this starter kit: [`ConvSearchStarter-action.json`](./ConvSearchStarter-action.json).

1. Use **Actions Global Settings** to upload the JSON file to your assistant. For more information, see [Uploading](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-admin-backup-restore#backup-restore-import).

1. Under "Variables"/"Created by you" (within the Actions page), set the `discovery_project_id` session variable using the project ID value you obtained [when configuring Watson Discovery above](#configure-watson-discovery-extension).

The advantage of the model chosen by default and the specific prompt used by the "Generate Answer" action is that it makes it easy to determine whether the query is not answerable. The downside, however, is that the generated answers tend to be short and terse.

If this does not suit your needs and you want to experiment with different prompts, do the following:

1. Go in the "Generate Answer" action and edit Step 5.

1. Edit the value of the `prompt` session variable. 

1. Modify the text of the prompt passed to the language model.  

Also, you can modify the `model_id` session variable to control which model is used.  You can see which models are available on [the model compatibility page of the watsonx tech preview API](https://bam.res.ibm.com/docs/models).

Here is an example of how to use the `Search` action:

<img src="./assets/sample.png" width="300"/>

# Remarks

The prompt in the starter kit comes from one of the `squad_v2` [prompt templates from FLAN](https://github.com/google-research/FLAN/blob/e9e4ec6e2701182c7a91af176f705310da541277/flan/templates.py).

Since FLAN-UL2 is trained on FLAN, matching the prompt they use might make the model more effective.

SQuAD v2 has a lot of unanswerable questions, so the FLAN prompt for SQuAD v2 might be particularly good for enabling the model to recognize when it should not answer.

SQuAD (including v2) has very concise answers, which could be one of the reasons why FLAN-UL2 is so terse when using SQuAD v2 prompts.