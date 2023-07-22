# IBM watsonx language model starter kit

Watsonx is our enterprise-ready AI and data platform designed to multiply the impact of AI across your business. It provides an API for interacting with generative language models. You can use this starter kit to connect IBM Watson Assistant to the watsonx API.

This starter kit does not provide any examples of anything that you can _do_ with this extension. It can be used for specific use cases as the generative language model component.

The watsonx specification in the starter kit describes one endpoint, and a few of the most important of the configuration options that this endpoint provides.

| Endpoint   | Description                                                                                                                                                                 |
| ---------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Generation | Used with watsonx text completion models such as `google/flan-ul2` and `google/flan-t5-xxl`. You provide text as a prompt and it returns the text that follows that prompt. |

## Prerequisites

### Create an API key and a project id

1. Log into [watsonx](https://dataplatform.cloud.ibm.com/wx/home?context=wx&apps=cos&nocache=true&onboarding=true&quick_start_target=watsonx) and [generate an API key](https://dataplatform.cloud.ibm.com/docs/content/wsj/analyze-data/ml-authentication.html?context=cpdaas). Save this API key somewhere safe and accessible. You will need this API key to setup watsonx custom extension later.
1. To find your watsonx project id, navigate to [your projects](https://dataplatform.cloud.ibm.com/projects/). Open the project you would like to work in, and go to the Manage tab and the Services and integrations tab. Click Associate service + and associate an instance of Watson Machine Learning (WML).
   If you do not have an instance of WML, you can provision a Lite instance from the [IBM Cloud catalog](https://cloud.ibm.com/catalog/services/watson-machine-learning). Then complete this step using the new Lite instance.

### Create an assistant

This starter kit requires that you use the [new Watson Assistant](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-welcome-new-assistant).

Create a new, empty assistant that you can use to test this starter kit. For more information, see [Adding more assistants](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-assistant-add).

## Connect your assistant to watsonx

You connect your assistant by using a watsonx specification to add a custom extension.

### Download the watsonx specification

Download the watsonx specification file from the extension starter kit: [watsonx-openapi.json](./watsonx-openapi.json).

You use this specification file to create and add the extension to your assistant.

### Build and add extension

1.  In your assistant, on the **Integrations** page, click **Build custom extension** and use the watsonx specification file to build a custom extension named `watsonx`. For general instructions on building any custom extension, see [Building the custom extension](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-build-custom-extension#building-the-custom-extension).

1.  After you build the watsonx extension and it appears on your **Integrations** page, click **Add** to add it to your assistant. For general instructions on adding any custom extension, see [Adding an extension to your assistant](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-add-custom-extension).

1.  In **Authentication**, choose **OAuth 2.0**. Make sure grant type is **Custom apikey** selected in the next dropdown. Now copy and paste your [API key](#create-an-api-key-and-a-project-id) you saved earlier into the **Apikey** field.

If you need any capabilities that are not in the watsonx specification that we provided, feel free to add them to the watsonx openapi specification. The specification in the kit is intended to be a simple example of how to get started, not a comprehensive encoding of everything that API can do.

## Upload sample action

Use **Actions Global Settings** to upload the [`watsonx-actions.json`](./watsonx-actions.json) file in this kit to your assistant. For more information, see [Uploading](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-admin-backup-restore#backup-restore-import). You may also need to refresh the action Preview chat after uploading in order to get all the session variables initialized before these actions will work correctly.

1. Under "Variables"/"Created by you" (within the Actions page), set the `watsonx_project_id` session variable using [a project ID value from watsonx](https://dataplatform.cloud.ibm.com/docs/content/wsj/manage-data/manage-projects.html?context=wx&audience=wdp). See also [this section](#create-an-api-key-and-a-project-id) for additional details on how to find your project id.

**NOTE**: If you import the actions _before_ configuring the extensions, you will see some errors on the actions because it could not find the extensions. Simply configure the extensions (as described [above](#prerequisites)) and re-import the action JSON file.

The starter kit includes [a JSON file with sample actions](./watsonx-actions.json) that are configured to use the watsonx extension.

| Action                        | Description                                                                                                                                                                                   |
| ----------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Invoke watsonx Generation API | Connects to watson with the selected model and the model input                                                                                                                                |
| Test model                    | Simple test action that asks what model, length, temperature and prompt you want and then calls "Invoke watsonx Generation API" so the model can generate a response to the specified prompt. |

Note that the "Test model" action includes a step that invokes an extension and includes a parameter named `model_id`. You can set the `model_id` session variable to control which model is used by `Test model`. You can see which models work with the Generate API by viewing the supported foundation models in [the watsonx Prompt Lab](https://dataplatform.cloud.ibm.com/docs/content/wsj/analyze-data/fm-prompt-lab.html?context=wx).

### Session variables

These are the session variables used in this example.

- `model_id`: The id of the watsonx model that you select for this action. Defaults to `google/flan-ul2`.
- `model_input`: The input to the model.
- `model_parameters_max_new_tokens` : The maximum number of new tokens to be generated. Defaults to 300.
- `model_parameters_min_new_tokens`: The minimum number of the new tokens to be generated. Defaults to 1.
- `model_parameters_temperature` : The value used to control the next token probabilities. The range is from 0.05 to 1.00; 0 makes it _mostly_ deterministic.
- `model_parameters_repetition_penalty`: Represents the penalty for penalizing tokens that have already been generated or belong to the context. The range is 1.0 to 2.0 and defaults to 1.1.
- `model_parameters_stop_sequences`: Stop sequences are one or more strings which will cause the text generation to stop if/when they are produced as part of the output. Stop sequences encountered prior to the minimum number of tokens being generated will be ignored. The list may contain up to 6 strings. Defaults to ["\n\n"]
- `model_response`: The text generated by the model in response to the user input.
- `watsonx_api_version` - watsonx api date version. It currently defaults to `2023-05-29`.
- `watsonx_project_id`: You **MUST** set this value to be [a project ID value from watsonx](https://dataplatform.cloud.ibm.com/docs/content/wsj/manage-data/manage-projects.html). By default, this is a [sandbox project id](https://dataplatform.cloud.ibm.com/docs/content/wsj/manage-data/sandbox.html) that is automatically created for you when you sign up for watsonx.ai.

Here is an example of how to use the `Test model` action:

<img src="./assets/sample.png" width="300"/>

### Limitations

Adding large values for max_new_tokens & min_new_tokens can result in timeouts, if you see any timeouts please adjust the values of those tokens.
