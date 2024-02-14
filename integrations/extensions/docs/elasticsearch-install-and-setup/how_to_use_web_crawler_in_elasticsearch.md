# How to set up and use the web crawler in Elasticsearch
This is a documentation about how to set up and use the web crawler in Elasticsearch and connect it to watsonx Assistant for Conversational Search.

## Tabel of contents:
* [Step 1: Set up Enterprise Search to enable the web crawler in Elasticsearch](#step-1-set-up-enterprise-search-to-enable-the-web-crawler-in-elasticsearch)
* [Step 2: Create and configure a web crawler in Elasticsearch](#step-2-create-and-configure-a-web-crawler-in-elasticsearch)
* [Step 3: Build an ELSER ingest pipeline with a chunking processor](#step-3-build-an-elser-ingest-pipeline-with-a-chunking-processor)
* [Step 4: Connect a web crawler index to watsonx Assistant for conversational search](#step-4-connect-a-web-crawler-index-to-watsonx-assistant-for-conversational-search)

## Step 1: Set up Enterprise Search to enable the web crawler in Elasticsearch
Before you start, you will need to install and set up your Elasticsearch cluster,
* For Elasticsearch on IBM Cloud, please refer to [ICD-elasticsearch-install-and-setup](../../docs/elasticsearch-install-and-setup/ICD_Elasticsearch_install_and_setup.md) for more details.
* For Elasticsearch (watsonx Discovery) on CloudPak, please refer to [watsonx-discovery-install-and-setup](../../docs/elasticsearch-install-and-setup/watsonx_discovery_install_and_setup.md) for more details.

### Set up Enterprise Search for Elasticsearch on IBM Cloud 
Assuming you have installed Kibana locally following [ICD-elasticsearch-install-and-setup](../../docs/elasticsearch-install-and-setup/ICD_Elasticsearch_install_and_setup.md), 
follow these steps to set up Enterprise Search in Elasticsearch:  
**NOTE: Enterprise Search requires at least 4GB of memory, so please make sure you have enough memory allocated to your Docker Engine.**
* Create a docker network
    ```shell
    docker network create elastic
    ```
  NOTE: `elastic` will be the name of your docker network.


* Restart Kibana within a network and with enterprise search host
  ```shell
  KIBANA_CONFIG_FOLDER=<path-to-your-kibana-config-folder>
  KIBANA_VERSION=<kibana-version>
  ```
  NOTE: `KIBANA_VERSION` needs to be compatible with the Elasticsearch version. It can be the same as the Elasticsearch version.
  Learn more about the compatibility with Elasticsearch from [here](https://www.elastic.co/support/matrix#matrix_compatibility)
  ```shell
  docker run -it --name kibana --network elastic --rm \
  -v ${KIBANA_CONFIG_FOLDER}:/usr/share/kibana/config \
  -p 5601:5601 \
  --env "ENTERPRISESEARCH_HOST=http://enterprise-search:3002" \
  docker.elastic.co/kibana/kibana:${KIBANA_VERSION}
  ```
  NOTE: Instead of passing `ENTERPRISESEARCH_HOST` as an environment variable, another option is to add the enterprise search 
  host to your kibana config YAML file, for example, 
  ```shell
  enterpriseSearch.host: "http://enterprise-search:3002"
  ```

* Generate an encryption key for Enterprise Search  
In a new terminal window, run the following command to generate an encryption key which will be used in the next step:
  ```shell
  ENCRYPT_KEY=$( openssl rand -hex 32 )
  ```

* Create environment variables for elasticsearch credentials:
    ```shell
    ES_URL=<your-elasticsearch-host>
    ES_USER=<your-elasticsearch-username>
    ES_PASSWORD=<your-elasticsearch-password>
    
    ES_VERSION=<your-elasticsearch-version>
    ES_CACERT_FOLDER=<your-config-folder-containing-elasticsearch-cert>
    ES_CACERT_NAME=<your-es-cert-file-name>
    ```
  NOTE: `ES_CACERT_FOLDER` can be your kibana config folder if you have stored the elasticsearch certificate in the folder following [ICD-elasticsearch-install-and-setup](../../docs/elasticsearch-install-and-setup/ICD_Elasticsearch_install_and_setup.md)

* Download and start Enterprise Search
    ```shell
    docker run \
    --name "enterprise-search" \
    --network "elastic" \
    --publish "3002:3002" \
    -v "${ES_CACERT_FOLDER}:/usr/share/enterprise-search/es-config:ro" \
    --interactive \
    --tty \
    --rm \
    --env "secret_management.encryption_keys=[${ENCRYPT_KEY}]" \
    --env "allow_es_settings_modification=true" \
    --env "elasticsearch.host=${ES_URL}" \
    --env "elasticsearch.username=${ES_USER}" \
    --env "elasticsearch.password=${ES_PASSWORD}" \
    --env "elasticsearch.ssl.enabled=true" \
    --env "elasticsearch.ssl.certificate_authority=/usr/share/enterprise-search/es-config/${ES_CACERT_NAME}" \
    --env "kibana.external_url=http://kibana:5601" \
    "docker.elastic.co/enterprise-search/enterprise-search:${ES_VERSION}"
    ```
* Verify it 
  * Open http://localhost:5601 in your browser and log into Kibana using your Elasticsearch username and password.
  * Navigate to the Search Overview page http://localhost:5601/app/enterprise_search/overview.
  * If you can see `Web Crawler` available as an option to ingest content, your Enterprise Search has been set up successfully.

### Set up Enterprise Search for watsonx Discovery on CloudPak
Coming soon...

## Step 2: Create and configure a web crawler in Elasticsearch 
* In Kibana, you can find the Web Crawler option on Search Overview http://localhost:5601/app/enterprise_search/overview. 
Click on `Start`, and follow the steps to create a Web Crawler index. 


* On the `Manage Domains` tab, add a domain, for example, `https://www.nationalparks.org`.


* Add an entry point under the domain `Entry points` tab, for example, `https://www.nationalparks.org/explore/parks`


* From the domain `Crawl Rules` tab, add two additional rules like below:  
  <img src="assets/add_crawl_rules_for_web_crawler.png" width="802" height="309" />  
  NOTE: The two rules tell the web crawler to only crawl URLs that begin with https://www.nationalparks.org/explore/parks. 
  Learn more about crawl rules from [here](https://www.elastic.co/guide/en/app-search/8.10/web-crawler-reference.html#web-crawler-reference-crawl-rule)


* You can now try the web crawler by clicking on `Crawl` in the upper right corner.  
  NOTE: The web crawler uses a default ingest pipeline to extract web content and store them as documents in the index. 
  The default ingest pipeline doesn't use chunking or semantic search such as ELSER to process the documents. To enable chunking 
  and ELSER for your web crawler, you will need to finish [Step 3](#step-3-create-an-elser-ingest-pipeline-with-a-chunking-processor) 
  first to build a custom ingest pipeline with a chunking processor, and then start the web crawler. 

## Step 3: Build an ELSER ingest pipeline with a chunking processor
To use ELSER for text expansion queries on chunked texts, you need to build an ingest pipeline with a chunking processor that uses the ELSER model.


### Update the index mappings of your web crawler
Define your web crawler index name as an environment variable:  
```shell
ES_INDEX_NAME=<your-web-crawler-index-name>
```
Update the index mappings:  
```shell
curl -X PUT "${ES_URL}/${ES_INDEX_NAME}/_mapping?pretty" -k \
-u "${ES_USER}:${ES_PASSWORD}" \
-H 'Content-Type: application/json' \
-d'
{
  "properties": {
    "passages": {
      "type": "nested",
      "properties": {
        "sparse.tokens": {
          "type": "rank_features"
        }
      }
    }
  }
}'
```
The above command will update the index mappings to specify `passages` to be `nested` type and `passages.sparse.tokens` to be `rank_features` type, 
because the default dynamic index mappings of the web crawler cannot recognize these types correctly during document ingestion.

NOTE: `rank_features` only works for ELSER v1 model. ELSER v2 requires a different type for the tokens. ELSER v2 has only been available since Elastic 8.11. Learn more about ELSER v2 from [here](https://www.elastic.co/guide/en/machine-learning/current/ml-nlp-elser.html)

### Build a custom ingest pipeline with two processors
Now you can build a custom ingest pipeline for your web crawler index on Kibana, following these steps:

* Open http://localhost:5601 and log into Kibana with your Elasticsearch credentials. Navigate to the [indices page](http://localhost:5601/app/enterprise_search/content/search_indices) 
  from the left-side menu via `Content` under `Search`, find your web crawler index, and click on it to go to the index page.


* Under `Pipelines` tab, click on `Copy and customize` to create a custom ingest pipeline, and you will see a new ingest pipeline named `<your-web-crawler-index-name>@custom`.  
  For example,  
  <img src="assets/web_crawler_ingest_pipeline_custom.png" width="955" height="340" />


* Click on `edit pipeline` and then `Manage` -> `Edit`, it will take you to the ingest pipeline `Edit` page 
  where you can add processors to the pipeline.


* Add a `Script` processor for chunking  
  In the ingest pipeline page, click on `Add a processor`, choose `Script` processor, and then add a Groovy script to the `Source` field.  
  For example,  
  <img src="assets/web_crawler_script_processor.png" width="577" height="718" />

  ```Groovy
  String[] envSplit = /((?<!M(r|s|rs)\.)(?<=\.) |(?<=\!) |(?<=\?) )/.split(ctx['body_content']);
  ctx['passages'] = new ArrayList();
  int i = 0;
  boolean remaining = true;
  if (envSplit.length == 0) {
      return
  } else if (envSplit.length == 1) {
      Map passage = ['text': envSplit[0]];ctx['passages'].add(passage)
  } else {
      while (remaining) {
          Map passage = ['text': envSplit[i++]];
          while (i < envSplit.length && passage.text.length() + envSplit[i].length() < params.model_limit) {passage.text = passage.text + ' ' + envSplit[i++]}
          if (i == envSplit.length) {remaining = false}
          ctx['passages'].add(passage)
          }
  }
  ```
  The above script splits `body_content` into sentences using regex and then combines sentences into passages with 
  a `model_limit` parameter to control the character length of each passage. `model_limit` is configured in `Parameters` field, for example,  
  ```json
  {
    "model_limit": 2048
  }
  ```
  Since ELSER v1 model is limited to 512 tokens per field for inference, it is better not to have more than 512 tokens for each passage. 
  `2048` is a reasonable character length limit because a text with 2048 characters will be unlikely to have more than 512 tokens. 
  Depending on how you want to chunk the `body_content` and which sematic search model you want to use, you may need to use 
  different `model_limit` values to optimize the chunking processor.  

  #### (Optional) Considerations for customizing the chunking processor
  You can update the above script to customize the chunked passages. For example, the `passage` definition statement can be updated.
  The original `passage` definition in the above script is 
  ```Groovy
  Map passage = ['text': envSplit[i++]];
  ```
  * If you need to include `title` as a field in each passage object, use the following `passage` definition statement:
    ```Groovy
    Map passage = ['text': envSplit[i++], 'title': ctx['title']];
    ```
  * If you need to insert `title` to the beginning of each chunked text, use the following `passage` definition statement:
    ```Groovy
    Map passage = ['text': ctx['title'] + '. ' + envSplit[i++], 'title': ctx['title']];
    ```


* Add a `Foreach` processor to process chunked texts using the ELSER model  
  In the ingest pipeline `Edit` page, click on `Add a processor`, choose `Foreach` processor, specify `passages` as the `Field`, and then add a JSON processor config to the `Processors` field.  
  For example,  
  <img src="assets/web_crawler_foreach_processor.png" width="575" height="716" />
  ```json
  {
    "inference": {
      "field_map": {
        "_ingest._value.text": "text_field"
      },
      "model_id": ".elser_model_1",
      "target_field": "_ingest._value.sparse",
      "inference_config": {
        "text_expansion": {
          "results_field": "tokens"
        }
      },
      "on_failure": [
        {
          "append": {
            "field": "_source._ingest.inference_errors",
            "value": [
              {
                "message": "Processor 'inference' in pipeline 'search-crawler-with-chunking@custom' failed with message '{{ _ingest.on_failure_message }}'",
                "pipeline": "search-crawler-with-chunking@custom",
                "timestamp": "{{{ _ingest.timestamp }}}"
              }
            ]
          }
        }
      ]
    }
  }
  ```
  NOTES:
  * `inference_config` in this example only works with ELSER v1 model, its structure will be different for ELSER v2 model. 
  * `inference_config.text_expansion` is required in the config to tell the Foreach processor to use `text_expansion` 
    and store the results in `tokens` field for each chunked text.
  * `_ingest._value.sparse` expects a `sparse` field for each chunk object as the target field.
  * `_ingest._value.text` expects a `text` field for each chunk object as the input field.
  * `search-crawler-with-chunking@custom` is the name of the ingest pipeline. You need to update it with your ingest pipeline name.  
  

  > ⛔️
  > **Caution**  
  > Don't forget to click on `Save pipeline` to save your changes!


* Start your web crawler and monitor its progress  
  Once you have added the processors to your ingest pipeline, you can kick off your web crawler to crawl the website URLs you have configured at earlier steps.  

  If you already started your web crawler at the end of [Step 2](#step-2-create-and-configure-a-web-crawler-in-elasticsearch), 
  and it finished extracting the documents, you may need to delete all the documents first with the following cURL command:
  ```shell
  curl -k -X POST "${ES_URL}/${ES_INDEX_NAME}/_delete_by_query" \
  -u "${ES_USER}:${ES_PASSWORD}" \
  -H 'Content-Type: application/json' -d'
  {
     "query":{
       "match_all":{}
     }
  }'
  ```
  Then, you can follow these steps to start your web crawler with the custom ingest pipeline:
  * Go to your web crawler index page, and click on `Crawl` in the upper right corner to start it. 
  * You will see new crawl requests on the overview page, and you can click on the request ids to see more details and to monitor the progress of your crawl requests.
    <img src="assets/web_crawler_overview_with_crawl_requests.png" width="966" height="563" />
  * If you see your crawler is running and the number of documents is increasing, your web crawler is working with the ingest pipeline. 
    You can now inspect the documents and learn more about the web crawler from [Elastic documentation](https://www.elastic.co/guide/en/enterprise-search/8.10/crawler.html) to improve or customize your web crawler. 


* Run a nested `text_expansion` query using cURL
  ```shell
  curl -k -X GET "${ES_URL}/${ES_INDEX_NAME}/_search?pretty" \
  -u "${ES_USER}:${ES_PASSWORD}" \
  -H 'Content-Type: application/json' -d'
  {
    "query": {
      "nested": {
        "path": "passages",
        "query": {
          "text_expansion": {
            "passages.sparse.tokens": {
              "model_id": ".elser_model_1",
              "model_text": "what is the best time to visit Arcadia national park?"
            }
          }
        },
        "inner_hits": {"_source": {"excludes": ["passages.sparse"]}}
      }
    }
  }'
  ```
  The above command sends a nested query to the Elasticsearch index. `text_expansion` is used in this query on the ELSER tokens 
  generated for each chunked text by the ingest pipeline. So, the search happens on the chunked texts. Learn more about nested 
  query from the [Elastic documentation](https://www.elastic.co/guide/en/elasticsearch/reference/8.10/query-dsl-nested-query.html).

  If you see successful results from the above query, you have successfully created a web crawler index with an ELSER ingest pipeline with a chunking processor.

## Step 4: Connect a web crawler index to watsonx Assistant for conversational search 

* Follow the instructions in [Language Model Conversational Search Starter Kit](../../starter-kits/language-model-conversational-search/README.md#example-1--connect-your-assistant-to-elasticsearch-and-watsonx-via-custom-extensions) 
  to build custom extensions with Elasticsearch and watsonx.ai to enable Conversational Search in watsonx Assistant.


> ⛔️
> **Caution**  
> * After uploading the [sample actions JSON file](../../starter-kits/language-model-conversational-search/elasticsearch-watsonx-actions.json) to your Assistant following the instructions [here](../../starter-kits/language-model-conversational-search/README.md#upload-sample-actions), 
    you need to switch the session variable `has_inner_hits` to `True` so that the right `query_body` will be used for your web crawler index. 
> * Remember to provide `_source` with `query_source` session variable when using your Elasticsearch extension in the action steps. 
    By limiting the fields available in the `_source`, we can limit the length of the query response. Otherwise, 
    you may encounter 500 errors due to the response body exceeding the length limit `102400`.  
    <img src="assets/config_query_source_when_use_es_extension.png" width="667" height="620">


* Example usage:  
  <img src="assets/conversation-search-example-with-web-crawler.png" width="285" height="480">
