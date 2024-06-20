from dotenv import load_dotenv

# load credentials and config from .env file
load_dotenv()

from os import getenv
from flask import Flask, request

import smtplib
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText

app = Flask(__name__)

smtp_host = getenv('SMTP_HOST')
smtp_port = int(getenv('SMTP_PORT'))
enable_tls = getenv('SMTP_ENABLE_TLS', 'true').lower() == 'true'

smtp_username = getenv('SMTP_USERNAME')
sender_email = getenv('SMTP_EMAIL') or smtp_username
smtp_password = getenv('SMTP_PASSWORD')

api_server_key = getenv('API_SERVER_KEY').strip()

@app.before_request
def authenticate():
    # Do not check authentication for the status route
    if request.path == '/':
        return

    token = request.headers.get('Authorization', '').strip()
    if token != f'Bearer {api_server_key}':
        return {'success': False, 'message': 'Authentication required.'}, 401

@app.route('/')
def status():
    return {
        "message": "SMTP extension server is up and running",
    }

@app.route('/send', methods=['POST'])
def send():
    try:
        data = request.get_json()

        receiver_email = data['receiver']
        subject = data['subject']
        body = data['body']

        message = MIMEMultipart()
        message["From"] = sender_email
        message["To"] = receiver_email
        message["Subject"] = subject
        message.attach(MIMEText(body, "plain"))

        with smtplib.SMTP(smtp_host, smtp_port) as server:
            if enable_tls:
                server.starttls()
            server.login(smtp_username, smtp_password)
            text = message.as_string()
            server.sendmail(sender_email, receiver_email, text)

        print(f"Email sent to {receiver_email} with subject {subject}")
        return {
            "message": f"Email sent to {receiver_email} with subject {subject}",
            "success": True
        }
    except Exception as e:
        print(f"Error sending email: {e}")
        return {
            "message": str(e),
            "success": False
        }, 500

if __name__ == '__main__':
    app.run(port=getenv('API_SERVER_PORT', 5000), host='0.0.0.0', debug=True)