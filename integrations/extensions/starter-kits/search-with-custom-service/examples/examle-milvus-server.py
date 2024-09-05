import flask, os, logging, pymilvus
from langchain_milvus import Milvus
from langchain_ibm import WatsonxEmbeddings
from ibm_watsonx_ai.foundation_models.utils.enums import EmbeddingTypes

WATSONX_AI_URL="https://us-south.ml.cloud.ibm.com"

EMBED = WatsonxEmbeddings(
        model_id="ibm/slate-125m-english-rtrvr-v2",
        url=WATSONX_AI_URL,
        apikey=os.environ.get("WATSONX_AI_APIKEY"),
        project_id=os.environ.get("WATSONX_AI_PROJECT_ID")
    )

MILVUS_CONNECTION={"host": os.environ.get("MILVUS_HOST"), "port": os.environ.get("MILVUS_PORT"),
                   "user": os.environ.get("MILVUS_USER"), "password": os.environ.get("MILVUS_PASSWORD")}

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)
app = flask.Flask(__name__)

def run_search(query, metadata):
    if metadata == None or 'collection_name' not in metadata:
        return "metadata.collection_name is not set", 400

    collection_name = metadata['collection_name']

    collection = Milvus(
        embedding_function=EMBED,
        connection_args=MILVUS_CONNECTION,
        collection_name=collection_name,
        index_params="text"
    )

    try:
        milvus_results = collection.similarity_search(query)
    except pymilvus.exceptions.MilvusException as e:
        return f'{e}', 500

    output_results = []
    for milvus_result in milvus_results:
        try:
            output_result = {
                "title": milvus_result.metadata['title'],
                "url": milvus_result.metadata['url'],
                "body": milvus_result.page_content
            }
        except AttributeError as e:
            return f'Malformed search result: {milvus_result}', 500   
        except Exception as e:
            return f'Unexpected error for: {milvus_result}.  Error: {e}', 500   

        output_results.append(output_result)

    return {"search_results": output_results}


@app.route("/query", methods=["POST"])
def answer():
    data = flask.request.json
    logger.info(data)
    query = data['query']
    metadata = data['metadata'] if 'metadata' in data else None
    results = run_search(query, metadata)
    logger.info(results)
    return results


if __name__ == '__main__':
    app.run()