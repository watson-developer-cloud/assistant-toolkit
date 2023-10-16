# Test it ALL

> When using this starter kit to create assistant instances, please make sure to **first create and add the extensions
to
> the assistant, then upload the actions.** Therefore, the actions can be automatically associated with the extension.

## Goal

This is an example of all the features supported by Watson Assistant when using a Custom Extension.
The OpenApi specification is vast, and hence not all of its features are supported by Watson Assistant.
This example describes all the features supported and provides a means to test them out.
It is expected that this example will grow over time as more features are supported.

## Features

The provided OpenAPI specification and WA actions currently covered the following features supported by Watson
Assistant:

- Dynamic Base Path in url
- Security: Basic, Bearer, API key and OAuth2
- Parameters: Within query string, header (including Authorization header) or URL path
- Error handling: When an extension error occurred (API server error, response size limit)
- Timeout handling: When an extension takes more than 30 seconds to execute
- Arrays: Both in request and response
- Pre and Post message webhooks

## Mock API Server

The custom extension requires a running API server to test the OpenAPI specification. We provide an existing mock 
server or the ability to deploy your own.

### Use IBM Hosted Mock API Server

We provide a hosted mock API server for you to test the OpenAPI specification. To use that server, please select
`https://custom-ext-testitall-ce.vkgqdtxxpfe.us-south.codeengine.appdomain.cloud` in the Servers dropdown on the Authentication tab
while adding the extension to your Watson Assistant.

### Deploy the Mock API Server by yourself

1. Deploy the express.js application to your server.
    - In a terminal, navigate to the `testitall/mock-server` folder.
    - Run `npm install` to install the dependencies.
    - Copy the `.env-sample` file to `.env`, and update the environment variables if needed.
    - Run `npm start` to start the server at port `4000`.
2. Update the server URLs in the OpenAPI specification file `testitall.openapi.json` to point to your server.
    - Assume your API server is running at `https://myserver.com:4000`
        - If you would like to test the OAuth2 related feature, then the API server must be running with HTTPS.
          - This mock server doesn't come with HTTPS support by itself, please consider to deploy with reverse proxy or third-party proxy service (e.g. ngrok) to
            enable HTTPS.
        - The server URL should NOT contain the tailing slash.
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
    - After the actions were uploaded, **edit each action to use the extension**.

## WA Actions and corresponding OpenAPI Endpoints

The example provides a set of actions to test the specification:

| WA Action Name                                               | WA Trigger Word            | OpenAPI Endpoint                 | Description                                                                                                                                                                                                                                                                                                  |
|--------------------------------------------------------------|----------------------------|----------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Test HTTP GET                                                | "GET"                      | `/test`                          | Test HTTP GET.                                                                                                                                                                                                                                                                                               |
| Test HTTP POST                                               | "POST"                     | `/test`                          | Test HTTP POST.                                                                                                                                                                                                                                                                                              |
| Test HTTP PUT                                                | "PUT"                      | `/test`                          | Test HTTP PUT.                                                                                                                                                                                                                                                                                               |
| Test HTTP DELETE                                             | "DELETE"                   | `/test`                          | Test HTTP DELETE.                                                                                                                                                                                                                                                                                            |
| Test handle error in response                                | "Error"                    | `/test/error`                    | The API server will return a 404 error to test error handling in WA.                                                                                                                                                                                                                                         |
| Test parse array inside object                               | "Arrays Object"            | `/test/arrays-object`            | The API server will reverse the array inside the object.                                                                                                                                                                                                                                                     |
| Test handle an almost too large response                     | "Context Almost Too Large" | `/test/context-almost-too-large` | The API server will return a response that is almost too large (99KB). The extension call itself should succeed. The action will try to store the response into a session variable which will cause any following conversation to produce a 413 error.                                                       |
| Test handle a too large response                             | "Context Too Large"        | `/test/context-too-large`        | The API server will return a response that is too large (500KB). The extension call should fail.                                                                                                                                                                                                             |
| Test set auth header with context variable                   | "Auth Header"              | `/test/auth-header`              | **Please make sure you've selected `No authentication` option in extension configuration, otherwise the auth header will be overridden.**<br/>The WA will set the Authorization header with the value provided by the user. The API server will echo the Authorization header in the response.               |
| Test post with parameters                                    | "Params"                   | `/test/params/{path_param}`      | WA will send a POST request with parameters in the query string, header and URL path. The API server will echo the parameters in the response.                                                                                                                                                               |
| Test response with a delay                                   | "Delay"                    | `/delay`                         | The API server will delay the response by the provided number of seconds (default: 3).                                                                                                                                                                                                                       |
| Test consecutive extension calls                             | "Consecutive"              | `/test`                          | WA will make 3 consecutive calls to the extension, without user interaction between them.                                                                                                                                                                                                                    |
| Test calling extension with different authentication methods | "Security"                 | `/security/{method}`             | **Please make sure you've selected the corresponding auth method in extension configuration before testing it.**<br/>WA will call the API endpoint that requires authentication.<br/>Supported methods: Basic, Bearer, API key and Oauth2 (except the implicit flow).<br/>See below for default credentials. |
| Test pre-message webhook                                     | "Pre-message"              | `/webhook/prewebhook`            | **Please make sure you've enabled the Pre-Message Webhook in environment configuration before testing it.**<br/>The server will set the session variable `pre_webhook_message` while receiving the pre-message webhook call.                                                                                 |
| Test post-message webhook                                    | "Post-message"             | `/webhook/postwebhook`           | **Please make sure you've enabled the Post-Message Webhook in environment configuration before testing it.**<br/>The server will set the session variable `post_webhook_message` while receiving the post-message webhook call.                                                                              |

