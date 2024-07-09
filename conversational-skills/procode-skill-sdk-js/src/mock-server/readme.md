# Mock Provider Server

## Goal
This server is a simple [Express](https://expressjs.com/) server which simulates an example provider. 

## How to use it

### Host it!
For testing purposes, you can either:
A. use one of the technologies that expose your localhost to the public domain via tunneling. 
B. Use IBM CodeEngine

#### IBM Cloud Code Engine Deployment

The server comes with the Dockerfile for Code Engine deployment. To deploy the server to Code Engine, please follow the
steps below:

1. Build the docker image
    Build the image with `docker build -t <image-name> .` The image name should follow the format
       of `<registry>/<namespace>/<image-name>:<tag>`. For example, `us.icr.io/testitall_ns/testitall_server:latest`.
2. Push the image to the container registry with `docker push <image-name>`
3. Create a Code Engine project
   and [deploy the image](https://cloud.ibm.com/docs/codeengine?topic=codeengine-deploy-app-crimage)


### Register your provider

```
POST {{host}}/v2/providers?version={{version}}

{
  "provider_id": "myProCodeProvider-noSpaces_no_special_char",
  "specification": {
    "servers": [
      {
        "url": "<your-pro-code-server-url>"
      }
    ],
    "components": {
      "securitySchemes": {
        "authentication_method": "none"
      }
    }
  },
  "private": {
    "authentication": {
      
    }
  }
}
```

### Create a Skill-Backed Action
Once a provider has been registered on a watsonx Assistant instance, skill-backed actions may be created from the Assistant UI.
During that process your server `listSkills` function will be called. 


### Test your Skill
Make a conversation within the Assistant UI. Each turn should hit your `orchestrate` function. Please note, if you are debugging and you have added a breakpoint, if the response isn't returned to watsonx Assistant within 30 seconds you will observe an error in the UI.
