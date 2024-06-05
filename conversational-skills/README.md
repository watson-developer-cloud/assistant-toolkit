# Conversational Skills: Registering a Pro-Code Conversational Skill Provider

The following document explains how a watsonx Assistant client should **invoke** Assistant's APIs in order to enable them to use the pro-code conversational skill feature. 

## Table of Contents

- [Overview of Conversational Skills](#Overview) 
- [Register a Conversational Skill Provider](#Register-a-Conversational-Skill-Provider) | `POST /v2/providers`
- [Retrieve Conversational Skill Provider(s)](#Retrieve-Conversational-Skill-Providers) | `GET /v2/providers`
- [Update a Conversational Skill Provider](#Update-a-Conversational-Skill-Provider) | `POST /v2/providers/{{provider_id}}`
- [Create a Skill-Backed Action](#Create-a-Skill-Backed-Action) | `POST /v2/assistants/{{assistant_id}}/skills/{{action_skill_id}}`

### Overview

Skill-backed actions, or _conversational skill actions_, enable builders of watsonx Assistant to begin tasks given their users' conversation input. Each task and its specification is fully contained in a conversational skill. Builders may choose to create skills through watsonx Orchestrate's [skill builder](https://www.ibm.com/products/watsonx-orchestrate/skills-studio), or choose to manage more complex workflows offline by independently creating / maintaining their own skills. 

Pro-code creators of conversational skills must do the following: 
(1) Register a conversational skill provider
(2) Implement the [client-side endpoints](procode-endpoints.md) to the written specification on their personal host
(3) Create a skill-backed action either through the API or through the watsonx Assistant UI*. 


*To create a skill backed action from the UI, the client must implement the "list conversational skills" endpoint.

### Register a Conversational Skill Provider 

The conversational skill provider must be registered on a watsonx Assistant instance before using Pro-Code Conversational Skills. The provider defines the protocol for how requests from Assistant to a client's externally hosted conversational skills should be constructed. 

Please reference [this document](procode-endpoints.md) to find documentation for client-side endpoints. Please note it is the client's responsibility to ensure that these APIs are implemented to spec for proper functionality. 

As of this revision, we **do not** support deletion of the conversational_skill provider once it has been registered onto a watsonx Assistant instance.

**Example cURL**

```json
curl -x POST '{{API}}/v2/providers?version={{version}}' \
--header 'Content-Type: application/json' \
--header 'Authorization: {{AUTHORIZATION}}' \
--data '{
    "provider_id": "myProCodeProvider-noSpaces_no_special_chars",
    "specification": {
        "servers": [
            {
                "url": "https://myProCodeProvider.com"
            }
        ],
        "components": {
            "securitySchemes": {
                "authentication_method": "basic",
                "basic": {
                    "username": {
                        "type": "value",
                        "value": "myBasicUserName"
                    }
                }
            }
        }
    },
    "private": {
        "authentication": {
            "basic": {
                "password": {
                    "type": "value",
                    "value": "myBasicPassword"
                }
            }
        }
    }
}'
```

**Example Request Body**
```json
{
    /* A user-provided ID that should not have spaces, tabs, or special characters. */ 
    "provider_id": "myProCodeProvider-noSpaces_no_special_chars",
    "specification": {
        "servers": [
            {
                /* URL where the client-implemented endpoints are hosted. */ 
                "url": "https://myProCodeProvider.com"
            }
        ],
        "components": {
            "securitySchemes": {
                /* Required enum that defines the type of authentication needed. */
                "authentication_method": "basic",
                /* Non-sensitive information pertaining to a specific authentication method */
                "basic": {
                    "username": {
                        "type": "value",
                        "value": "myBasicUserName"
                    }
                }
            }
        }
    },
    /* Sensitive, private data for authentication. */
    "private": {
        "authentication": {
            "basic": {
                "password": {
                    "type": "value",
                    "value": "myBasicPassword"
                }
            }
        }
    }
}
```

**Example Response**

```json
{
  "provider_id": "myProCodeProvider-noSpaces_no_special_chars",
  "specification": {
    "servers": [
      {
        "url": "https://myProCodeProvider.com"
      }
    ],
    "components": {
      "securitySchemes": {
        "basic": {
          "username": {
            "type": "value",
            "value": "myBasicUsername"
          }
        },
        "authentication_method": "basic"
      }
    }
  }
}
```

- Currently, we support the registration of only **one** conversational skill provider. 
- Information within the `private` object will not be returned by the API on response.
- `authentication_method`: An required enum in the request body denoting the authentication method for the conversational skill provider.
  - Accepted values include: 'basic', 'bearer', 'api_key', 'oauth2', or 'none'
- `private.authentication` should be an empty object when `authentication_method` is `none`.
- The `provider_id` is **immutable** after creation.



### Retrieve Conversational Skill Providers

Returns a list of conversational skill providers.

**Example cURL**
```json
curl -x GET '{{API}}/v2/providers?version={{version}}' \
--header 'Content-Type: application/json' \
--header 'Authorization: {{AUTHORIZATION}}'
```

**Example Request Body**
```json
N/A
```

**Example Response**
```json
{
    "conversational_skill_providers": [
        {
            "created": "2024-05-30T00:52:08.368Z",
            "updated": "2024-05-30T00:52:08.368Z",
            "provider_id": "myProCodeProvider-noSpaces_no_special_chars",
            "specification": {
              "servers": [
                {
                  "url": "https://myProCodeProvider.com"
                }
              ],
              "components": {
                "securitySchemes": {
                  "basic": {
                    "username": {
                      "type": "value",
                      "value": "myBasicUsername"
                    }
                  },
                  "authentication_method": "basic"
                }
              }
            }
        }
    ],
    "pagination": {
        "refresh_url": "/v2/providers?version=2021-11-27&include_count=true&include_audit=true&verbose=true",
        "total": 1,
        "matched": 1
    }
}
```
- Standard pagination, as following other Assistant APIs, is supported.
- Audit metadata such as the `created`, `updated` timestamps can be available on the response with `?include_audit=true`.
- Information within the `private` object will not be returned by the API on response.


### Update a Conversational Skill Provider 

The conversational skill provider may be updated to use a different host name, or different authentication type. The `provider_id`, as it is set by the user OR returned in the LIST /providers request, is required as a path parameter.

**Example cURL**

```json
curl -x POST '{{API}}/v2/providers/{{provider_id}}?version={{version}}' \
--header 'Content-Type: application/json' \
--header 'Authorization: {{AUTHORIZATION}}' \
--data '{
    "provider_id": "myProCodeProvider-noSpaces_no_special_chars",
    "specification": {
        "servers": [
            {
                "url": "https://myProCodeProvider.com"
            }
        ],
        "components": {
            "securitySchemes": {
                "authentication_method": "api_key",
            }
        }
    },
    "private": {
        "authentication": {
            "api_key": {
                "My-API-Key": {
                    "type": "value",
                    "in": "header",
                    "value": "myAPIKeyAuth"
                }
            }
        }
    }
}'
```

**Example Request Body**
```json
{
    /* A user-provided ID that should not have spaces, tabs, or special characters. */ 
    "provider_id": "myProCodeProvider-noSpaces_no_special_chars",
    "specification": {
        "servers": [
            {
                /* URL where the client-implemented endpoints are hosted. */ 
                "url": "https://myProCodeProvider.com"
            }
        ],
        "components": {
            "securitySchemes": {
                /* Required enum that defines the type of authentication needed. */
                "authentication_method": "api_key",
            }
        }
    },
    /* Sensitive, private data for authentication. */
    "private": {
        "authentication": {
            "api_key": {
                "My-API-Key": {
                    "type": "value",
                    "in": "header",
                    "value": "myAPIKeyAuth"
                }
            }
        }
    }
}
```

**Example Response**

```json
{
  "provider_id": "myProCodeProvider-noSpaces_no_special_chars",
  "specification": {
    "servers": [
      {
        "url": "https://myProCodeProvider.com"
      }
    ],
    "components": {
      "securitySchemes": {
        "authentication_method": "api_key"
      }
    }
  }
}
```

- `provider_id` is immutable. A new value passed in through the request body will not be accepted by the API.
- Information within the `private` object will not be returned by the API on response.
- `authentication_method`: An required enum in the request body denoting the authentication method for the conversational skill provider.
  - Accepted values include: 'basic', 'bearer', 'api_key', 'oauth2', or 'none'
- `private.authentication` should be an empty object when `authentication_method` is `none`.


### Create a Skill-Backed Action

Once a provider has been registered on a watsonx Assistant instance, skill-backed actions may be created from the Assistant UI. Builders may tune the logic, routing, conditions, and conversational inputs from the user for invoking the skill. 

To create a skill-backed action from the API, please reference the cloud API Docs.
