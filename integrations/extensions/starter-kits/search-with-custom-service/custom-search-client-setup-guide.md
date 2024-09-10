### Setup your client for your Custom Service

This starter kit is to provide additional details and example code for setting up your client for your Custom Service. For more details, please refer to the [official documentation](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-search-customsearch-add#setup-custom-service-client).

To set up your client for as your **Custom Service** for your Search Integration, you will need to be using a custom client for your Watson Assistant, as opposed to the default **web chat** client. 

When your search skill is set up to use a Custom Service through your client, any `/message` request in which a search is executed will return back to the client to complete the search. Your custom client will have to intercept the responses to these message requests, and check if a search was requested in `output.actions`. If a `search` request is returned to your custom client, you will have to make the search on the client side, and then return the search results in the subsequent `/message` request.

##### Message Response with a Search Request

When any search is executed within a `/message` call, Watson Assistant will return back to the client to complete the search. That `/message` response will include an object in `output.actions` with `type = "search"` as well as the details for the search to be made.

```json
{
  "output" : {
    "generic" : [ ... ],
    "actions" : [
      {
	    "type" : "search",
	    "query" : "<query to search>",
	    "filter" : "<filter for the search>", // optional
	    "metadata" : { ... } // optional
      }
    ]
  },
  ...
}
```

##### Subsequent Message Request with Search Results

On receiving a search request like the above example, your custom client will be responsible for making the search, and sending a subsequent message request with the results for that search.

``` json
{
  "user_id" : "wxa_user",
  "input" : {
    "message_type" : "search_results",
    "search_results" : [
	  {
	    "result_metadata" : { // optional
	      "score" : 123
	    },
	    "title" : "<search result title>",
	    "body" : "<search result body>",
	    "url" : "<search result url>" // optional
	  },
      ...
    ]
  }
}
```

##### Search Integration setup

Once your client is set up to intercept search requests, and send a subsequent `search_results` message with the search results, navigate to the Environments page, and click `Add +` on the Search Integration for your **Draft** environment (or navigate to the Search Integration via the **Integrations** page). Select **Custom service** from the modal.

Next, update the radio button at the top to select **Through your client**. Press the `Save` button, you will be brought to a **Settings** page, where you can optionally configure a **Default filter** to be used for searches if no filter is provided at a step/dialog node, and **Metadata** to be sent with the requests to your server. 

##### Example

To help get you started, there is an [example web chat client](./examples/example-web-chat-client.html) included in this folder. This example shows how to create a custom web chat client set up for client side search. In this web chat example, the instance's on `receive` event is overriden to check for a search action in the message response, and send a subsequent message request with the search results. In this example, a sample search result is returned for all queries, but for your actual client, you would instead make the search detailed in the returned search action, and use those actual search results in the subsequent outgoing message.