

## Background

When it comes to be developer friendly, Hubspot is great.
Their public api documentation publishes an openapi doc and offers a postman collection
https://developers.hubspot.com/docs/api/crm/tickets.
In addition Hubspot provides an endpoint that lists all the OpenAPIs of their product
https://api.hubspot.com/api-catalog-public/v1/apis?_ga=2.141533746.1208547948.1639927461-1206602246.1639927461
and if you use just need one, like ticketing then you can just get it here
https://api.hubspot.com/api-catalog-public/v1/apis/crm/v3/objects/tickets

There is also a free version of Hubspot which makes using Hubspot for proofs of concept, testing, etc a great way to get starter and upgrade your account as needed.


## Obtaining API Key
To call Hubspot api, you would need to obtain an api key.
Click on Settings ( cog wheel ) at the top right then on the menu go to `Integrations->Api Key`

## Using this starter kit
This starter kit specifically expects 5 parameters for filing a ticket: 
- Subject
- hs_pipeline_stage
- charge_name
- charge_amount
- charge_date
In most cases you will want to customize this for your use case. You will need to access hubspot and create these custom fields within a ticket, as well as update the openAPI spec to expect these 5. You should update the required parameters in the $ref for the request object. You will then follow along and edit the steps in your action skill to match
