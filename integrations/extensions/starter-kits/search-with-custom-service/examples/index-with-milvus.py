import os, PyPDF2, logging

from langchain_milvus import Milvus
from langchain.text_splitter import RecursiveCharacterTextSplitter
from langchain_ibm import WatsonxEmbeddings
from ibm_watsonx_ai.foundation_models.utils.enums import EmbeddingTypes
from pymilvus import connections, utility

# If this is true, then we regenerate the index even if it already exists
FORCE_INDEXING=False

WATSONX_AI_URL="https://us-south.ml.cloud.ibm.com"

SOURCE_FILES=["./sample-documents/IBM_Annual_Report_2023.pdf"]
SOURCE_URLS=["https://www.ibm.com/annualreport/assets/downloads/IBM_Annual_Report_2023.pdf"]
SOURCE_TITLES=["IBM Annual Report 2023"]

COLLECTION_NAME=os.environ.get("MILVUS_COLLECTION_NAME")

EMBED = WatsonxEmbeddings(
        model_id="ibm/slate-125m-english-rtrvr-v2",
        url=WATSONX_AI_URL,
        apikey=os.environ.get("WATSONX_AI_APIKEY"),
        project_id=os.environ.get("WATSONX_AI_PROJECT_ID")
    )

MILVUS_CONNECTION = {
    "uri": f"https://{os.environ.get('MILVUS_HOST')}:{os.environ.get('MILVUS_PORT')}",
    "user": os.environ.get("MILVUS_USER"),
    "password": os.environ.get("MILVUS_PASSWORD")
    }

CHUNK_SIZE=500
CHUNK_OVERLAP=125

logger = logging.getLogger(__name__)
formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
handler = logging.StreamHandler()
handler.setLevel(logging.INFO)
handler.setFormatter(formatter)
logger.addHandler(handler)
logger.info("Logger initialized")

def connect(connection_info):
    collection = Milvus(
           embedding_function=EMBED,
           connection_args=connection_info,
           collection_name=COLLECTION_NAME,
           index_params={}
       )
    return collection


def index(connection_info, filenames, urls, titles):
    texts, metadata = load_docs_pdf(filenames, urls, titles)
    text_splitter = RecursiveCharacterTextSplitter(chunk_size=CHUNK_SIZE, chunk_overlap=CHUNK_OVERLAP)
    split_texts = text_splitter.create_documents(texts, metadata)
    logging.info(f"Documents chunked.  Sending to Milvus.")
    collection = Milvus.from_documents(
        documents=split_texts[:1], # Initialize the collection with one document
        embedding=EMBED,
        connection_args=connection_info,
        collection_name=COLLECTION_NAME,
        drop_old=True,
        auto_id=True,
        )
    collection.add_documents(split_texts[1:], batch_size=100) # Add the rest of the documents to the collection
    return collection


def load_docs_pdf(filenames, urls, titles):
    texts = []
    metadata = []
    i = 0
    for filename in filenames:
        if len(urls) > i:
            url = urls[i]
        else:
            url = ""
        if len(titles) > i:
            title = titles[i]
        else:
            title = ""
        with open(filename, 'rb') as f:
            pdf_reader = PyPDF2.PdfReader(f)
            for page in pdf_reader.pages:
                text = page.extract_text()
                texts.append(text)
                metadata.append({'url': url, 'title': title})
        i += 1
    return texts, metadata


def run(force_indexing=False):
    print(MILVUS_CONNECTION)

    connections.connect(uri=MILVUS_CONNECTION["uri"], secure=True, alias="default", user=MILVUS_CONNECTION["user"], password=MILVUS_CONNECTION["password"])
    has = utility.has_collection(COLLECTION_NAME)
    print(f"Does collection {COLLECTION_NAME} exist in Milvus: {has}")

    logger.setLevel(logging.INFO)
    if has and not force_indexing:
        logging.info(f"Connecting to {MILVUS_CONNECTION}")
        collection = connect(MILVUS_CONNECTION)
    else:
        logging.info(f"Indexing at {MILVUS_CONNECTION}")
        collection = index(MILVUS_CONNECTION, SOURCE_FILES, SOURCE_URLS, SOURCE_TITLES)
    
    # Test out a query to Milvus & print results
    query = "What were earnings in 2023?"
    results = collection.similarity_search(query)

    for result in results:
        print("----------")
        title = result.metadata['title']
        print(title)
        url = result.metadata['url']
        print(url)
        text = result.page_content
        print(text)


if __name__ == "__main__":
    run(force_indexing=FORCE_INDEXING)