For detailed description of each endpoint, please see the [OpenApi Spec](./testitall.openapi.json) provided.

## Extension Authentication Configuration

When adding the extension to your Watson Assistant, you may configure the authentication method used when calling the
extension.

The credentials in this section are used by default for the corresponding authentication methods. When hosting your own
API
server, you may change them by editing the `.env` file.

### Basic

- Username: `"WA_USERNAME"`
- Password: `"WA_PASSWORD"`

### Bearer

- Token: `"WA_TOKEN"`

### API Key

- API key: `"WA_APIKEY"`

### OAuth 2.0

Watson Assistant also supports using OAuth2 to authenticate the extension. More information
available [here](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-add-custom-extension#add-custom-extension-oauth).
The following are the configuration for each OAuth2 grant type:

#### Authorization Code

Please first enter the Client ID:

- Client ID: `"WA_CLIENT_ID"`

> Note: You don't need to manually copy the Redirect URL to your OAuth2 provider. The URL is sent together with the
> request to the provider.

Then, click the `Grant Access` button to authorize the extension to access your OAuth2 provider. You will be redirected 
to the provider's authorization page, which will ask you to authorize the
extension. After you authorize the extension, you will be redirected back to the Assistant, with the authorization code
passed to the Watson Assistant.

- Client secret: `"WA_CLIENT_SECRET"`
- Client authentication: `Send as Body` (default)
- Header prefix: `Bearer` (default)

#### Client Credentials

- Client ID: `"WA_CLIENT_ID"`
- Client secret: `"WA_CLIENT_SECRET"`
- Client authentication: `Send as Body` (default)
- Header prefix: `Bearer` (default)

#### Password

- Client ID: `"WA_CLIENT_ID"`
- Client secret: `"WA_CLIENT_SECRET"`
- Username: `"WA_USERNAME"`
- Password: `"WA_PASSWORD"`
- Client authentication: `Send as Body` (default)
- Header prefix: `Bearer` (default)

#### Custom API Key

- Custom API key secret (for custom_apikey grant type): `"WA_CUSTOM_APIKEY_SECRET"`
- Client authentication: `Send as Body` (default)
- Header prefix: `Bearer` (default)

## Assistant Webhook Configuration

> If you would like to test the webhook related feature, then the API server must be running with HTTPS.

The mock server also provided endpoints for pre-message and post-message webhook. To enable them, please follow the
steps below:

1. Navigate to the `Environment` tab of your Watson Assistant instance.
2. Select an environment, and click the gear icon beside the environment's name to open the environment configuration
   page.
![Environment Configuration](./assets/webhook_env_config_1.png)
3. Select `Webhooks` > `Pre-Message Webhook` or `Post-Message Webhook` from the menu on the left.
4. Enable the webhook by clicking the toggle switch.
5. Enter the URL of the webhook.
    - Pre-message webhook: `https://myserver.com:4000/webhook/prewebhook`. For IBM hosted mock server, please use
      `https://custom-ext-testitall-ce.vkgqdtxxpfe.us-south.codeengine.appdomain.cloud/webhook/prewebhook`.
    - Post-message webhook: `https://myserver.com:4000/webhook/postwebhook`. For IBM hosted mock server, please use
      `https://custom-ext-testitall-ce.vkgqdtxxpfe.us-south.codeengine.appdomain.cloud/webhook/postwebhook`.
6. Enter anything in the `Secret` field.
![Webhook Configuration](./assets/webhook_env_config_2.png)