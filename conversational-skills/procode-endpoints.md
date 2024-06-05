# Conversational Skills: Masterdoc for API Endpoints to Implement by the Pro-Code Conversational Skill Client

The following endpoints are the APIs that will be implemented **by** a pro-code client of watsonx Assistant, and consumed by the watsonx Assistant product.

## Table of Contents

- [Runtime API](#runtime-api) | `POST /providers/{{provider_id}}/conversational_skills/{{conversational_skill_id}}/orchestrate`
- [List Conversational Skills](#list-conversational-skills) | `GET /providers/{{provider_id}}/conversational_skills`
- [Complete OAS (Open API Schema)](#oas)

### Runtime API 

Once a skill-backed action is created, it may be invoked on the [message API](https://cloud.ibm.com/apidocs/assistant-v2#message). In order for a callout from the assistant to a pro-code conversational skill to succeed, the runtime API (`POST /providers/{{provider_id}}/conversational_skills/{{conversational_skill_id}}/orchestrate`) must be implemented on the host from the registration of the pro code provider.

You may find an example of a NodeJS SDK [here](procode-skill-sdk-js)

**Example Request Body**
```json
{
    "input": {
        /* Note: user text message */
        "text": "<user-input-text>",
        ...
        "message_type": "<one of: text, search, ..., or event>",
        "event": {
            /* Note: proposed enhancements for conveying a selection */
            "name": "selection",
            "type": "<one of: single, multiple>",
            /* mandatory when type is single */
            "selected_item": {
                /* one of (key, value), or index is mandatory */
                "key": "<item-key-attribute>",
                "value": "<item-key-value>",
                "index": 0
            },
            /* mandatory when type is multiple */
            "selected_items": [
                { /* selected_item schema */ }
            ],
            "items": []
        }
    },
    "context": {
        "global": {
            ...
            /* Note: session, assistant, and environment details */
            "session_id": "<session-id>",
            "assistant_id": "<assistant-id>",
            "environment_id": "<environment-id>",
            /* Note: `session_history` */
            "session_history": []
        },
        "integrations": {
            ...
            "chat": {
                ...
                "private": {
                    /* auth: existing support */
                    "jwt": "<token-issued-by-chat-host-enterprise>"
                }
            }
        },
        "skills": {
            "main skill": {..},
            "actions": {..},
            "conversational skills": {
                "stack": [
                    {
                        "type": "<one of: action, or conversational_skill>",
                        /* One of the following is mandatory */
                        "conversational_skill": "<conversational-skill-catalog-item-id>",
                        "action": "<action-id>"
                    }
                ],
                "<conversational-skill-catalog-item-id>": {
                    "slots": {..},
                    "local_variables": {
                        "<variable>": Any
                    }
                },
                "session_variables": {
                    "<variable>": Any
                }
            },
            "active_skill": "<one of: 'main skill', 'actions skill', 'conversational skills'>"
        }
    },
    /* Slot change events */
    "slots_changed": {
        "added": [], /* newly filled slot names */
        "repaired": [], /* repaired slot names */
        "refined": [] /* refined slot names */
    },
    "output": {},
}
```

**Response**

```json
{
    "context": {
        ...
        "skills": {
            ...
            "conversational skills": {
                ...
                /* Note: only this part of the context is writable for conversational_skills */
                "<conversational-skill-catalog-item-id>": {
                    "slots": {..},
                    "local_variables": {
                        "<variable>": Any
                    }
                },
                "session_variables": {
                    "<variable>": Any
                }
            },
            ...
        }
    },
    "output": {},
    "resolver": "<one of: continue, user-interaction, end-conversation, fallback, validation_failure, callout, invoke-another-skill, invoke-another-skill-and-end, invoke-another-action, invoke-another-action-and-end>"
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


### OAS

```yaml
openapi: 3.0.3
info:
  title: Procode Endpoints for Conversational Skills
  description: |-
    This document describes the following APIs that will be implemented by a watsonx Assistant client, and consumed by the watsonx Assistant team in order to enable the objectives set forth by the Conversational Skills Mission. 
  license:
    name: Apache 2.0
    url: http://www.apache.org/licenses/LICENSE-2.0.html
  version: 1.0.11
servers:
  - url: www.derp.com
tags:
  - name: Conversational Skills MVP
    description: Everything needed for the conversational skills
paths:
  /providers/{provider_id}/conversational_skills:
    get:
      tags:
      - Conversational Skills MVP
      summary: Fetch a list of conversational skills
      description: "Retrieves a list of conversational skills associated to a particular provider."
      operationId: fetchSkills
      parameters:
      - name: provider_id 
        in: path
        description: Unique identifier of the provider that possesses the conversational skill. It represents the instance that is linked with the WxA instance.
        required: true
        style: simple
        explode: false
        schema:
          type: string
      - name: assistant_id
        in: query
        description: Assistant ID values that need to be considered for filtering
        required: true
        explode: true
        schema:
          type: string
          default: available
      - name: environment_id
        in: query
        description: Environment ID values that need to be considered for filtering
        required: true
        explode: true
        schema:
          type: string
          default: available
      responses:
        "200":
          description: Successful request.
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ListSkillsResponse'
        "400":
          description: Invalid request.
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'
        "500":
          description: Internal error.
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'
  /providers/{provider_id}/conversational_skills/{conversational_skill_id}/orchestrate:
    post:
      tags:
      - Conversational Skills MVP
      summary: Orchestrate a conversation
      description: "Sends user input along with conversation state (including slots and other context data) stored by watsonx Assistant, and the current turn output, to the conversational skill, to let it run its business logic and tell watsonx Assistant what to do next."
      operationId: orchestrate
      parameters:
      - name: provider_id 
        in: path
        description: Unique identifier of the provider that possesses the conversational skill. It represents the instance that is linked with the WxA instance.
        required: true
        style: simple
        explode: false
        schema:
          type: string
      - name: conversational_skill_id
        in: path
        description: Unique identifier of the conversational skill. It represents business logic to orchestrate a specific conversation.
        required: true
        style: simple
        explode: false
        schema:
          type: string
      requestBody:
        description: "The message to be sent. This includes the user's input, along with optional content such as intents and entities."
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/OrchestrationRequest'
      responses:
        "200":
          description: Successful request.
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/OrchestrationResponse'
        "400":
          description: Invalid request.
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'
        "500":
          description: Internal error.
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'
components:
  schemas:
    ConversationalSkill:
      type: object
      properties:
        id:
          type: string
          description: The unique identifier of a conversational skill
        name:
          type: string
          description: The name of a conversational skill
        description:
          type: string
          description: The description of a conversational skill
        created:
          type: string
          description: The created timestamp of a conversational skill
        modified:
          type: string
          description: The created timestamp of a conversational skill
        metadata:
          type: object
          description: Additional metadata of a conversational skill
      description: Information about a conversational skill
    ListSkillsResponse: 
      type: object
      properties: 
        conversational_skills: 
          type: array
          description: |-
            An array of conversational_skill objects. Each item in the array should be a unique conversational skill.
          items:
            $ref: '#/components/schemas/ConversationalSkill'
    OrchestrationRequest:
      type: object
      properties:
        input:
          $ref: '#/components/schemas/MessageInput'
        context:
          $ref: '#/components/schemas/MessageContext'
        output:
          $ref: '#/components/schemas/MessageOutput'
      description: An orchestration request.
    MessageInput:
      type: object
      properties:
        message_type:
          type: string
          description: |-
            The type of the message:
            - `text`: The user input is processed normally by the assistant.
            - `search`: Only search results are returned. (Any dialog or action skill is bypassed.)
            **Note:** A `search` message results in an error if no search skill is configured for the assistant.
          default: text
          enum:
          - text
          - search
        text:
          maxLength: 2048
          minLength: 1
          type: string
          description: "The text of the user input. This string cannot contain carriage return, newline, or tab characters."
        attachments:
          maxItems: 5
          type: array
          description: "An array of multimedia attachments to be sent with the message. Attachments are not processed by the assistant itself, but can be sent to external services by webhooks. \n\n **Note:** Attachments are not supported on IBM Cloud Pak for Data."
          items:
            $ref: '#/components/schemas/MessageInputAttachment'
      description: An input object that includes the input text.
    MessageInputAttachment:
      required:
      - url
      properties:
        url:
          type: string
          description: The URL of the media file.
        media_type:
          type: string
          description: The media content type (such as a MIME type) of the attachment.
      description: A reference to a media file to be sent as an attachment with the message.
    MessageContext:
      type: object
      properties:
        global:
          $ref: '#/components/schemas/MessageContextGlobal'
        skills:
          $ref: '#/components/schemas/MessageContextSkills'
        integrations:
          type: object
          description: "An object containing context data that is specific to particular integrations. For more information, see the [documentation](https://cloud.ibm.com/docs/assistant?topic=assistant-dialog-integrations)."
    MessageContextGlobal:
      allOf:
      - $ref: '#/components/schemas/BaseMessageContextGlobal'
      - properties:
          session_id:
            type: string
            description: The session ID.
            readOnly: true
    BaseMessageContextGlobal:
      type: object
      properties:
        system:
          $ref: '#/components/schemas/MessageContextGlobalSystem'
      description: Session context data that is shared by all skills used by the assistant.
    MessageContextGlobalSystem:
      type: object
      properties:
        timezone:
          type: string
          description: The user time zone. The assistant uses the time zone to correctly resolve relative time references.
        user_id:
          maxLength: 256
          minLength: 1
          type: string
          description: |-
            A string value that identifies the user who is interacting with the assistant. The client must provide a unique identifier for each individual end user who accesses the application. For user-based plans, this user ID is used to identify unique users for billing purposes. This string cannot contain carriage return, newline, or tab characters. If no value is specified in the input, **user_id** is automatically set to the value of **context.global.session_id**.
            **Note:** This property is the same as the **user_id** property at the root of the message body. If **user_id** is specified in both locations in a message request, the value specified at the root is used.
        turn_count:
          type: integer
          description: "A counter that is automatically incremented with each turn of the conversation. A value of 1 indicates that this is the the first turn of a new conversation, which can affect the behavior of some skills (for example, triggering the start node of a dialog)."
        locale:
          type: string
          description: |-
            The language code for localization in the user input. The specified locale overrides the default for the assistant, and is used for interpreting entity values in user input such as date values. For example, `04/03/2018` might be interpreted either as April 3 or March 4, depending on the locale.
             This property is included only if the new system entities are enabled for the skill.
          enum:
          - en-us
          - en-ca
          - en-gb
          - ar-ar
          - cs-cz
          - de-de
          - es-es
          - fr-fr
          - it-it
          - ja-jp
          - ko-kr
          - nl-nl
          - pt-br
          - zh-cn
          - zh-tw
        reference_time:
          type: string
          description: |-
            The base time for interpreting any relative time mentions in the user input. The specified time overrides the current server time, and is used to calculate times mentioned in relative terms such as `now` or `tomorrow`. This can be useful for simulating past or future times for testing purposes, or when analyzing documents such as news articles.
            This value must be a UTC time value formatted according to ISO 8601 (for example, `2021-06-26T12:00:00Z` for noon UTC on 26 June 2021).
            This property is included only if the new system entities are enabled for the skill.
        session_start_time:
          type: string
          description: |-
            The time at which the session started. With the stateful `message` method, the start time is always present, and is set by the service based on the time the session was created. With the stateless `message` method, the start time is set by the service in the response to the first message, and should be returned as part of the context with each subsequent message in the session.
            This value is a UTC time value formatted according to ISO 8601 (for example, `2021-06-26T12:00:00Z` for noon UTC on 26 June 2021).
        state:
          type: string
          description: "An encoded string that represents the configuration state of the assistant at the beginning of the conversation. If you are using the stateless `message` method, save this value and then send it in the context of the subsequent message request to avoid disruptions if there are configuration changes during the conversation (such as a change to a skill the assistant uses)."
        skip_user_input:
          type: boolean
          description: For internal use only.
      description: Built-in system properties that apply to all skills used by the assistant.
    MessageContextSkills:
      type: object
      properties:
        main skill:
          $ref: '#/components/schemas/MessageContextDialogSkill'
        actions skill:
          $ref: '#/components/schemas/MessageContextActionSkill'
        conversational skills:
          $ref: '#/components/schemas/MessageContextConversationalSkills'
        active_skill:
          type: string
          description: "When a conversation is underway, and it hasn't yet finished, the Assistant uses this attribute to track which skill should start processing the next user message in the conversation. At appropriate milestones in a conversation, e.g., initial routing, digression, return from digression, the Assistant updates this attribute to indicate which skill should process the next user message."
          nullable: true
          enum:
          - main skill
          - actions skill
          - conversational skills
      description: Context data specific to particular skills used by the assistant.
    MessageContextDialogSkill:
      allOf:
      - description: Context variables that are used by the dialog skill.
      - $ref: '#/components/schemas/BaseMessageContextSkill'
    BaseMessageContextSkill:
      type: object
      properties:
        user_defined:
          type: object
          additionalProperties:
            description: A user-defined context variable.
          description: An object containing any arbitrary variables that can be read and written by a particular skill.
        system:
          $ref: '#/components/schemas/MessageContextSkillSystem'
    MessageContextSkillSystem:
      type: object
      properties:
        state:
          type: string
          description: "An encoded string that represents the current conversation state. By saving this value and then sending it in the context of a subsequent message request, you can return to an earlier point in the conversation. If you are using stateful sessions, you can also use a stored state value to restore a paused conversation whose session is expired."
      additionalProperties:
        description: For internal use only.
      description: System context data used by the skill.
    MessageContextActionSkill:
      allOf:
      - $ref: '#/components/schemas/BaseMessageContextSkill'
      - properties:
          action_variables:
            type: object
            additionalProperties:
              description: An action variable.
            description: "An object containing action variables. Action variables can be accessed only by steps in the same action, and do not persist after the action ends."
          skill_variables:
            type: object
            additionalProperties:
              description: A skill variable.
            description: "An object containing skill variables. (In the watsonx Assistant user interface, skill variables are called _session variables_.) Skill variables can be accessed by any action and persist for the duration of the session."
        description: "Context variables that are used by the action skill. Private variables are persisted, but not shown."
    MessageContextConversationalSkills:
      allOf:
      - $ref: '#/components/schemas/BaseMessageContextSkill'
      - type: object
        properties:
          session_variables:
            type: object
            additionalProperties:
              description: A session variable
            description: An object containing variables that the conversational skills want to share with other skills in the assistant beyond their execution. Session variables can be accessed by any conversational skill / action and persist for the duration of the session.
        additionalProperties:
          $ref: '#/components/schemas/MessageContextConversationalSkill'
    MessageContextConversationalSkill:
      properties:
        slots:
          type: object
          additionalProperties:
            $ref: '#/components/schemas/Slot'
          description: "An object containing the slots, which, the conversational skill intends to gather or repair based on user input."
        local_variables:
          type: object
          additionalProperties:
            description: An conversation skill variable.
          description: "An object containing local variables. Local variables can be accessed by the conversational skill, and do not persist after the conversational skill ends."
      description: "Slots and variables that are used by the conversational skill for its processing. This area is specific to a conversational skill. It is not shared with other skills, and is not persisted beyond the completion of the conversational skill."
    MessageOutput:
      properties:
        generic:
          type: array
          description: Output intended for any channel. It is the responsibility of the client application to implement the supported response types.
          items:
            $ref: '#/components/schemas/RuntimeResponseGeneric'
      description: Assistant output to be rendered or processed by the client.
    RuntimeResponseGeneric:
      discriminator:
        propertyName: response_type
        mapping:
          audio: '#/components/schemas/RuntimeResponseTypeAudio'
          channel_transfer: '#/components/schemas/RuntimeResponseTypeChannelTransfer'
          connect_to_agent: '#/components/schemas/RuntimeResponseTypeConnectToAgent'
          date: '#/components/schemas/RuntimeResponseTypeDate'
          iframe: '#/components/schemas/RuntimeResponseTypeIframe'
          image: '#/components/schemas/RuntimeResponseTypeImage'
          option: '#/components/schemas/RuntimeResponseTypeOption'
          suggestion: '#/components/schemas/RuntimeResponseTypeSuggestion'
          pause: '#/components/schemas/RuntimeResponseTypePause'
          search: '#/components/schemas/RuntimeResponseTypeSearch'
          text: '#/components/schemas/RuntimeResponseTypeText'
          user_defined: '#/components/schemas/RuntimeResponseTypeUserDefined'
          video: '#/components/schemas/RuntimeResponseTypeVideo'
      oneOf:
      - $ref: '#/components/schemas/RuntimeResponseTypeText'
      - $ref: '#/components/schemas/RuntimeResponseTypePause'
      - $ref: '#/components/schemas/RuntimeResponseTypeImage'
      - $ref: '#/components/schemas/RuntimeResponseTypeOption'
      - $ref: '#/components/schemas/RuntimeResponseTypeConnectToAgent'
      - $ref: '#/components/schemas/RuntimeResponseTypeSuggestion'
      - $ref: '#/components/schemas/RuntimeResponseTypeChannelTransfer'
      - $ref: '#/components/schemas/RuntimeResponseTypeSearch'
      - $ref: '#/components/schemas/RuntimeResponseTypeUserDefined'
      - $ref: '#/components/schemas/RuntimeResponseTypeVideo'
      - $ref: '#/components/schemas/RuntimeResponseTypeAudio'
      - $ref: '#/components/schemas/RuntimeResponseTypeIframe'
      - $ref: '#/components/schemas/RuntimeResponseTypeDate'
    RuntimeResponseTypeAudio:
      required:
      - response_type
      - source
      properties:
        response_type:
          type: string
          description: The type of response returned by the dialog node. The specified response type must be supported by the client application or channel.
        source:
          type: string
          description: The `https:` URL of the audio clip.
        title:
          type: string
          description: The title or introductory text to show before the response.
        description:
          type: string
          description: The description to show with the the response.
        channels:
          type: array
          description: "An array of objects specifying channels for which the response is intended. If **channels** is present, the response is intended for a built-in integration and should not be handled by an API client."
          items:
            $ref: '#/components/schemas/ResponseGenericChannel'
        channel_options:
          type: object
          description: For internal use only.
        alt_text:
          maxLength: 100
          minLength: 1
          type: string
          description: Descriptive text that can be used for screen readers or other situations where the audio player cannot be seen.
    RuntimeResponseTypeChannelTransfer:
      required:
      - message_to_user
      - response_type
      - transfer_info
      properties:
        response_type:
          type: string
          description: "The type of response returned by the dialog node. The specified response type must be supported by the client application or channel. \n\n **Note:** The `channel_transfer` response type is not supported on IBM Cloud Pak for Data."
        message_to_user:
          type: string
          description: The message to display to the user when initiating a channel transfer.
        transfer_info:
          $ref: '#/components/schemas/ChannelTransferInfo'
        channels:
          type: array
          description: "An array of objects specifying channels for which the response is intended. If **channels** is present, the response is intended for a built-in integration and should not be handled by an API client."
          items:
            $ref: '#/components/schemas/ResponseGenericChannel'
    RuntimeResponseTypeConnectToAgent:
      required:
      - response_type
      properties:
        response_type:
          type: string
          description: The type of response returned by the dialog node. The specified response type must be supported by the client application or channel.
        message_to_human_agent:
          type: string
          description: A message to be sent to the human agent who will be taking over the conversation.
        agent_available:
          allOf:
          - $ref: '#/components/schemas/AgentAvailabilityMessage'
          - description: An optional message to be displayed to the user to indicate that the conversation will be transferred to the next available agent.
        agent_unavailable:
          allOf:
          - $ref: '#/components/schemas/AgentAvailabilityMessage'
          - description: An optional message to be displayed to the user to indicate that no online agent is available to take over the conversation.
        transfer_info:
          $ref: '#/components/schemas/DialogNodeOutputConnectToAgentTransferInfo'
        topic:
          type: string
          description: "A label identifying the topic of the conversation, derived from the **title** property of the relevant node or the **topic** property of the dialog node response."
        channels:
          type: array
          description: "An array of objects specifying channels for which the response is intended. If **channels** is present, the response is intended for a built-in integration and should not be handled by an API client."
          items:
            $ref: '#/components/schemas/ResponseGenericChannel'
    RuntimeResponseTypeDate:
      required:
      - response_type
      properties:
        response_type:
          type: string
          description: The type of response returned by the dialog node. The specified response type must be supported by the client application or channel.
    RuntimeResponseTypeIframe:
      required:
      - response_type
      - source
      properties:
        response_type:
          type: string
          description: The type of response returned by the dialog node. The specified response type must be supported by the client application or channel.
        source:
          type: string
          description: The `https:` URL of the embeddable content.
        title:
          type: string
          description: The title or introductory text to show before the response.
        description:
          type: string
          description: The description to show with the the response.
        image_url:
          type: string
          description: The URL of an image that shows a preview of the embedded content.
        channels:
          type: array
          description: "An array of objects specifying channels for which the response is intended. If **channels** is present, the response is intended for a built-in integration and should not be handled by an API client."
          items:
            $ref: '#/components/schemas/ResponseGenericChannel'
    RuntimeResponseTypeImage:
      required:
      - response_type
      - source
      properties:
        response_type:
          type: string
          description: The type of response returned by the dialog node. The specified response type must be supported by the client application or channel.
        source:
          type: string
          description: The `https:` URL of the image.
        title:
          type: string
          description: The title to show before the response.
        description:
          type: string
          description: The description to show with the the response.
        channels:
          type: array
          description: "An array of objects specifying channels for which the response is intended. If **channels** is present, the response is intended for a built-in integration and should not be handled by an API client."
          items:
            $ref: '#/components/schemas/ResponseGenericChannel'
        alt_text:
          maxLength: 100
          minLength: 1
          type: string
          description: Descriptive text that can be used for screen readers or other situations where the image cannot be seen.
    RuntimeResponseTypeOption:
      required:
      - options
      - response_type
      - title
      properties:
        response_type:
          type: string
          description: The type of response returned by the dialog node. The specified response type must be supported by the client application or channel.
        title:
          type: string
          description: The title or introductory text to show before the response.
        description:
          type: string
          description: The description to show with the the response.
        preference:
          type: string
          description: The preferred type of control to display.
          enum:
          - dropdown
          - button
        options:
          type: array
          description: An array of objects describing the options from which the user can choose.
          items:
            $ref: '#/components/schemas/DialogNodeOutputOptionsElement'
        channels:
          type: array
          description: "An array of objects specifying channels for which the response is intended. If **channels** is present, the response is intended for a built-in integration and should not be handled by an API client."
          items:
            $ref: '#/components/schemas/ResponseGenericChannel'
    RuntimeResponseTypePause:
      required:
      - response_type
      - time
      properties:
        response_type:
          type: string
          description: The type of response returned by the dialog node. The specified response type must be supported by the client application or channel.
        time:
          type: integer
          description: "How long to pause, in milliseconds."
        typing:
          type: boolean
          description: Whether to send a "user is typing" event during the pause.
        channels:
          type: array
          description: "An array of objects specifying channels for which the response is intended. If **channels** is present, the response is intended for a built-in integration and should not be handled by an API client."
          items:
            $ref: '#/components/schemas/ResponseGenericChannel'
    RuntimeResponseTypeSearch:
      required:
      - additional_results
      - header
      - primary_results
      - response_type
      properties:
        response_type:
          type: string
          description: The type of response returned by the dialog node. The specified response type must be supported by the client application or channel.
        header:
          type: string
          description: The title or introductory text to show before the response. This text is defined in the search skill configuration.
        primary_results:
          type: array
          description: An array of objects that contains the search results to be displayed in the initial response to the user.
          items:
            $ref: '#/components/schemas/SearchResult'
        additional_results:
          type: array
          description: An array of objects that contains additional search results that can be displayed to the user upon request.
          items:
            $ref: '#/components/schemas/SearchResult'
        channels:
          type: array
          description: "An array of objects specifying channels for which the response is intended. If **channels** is present, the response is intended for a built-in integration and should not be handled by an API client."
          items:
            $ref: '#/components/schemas/ResponseGenericChannel'
    RuntimeResponseTypeSuggestion:
      required:
      - response_type
      - suggestions
      - title
      properties:
        response_type:
          type: string
          description: The type of response returned by the dialog node. The specified response type must be supported by the client application or channel.
        title:
          type: string
          description: The title or introductory text to show before the response.
        suggestions:
          type: array
          description: An array of objects describing the possible matching dialog nodes from which the user can choose.
          items:
            $ref: '#/components/schemas/DialogSuggestion'
        channels:
          type: array
          description: "An array of objects specifying channels for which the response is intended. If **channels** is present, the response is intended for a built-in integration and should not be handled by an API client."
          items:
            $ref: '#/components/schemas/ResponseGenericChannel'
    RuntimeResponseTypeText:
      required:
      - response_type
      - text
      properties:
        response_type:
          type: string
          description: The type of response returned by the dialog node. The specified response type must be supported by the client application or channel.
        text:
          type: string
          description: The text of the response.
        channels:
          type: array
          description: "An array of objects specifying channels for which the response is intended. If **channels** is present, the response is intended for a built-in integration and should not be handled by an API client."
          items:
            $ref: '#/components/schemas/ResponseGenericChannel'
    RuntimeResponseTypeUserDefined:
      required:
      - response_type
      - user_defined
      properties:
        response_type:
          type: string
          description: The type of response returned by the dialog node. The specified response type must be supported by the client application or channel.
        user_defined:
          type: object
          additionalProperties:
            description: Any property used by the user-defined response type.
          description: An object containing any properties for the user-defined response type.
        channels:
          type: array
          description: "An array of objects specifying channels for which the response is intended. If **channels** is present, the response is intended for a built-in integration and should not be handled by an API client."
          items:
            $ref: '#/components/schemas/ResponseGenericChannel'
    RuntimeResponseTypeVideo:
      required:
      - response_type
      - source
      properties:
        response_type:
          type: string
          description: The type of response returned by the dialog node. The specified response type must be supported by the client application or channel.
        source:
          type: string
          description: The `https:` URL of the video.
        title:
          type: string
          description: The title or introductory text to show before the response.
        description:
          type: string
          description: The description to show with the the response.
        channels:
          type: array
          description: "An array of objects specifying channels for which the response is intended. If **channels** is present, the response is intended for a built-in integration and should not be handled by an API client."
          items:
            $ref: '#/components/schemas/ResponseGenericChannel'
        channel_options:
          type: object
          description: For internal use only.
        alt_text:
          maxLength: 100
          minLength: 1
          type: string
          description: Descriptive text that can be used for screen readers or other situations where the video cannot be seen.
    ResponseGenericChannel:
      properties:
        channel:
          type: string
          description: A channel for which the response is intended.
    DialogNodeOutputOptionsElement:
      required:
      - label
      - value
      properties:
        label:
          type: string
          description: The user-facing label for the option.
        value:
          $ref: '#/components/schemas/DialogNodeOutputOptionsElementValue'
    DialogNodeOutputOptionsElementValue:
      properties:
        input:
          $ref: '#/components/schemas/MessageInput'
      description: An object defining the message input to be sent to the assistant if the user selects the corresponding option.
    AgentAvailabilityMessage:
      type: object
      properties:
        message:
          maxLength: 512
          minLength: 1
          type: string
          description: The text of the message.
    DialogNodeOutputConnectToAgentTransferInfo:
      type: object
      properties:
        target:
          type: object
          additionalProperties:
            type: object
            additionalProperties:
              description: Any property intended for the target service desk system.
            description: An object containing routing or other contextual information specific to a target service desk system.
      description: Routing or other contextual information to be used by target service desk systems.
    DialogSuggestion:
      required:
      - label
      - value
      properties:
        label:
          type: string
          description: "The user-facing label for the suggestion. This label is taken from the **title** or **user_label** property of the corresponding dialog node, depending on the disambiguation options."
        value:
          $ref: '#/components/schemas/DialogSuggestionValue'
        output:
          type: object
          additionalProperties: true
          description: The dialog output that will be returned from the watsonx Assistant service if the user selects the corresponding option.
    DialogSuggestionValue:
      properties:
        input:
          $ref: '#/components/schemas/MessageInput'
      description: "An object defining the message input to be sent to the assistant if the user selects the corresponding disambiguation option. \n\n **Note:** This entire message input object must be included in the request body of the next message sent to the assistant. Do not modify or remove any of the included properties."
    ChannelTransferInfo:
      required:
      - target
      type: object
      properties:
        target:
          $ref: '#/components/schemas/ChannelTransferTarget'
      description: Information used by an integration to transfer the conversation to a different channel.
    ChannelTransferTarget:
      type: object
      properties:
        chat:
          $ref: '#/components/schemas/ChannelTransferTargetChat'
      description: "An object specifying target channels available for the transfer. Each property of this object represents an available transfer target. Currently, the only supported property is **chat**, representing the web chat integration."
    ChannelTransferTargetChat:
      type: object
      properties:
        url:
          type: string
          description: The URL of the target web chat.
      description: Information for transferring to the web chat integration.
    SearchResult:
      required:
      - id
      - result_metadata
      properties:
        id:
          type: string
          description: |-
            The unique identifier of the document in the Discovery service collection.
            This property is included in responses from search skills, which are available only to Plus or Enterprise plan users.
        result_metadata:
          $ref: '#/components/schemas/SearchResultMetadata'
        body:
          type: string
          description: "A description of the search result. This is taken from an abstract, summary, or highlight field in the Discovery service response, as specified in the search skill configuration."
        title:
          type: string
          description: "The title of the search result. This is taken from a title or name field in the Discovery service response, as specified in the search skill configuration."
        url:
          type: string
          description: The URL of the original data object in its native data source.
        highlight:
          $ref: '#/components/schemas/SearchResultHighlight'
        answers:
          maxItems: 1
          minItems: 0
          type: array
          description: "An array specifying segments of text within the result that were identified as direct answers to the search query. Currently, only the single answer with the highest confidence (if any) is returned.\n\n**Notes:** \n - Answer finding is available only if the search skill is connected to a Discovery v2 service instance. \n - Answer finding is not supported on IBM Cloud Pak for Data."
          items:
            $ref: '#/components/schemas/SearchResultAnswer'
    SearchResultAnswer:
      required:
      - confidence
      - text
      properties:
        text:
          type: string
          description: The text of the answer.
        confidence:
          maximum: 1
          minimum: 0
          type: number
          description: "The confidence score for the answer, as returned by the Discovery service."
          format: double
      description: An object specifing a segment of text that was identified as a direct answer to the search query.
    SearchResultHighlight:
      type: object
      properties:
        body:
          type: array
          description: "An array of strings containing segments taken from body text in the search results, with query-matching substrings highlighted."
          items:
            type: string
        title:
          type: array
          description: "An array of strings containing segments taken from title text in the search results, with query-matching substrings highlighted."
          items:
            type: string
        url:
          type: array
          description: "An array of strings containing segments taken from URLs in the search results, with query-matching substrings highlighted."
          items:
            type: string
      additionalProperties:
        type: array
        description: "An array of strings containing segments taken from a field in the search results that is not mapped to the `body`, `title`, or `url` property, with query-matching substrings highlighted. The property name is the name of the field in the Discovery collection."
        items:
          type: string
      description: An object containing segments of text from search results with query-matching text highlighted using HTML `<em>` tags.
    SearchResultMetadata:
      properties:
        confidence:
          type: number
          description: "The confidence score for the given result, as returned by the Discovery service."
          format: double
        score:
          type: number
          description: "An unbounded measure of the relevance of a particular result, dependent on the query and matching document. A higher score indicates a greater match to the query parameters."
          format: double
      description: An object containing search result metadata from the Discovery service.
    OrchestrationResponse:
      required:
      - context
      - output
      - resolver
      properties:
        context:
          allOf:
          - $ref: '#/components/schemas/MessageContext'
          - description: "Only the `skills['conversational skills']` part of the context is writeable by conversational skills. The skill should updates its state comprising of `slots`, `local_variables` and `session_variables`."
        output:
          $ref: '#/components/schemas/MessageOutput'
        resolver:
          type: string
          description: A resolver is a way for the conversational skill to indicate how it wants the Assistant to proceed.
          enum:
          - continue
          - user-interaction
          - end-conversation
          - fallback
          - validation_error
      description: Response expected from Conversational skill.
    ErrorResponse:
      required:
      - code
      - error
      properties:
        error:
          type: string
          description: General description of an error.
        errors:
          type: array
          description: Collection of specific constraint violations associated with the error.
          items:
            $ref: '#/components/schemas/ErrorDetail'
        code:
          type: integer
          description: HTTP status code for the error response.
    ErrorDetail:
      required:
      - message
      properties:
        message:
          type: string
          description: Description of a specific constraint violation.
        path:
          type: string
          description: The location of the constraint violation.
    Slot:
      required:
      - description
      - type
      type: object
      properties:
        description:
          type: string
          description: Natural language description resembling how the assistant prompts for the slot.
        type:
          type: string
          description: "Type of slot, e.g., free-text, sys-number, sys-date, sys-time, entity."
          enum:
          - free-text
          - sys-number
          - sys-date
          - sys-time
          - entity
        schema:
          $ref: '#/components/schemas/EntitySchema'
        value:
          type: string
          description: "Optional, normalized value for the slot, if already filled."
        literal:
          type: string
          description: "Optional, literal value entered by the user, if slot is already filled and different from normalized value."
    EntitySchema:
      required:
      - entity
      type: object
      properties:
        entity:
          type: string
          description: watsonx Assistant's entity schema name.
        values:
          type: array
          items:
            $ref: '#/components/schemas/EntityValue'
      description: "Schema definition for the slot, required if type is entity."
    EntityValue:
      required:
      - value
      type: object
      properties:
        value:
          type: string
        synonyms:
          type: array
          items:
            type: string
  parameters:
    ConversationalSkillPathParam:
      name: conversational_skill_id
      in: path
      description: Unique identifier of the conversational skill. It represents business logic to orchestrate a specific conversation.
      required: true
      style: simple
      explode: false
      schema:
        type: string
```
