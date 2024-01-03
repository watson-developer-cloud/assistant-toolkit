import os

from flask import Flask, request
from dotenv import load_dotenv

from document import Document
from session import Session
from utils import determine_option_index, format_search_result_text

load_dotenv()  # take environment variables from .env

app = Flask(__name__)

WD_ENABLED = os.getenv('WD_ENABLED', 'true').lower() == 'true'
WATSONX_ENABLED = os.getenv('WATSONX_ENABLED', 'true').lower() == 'true'


@app.before_request
def retrieve_session_data():
    """
    Retrieve session data from request by reading the session_id parameter. If the session_id is not present or invalid,
    a new session will be created.
    The session object will be added as a property into the request object, which can be accessed with `request.session`
    in route functions.
    :return:
    """

    # Do not check session for the status route
    if request.path == '/':
        return

    data = request.get_json()
    if data is None or 'session_id' not in data:
        request.session = Session.new_session()
    else:
        request.session = Session.get_session(data['session_id'])


@app.before_request
def authenticate():
    """
    Check if the request has correct Bearer token. The API key is set by the API_SERVER_KEY environment variable.
    :return:
    """

    # Do not check authentication for the status route
    if request.path == '/':
        return

    token = request.headers.get('Authorization', '').strip()
    if token != f'Bearer {os.getenv("API_SERVER_KEY")}'.strip():
        return {'success': False, 'message': 'Authentication required.'}, 401


@app.route('/')
def status():
    return 'Teams File Upload API server is up and running.'


@app.post('/upload')
def upload():
    """
    Retrieve the document from the URL provided by Teams, store its data into current session object and upload it to
    Watson Discovery. The file's text will be extracted and stored in the session object as well.
    :return:
    """
    try:
        data = request.get_json()

        attachments = data.get('attachments')

        if None in (attachments,):
            return {
                'success': False,
                'message': 'Missing required parameters. Required parameters: attachments'
            }, 400

        print(f"Uploading {len(attachments)} documents...")
        for file in attachments:
            file_url = file['url']
            mine_type = file['media_type']

            document = Document.from_url(file_url, mine_type)
            if WD_ENABLED:
                document.upload_to_wd()
            document.extract_text()

            print(f"Document {document.file_name} uploaded successfully.")

            request.session.add_document(document)
    except Exception as e:
        return {'success': False, 'message': str(e)}, 500

    return {
        'success': True,
        'session_id': request.session.id,
        'message': f"Documents has been uploaded successfully.",
    }


@app.post('/docs_status')
def docs_status():
    """
    Retrieve the status of all documents in current session.
    :return:
    """

    try:
        return {
            'success': True,
            'session_id': request.session.id,
            # list of dict that contains file info, not really used
            'documents': request.session.get_all_documents_status(),
            # list of file names in format "1. file_name", used to generate WA dynamic options
            'options': request.session.get_all_documents_options(),
            # HTML table that contains file info, rendered in Teams chat
            'message': f'Here are the files available in our conversation:\n{request.session.get_all_documents_status_table()}'
        }

    except Exception as e:
        return {'success': False, 'message': str(e)}, 500


@app.post('/summarize')
def summarize():
    """
    Summarize the document with the given index with WatsonX foundation model.
    :return:
    """

    try:
        if not WATSONX_ENABLED:
            raise ValueError("WatsonX feature is not enabled.")

        data = request.get_json()
        index = determine_option_index(data.get('file_index'))
        if index is None:
            return {
                'success': False,
                'message': 'Missing required parameters. Required parameters: file_index'
            }, 400

        doc = request.session.get_doc_by_index(index)

        return {
            'success': True,
            'session_id': request.session.id,
            'summary': doc.summarize()
        }
    except Exception as e:
        return {'success': False, 'message': str(e)}, 500


@app.post('/search_in_doc')
def search_in_doc():
    """
    Search within specific document for given query with Watson Discovery.
    :return:
    """

    try:
        if not WD_ENABLED:
            raise ValueError("Watson Discovery feature is not enabled.")

        data = request.get_json()
        index = determine_option_index(data.get('file_index'))
        query = data.get('query')

        if None in (index, query):
            return {
                'success': False,
                'message': 'Missing required parameters. Required parameters: file_index, query'
            }, 400

        doc = request.session.get_doc_by_index(index)

        search_results = doc.search_in_doc(query)

        has_results = len(search_results) > 0

        filename = doc.file_name
        if doc.url:
            filename = f"<a href='{doc.url}'>{filename}</a>"

        if not has_results:
            return {
                'success': True,
                'session_id': request.session.id,
                'results': search_results,
                'message': f"I found no result for your query '{query}' in document {filename}."
            }
        else:
            # Display the search result in the following format:
            # I found 2 result(s) about 'query' in document 'filename':
            # |---|-----------------------------|
            # | 1 | result 1 blablabla          |
            # |---|-----------------------------|
            # | 2 | result 2 blablabla          |
            # |---|-----------------------------|

            message = f"I found {len(search_results)} result(s) about '{query}' in document {filename}:\n"

            message += "<table>"

            for i, result in enumerate(search_results):
                message += f"<tr><td>{i + 1}</td><td>{format_search_result_text(result)}</td></tr>"

            message += "</table>"

            return {
                'success': True,
                'session_id': request.session.id,
                'results': search_results,
                'message': message
            }

    except Exception as e:
        return {'success': False, 'message': str(e)}, 500


if __name__ == '__main__':
    app.run(port=os.getenv('API_SERVER_PORT', 5000), host='0.0.0.0', debug=True)
