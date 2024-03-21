# Elasticsearch Installation and Setup Documentation

This directory contains documentation for installing and setting up Elasticsearch along with related tools and integrations.

## Elasticsearch Setup
- [Guide to installing Docker or Docker alternatives](how_to_install_docker.md): A guide explaining Docker and Docker Compose installation options, essential for running Elasticsearch-related applications.
- [Set up Elasticsearch from IBM Cloud and integrate it with Watson Assistant](ICD_Elasticsearch_install_and_setup.md): Instructions for provisioning Elasticsearch instance on IBM Cloud and setting up related services like Kibana.
- [Set up watsonx Discovery (Elasticsearch) and integrate it with Watson Assistant in CloudPak](watsonx_discovery_install_and_setup.md): Documentation for setting up Watsonx Discovery (Elasticsearch) and integrating it with Watson Assistant in CloudPak.

## Elasticsearch integration with Watson Assistant
### 1. Set up a custom Elasticsearch extension
You can follow the guide [here](../../starter-kits/elasticsearch/README.md) to build a custom Elasticsearch extension allowing your assistant to search for information in an Elasticsearch index and show what it finds in the chat.
### 2. Set up the built-in Elasticsearch extension
You can set up search over an Elasticsearch index using the built-in Elastic search extension as well. Please follow the instructions [here](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-search-elasticsearch-add) to set up the extension for your assistant. You can then enable search for your extension using ELSER or dense vector search by following the relevant instruction below 

#### a. ELSER
For semantic search using ELSER v1, please include the following query body in the Search extension settings:

```json
{
  "query":{
    "text_expansion":{
      "ml.tokens":{
        "model_id":".elser_model_1",
        "model_text":"$QUERY"
      }
    }
  }
}
```

<img src="assets/query_body_for_elasticsearch.png" width="547" height="638" />

For ELSER v2 use the following query body instead
```json
{
  "query": {
    "text_expansion": {
      "content_embedding": {
        "model_id": ".elser_model_2",
        "model_text": "$QUERY"
      }
    }
  }
}
```

#### b. Dense Vector (KNN) Search
For dense vector search use the following json object:
```json
{
  "knn": {
    "field": "text_embedding.predicted_value",
    "query_vector_builder": {
      "text_embedding": {
        "model_id": "intfloat__multilingual-e5-small",
        "model_text": "$QUERY"
      }
    },
    "k": 10,
    "num_candidates": 100
  }
}
```

## Document Ingestion and Search
- [Set up the web crawler in Elasticsearch](how_to_use_web_crawler_in_elasticsearch.md): Guide for setting up and using the web crawler in Elasticsearch and connecting it to Watsonx Assistant for Conversational Search.
- [Working with PDF and office documents in Elasticsearch](how_to_index_pdf_and_office_documents_elasticsearch.md): Guide for working with PDF and Office Documents in Elasticsearch, including indexing and connecting to Watson Assistant for Conversational Search.
- [Set up text embedding models in Elasticsearch](text_embedding_deploy_and_use.md): Instructions for setting up and using 3rd-party text embeddings for dense vector search in Elasticsearch.
