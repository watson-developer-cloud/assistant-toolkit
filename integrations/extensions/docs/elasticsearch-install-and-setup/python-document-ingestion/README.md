# Working with PDF and Office Documents in Elasticsearch

## Table of contents:
* [Step 1: Create a python virtual environment and upgrade pip](#step-1-create-a-python-virtual-environment-and-upgrade-pip)
* [Step 2: Create all the necessary environment variables](#step-2-create-all-the-necessary-environment-variables)
* [Step 3: Create an ELSER ingest pipeline with an inference processor](#step-3-create-an-elser-ingest-pipeline-with-an-inference-processor)
* [Step 4: Create an index for storing tokens as rank features](#step-4-create-an-index-for-storing-tokens-as-rank-features)
* [Step 5: Running the python notebook](#step-5-running-the-python-notebook)

## Pre-requisites:

1. The following tutorial assumes there exists a folder of documents that you would like to index into Elasticsearch. These can be PDF, Microsoft Office, OpenOffice, HTML, Text files etc... See full list of supported types [here](https://tika.apache.org/2.9.1/formats.html) 

	Optional: If using IBM COS or other Cloud Object Storage for your files, you can follow instructions to use [s3fs](https://cloud.ibm.com/docs/cloud-object-storage?topic=cloud-object-storage-s3fs) or [rclone](https://cloud.ibm.com/docs/cloud-object-storage?topic=cloud-object-storage-rclone) to synchronize your data to a local mounted filesystem

2. You will need a working install of [python](https://www.python.org/downloads/) and Java 8+ on your machine.

## Introduction

Before you start, ensure you have set up your Elasticsearch cluster:

* For Elasticsearch on IBM Cloud, please refer to [ICD-elasticsearch-install-and-setup](./ICD_Elasticsearch_install_and_setup.md) for more details.
* For Elasticsearch (watsonx Discovery) on CloudPak, please refer to [watsonx-discovery-install-and-setup](./watsonx_discovery_install_and_setup.md) for more details.


### Step 1: Create a python virtual environment and upgrade pip

`venv` allows you to manage separate package installations for different projects. It creates a “virtual” isolated Python installation. When you switch projects, you can create a new virtual environment which is isolated from other virtual environments. You benefit from the virtual environment since packages can be installed confidently and will not interfere with another project’s environment.

```bash
python3 -m venv .venv
source .venv/bin/activate
python3 -m pip install --upgrade pip
```


### Step 2: Create all the necessary environment variables

The `ES_INDEX_NAME` and `ES_PIPELINE_NAME` variables can be whatever you would like to name your index and ingestion pipeline for use throughout this process/guide as references in various steps below.

  ```bash
  export ES_URL=https://<hostname:port>
  export ES_USER=<username>
  export ES_PASSWORD=<password>
  export ES_CACERT=<path-to-your-cert>
  export ES_INDEX_NAME=<name-of-index>
  export ES_PIPELINE_NAME=<name-of-pipeline>
  export DOCS_DIR=<full-path-to-your-directory-of-documents>
  ``` 

### Step 3: Create an ELSER ingest pipeline with an inference processor

If you already have an existing pipeline in your Elasticsearch instance that uses an inference processor with ELSER against the "text" field, you can choose to reuse that and skip creation of a new pipeline.

If not, then below is a sample request to create a pipeline that transforms the "text" field using the ELSER model and produces the terms along with weights as a sparse vector in the "ml" field at index time.

You will be able to reference this pipeline in the next few steps as a part of indexing the documents of choice.

Learn more about [inference-ingest-pipeline](https://www.elastic.co/guide/en/elasticsearch/reference/8.10/semantic-search-elser.html#inference-ingest-pipeline) from the tutorial 

```bash
curl -X PUT "${ES_URL}/_ingest/pipeline/${ES_PIPELINE_NAME}?pretty" -u "${ES_USER}:${ES_PASSWORD}" \
-H "Content-Type: application/json" --cacert "${ES_CACERT}" -d'
{
  "processors": [
    {
      "inference": {
        "model_id": ".elser_model_1",
        "target_field": "ml",
        "field_map": {
          "text": "text_field"
        },
        "inference_config": {
          "text_expansion": {
            "results_field": "tokens"
          }
        }
      }
    }
  ]
}'
```	 



### Step 4: Create an index for storing tokens as rank features

```bash
curl -X PUT "${ES_URL}/${ES_INDEX_NAME}?pretty" -u "${ES_USER}:${ES_PASSWORD}" \
-H "Content-Type: application/json" --cacert "${ES_CACERT}" -d'
{
  "settings":{
  "index":{
  "default_pipeline":"'"${ES_PIPELINE_NAME}"'"
  }
},
  "mappings": {
    "properties": {
      "ml.tokens": {
        "type": "rank_features"
      },
      "text": {
        "type": "text"
      }
    }
  }
}'
```

NOTE: `rank_features` only works for ELSER v1 model. ELSER v2 requires a different type for the tokens. ELSER v2 has only been available since Elastic 8.11. Learn more about ELSER v2 from [here](https://www.elastic.co/guide/en/machine-learning/current/ml-nlp-elser.html)


### Step 5: Running the python notebook

### Step 5a. Install the required libraries

To get started, navigate to the `python-document-ingestion` directory and install the requirements provided in the `requirements.txt` directory:

```bash
cd python-document-ingestion
pip3 install -r requirements.txt
```

### Step 5b. Run the provided notebook to ingest documents

Start the notebook environment using the below command from within your created `venv` from [Step 1](#step-1-create-a-python-virtual-environment-and-upgrade-pip). Then, open the `doc-ingestion.ipynb` notebook on your web browser.

```bash
jupyter notebook
```

Run each cell in order using the "Shift + Enter" shortcut or by clicking the ![play](../assets/jupyter_play_button.png) button.

After the last cell has completed execution, you will see a message like "**Indexed n/n documents**" that indicates that your documents were successfully chunked and ingested into Elasticsearch. 
If you see an error and only a few of your documents got indexed, you may want to delete the index through the [Kibana dashboard](../ICD_Elasticsearch_install_and_setup.md#step-2-set-up-kibana-to-connect-to-elasticsearch) or using the [delete index API](https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-delete-index.html)
and restart again by attempting with smaller number of documents at a time. 

If you'd like to update the number of documents to send per request in the Elasticsearch API, you can update the 
`chunk_size` parameter in the call to `helpers.streaming_bulk` method in the final cell of the notebook.

Your documents are now available in the index, ready for searching and querying. Follow the steps outlined below to use this index in a RAG based setup with Watson Assistant. 

**NOTE**: There are some example documents available [here](../assets/sample_pdf_docs), if you would like to test the setup.

### Step 5: Connecting Watson Assistant to Elasticsearch for Conversational Search

There are two options to connect your elasticsearch instance to your assistant for conversational search:



#### Step 5a. Using the built-in watsonX Assistant Conversational Search

Follow instructions in the [elasticsearch integration documentation](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-search-elasticsearch-add#setup-elasticsearch) to configure your elasticsearch index for conversational search

**NOTES**

1. In `Step 3` , use `title` for the **title** field and `text` for the **text** field
2. In `Step 4` , configure the custom query body using the snippet below: 

	```
	{
	  "query": {
	    "text_expansion": {
	      "ml.tokens": {
	        "model_id": ".elser_model_1",
	        "model_text": "$QUERY"
	      }
	    }
	  }
	}
	```

3. Remember to enable the **conversational search** toggle to `on` to activate it

<img src="../assets/elasticsearch-integration.png" width="400"/>


#### Step 5b. Using custom extensions

Follow the steps outlined in guide to [connect your assistant to elasticsearch and watsonx using custom extensions](../../starter-kits/language-model-conversational-search#example-1-connect-your-assistant-to-elasticsearch-and-watsonx-via-custom-extensions) 

> ⛔️
> **Caution**  
> 
> * Remember to set `_source` to `query_source` session variable when setting up your Elasticsearch extension. By limiting the fields available in the `_source`, we can limit the length of the query response, potentially avoiding the 500 error that may be encountered due to length limits.

#### Example usage:

Here is an example of how to use the `Search` action for this starter kit conversational search example:

<img src="../assets/conversational_search_example_python_doc_ingestion.png" width="300"/>