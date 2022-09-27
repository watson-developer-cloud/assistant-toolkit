# Zendesk Guide article search

## Background

This is a starter kit for accessing the Zendesk "Search Articles" API, which is part of the Zendesk Guide feature.  It is one of two starter kits we have for working with Zendesk.  The other is the [Zendesk Support](https://github.com/watson-developer-cloud/assistant-toolkit/tree/master/integrations/extensions/starter-kits/zendesk-support) starter kit, which enables submitting and querying support tickets for the Zendesk Support feature.

The OpenAPI spec in this starter kit includes the following endpoint:

- `GET /api/v2/help_center/articles/search`: Search for articles relevant to a given query.

That endpoint is described in detail at https://developer.zendesk.com/api-reference/help_center/help-center-api/search/#search-articles.

## Basic vs Advanced
The `basic` starter kit implements a single yet useful API call for retrieving articles. If you are new to custom extensions, we recommend you start there to get an idea on how to use custom extensions with skills. The `advanced` folder contains a kit with the more advanced functionality of API parameters for filtering your search results, which you can use as a creative springboard for more complex use cases.

## Pre-Requisite Steps

Follow the steps listed in [Pre-Req: Getting Auth Keys and Configuring Your Server](https://github.com/watson-developer-cloud/assistant-toolkit/tree/master/integrations/extensions/starter-kits/zendesk-support#pre-req-getting-auth-keys-and-configuring-your-server) to configure Zendesk to let you access its APIs.

## Other Setup Info

### Setup in a new Assistant

If you want to make a _new_ Assistant using this starter kit, take the following steps:

- Download the OpenAPI specification (`zendesk-article-search-openapi.json`) and Actions JSON file (`zendesk-article-search-actions.json`) from the `basic` folder in this starter kit.
   - This section assumes the basic version of the starter kit, which will provide a basic Zendesk article search capability. For Zendesk filtered search, use the advanced version of the starter kit by downloading the files from the `advanced` folder.
- Use the OpenAPI specification to [build a custom extension](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-build-custom-extension#building-the-custom-extension). In the `Import OpenAPI` step, you will upload (by click or drag-and-drop) the `zendesk-article-search-openapi.json` file to specify the authentication and methods for your extension. You will be able to review the list of the servers and server variables found within the OpenAPI document.
- [Add the extension to your assistant](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-add-custom-extension) using the credentials you obtained in the first step above.
  - For Authentication, select the authentication type provided in your openapi specification from the drop-down menu. In this starter kit, it is `Basic auth`.
  - Next, enter the Zendesk username and password (typically {your_email}/token and the API token) that you obtained in the `Pre-Requisite Steps` section above.
  - Finally, enter the value for your subdomain to access zendesk.com. For example, if your url is https://my-test-domain.zendesk.com, you would enter `my-test-domain` in the Server variables section.
  - Review your extension setup and close to move on to the next steps.
- Now you are ready to add the actions from the starter kit. Select `Actions` in the upper left menu. Then select `Global settings` at the top right of the Actions window and select the `Upload/Download` tab. Now [upload `zendesk-article-search-actions.json`](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-admin-backup-restore#backup-restore-import) to define the actions in this starter kit. Return to `Actions` and you will see three actions: `Search`, `Show search results`, and `Show search result` from the starter kit in the `Created by you` section.
- Use either method listed in [Configuring Your Actions Skill to use an Extension](https://github.com/watson-developer-cloud/assistant-toolkit/blob/master/integrations/extensions/README.md#configuring-your-actions-skill-to-use-an-extension) to configure the actions you uploaded to invoke the custom extension you built.

If you are setting up in a new assistant, skip to the section `Using this Starter Kit`.  

### Setup in a pre-existing Assistant

If you want to add this starter kit to an _existing_ assistant, you cannot use the Actions JSON file (e.g.,`zendesk-article-search-actions.json`) since it will overwrite your existing configuration. So instead, follow the following process:

- Download the OpenAPI specification in this starter kit.
- Use the OpenAPI specification to [build a custom extension](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-build-custom-extension#building-the-custom-extension).
- [Add the extension to your assistant](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-add-custom-extension) using the credentials you obtained in the pre-requisite step above.
- Go to `Variables > Created by you` and add `query_text`, `search_results`, `search_result`, `link`, `title`, and `snippet`.
- Go to `Actions > Created by you` and create three new actions titled "Search", "Show search results", and Show search result" respectively.
- Click on the "Search" action and put "Search" in "What does your customer say to start this interaction?".  Add step 1:
  - Click the fX button to add a variable and add new session variable `query_text` and select "Expression" type and then put `input.text` or `input.original_text` as the expression.  The former will employ spelling correction to fix any detected spelling errors before sending the query, which can be helpful but it can also be counterproductive if your documents include specialized terminology that is not in our dictionary (such as product names) so you can use `input.original_text` as the alternative in such cases.
  - Optional: In "Assistant says", put `Searching for: ${query_text}`
  - In "And then", select "Use an extension", select the extension you made back in step 2, and select the search endpoint and set the `query` paramter to the `query_text` session variable.

![Add query step](./assets/add-query-step.gif)<br>

- Click "New Step" and change "without conditions" to "with conditions" and select "Ran successfully" is "false".  Also set "And then" to "End the action".  Then add the following to the "Assistant says":
Sorry.  The search failed!  Please try again another time.

![Add search failed step](./assets/search-failed-step.gif)<br>

- Still in the "Search" action, add a "New Step".  In the new step:
  - In "Assistant says" hit `$` and select "Ran Successfully" and then click on `</>` in the upper right of that box to see the full JSON for the response.  In there, you should see a field called `variable` with a value that looks something like `step_123_result_1`.  Copy that value.
  - Click "abc" in the upper right and delete the variable in "Assistant says" (we only put it there to copy the variable name).
  - Change "without conditions" to "with conditions" and select "true" for "Ran successfully" and "${step_123_result_1.body.results}.size > 0"
  - Click on "Variable values" and set `search_results` to "${step_123_result_1.body.results}". This allows you to pass the search results to actions that display them.
  - Set "And then" to "Go to another action" and select "Show search results" and "End current action" upon return.

![Pass results to display action](./assets/pass-results-to-display.gif)<br>

- Click "New Step" and change "without conditions" to "with conditions" and select "Ran successfully" is "true" and "${step_123_result_1.body.results}.size = 0".  Also set "And then" to "End the action".  Then add the following to the "Assistant says":
No search results were found for query "${query_text}"

![No results were found](./assets/no-results-step.gif)<br>

- Close the "Search" action.
- Click on the "Show search results" action and Add step 1:
  - Change "without conditions" to "with conditions" and add the expression ${search_results}.size> 0.
  - In "Variable values" set "search_result" to ${search_results}.get(0)
  - Set "And then" to "Go to another action" and select "Show search result" and "Continue" upon return.
- Duplicate this step by clicking on the "duplicate" icon in the left menu to show the next result.
  - Update the condition and the "search_result" variable assignment to the next available result. For the second result, set the condition to ${search_results}.size> 1, and the "search_result" to ${search_results}.get(1).
  - Duplicate this step for as many results as you want to display. For example, if you want to show 3 results, you will have 3 steps in this action, with "search_result" set to${search_results}.get(0), ${search_results}.get(1), and ${search_results}.get(2), respectively.
- Close the "Show search results" action.

![Show search results](./assets/show-search-results.gif)<br>


- Click on the "Show search result" action and Add step 1:
  - In "Variable values" set "link" to "$search_result.html_url", set "title" to "$search_result.title", and set "snippet" to "$search_result.snippet"
  - Then add the following to the "Assistant says" to display the result and then set "And then" to "End the action".

```
<a href="${link}" target="_blank">${title}</a>
${snippet}
```

- Close the "Show search result" action.

![Show search result](./assets/show-search-result.gif)<br>

- Close the action editor (by clicking X in the upper right)
- Go to "Actions" > "Set by assistant" > "No action matches" and remove all the steps from the action.  Add in a new step.  Under "And then" select "Go to another action" and select "Search" and click "End this action after the subaction is completed".
- You may also want to go to "Actions" > "Set by assistant" > "Fallback" and do the same thing as in the previous step.  Note, however, that this will prevent your assistant from escalating to a human agent when a customer asks to connect to a human agent (which is part of the default behavior for "Fallback") so only do this if you do not have your bot connected to a human agent chat service.  For more details on connecting to human agents within Watson Assistant see [our documentation](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-human-agent) and [blog post](https://medium.com/ibm-watson/bring-your-own-service-desk-to-watson-assistant-b39bc920075c).
- Go to the Search action and remove "Search" from the "Customer starts with" list so that the search action _only_ triggers via the "Go to another action" settings described in steps 13-15 above.  If you skip this, then the "Search" action will also be considered by the intent recognizer as a possible intent, which adds unnecessary complexity to the intent recognition and thus could result in lower overall intent recognition accuracy.

## Using this Starter Kit

### Basic
Once this starter kit is properly installed, you can issue a query to your bot and if there is no other action that you've configured that matched that query then it will generate search results for that query.

### Advanced
[The Zendesk article search API](https://developer.zendesk.com/api-reference/help_center/help-center-api/search/#search-articles) includes several parameters that can be used to filter the search results. For example, you can filter results based on when a document was created or updated. To use the filter parameters in the advanced starter kit, follow the installation instructions given above to install the starter kit, but use the OpenAPI specification (`zendesk-article-advanced-search-openapi.json`) and Actions JSON file (`zendesk-article-advanced-search-actions.json`) from the `advanced` folder.

If you are setting up in a new assistant, after you have uploaded the Actions JSON file (`zendesk-article-advanced-search-actions.json`), return to `Actions` and you will see four actions: `Search`, `Search by document creation date`, `Show search results`, and `Show search result` from the starter kit in the `Created by you` section.

#### Example Usage for Filtered Query by Document Creation Date
The `Search by document creation date` is preconfigured in the starter kit to conduct the query search in documents created only after `created_by_date`. This section describes how to create this filtered search action.
- Go to `Variables` -> `Created by you`. Create a session variable `created_by_date` and set it to a date. In this example it is 2022-07-15. This will be the value of the `created_after` query parameter used to filter the results in this example.

![Create session variable](./assets/create-filter-session-variable.gif)<br>

- Go to `Actions` -> `Created by you`. Duplicate the basic search action.
- Rename the new action to "Search by document creation date", or something similar.
- Enter the actions editor by clicking on the new "Search by document creation date" action that you just created. Enter phrases that a customer types or says to start the conversation to trigger this filtered search. In this example, we use "recent rates" and "recent interest rates" to identify the `Search by document creation date` action.

![Create new search action](./assets/search-by-create-date-action.gif)<br>

- Next, go to the first conversation step and add the filter parameter to your extension. In "And then", select "edit extension" to set the `created_after` parameter to the session variable `created_by_date` that you just created.

![Set filter parameter in extension](./assets/set-filter-parameter-in-extension.gif)<br>

- Now the set up of your filtered search by creation date action is complete. You can try out your new filtered search action by typing "recent rates" in the Preview.

![Try out filtered search](./assets/recent-rates.gif)<br>
