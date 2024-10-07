# BluePoints Conversational Skill Provider for watsonx Assistant (Example)

This project is an example of a conversational skill provider for watsonx Assistant using the Springboot web server starter. It demonstrates how a certain amount of BluePoints can be sent to a recipient with a comment. All slots (`recipient`, `amount`, `receiver_comment`) may be specified in a single prompt. The skill queries a mocked backend for the recipient. The mocked backend only contains the following users:

- John Miller
- Mary Miller
- Peter Miller

## Prompt examples

- I would like to send BluePoints.
- I would like to send BluePoints to Peter Miller.
- I would like to send 50 BluePoints to Peter Miller.
- I would like to send 50 BluePoints to Peter Miller with commenet "Great job!".

## Running the application

You can run this application locally by using the following command from within this folder:

```shell script
mvn spring-boot:run
```

This will start the application server and make the below endpoints available on `localhost:8080`

/providers/{provider_id}/conversational_skills (GET)

/providers/{provider_id}/conversational_skills/{conversational_skill_id}/orchestrate (POST)

Where {provider_id} can be any provided interger/uid and {conversational_skill_id} must be `bluepoints`

The documentation of these REST endpoints can be found [here](https://github.com/watson-developer-cloud/assistant-toolkit/blob/master/conversational-skills/procode-endpoints.md).
