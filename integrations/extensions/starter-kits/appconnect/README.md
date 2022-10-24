# IBM App Connect Custom Extension

IBM App Connect is an Integration Platform as a Service (or iPaaS) product that enables rapid integration with a wide range of cloud services. You can read all about IBM App Connect [here](https://www.ibm.com/cloud/app-connect).

## Pre-Req: Setting Up
The extension included with this starter kit is discussed in detail in the following [blog article](https://medium.com/ibm-watson/watson-assistant-just-got-connected-a1f46dc9b130).

You'll find in that article that after you have built out a flow in IBM App Connect, you can export an OpenAPI definition of your new flow. After converting the spec from Swagger 2.0 to OpenApi 3.0, you can then import it into Watson Assistant to create the extension. 

## Using this Starter Kit

The `appconnect.openapi.json` included with this starter kit is only to show an example of what your spec should look like after it has been exported from App Connect and converted to an OpenAPI 3.0 spec. It should **not** be used with your own App Connect flow because it points to the endpoint created for the application created for the above blog.

Also, the `appconnect.actions.json` included includes a single action for create a ticket. It relies on an extension called `Ticketing`. Everything needed to create your first App Connect extension can be found in the blog, so please start there.
