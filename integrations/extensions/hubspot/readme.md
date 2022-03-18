

## Background

When it comes to being developer friendly, Hubspot is great.
Their public API documentation publishes an OpenAPI doc and offers a Postman collection:  https://developers.hubspot.com/docs/api/crm/tickets.

In addition, Hubspot provides an endpoint that lists all the OpenAPIs of their product, https://api.hubspot.com/api-catalog-public/v1/apis?_ga=2.141533746.1208547948.1639927461-1206602246.1639927461.  

If you need only one for ticketing, you can find it here:  https://api.hubspot.com/api-catalog-public/v1/apis/crm/v3/objects/tickets.

There is also a free version of Hubspot which makes Hubspot a great place to start for proofs of concept, testing, etc that allows you to upgrade your account as needed.


## Obtaining an API key
To call the Hubspot API, you will need an API key.

Click on Settings (the cog wheel icon) at the top right of the page, then click `Integrations` in the left menu to open a drop-down, and select `Api Key` from the drop-down menu.

There you can create a new API key or access your existing API keys.  You can also see a log of API calls using each API key, which is helpful to debug API calls sent from your assistants that do not produce the expected outcome in Hubspot or in your assistants.

## Using this starter kit

This starter kit expects five parameters when creating a ticket: 
- Subject
- hs_pipeline_stage
- charge_name
- charge_amount
- charge_date

Subject and hs_pipeline_stage exist by default in Hubspot's ticketing schema.  The hs_pipeline_stage field in the API corresponds to the `Ticket status` of a Hubspot ticket; we recommend submitting a value of `1` for the hs_pipeline_stage field, which will set the `Ticket status` to `New`.  The Subject field in the API corresponds to the `Ticket name` of a Hubspot ticket.

charge_name, charge_amount, and charge_date are custom fields we created for this starter kit.  To use this starter kit's .json file in your custom extension, you should add these parameters to your Hubspot ticketing system.  Click on Settings (the cog wheel icon) at the top right of the page, then click `Objects` in the left menu to open a drop-down, and select `Tickets` from the drop-down menu.  Then click `Manage ticket properties` and `Create property`.  

In most cases you will want to customize these fields for your own proof of concept or test's use case. You will need to access Hubspot and create your own custom fields as described in the previous paragraph, as well as update the OpenAPI spec (the .json file) to expect your own custom fields. You should update the required parameters in the $ref for the request object. You should then edit the steps in your assistant's Action skill to match.
