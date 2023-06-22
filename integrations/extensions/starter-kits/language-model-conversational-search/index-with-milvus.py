import os, PyPDF2, logging

from langchain.vectorstores import Milvus
from langchain.embeddings import HuggingFaceHubEmbeddings
from langchain.text_splitter import RecursiveCharacterTextSplitter

SOURCE_FILE_NAMES=["Lendyr Everyday Card.pdf", "Lendyr Preferred Card.pdf", "Lendyr Topaz Card.pdf"]
SOURCE_URLS=["https://www.lendyr.com/", "https://www.lendyr.com/", "https://www.lendyr.com/"]
SOURCE_TITLES=["Lendyr Everyday Card", "Lendyr Preferred", "Lendyr Topaz"]

# SOURCES_TOPIC should be a single title that describes all of the sources.  The LLM uses it to decide whether to search those sources.
SOURCES_TOPIC="Lendyr Credit Cards"

INDEX_NAME="Lendyr3"

EMBED = HuggingFaceHubEmbeddings(repo_id="sentence-transformers/all-MiniLM-L6-v2")
MILVUS_CONNECTION={"host": os.environ.get("MILVUS_HOST"), "port": os.environ.get("MILVUS_PORT")}

CHUNK_SIZE=250
CHUNK_OVERLAP=20

logger = logging.getLogger(__name__)
formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
handler = logging.StreamHandler()
handler.setLevel(logging.INFO)
handler.setFormatter(formatter)
logger.addHandler(handler)
logger.info("Logger initialized")

def connect(connection_info):
    index = Milvus(
           EMBED,
           connection_args=connection_info,
           collection_name=INDEX_NAME,
           index_params="text"
       )
    return index


def index(connection_info, filenames, urls, titles):
    texts, metadata = load_docs_pdf(filenames, urls, titles)
    text_splitter = RecursiveCharacterTextSplitter(chunk_size=CHUNK_SIZE, chunk_overlap=CHUNK_OVERLAP)
    split_texts = text_splitter.create_documents(texts, metadata)
    logging.info(f"Documents chunked.  Sending to Milvus.")
    index = Milvus.from_documents(documents=split_texts, embedding=EMBED, connection_args=connection_info, collection_name=INDEX_NAME)
    return index


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
    return texts, metadata

INDEXED=True
if __name__ == "__main__":
    logger.setLevel(logging.INFO)
    if INDEXED:
        logging.info(f"Connecting to {MILVUS_CONNECTION}")
        index = connect(MILVUS_CONNECTION)
    else:
        logging.info(f"Indexing at {MILVUS_CONNECTION}")
        index = index(MILVUS_CONNECTION, SOURCE_FILE_NAMES, SOURCE_URLS, SOURCE_TITLES)
    
    print(index)
    query = "What is the interest rate for Lendyr Preferred?"
    results = index.similarity_search(query)
    print(results)
