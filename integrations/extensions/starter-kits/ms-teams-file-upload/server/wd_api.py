import os
from typing import BinaryIO

from ibm_watson import DiscoveryV2
from ibm_cloud_sdk_core.authenticators import BasicAuthenticator

from dotenv import load_dotenv

load_dotenv()  # take environment variables from .env

authenticator = BasicAuthenticator('apikey', os.getenv('WD_APIKEY'))
discovery = DiscoveryV2(
    version=os.getenv('WD_API_VERSION', '2023-03-31'),
    authenticator=authenticator
)
discovery.set_service_url(os.getenv('WD_API_URL'))


def add_document(f: BinaryIO, file_name: str,
                 file_content_type: str = None, project_id: str = os.getenv('WD_PROJECT_ID'),
                 collection_id: str = os.getenv('WD_COLLECTION_ID')) -> dict:
    response = discovery.add_document(
        project_id=project_id,
        collection_id=collection_id,
        filename=file_name,
        file_content_type=file_content_type,
        file=f,
    ).get_result()
    return response


def check_status(document_id: str, project_id: str = os.getenv('WD_PROJECT_ID'),
                 collection_id: str = os.getenv('WD_COLLECTION_ID')) -> dict:
    response = discovery.get_document(
        project_id=project_id,
        collection_id=collection_id,
        document_id=document_id,
    ).get_result()
    return response


def search_in_doc(document_id: str, query: str, project_id: str = os.getenv('WD_PROJECT_ID'),
                  collection_id: str = os.getenv('WD_COLLECTION_ID')) -> dict:
    response = discovery.query(
        project_id=project_id,
        collection_ids=[collection_id],
        natural_language_query=query,
        filter=f"document_id::{document_id}",
    ).get_result()
    return response
