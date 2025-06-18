from io import BytesIO
from typing import List

import requests

from utils import determine_file_name
from watsonx_api import summarize
from wd_api import add_document, check_status, search_in_doc

from PyPDF2 import PdfReader


class Document:
    def __init__(self, file_name: str, binary_data: bytes, mine_type: str = None, text: str = None, url: str = None):
        self.url = url
        self.file_name = file_name
        self.mine_type = mine_type
        self.binary_data = binary_data
        self.text = text
        self.wd_id = None
        self.wd_status = 'not uploaded'

    def upload_to_wd(self) -> dict:
        response = add_document(BytesIO(self.binary_data), self.file_name, self.mine_type)
        self.wd_id = response['document_id']
        self.wd_status = response['status']
        return response

    def check_wd_status(self, force: bool = False) -> dict:
        if self.wd_id is None:
            return {'status': 'not uploaded'}

        if not force and self.wd_status == 'available':
            return {'status': 'available'}

        response = check_status(self.wd_id)
        self.wd_status = response['status']
        return response['status']

    @classmethod
    def from_url(cls, url: str, mine_type: str) -> 'Document':
        # Download file as binaryIO
        file_response = requests.get(url)
        file_data = file_response.content

        # The filename is determined by reading the Content-Disposition header of the file response.
        # If the header is not present, a random name is generated.
        file_name = determine_file_name(file_response)

        if file_response.status_code != 200:
            raise ValueError(f"Error downloading file from {url}. Server response code: {file_response.status_code}")

        print(f"File downloaded, name: {file_name}; size: {int(file_response.headers['Content-Length']) // 1024} KB")

        return cls(file_name, file_data, mine_type, None, url)

    def extract_text(self) -> str:
        if 'pdf' not in self.mine_type:
            raise ValueError(f"File type {self.mine_type} not supported.")

        texts = []
        reader = PdfReader(BytesIO(self.binary_data))

        for page in reader.pages:
            texts.append(page.extract_text())

        self.text = '\n\n'.join(texts)
        return self.text

    def summarize(self) -> str:
        if self.text is None:
            raise ValueError("Document text not extracted.")

        return summarize(self.text)

    def search_in_doc(self, query: str) -> List[str]:
        self.check_wd_status()

        if self.wd_status != 'available':
            raise ValueError(f"Document {self.file_name} is not available on WD yet. Current status: {self.wd_status}")

        response = search_in_doc(self.wd_id, query)

        results = []

        for result in response['results']:
            for document_passage in result['document_passages']:
                results.append(document_passage['passage_text'])

        return results

    def get_size_string(self) -> str:
        size = len(self.binary_data)

        if size < 1024:
            return f"{size} B"
        elif size < 1024 ** 2:
            return f"{size // 1024} KB"
        elif size < 1024 ** 3:
            return f"{size // 1024 ** 2} MB"
