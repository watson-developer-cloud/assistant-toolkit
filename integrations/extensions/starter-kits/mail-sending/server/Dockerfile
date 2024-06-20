FROM python:3.10-slim-buster

WORKDIR /python-docker

COPY requirements.txt requirements.txt
RUN pip3 install -r requirements.txt

COPY . .
COPY .env .env

EXPOSE 5000

CMD [ "python3", "app.py" ]