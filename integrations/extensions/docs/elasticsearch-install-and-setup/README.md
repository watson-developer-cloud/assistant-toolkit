# Elasticsearch Installation and Setup Documentation

This directory contains documentation for installing and setting up Elasticsearch along with related tools and integrations.

## Elasticsearch Setup
- [Guide to installing Docker or Docker alternatives](how_to_install_docker.md): A guide explaining Docker and Docker Compose installation options, essential for running Elasticsearch-related applications.
- [Set up Elasticsearch from IBM Cloud and integrate it with Watson Assistant](ICD_Elasticsearch_install_and_setup.md): Instructions for provisioning Elasticsearch instance on IBM Cloud and setting up related services like Kibana.
- [Set up watsonx Discovery (Elasticsearch) and integrate it with Watson Assistant in CloudPak](watsonx_discovery_install_and_setup.md): Documentation for setting up Watsonx Discovery (Elasticsearch) and integrating it with Watson Assistant in CloudPak.

## Elasticsearch integration with Watson Assistant
### Option 1: Set up a custom Elasticsearch extension
You can follow the guide [here](../../starter-kits/elasticsearch/README.md) to build a custom Elasticsearch extension allowing your assistant to search for information in an Elasticsearch index and show what it finds in the chat.
### Option 2: Use the built-in Search integration
You can set up search over an Elasticsearch index using the built-in search integration. Please follow the instructions [here](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-search-elasticsearch-add) to set up the Search integration for your assistant.  

By default, keyword search is used for your Search integration, but you can configure the query body in the `Advanced Elasticsearch Settings` to enable advanced search such as semantic search with ELSER, KNN dense vector search, and using nested queries to search over nested documents. Here are some examples:

#### a. Semantic search with ELSER
For semantic search using ELSER v1, please include the following query body in the `Advanced Elasticsearch Settings`:

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

For ELSER v2, use the following query body instead
```json
{
  "query": {
    "text_expansion": {
      "ml.tokens": {
        "model_id": ".elser_model_2",
        "model_text": "$QUERY"
      }
    }
  }
}
```
Notes:
* `ml.tokens` refers to the field that stores the ELSER tokens. You may need to update it if you use a different field in your index.
* `.elser_model_1` is the model ID for ELSER v1.
* `.elser_model_2` is the model ID for ELSER v2. An optimized version `.elser_model_2_linux-x86-64` can also be used if it is available in your Elasticsearch deployment.
* Learn more about ELSER from [here](https://www.elastic.co/guide/en/elasticsearch/reference/current/semantic-search-elser.html)


#### b. Dense Vector (KNN) Search
For dense vector search, use the following knn query body as an example:
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
Notes:
* `text_embedding.predicted_value` refers to the field that stores the dense vectors. You may need to update it if you use a different field in your index.
* `text_embedding` under `query_vector_builder` is the natural language processing task to perform. It has to be `text_embedding` for KNN search.
* `intfloat__multilingual-e5-small` is the embedding model ID. You may need to update it if you want to use a different embedding model.
* Learn more about knn search from [here](https://www.elastic.co/guide/en/elasticsearch/reference/current/knn-search.html).
* Learn more about how to set up a text embedding model in Elasticsearch from [here](text_embedding_deploy_and_use.md).

#### c. Using nested queries to search over nested documents with ELSER
The nested queries are used in the [PDF ingestion guide](how_to_use_web_crawler_in_elasticsearch.md) and [web crawl guide]((how_to_index_pdf_and_office_documents_elasticsearch.md)). Please refer to the guides for more details. 

For using nested queries to search over nested documents, use the following query body as an example:
```json
{
  "query": {
    "nested": {
      "path": "passages",
      "query": {
        "text_expansion": {
          "passages.sparse.tokens": {
            "model_id": ".elser_model_1",
            "model_text": "$QUERY"
          }
        }
      },
      "inner_hits": {"_source": {"excludes": ["passages.sparse"]}}
    }
  },
  "_source": false
}
```
Notes:
* `passages` is the nested field that stores nested documents. You may need to update it if you use a different nested field in your index.
* `passages.sparse.tokens` refers to the field that stores the ELSER tokens for the nested documents.
* `"inner_hits": {"_source": {"excludes": ["passages.sparse"]}}` is to exclude the ELSER tokens from the nested documents in the search results.
* `"_source": false` is to exclude top-level fields in the search results.
* Learn more about nested queries and fields from [here](https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-nested-query.html)

### Federated Search
You can follow the guide [here](federated_search.md) to run queries across multiple indexes within your Elasticsearch cluster.

## Document Ingestion with Elasticsearch
- [Set up the web crawler in Elasticsearch](how_to_use_web_crawler_in_elasticsearch.md): Guide for setting up and using the web crawler in Elasticsearch and connecting it to Watsonx Assistant for Conversational Search.
- [Working with PDF and office documents in Elasticsearch](how_to_index_pdf_and_office_documents_elasticsearch.md): Guide for working with PDF and Office Documents in Elasticsearch, including indexing and connecting to Watson Assistant for Conversational Search.
- [Set up text embedding models in Elasticsearch](text_embedding_deploy_and_use.md): Instructions for setting up and using 3rd-party text embeddings for dense vector search in Elasticsearch.
