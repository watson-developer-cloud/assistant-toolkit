# How to set up watsonx Discovery (Elasticsearch) and integrate it with Watson Assistant in CloudPak
This is a documentation about how to set up watsonx Discovery (Elasticsearch) and integrate it with watsonx Assistant in CloudPak

## Table of contents:
* [Step 1: Install Elastic Cloud on Kubernetes(ECK) on CloudPak](#step-1-install-elastic-cloud-on-kubernetes-eck-in-cloudpak)
* [Step 2: Enable semantic search with ELSER in Elasticsearch](#step-2-enable-semantic-search-with-elser-in-elasticsearch)
* [Step 3: Create a custom extension for Elasticsearch in watsonx Assistant](#step-3-build-a-custom-extension-in-watsonx-assistant-for-elasticsearch-api)


## Step 1: Install Elastic Cloud on Kubernetes(ECK) on CloudPak
This step is about installing Elastic Cloud on Kubernetes (ECK) in CloudPak. 

Before you begin, you will need:
* Access to a CloudPak cluster where watsonx Assistant is deployed
* An ECK enterprise Orchestration license

### Log in to your CloudPak cluster
* Log in to your Redhat OpenShift console using your admin credentials.
* Find your login API token by clicking on `Copy login command` from the top right dropdown menu.
* After displaying the token, copy the `Log in with this token` command, and run it from your terminal window.  
  NOTE: If you don't have the OpenShift CLI (`oc`) installed, you can find the instructions to install it by clicking on 
  the `Command line tools` from the top right :grey_question: dropdown menu.
* If you see logs like below, you have successfully logged into your CloudPak cluster
  ```
  Logged into "https://api.wa24.cp.fyre.ibm.com:6443" as "kube:admin" using the token provided.

  You have access to 78 projects, the list has been suppressed. You can list all projects with 'oc projects'

  Using project "default".
  ```

### Install ECK with Elasticsearch cluster and Kibana
* Create environment variables for the installation, for example, 
  ```shell
  export ES_NAMESPACE="elastic"
  export STORAGECLASS="ocs-storagecluster-ceph-rbd"
  export ES_CLUSTER="wxd"
  ```
  NOTE: you need to find a valid StorageClass name. `oc get sc` will show you all the StorageClass objects.
* Create a new namespace for ECK installation
  ```shell
  oc create ns ${ES_NAMESPACE}
  ```
* Create ECK crds
  ```shell
  oc create -f https://download.elastic.co/downloads/eck/2.9.0/crds.yaml
  ```
  NOTE: `2.9.0` is the latest supported version by watsonx Discovery
* Create ECK operator in a different namespace `elastic-system`
  ```shell
  oc apply -f https://download.elastic.co/downloads/eck/2.9.0/operator.yaml
  ```
* Create a Elasticsearch cluster
  ```shell
  cat <<EOF |oc apply -f -
  apiVersion: elasticsearch.k8s.elastic.co/v1
  kind: Elasticsearch
  metadata:
    name: ${ES_CLUSTER}
    namespace: ${ES_NAMESPACE}
  spec:
    volumeClaimDeletePolicy: DeleteOnScaledownOnly
    version: 8.11.1
    nodeSets:
      - name: default
        count: 3
        volumeClaimTemplates:
          - metadata:
              name: elasticsearch-data # Do not change this name unless you set up a volume mount for the data path.
            spec:
              accessModes:
                - ReadWriteOnce
              resources:
                requests:
                  storage: 50Gi
              storageClassName: ${STORAGECLASS}
        podTemplate:
          spec:
            containers:
              - name: elasticsearch
                resources:
                  requests:
                    memory: 8Gi
                    cpu: 2
                  limits:
                    cpu: 8
                    memory: 8Gi
  EOF
  ```  
  NOTE: Learn more about configuring compute resources from [here](https://www.elastic.co/guide/en/cloud-on-k8s/2.9/k8s-managing-compute-resources.html).
  * Monitor Elasticsearch health and creation progress
    ```shell
    oc get Elasticsearch -n ${ES_NAMESPACE}
    ```
  * Check that the Elasticsearch pods are running 
    ```shell
    oc get pods -n ${ES_NAMESPACE}
    ```

* Create a Kibana instance and associate it with your Elasticsearch cluster
  ```shell
  cat <<EOF |oc apply -f -
  apiVersion: kibana.k8s.elastic.co/v1
  kind: Kibana
  metadata:
    name: ${ES_CLUSTER}
    namespace: ${ES_NAMESPACE}
  spec:
    version: 8.11.1
    count: 3
    elasticsearchRef:
      name: ${ES_CLUSTER}
    podTemplate:
      spec:
        containers:
        - name: kibana
          env:
            - name: NODE_OPTIONS
              value: "--max-old-space-size=2048"
          resources:
            requests:
              memory: 1Gi
              cpu: 0.5
            limits:
              memory: 2.5Gi
              cpu: 2
  EOF
  ```  
  NOTE: the container resources are configurable.
  * Monitor Kibana health and creation progress
    ```shell
    oc get kibana -n ${ES_NAMESPACE}
    ```
  * A ClusterIP Service is automatically created for Kibana, check it by:
    ```shell
    oc get service ${ES_CLUSTER}-kb-http -n ${ES_NAMESPACE} 
    ```

* Add an ECK enterprise license  
  When you install the default distribution of ECK, you receive a Basic license. If you have a valid Enterprise 
  subscription or a trial license extension, you will receive a link to download a license as a JSON file. You can then
  add the license to your ECK installation. 
  ```shell
  oc create secret generic eck-license --from-file=my-license-file.json -n elastic-system
  oc label secret eck-license "license.k8s.elastic.co/scope"=operator -n elastic-system
  ```
  NOTE: Replace `my-license-file.json` with the JSON file you have downloaded. Learn more about adding a ECK license [here](https://www.elastic.co/guide/en/cloud-on-k8s/current/k8s-licensing.html#k8s-add-license).

### Verify the installation 
* Obtain the Elasticsearch credentials
  * The default username is `elastic`
  * The password can be obtained by the following command:
    ```shell
    oc get secret ${ES_CLUSTER}-es-elastic-user -o=jsonpath='{.data.elastic}' -n ${ES_NAMESPACE}| base64 --decode; echo
    ```
* Use `port-forward` to access Kibana locally:
  ```shell
  oc port-forward service/${ES_CLUSTER}-kb-http 5601 -n ${ES_NAMESPACE}
  ```
* Open https://localhost:5601 in browser, and login using the Elasticsearch credentials. 
  If you can successfully log into Kibana and see the Kibana dashboard without any errors, your ECK installation is successful.

## Step 2: Enable semantic search with ELSER in Elasticsearch
This step is to enable semantic search using ELSER. Here are the tutorials from Elasticsearch doc:  
ELSER v1: https://www.elastic.co/guide/en/elasticsearch/reference/8.10/semantic-search-elser.html  
ELSER v2: https://www.elastic.co/guide/en/elasticsearch/reference/8.11/semantic-search-elser.html

Note: ELSER v2 has become available since Elasticsearch 8.11. It is preferred to use ELSER v2 if it is available.

The following steps are based on ELSER v2 model:
### Use port-forward to access Elasticsearch cluster locally
In a new terminal window with `ES_CLUSTER` and `ES_NAMESPACE` available as environment variables, run the following 
command to make the Elasticsearch cluster accessible on your local machine.
  ```shell
  oc port-forward service/${ES_CLUSTER}-es-http 9200 -n ${ES_NAMESPACE}
  ```

### Create environment variables for Elasticsearch credentials
In a new terminal window with `ES_CLUSTER` and `ES_NAMESPACE` available as environment variables, follow the following 
steps to create environment variables for later use.  
* Download TLS certificate and create an environment variable for it
  ```shell
  oc -n ${ES_NAMESPACE} get secret "${ES_CLUSTER}-es-http-certs-public" -o go-template='{{index .data "tls.crt" | base64 --decode }}' > tls.crt
  
  export ES_CACERT="tls.crt"
  ```
  NOTE: you can skip the cert if you are okay with insecure connections. Instead of providing `--cacert` to your curl commands, 
  you can use `-k` or `--insecure` for insecure connections.
* Create other environment variables for Elasticsearch credentials
  ```shell
  export ES_URL=https://localhost:9200
  export ES_USER=<username>
  export ES_PASSWORD=<password>
  ```
  You have obtained `ES_USER` and `ES_PASSWORD` from [obtain-the-elasticsearch-credentials](#verify-the-installation) step.

### Enable ELSER model (v2)
ELSER model is not enabled by default, but you can enable it in Kibana. Please follow the [download-deploy-elser instructions](https://www.elastic.co/guide/en/machine-learning/8.11/ml-nlp-elser.html#download-deploy-elser) to do it.

Note: `.elser_model_2_linux-x86_64` is an optimized version of the ELSER v2 model and is preferred to use if it is available. Learn more about [inference-ingest-pipeline](https://www.elastic.co/guide/en/elasticsearch/reference/8.11/semantic-search-elser.html#inference-ingest-pipeline) from the tutorial.

### Load data into Elasticsearch
In Kibana, you can upload a data file to Elasticsearch cluster using the Data Visualizer in the Machine Learning UI https://localhost:5601/app/ml/filedatavisualizer.
(You need to use `port-forward` to allow local access to Kibana service and login using Elasticsearch credentials as described in
[verify-the-installation](#verify-the-installation) step)

As an example, you can download [wa-docs-100](./assets/wa_docs_100.tsv) TSV data and upload it to Elasticsearch.
This dataset contains documents processed from the watsonx Assistant product documents. There are three columns in this TSV file,
`title`, `section_title` and `text`. The columns are extracted from the original documents. Specifically,
each `text` value is a small chunk of text split from the original document.

In Kibana,
* Select your downloaded file to upload  
  <img src="./assets/upload_file_though_data_visualizer.png" width="463" height="248" />
* Click `Override settings` and then check `Has header row` checkbox because the example dataset has header row  
  <img src="./assets/override_settings_for_uploaded_file.png" width="553" height="446" />
* Import the data to a new Elasticsearch index and name it `wa-docs`  
  <img src="./assets/import_data_to_new_index.png" width="509" height="356" />  
  Once finished, you have created an index for the data you just uploaded.

### Create an index with mappings for ELSER output
  ```bash
  curl -X PUT "${ES_URL}/search-wa-docs?pretty" -u "${ES_USER}:${ES_PASSWORD}" \
  -H "Content-Type: application/json" --cacert "${ES_CACERT}" -d'
  {
    "mappings": {
      "_source": {
          "excludes": [
            "text_embedding"
          ]
      },
      "properties": {
        "text_embedding": {
          "type": "sparse_vector"
        },
        "text": {
          "type": "text"
        }
      }
    }
  }'
  ```
Notes:
* `search-wa-docs` will be your index name
* `text_embedding` is the field that will keep ELSER output when data is ingested, and `sparse_vector` type is required for ELSER output field
* `text` is the input filed for the inference processor. In the example dataset, the name of the input field is `text` which will be used by ELSER model to process.
* Learn more about [elser-mappings](https://www.elastic.co/guide/en/elasticsearch/reference/8.11/semantic-search-elser.html#elser-mappings) from the tutorial

### Create an ingest pipeline with an inference processor
Create an ingest pipeline with an inference processor to use ELSER to infer against the data that will be ingested in the pipeline.
  ```bash
  curl -X PUT "${ES_URL}/_ingest/pipeline/elser-v2-test?pretty" -u "${ES_USER}:${ES_PASSWORD}" \
  -H "Content-Type: application/json" --cacert "${ES_CACERT}" -d'
  {
    "processors": [
      {
        "inference": {
          "model_id": ".elser_model_2_linux-x86_64",
          "input_output": [
            {
              "input_field": "text",
              "output_field": "text_embedding"
            }
          ]
        }
      }
    ]
  }'
  ```
Note: `.elser_model_2_linux-x86_64` is an optimized version of the ELSER v2 model and is preferred to use if it is available. Learn more about [inference-ingest-pipeline](https://www.elastic.co/guide/en/elasticsearch/reference/8.11/semantic-search-elser.html#inference-ingest-pipeline) from the tutorial.

### Ingest the data through the inference ingest pipeline
Create the tokens from the text by reindexing the data through the inference pipeline that uses ELSER as the inference model.
  ```bash
  curl -X POST "${ES_URL}/_reindex?wait_for_completion=false&pretty" -u "${ES_USER}:${ES_PASSWORD}" \
  -H "Content-Type: application/json" --cacert "${ES_CACERT}" -d'
  {
    "source": {
      "index": "wa-docs"
    },
    "dest": {
      "index": "search-wa-docs",
      "pipeline": "elser-v2-test"
    }
  }'
  ```
* `wa-docs` is the index you created when uploading the example file to Elasticsearch cluster. It contains the text data
* `search_wa-docs` is the search index that has ELSER output field
* `elser-v2-test` is the ingest pipeline with an inference processor using ELSER model

### Semantic search by using the text_expansion query
To perform semantic search, use the `text_expansion` query, and provide the query text and the ELSER model ID.
The example below uses the query text "How to set up custom extension?", the `text_embedding` field contains
the generated ELSER output:
  ```bash
  curl -X GET "${ES_URL}/search-wa-docs/_search?pretty" -u "${ES_USER}:${ES_PASSWORD}" \
  -H "Content-Type: application/json" --cacert "${ES_CACERT}" -d'
  {
     "query":{
        "text_expansion":{
           "text_embedding":{
              "model_id":".elser_model_2_linux-x86_64",
              "model_text":"how to set up custom extension?"
           }
        }
     }
  }'
  ```
Note: Learn more about [text-expansion-query](https://www.elastic.co/guide/en/elasticsearch/reference/8.11/semantic-search-elser.html#text-expansion-query) from the tutorial.

## Step 3: Build a custom extension in watsonx Assistant for Elasticsearch API

### Provision a watsonx Assistant instance in your CloudPak cluster
From you CloudPak cluster, you need to provision a watsonx Assistant instance and then create an assistant in the new experience.

### Retrieve Elasticsearch endpoints  
  The below command will print out the Elasticsearch Cluster IP and port that you will use as the host and port to access Elasticsearch in your assistant on CloudPak.
  ```shell
  oc -n ${ES_NAMESPACE} get svc "$ES_CLUSTER-es-http" -o jsonpath='{.spec.clusterIP}:{.spec.ports[0].port}'; echo
  ```

### Configure TLS connection with watsonx Assistant  
  By default, watsonx Assistant (wxA) doesn't trust any certificate that's not in its trusted keystore, which means that
  you won't be able to make TLS connections between wxA and Elasticsearch. To make wxA trust Elastic's self-signed certificate, 
  here are two options: 

#### Option 1 (**Not Recommended for Production Use**): Enable the `TRUST_ALL_CERTIFICATES` flag in wxA to trust all certificates
* Define wxA namespace and resource name as environment variables
  ```shell
  WA_INSTANCE="wa"
  WA_NAMESPACE="cpd"
  ```
  NOTE: `WA_INSTANCE` is your wxA resource name, and `WA_NAMESPACE` is the namespace where your wxA is deployed.

* Enable the `TRUST_ALL_CERTIFICATES` flag for wxA via a `patch` command
  ```shell
  oc -n $WA_NAMESPACE patch wa $WA_INSTANCE --type='merge' -p='{"configOverrides":{"webhooks_connector":{"extra_vars":{"TRUST_ALL_CERTIFICATES":true}}}}'
  ```
  Please wait a few minutes for the `wa-webhooks-connector` pod to restart. Once the pod has restarted successfully, 
  TLS connections between your Elasticsearch service and wxA have been enabled.  


  This approach is not recommended for production use, especially for applications that involve sensitive data, 
  because it introduces a security risk. It configures wxA always to trust that all services it connects to through
  search integrations or custom integrations are the services they expect to be at the IP addresses configured in the integrations. 
  Option 2 below eliminates this risk by using a certificate to securely verify that the Elasticsearch service you are 
  connecting to is the one configured for your assistant to connect to.


#### Option 2: Add Elasticsearch's CA certificate to wxA's truststore
* Define wxA namespace and resource name as environment variables, for example,
  ```shell
  WA_INSTANCE="wa"
  WA_NAMESPACE="cpd"
  ```
  NOTE: `WA_INSTANCE` is your wxA resource name, and `WA_NAMESPACE` is the namespace where your wxA is deployed. 
* Get the Elasticsearch CA certificate and create an environment variable for it
  ```shell
  CERT=$( oc -n ${ES_NAMESPACE} get secret "${ES_CLUSTER}-es-http-certs-public" -o go-template='{{index .data "ca.crt" }}' )
  ```
* Create a Secret with the Elastic CA certificate and mount it to the WatsonAssistantIntegrations deployment
  ```
  cat <<EOF | oc apply -n ${WA_NAMESPACE} -f -
  apiVersion: v1
  data:
    ca_cert: ${CERT}
  kind: Secret
  metadata:
    name: ${WA_INSTANCE}-custom-webhooks-cert
  type: Opaque
  ---
  apiVersion: assistant.watson.ibm.com/v1
  kind: TemporaryPatch
  metadata:
    name: ${WA_INSTANCE}-add-custom-webhooks-cert
  spec:
    apiVersion: assistant.watson.ibm.com/v1
    kind: WatsonAssistantIntegrations
    name: ${WA_INSTANCE}
    patchType: patchStrategicMerge
    patch:
      webhooks-connector:
        deployment:
          spec:
            template:
              spec:
                containers:
                - name: webhooks-connector
                  env:
                  - name: CERTIFICATES_IMPORT_LIST
                    value: /etc/secrets/kafka/ca.pem:kafka_ca,/etc/secrets/custom/ca.pem:custom_ca
                  volumeMounts:
                  - mountPath: /etc/secrets/custom
                    name: custom-cert
                    readOnly: true
                volumes:
                - name: custom-cert
                  secret:
                    defaultMode: 420
                    items:
                    - key: ca_cert
                      path: ca.pem
                    secretName: ${WA_INSTANCE}-custom-webhooks-cert
  EOF
  ```
  Please wait a few minutes for the `wa-webhooks-connector` pod to restart, which will happen automatically
  due to the changes applied by the patch. This will take a few minutes or slightly longer. Please be patient.  

  After the pod has restarted, check the logs by running `oc logs wa-webhooks-connector-xxxx`.
  You should see two lines like this at the beginning of the log:
  ```
  [watson@dvt-001-inf ~]$ oc -n ${WA_NAMESPACE} logs wa-webhooks-connector-74756c6748-cmdr6
  Certificate was added to keystore
  Certificate was added to keystore
  ```
* Add required hostnames as Subject Alternative Names (SANs) to the Self-Signed Certificate in Elasticsearch
  * Edit the Elasticsearch deployment:
    ```shell
    oc edit -n ${ES_NAMEPSACE} elasticsearch
    ```
  * Add required hostnames as SANs,  
    You can add `localhost` and the Elasticsearch endpoint/ClusterIP obtained from [retrieve-elasticsearch-endpoints](#retrieve-elasticsearch-endpoints) 
    step as SANs to make them accessible by watsonx Assistant via TLS connection. For example,  
    ```shell
    spec:
      auth: {}
      http:
        service:
          metadata: {}
          spec: {}
        tls:
          certificate: {}
          selfSignedCertificate:
            subjectAltNames:
            - dns: localhost  # for secure localhost access
            - ip: 172.30.249.131 # Change it to your Elasticsearch ClusterIP
    ```

### Follow the instructions to [build a custom extension in watsonx Assistant with Elasticsearch API](../../starter-kits/elasticsearch/README.md#build-a-custom-extension-in-watsonx-assistant-with-elasticsearch-api)
You will need your Elasticsearch credentials to configure a custom extension. 
In addition to the Elasticsearch `username` and `password` obtained from [verify-the-installation](#verify-the-installation) step, the Elasticsearch ClusterIP from [retrieve-elasticsearch-endpoints](#retrieve-elasticsearch-endpoints) will be the `host`, 
and the `port` is `9200`. 
