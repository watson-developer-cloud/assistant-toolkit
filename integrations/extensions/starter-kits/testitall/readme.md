# Test it ALL

## Goal

This is an example of all the features supported by Watson Assistant when using a Custom Extension.
The OpenApi specification is vast, and hence not all of its features are supported Watson Assistant.
This example describes all the features supported and provides means to test them out.
It is expected that this example will grow over time as more features are supported.

## Features

The provided OpenAPI specification and WA actions currently covered the following features supported by Watson
Assistant:

- Dynamic Base Path in url
- Security: Basic, Bearer, API key and OAuth2
- Parameters: Within query string, header or URL path
- Error handling - when an extension error occurred (API server error, response size limit)
- Timeout handling - when an extension takes more than 30 seconds to execute
- Arrays - both in request and response
- Pre- and Post-message webhooks

## Mock API Server

The custom extension requires a running API server to test the OpenAPI specification. To use / deploy the server, we
provide two options:

### Use IBM Hosted Mock API Server

We provide a hosted mock API server for you to test the OpenAPI specification. To use that server, please select Server
URL as
`https://custom-ext-yipeng-ce.vkgqdtxxpfe.us-south.codeengine.appdomain.cloud/ - IBM Hosted Mock API Server`
while adding the extension to your Watson Assistant.

### Deploy the Mock API Server by yourself

1. Deploy the express.js application to your server. You can find the source code in `server` folder.
    - Run `npm install` to install the dependencies.
    - Copy the `.env-sample` file to `.env`, and update the environment variables if needed.
    - Run `npm start` to start the server at port `4000`.
2. Update the server URLs in the OpenAPI specification file `testitall.openapi.json` to point to your server.
    - Assume your API server is running at `https://myserver.com:4000`
        - Please note that if you want to test OAuth2 related feature, then the API server must be running at HTTPS.
    - Update the `servers` section in the OpenAPI specification file to:
      ```json
      "servers": [
        {
          "url": "https://myserver.com:4000",
          "description": "Your API server",
          "variables": {}
        }
      ]
      ```
    - If you would like to test the OAuth2 related feature, update the `components/securitySchemes/OAuth2` section to:
      ```json
          
      "OAuth2": {
        "type": "oauth2",
        "flows": {
          "authorizationCode": {
            "authorizationUrl": "https://myserver.com:4000/oauth2-provider/auth-code/authorize",
            "tokenUrl": "https://myserver.com:4000/oauth2-provider/auth-code/token",
            "refreshUrl": "https://myserver.com:4000/oauth2-provider/auth-code/token",
            "scopes": {
              "read": "Read access to protected resources",
              "custom": "Add your own scope"
            }
          },
          "password": {
            "tokenUrl": "https://myserver.com:4000/oauth2-provider/password/token",
            "refreshUrl": "https://myserver.com:4000/oauth2-provider/password/token",
            "scopes": {
              "read": "Read access to protected resources",
              "custom": "Add your own scope"
            }
          },
          "clientCredentials": {
            "tokenUrl": "https://myserver.com:4000/oauth2-provider/client-cred/token",
            "refreshUrl": "https://myserver.com:4000/oauth2-provider/client-cred/token",
            "scopes": {
              "read": "Read access to protected resources",
              "custom": "Add your own scope"
            }
          },
          "x-apikey": {
            "tokenUrl": "https://myserver.com:4000/oauth2-provider/custom-apikey/token",
            "refreshUrl": "https://myserver.com:4000/oauth2-provider/custom-apikey/token",
            "grantType": "custom_apikey",
            "secretKeys": [
              "apikey_secret"
            ],
            "scopes": {
              "read": "Read access to protected resources",
              "custom": "Add your own scope"
            }
          }
        }
      }
        ```
    - Upload the updated OpenAPI specification file to create a new custom extension, and add it to your Watson
      Assistant.
    - Upload actions to your Watson Assistant.
    - After the actions were uploaded, edit each action to use the extension.

## WA Actions and corresponding OpenAPI Endpoints

The example provides a set of actions to test the specification:

| WA Action Name                                               | WA Trigger Word            | OpenAPI Endpoint                 | Description                                                                                                                                                                                                                                                                                                  |
|--------------------------------------------------------------|----------------------------|----------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Test HTTP GET                                                | "GET"                      | `/test`                          | Test HTTP GET.                                                                                                                                                                                                                                                                                               |
| Test HTTP POST                                               | "POST"                     | `/test`                          | Test HTTP POST.                                                                                                                                                                                                                                                                                              |
| Test HTTP PUT                                                | "PUT"                      | `/test`                          | Test HTTP PUT.                                                                                                                                                                                                                                                                                               |
| Test HTTP DELETE                                             | "DELETE"                   | `/test`                          | Test HTTP DELETE.                                                                                                                                                                                                                                                                                            |
| Test handle error in response                                | "Error"                    | `/test/error`                    | API server will return a 404 error to test error handling in WA.                                                                                                                                                                                                                                             |
| Test parse array inside object                               | "Arrays Object"            | `/test/arrays-object`            | API server will reverse the array inside the object.                                                                                                                                                                                                                                                         |
| Test handle an almost too large response                     | "Context Almost Too Large" | `/test/context-almost-too-large` | API server will return a response that is almost too large (99KB). The extension call itself should success, but then the action will try store that response into a session variable, cause any following conversation to produce a 413 error.                                                              |
| Test handle a too large response                             | "Context Too Large"        | `/test/context-too-large`        | API server will return a response that is too large (500KB). The extension call should fail.                                                                                                                                                                                                                 |
| Test post with parameters                                    | "Params"                   | `/test/params/{path_param}`      | WA will send a POST request with parameters in the query string, header and URL path. The API server will echo the parameters in the response.                                                                                                                                                               |
| Test response with a delay                                   | "Delay"                    | `/delay`                         | API server will delay the response by the provided number of seconds (default: 3).                                                                                                                                                                                                                           |
| Test calling extension with different authentication methods | "Security"                 | `/security`                      | **Please make sure you've selected the corresponding auth method in exntesion configuration before testing it.**<br/>WA will call the API endpoint that requires authentication.<br/>Supported methods: Basic, Bearer, API key and Oauth2 (except the implicit flow).<br/>See below for default credentials. |
| Test pre-message webhook                                     | "Pre-message"              | `/webhook/prewebhook`            | **Please make sure you've enabled the Pre-Message Webhook in environment configuration before testing it.**                                                                                                                                                                                                  |
| Test post-message webhook                                    | "Post-message"             | `/webhook/postwebhook`           | **Please make sure you've enabled the Post-Message Webhook in environment configuration before testing it.**                                                                                                                                                                                                 |

For detailed description of each endpoint, please see the [OpenApi Spec](./testitall.openapi.json) provided.

### Default Authentication Credentials
The following credentials are used by default for the corresponding authentication methods. When hosting your own API server, you can change them by editing the `.env` file.

#### Basic
- Username: `WA_USERNAME`
- Password: `WA_PASSWORD`
#### Bearer
- Token: `WA_TOKEN`
#### API Key
- API key: `WA_APIKEY`
#### OAuth2
- Client ID: `WA_CLIENT_ID`
- Client Secret: `WA_CLIENT_SECRET`
- Username (for password grant type): `WA_USERNAME`
- Password (for password grant type): `WA_PASSWORD`
- Custom API key secret (for custom_apikey grant type): `WA_CUSTOM_APIKEY_SECRET`
