# Elasticsearch

## Background 

This is a starter kit for accessing the IBM Cloud Databases for Elasticsearch via a custom extension to IBM watsonx Assistant.
The purpose of doing so is to allow a chatbot built in IBM watsonx Assistant to search for information in Elasticsearch 
and show what it finds in the chat.  This starter kit uses the custom extensions feature in [the new IBM watsonx Assistant experience](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-watson-assistant-faqs#faqs-new-experience) 
using actions.  Anyone with an older IBM watsonx Assistant instance based on the classic experience and dialogs will 
need to upgrade to the new IBM watsonx Assistant experience before they can use the features described here.

## Install and set up Elasticsearch

Before starting, you will need to provision a Elasticsearch instance and set up a search index. Please follow the steps in 
[elasticsearch-install-and-setup-guide](../../docs/elasticsearch-install-and-setup/ICD_Elasticsearch_install_and_setup.md) for more details.

## Build a custom extension in watsonx Assistant with Elasticsearch API

* Download the OpenAPI specification [elasticsearch-generic-openapi.json](assets/elasticsearch-generic-openapi.json)
* Use the OpenAPI specification to [build a custom extension](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-build-custom-extension#building-the-custom-extension), name it `Elasticsearch`
* [Add the extension to your assistant](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-add-custom-extension)
  * Choose Draft or Live environment to add your extension (use Draft for testing and verification)
  * Configure the authentication with your Elasticsearch credentials 
* Verify the custom extension
  * Find the `No action matches` action under `Set by assistant` from the Actions menu
  * In the first conversation step `No action matches count <= 3`, choose `use a extension`, 
  * Choose your Elasticsearch extension, and choose the operation, and then specify the index name and query body. For example,  
    <img src="assets/use_elasticsearch_custom_extension.png" width="669" height="627" />
  * You can set `query_body` as a session variable. The following is a query body example for semantic search with ELSER enabled:  
    ```json
    {
      "text_expansion":{
        "ml.tokens":{
          "model_id":".elser_model_1",
          "model_text":"how to set up a custom extension?"
        }
      }
    }
    ```
  * Try typing in anything in your preview chat to trigger `No action matches` action. 
    If you see a successful extension call with valid response in the debug view, your Elasticsearch custom extension has been set up successfully.