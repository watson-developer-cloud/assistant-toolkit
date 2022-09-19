# Google custom search

## Background

This is a starter kit for accessing the Google "Custom Search" JSON API. The "Custom Search" API allows search over a website, collection of websites or the world wide web using the [Google Programmable Search Engine](https://developers.google.com/custom-search/docs/overview) which is a configurable search that allows you to customize search features based on your use-case.

The OpenAPI spec in this starter kit includes the following endpoints:

- `GET /customsearch/v1`: Search for content relevant to a given query over the entire web.
- `GET /customsearch/v1/siterestrict`: Search for content relevant to a given query over a specific collection of websites.

The endpoints are described in detail at:
 
1. **Custom Search**: https://developers.google.com/custom-search/v1/reference/rest/v1/cse/list 
2. **Custom Search Site Restricted**: https://developers.google.com/custom-search/v1/reference/rest/v1/cse.siterestrict/list

The Site Restricted API is similar to the Custom Search JSON API except that it has no daily query [limit](https://developers.google.com/custom-search/v1/overview#pricing). It is important to note that the Site Restricted API is used to be run with a Programmable Search Engine which is restricted to only searching specific sites (10 or fewer) whereas the regular Custom Search API can be run with a search engine which searches over the entire web.

This starter kit exposes only the minimal functionality needed for simple use cases, and you will need to extend it if you want to cover more advanced ones.  For example, the API has parameters for restricting search based on the date or country that are not listed in the simple OpenAPI specification provided in the starter kit, so you would need to add these if you wanted to employ such filters.

## Pre-Requisite Steps

Follow the steps listed in the [Before you start](https://developers.google.com/custom-search/v1/introduction#before_you_start) section to create the custom programmable search engine ( and thereafter obtain the ID of that Programmable Search Engine) as well as the [API key](https://developers.google.com/custom-search/v1/introduction#identify_your_application_to_google_with_api_key) to identify your application.

## Other Setup Info

### Setup in a new Assistant

If you want to make a _new_ Assistant using this starter kit, take the following steps:

- Download the OpenAPI specification (`google-custom-search-openapi.json`) and Actions JSON file (`google-custom-search-actions.json`) in this starter kit.
- Use the OpenAPI specification to [build a custom extension](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-build-custom-extension#building-the-custom-extension).
- [Add the extension to your assistant](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-add-custom-extension) using the API key you obtained in the pre-requisites above.
- [Upload the Actions JSON file](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-admin-backup-restore#backup-restore-import).
- Use either method listed in [Configuring Your Actions Skill to use an Extension](https://github.com/watson-developer-cloud/assistant-toolkit/blob/master/integrations/extensions/README.md#configuring-your-actions-skill-to-use-an-extension) to configure the actions you uploaded to invoke the custom extension you built. Set the `query` parameter to the `query_text` session variable and the `cx` parameter to an *Expression* setting the value to the `Programmable Search Engine ID` you obtained during the pre-requisites step


### Setup in a pre-existing Assistant

If you want to add this starter kit to an _existing_ assistant, you cannot use the Actions JSON file since it will overwrite your existing configuration.  So instead, follow the following process:

- Download the OpenAPI specification in this starter kit.
- Use the OpenAPI specification to [build a custom extension](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-build-custom-extension#building-the-custom-extension).
- [Add the extension to your assistant](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-add-custom-extension) using the API key you obtained in the pre-requisites step above.

[//]: # (- Go to `Variables > Created by you` and add `query_text`, `result_items` `link0`, `link1`, `link2`, `title0`, `title1`, `title2`, `snippet0`,`snippet1`, and `snippet2`.)
- (- Go to `Variables > Created by you` and add `query_text`, `search_results`, `search_result`, `extension_result`, `link`, `title`, `title1`, `snippet`)
- Create a new action and name it as `Search`.
  - Click the fX button to add a variable and add new session variable `query_text` and select "Expression" type and then put `input.text` or `input.original_text` as the expression.  The former will employ spelling correction to fix any detected spelling errors before sending the query, which can be helpful but it can also be counterproductive if your documents include specialized terminology that is not in our dictionary (such as product names) so you can use `input.original_text` as the alternative in such cases.
  - Optional: In "Assistant says", put `Searching for: ${query_text}`.
  - In "And then", select "Use an extension", select the extension you made back in step 2, and select the search endpoint and set the `query` parameter to the `query_text` session variable and the `cx` parameter to an *Expression* setting the value to the `Programmable Search Engine ID` you obtained during the pre-requisites step.
  - In new step, Store the results returned by extension (success or failure) in variable `extension_result`.
  - In next steps, call the `Process Result` and `Filtered Search` action to do further processing on the results stored in previous step.


- "Show search results" action calls the "Search result" action up to three times depending on how many search results were received.  The "Search result" action shows full response with a link, title and a snippet.
![Search failed](./assets/search-failed.gif)<br>

- In the "Process result" action, add a "New Step".  In the new step:
  - change "without conditions" to "with conditions" and "check if `extension_result.success==true`
  - Click the fX button to add variables and set the `search_results` variable to an `Expression`. Type in the following replacing `extension_result.body.items` with the actual variable name:
  ```
  search_results = ${extension_result}.body.items
  ```
![Set result items](./assets/set-result-items.gif)<br>
 
- Click "New step" and change "without conditions" to "with conditions" and select `extension_result.success==false`. Then add the following to the "Assistant says":
No search results were found for query "${query_text}" 
Next, under "And then" select "End the action"

In `Show search result` action
- Add a "New Step" then:
  - Change without conditions to "with conditions" and check if `${search_results}.size==0` then add Assistant says:
    - Search results not found! Please try again
- In further steps check iterate over each search results and check if it exists, For example if we would like to show 3 results then there will be 3 more steps checking if the result exists.
- Change without conditions to "with conditions" and check if `${search_results}.size>0` then 
  - Click fx and change variable value `search_result` to expression `${search_results}.get(0)`
- Change without conditions to "with conditions" and check if `${search_results}.size>1` then
  - Click fx and change variable value `search_result` to expression `${search_results}.get(1)`
- Change without conditions to "with conditions" and check if `${search_results}.size>2` then
  - Click fx and change variable value `search_result` to expression `${search_results}.get(2)`
![No results found](./assets/no-results.gif)<br>
In `Search result` action
- Add a "New Step", then:
  - Click the fX button to add variables and add all of the following new session variables, replacing `step_123_result_1` with the actual variable name and selecting "Expression" each time. For context on why we do this and what these mean, see [Extensions Made Easy with Watson Assistant Starter Kits](https://medium.com/ibm-watson/extensions-made-easy-with-watson-assistant-starter-kits-6b177f624697):
```
link = ${search_result}.link
title = ${search_result}.title
snippet = ${search_result}.htmlSnippet
```

![Map variables to results](./assets/map-variables.gif)<br>

- And Then add the following to the "Assistant says":

```
<a href="${link}" target="_blank">${title}</a>
${snippet}
```

![Create first result snippet](./assets/create-snippet-0.gif)<br>

Filtered Search
Google custom search api provide list of customizable query parameters that can scope the results based on the parameters.

- Create new action for each kind of filter
  - Date restrict
    - Add new step, with Assistant says "Please provide the number of days old required?"
    - In second step, store the user provided number of days in session variable `number_of_days_old`, Call the extension with the parameters `query_text` for `q`, `cx` for `cx` and `number_of_days_old` for `daterestrict` query parameter
    - In third step, store the result in session variable `extension_result` and redirect to action `Process result`.
  - Include terms
    - Add new step, with Assistant says "Please provide the terms to include (comma separated)?"
    - In second step, store the user provided number of days in session variable `include_terms`, Call the extension with the parameters `query_text` for `q`, `cx` for `cx` and `include_terms` for `orTerms` query parameter
    - In third step, store the result in session variable `extension_result` and redirect to action `Process result`.
  - Exclude terms
    - Add new step, with Assistant says "Please provide the terms to exclude (comma separated)?"
    - In second step, store the user provided number of days in session variable `exclude_terms`, Call the extension with the parameters `query_text` for `q`, `cx` for `cx` and `exclude_terms` for `exclude_terms` query parameter
    - In third step, store the result in session variable `extension_result` and redirect to action `Process result`.
  


![Create third result snippet](./assets/create-snippet-2.gif)<br>

- Close the action editor (by clicking X in the upper right)
- Go to "Actions" > "Set by assistant" > "No action matches" and remove all the steps from the action.  Add in a new step.  Under "And then" select "Go to another action" and select "Search" and click "End this action after the subaction is completed".
- You may also want to go to "Actions" > "Set by assistant" > "Fallback" and do the same thing as in the previous step.  Note, however, that this will prevent your assistant from escalating to a human agent when a customer asks to connect to a human agent (which is part of the default behavior for "Fallback") so only do this if you do not have your bot connected to a human agent chat service.  For more details on connecting to human agents within Watson Assistant see [our documentation](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-human-agent) and [blog post](https://medium.com/ibm-watson/bring-your-own-service-desk-to-watson-assistant-b39bc920075c).
- Go to the Search action and remove "Search" from the "Customer starts with" list so that the search action _only_ triggers via the "Go to another action" settings described in steps 13-15 above.  If you skip this, then the "Search" action will also be considered by the intent recognizer as a possible intent, which adds unnecessary complexity to the intent recognition and thus could result in lower overall intent recognition accuracy.

## Using this Starter Kit

Once this starter kit is properly installed, you can issue a query to your bot and if there is no other action that you've configured that matched that query then it will generate search results for that query.

Feel free to contribute to this starter kit, or add other starter kits by following these [contribution guidelines](../../docs/CONTRIBUTING.md).