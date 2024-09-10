
### Setup your server for your Custom Service

This starter kit is to provide additional details and example code for setting up your server for your Custom Service. For more details, please refer to the [official documentation](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-search-customsearch-add#setup-custom-service-server).

The first step to setup a server as your **Custom Service** for your Search Integration, is to setup a server that receives search requests, makes a search, and then constructs a response with those `search_results`.

For authentication, your server can be setup to either use **Basic** username/password auth, **API key** auth, or no auth at all.

##### Request

When any of your users makes a search, a request to your server will be sent in the following format:

```json
{
  "query" : "<query to search>",
  "filter" : "<filter>", // optional,
  "metadata" :  {  /* optional metadata, if configured in Search Integration */ }
}
```

##### Response

On receiving a request to search, your server should retrieve the search results for that request, and then respond in the following format:

```json
{
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
```

##### Search Integration setup

Once your Server is set up to receive search requests, and respond with the search results, navigate to the Environments page, and click `Add +` on the Search Integration for your **Draft** environment (or navigate to the Search Integration via the **Integrations** page). Select **Custom Service** from the modal.

Leave the radio button set to **By providing credentials** and fill in the connection details for your server.

Once you fill in the connection details and press the `Save` button, you will be brought to a **Settings** page, where you can optionally configure a **Default filter** to be used for searches if no filter is provided at a step/dialog node, and **Metadata** to be sent with the requests to your server. 

##### Debugging

If there is an issue reaching out to your search server, the `output.generic` item in the `/message` response will include and `errors` array that should explain the issue. 

For example, if the response from your server did not include `search_results`, the `output.generic` item in the `/message` response will include:
```json
{
  "errors" : [ 
    { 
      "message" : "must have required property 'search_results'",
      "path" : "" 
    } 
  ]
}
```

##### Example

To help get you started, there are a few example servers included in the [examples](./examples) folder. 

These examples use Python Flask servers to receive search requests, and return results in the specified format.

###### Simple Example

The [simple server](./examples/example-simple-server.py) example returns a sample search result for all search requests. For your actual server, you would instead make the search detailed in the search request, and return those results.

###### Example with Milvus

The provided [sample Milvus server](./examples/examle-milvus-server.py) makes a request to Milvus to get search results (using embeddings from watsonx.ai.)

In addition to this sample Milvus server, there is also a [python script](./examples/index-with-milvus.py) included. That script converts PDF files to text, chunks them, encodes the chunks using a watsonx.ai embedding model, and then stores the embeddings and text in Milvus. You would need to update that script to point to your content and you may want to change other configuration options such as which embedding model is used.