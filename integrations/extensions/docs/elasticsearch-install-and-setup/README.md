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
You can set up search over Elasticsearch indices using the built-in search integration. Please follow the instructions [here](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-search-elasticsearch-add) to set up the Search integration for your assistant.

#### Configure the Advanced Elasticsearch Settings
There are two settings under `Advanced Elasticsearch Settings` for using custom query body and custom filters to achieve advanced search use cases. See the guide [How to configure Advanced Elasticsearch Settings](./how_to_configure_advanced_elasticsearch_settings.md) for more details. 

#### Federated search
You can follow the guide [here](federated_search.md) to run queries across multiple indexes within your Elasticsearch cluster.

## Document Ingestion with Elasticsearch
- [Set up the web crawler in Elasticsearch](how_to_use_web_crawler_in_elasticsearch.md): Guide for setting up and using the web crawler in Elasticsearch and connecting it to Watsonx Assistant for Conversational Search.
- [Working with PDF and office documents in Elasticsearch](how_to_index_pdf_and_office_documents_elasticsearch.md): Guide for working with PDF and Office Documents in Elasticsearch, including indexing and connecting to Watson Assistant for Conversational Search.
- [Set up text embedding models in Elasticsearch](text_embedding_deploy_and_use.md): Instructions for setting up and using 3rd-party text embeddings for dense vector search in Elasticsearch.
