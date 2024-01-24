# Working with web crawler in Elasticsearch
This is a documentation about how to set up and use web crawler in Elasticsearch.

## Tabel of contents:
* [Step 1: Set up Enterprise Search to enable web crawler in Elasticsearch]()
* [Step 2: Create and configure a web crawler in Elasticsearch]()
* [Step 3: Enable text_chunking with web crawler]()
* [Step 4: Connect a web crawler index to watsonx Assistant for conversational search]()

## Step 1: Set up Enterprise Search to enable web crawler in Elasticsearch
Before you start, you will need to install and set up your Elasticsearch cluster,
* For Elasticsearch on IBM Cloud, please refer to [ICD-elasticsearch-install-and-setup](../../docs/elasticsearch-install-and-setup/ICD_Elasticsearch_install_and_setup.md) for more details.
* For Elasticsearch (watsonx Discovery) on CloudPak, please refer to [watsonx-discovery-install-and-setup](../../docs/elasticsearch-install-and-setup/watsonx_discovery_install_and_setup.md) for more details.

### Set up Enterprise Search for Elasticsearch on IBM Cloud 
Assuming you have installed Kibana locally following [ICD-elasticsearch-install-and-setup](../../docs/elasticsearch-install-and-setup/ICD_Elasticsearch_install_and_setup.md) for more details.
Stop your Kibana service, and start it again with the following command:
```shell
docker run -it --name kibana --rm \
-v <path_to_your_kibana_config_folder>:/usr/share/kibana/config \
-p 5601:5601 \
--env "ENTERPRISESEARCH_HOST=http://enterprise-search:3002" \
docker.elastic.co/kibana/kibana:<kibana_version>
```
NOTE: 

### Set up Enterprise Search for watsonx Discovery on CloudPak
TBD

## Step 2: Create and configure a web crawler in Elasticsearch

## Step 3: Enable text chunking with web crawler

## Step 4: Connect a web crawler index to watsonx Assistant for conversational search