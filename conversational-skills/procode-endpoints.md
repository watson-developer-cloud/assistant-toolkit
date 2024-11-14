# Conversational Skills: Masterdoc for API Endpoints to Implement by the Pro-Code Conversational Skill Client

The following endpoints are the APIs that will be implemented **by** a pro-code client of watsonx Assistant, and consumed by the watsonx Assistant product.

## Table of Contents

- [Runtime API](#runtime-api) | `POST /providers/{{provider_id}}/conversational_skills/{{conversational_skill_id}}/orchestrate`
- [List Conversational Skills](#list-conversational-skills) | `GET /providers/{{provider_id}}/conversational_skills`
- [Get Conversational Skill](#get-conversational-skill) | `GET /providers/{{provider_id}}/conversational_skills/{{conversational_skill_id}}`
- [Complete OAS (Open API Schema)](#oas)

### Runtime API 

Once a skill-backed action is created, it may be invoked on the [message API](https://cloud.ibm.com/apidocs/assistant-v2#message). In order for a callout from the assistant to a pro-code conversational skill to succeed, the runtime API (`POST /providers/{{provider_id}}/conversational_skills/{{conversational_skill_id}}/orchestrate`) must be implemented on the host from the registration of the pro code provider.

You may find an example of a NodeJS SDK [here](procode-skill-sdk-js)

**Example Request Body**
```json
request = {
    "input": { /* WxA input schema */ },
    "context": {
        "global": {
            /* Useful information for conversational_skill */
            "session_id": "<session-id>",
            "assistant_id": "<assistant-id>",
            "environment_id": "<environment-id>",
            "language": "<Assistant Language>",
            "session_history": [],
        },
        "integrations": {
            "chat": {
                "private": {
                    "jwt": "<Any>" /* auth: existing support */,
                }
            }
        }
    },
    "slots": [
        {
            /*  Note: other slot details are not required to be communicated 
            to the conversational_skill. it already has them.  */
            "name": "<slot-name>",
            "value": {
                "normalized": "<normalized-value>",
                "literal": "<literal-value>"
            },
            /* Flag to indicate if this turn filled, or repaired the value */
            "event": "one of: fill, repair"
        }
    ],
    "state": {
        "current_slot": "<slot-name>",
        "local_variables": {},
        "session_variables": {}
    },
    "confirmation_event": "<one of: user_confirmed | user_cancelled>"
}
```

**Response**

```json
response = {
    "state": {
        "local_variables": {},
        "session_variables": {}
    },
    "output": {
        "generic": [ 
            /*  Any sequence of WxA supported response_types schema 
                + the following new response_type */
            {
                "response_type": "slots",
                "slots": [{
                    "name": "<a unique name for the slot>",
                    "type": "one of: string | number | date | time | regex | entity | confirmation",
                    "description": "<a description for the slot>",
                    "validation_error": "<an error message to display to the user in case the slot value is not valid per business rules>",
                    "prompt": Any,
                    "value": {
                        "normalized": "<normalized-value>",
                        "literal": "<literal-value>"
                    }
                }],
                "confirmation": Any
            }
        ]
    },
    "resolver": {
        "type": "<one of: user_interaction, skill_complete, skill_cancel>",
        "<type>": Any
    }
}
```

### List Conversational Skills

Fetches a list of conversational skills. The `assistant_id` and `environment_id` must be passed as query parameters.

**NOTE** This endpoint **MUST BE** implemented in order to create a skill-backed action from the watsonx Assistant UI / AI assistant builder. This endpoint is **optional** for users who create conversational flows entirely from the API.


**Example Request Body**
```json
N/A
```

**Example Response**
```json
{
  "conversational_skills": [
    {
      "id": "{{conversational_skill_id}}",
      "name": "Order Takeout",
      /* Readable description of the Conversational Skill's purpose */
      "description": "Enables a user to place a takeout food order from a restaurant", 
      "created": "2024-02-01T04:55:18.871Z",
      "modified": "2024-02-01T04:55:18.871Z",
      /* Optional free-form metadata object for future usecases. */
      "metadata": { 
        "last_modified_by": "Rachel@ibm.com"
      }
    },
    {
      "id": "{{conversational_skill_id}}",
      "name": "Make Reservation",
      /* Readable description of the Conversational Skill's purpose */
      "description": "Enables a user to place a reservation at a restaurant", 
      "created": "2024-02-01T04:55:18.871Z",
      "modified": "2024-01-01T04:55:18.871Z",
      /* Optional free-form metadata object for future usecases. */
      "metadata": { 
        "last_modified_by": "Anbu@ibm.com"
      }
    }
  ],
  "pagination": {
    "total": "2"
  }
}
```


### Get Conversational Skill

Retrieves the information of a conversational_skill. The `assistant_id` and `environment_id` must be passed as query parameters.

**NOTE** This endpoint **MUST BE** implemented in order to enable variable-passing on the watsonx Assistant UI.

**Example Request Body**
```json
N/A
```

**Example Response**
```json
{
  "id": "{{conversational_skill_id}}",
  "name": "Order Takeout",
  "description": "Enables a user to place a takeout food order from a restaurant",
  "created": "2024-02-01T04:55:18.871Z",
  "modified": "2024-02-01T04:55:18.871Z",
  "input": {
      "slots": [
          {
              "name": "<slot name>", 
              "description": "<slot description>", 
              "type": "one of: <string | number | date | time | regex | entity | confirmation | any>", 
          }
      ]
  },
}
```

### OAS

See [procode-spec.yaml](./procode-spec.yaml)
