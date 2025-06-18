import uuid
from typing import List

from document import Document

sessions = {}


class Session:
    """
    A session object stores all the documents uploaded by the user in a conversation.
    """
    def __init__(self):
        self.id = str(uuid.uuid4())
        self.documents: List[Document] = []

    @classmethod
    def new_session(cls) -> 'Session':
        session = cls()
        sessions[session.id] = session
        return session

    @classmethod
    def get_session(cls, session_id: str) -> 'Session':
        return sessions.get(session_id, cls.new_session())

    def add_document(self, document: Document):
        self.documents.append(document)

    def get_all_documents_status(self) -> list[dict]:
        for doc in self.documents:
            doc.check_wd_status()

        return [{"file_index": i + 1, "file_name": doc.file_name, "wd_status": doc.wd_status} for i, doc in
                enumerate(self.documents)]

    def get_all_documents_options(self) -> list[str]:
        for doc in self.documents:
            doc.check_wd_status()

        return [f"{i + 1}. {doc.file_name} ({doc.wd_status})" for i, doc in enumerate(self.documents)]

    def get_all_documents_status_table(self, available_on_wd: bool = False) -> str:
        result = "<table><tr><th>Index</th><th>File name</th><th>Mime type</th><th>Size</th><th>WD processing status</th></tr>"

        for i, doc in enumerate(self.documents):
            doc.check_wd_status()

            if available_on_wd and doc.wd_status != 'available':
                continue

            filename = doc.file_name
            if doc.url:
                filename = f"<a href='{doc.url}'>{filename}</a>"

            result += f"<tr><td>{i + 1}</td><td>{filename}</td><td>{doc.mine_type}</td><td>{doc.get_size_string()}</td><td>{doc.wd_status}</td></tr>"

        result += "</table>"

        return result

    def get_doc_by_index(self, index: int) -> Document:
        if len(self.documents) == 0:
            raise ValueError("No documents uploaded.")
        elif index >= len(self.documents):
            raise ValueError(f"Invalid document index {index}. There are {len(self.documents)} documents uploaded.")
        elif index < 0:
            raise ValueError(f"Invalid document index {index}. Index must be greater than or equal to 0.")

        return self.documents[index]
