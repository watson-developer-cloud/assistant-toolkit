# MailChimp Custom Extension

![MailChimp](./assets/mailchimp-logo.png)

MailChimp is a commonly used mass email distribution tool that offers clients a variety of marketing capabilities. This starter kit focuses on MailChimp's campaign feature.

This starter kit offers support for the following API endpoints:

- POST Create Ticket
- GET List Tickets
- GET Get Ticket Info
- DELETE Archive Ticket

The MailChimp public API docs can be found [here](https://mailchimp.com/developer/marketing/api/).

## Pre-Req 1: Getting Auth Keys
1. Create an account at MailChimp. 
1. Generate an API key and configure whichever server your Mailchimp account is bound to.

See https://mailchimp.com/developer/marketing/guides/quick-start/#generate-your-api-key for more details.

## Pre-Req 2: Configure MailChimp Campaign
Next, you will need to be able to send a campaign.

1. Create a list (audience) then add members to this list.
2. Create a campaign.

**Tip:** Mailchimp will error and won't send the campaign unless its structured correctly. Therefore it is recommended to log into Mailchimp, search for the campaign, and edit it until everything has a green check.
Here you will find how to start with an audience and a member: https://mailchimp.com/developer/marketing/guides/create-your-first-audience/.

## Using this Starter Kit
Upload the provided OpenAPI spec as a custom extension, and add your token to the Auth section when prompted, and you should be able to start using the MailChimp API within your assistant and actions skill.

- **Action 1.** I want to create a playlist.
    ```
    Operation: Create a Playlist
    Parameters:
      - user_id: 1. What is your user id?
      - name: 2. What do you want to name ...
      - description: 3. Give a short description for ...
      - public: isPublic
    ```

## Example Usage
A conversation using this starter kit could look like the following:<br>

![mailchimp-convo](./assets/mailchimp-convo.png)

Free free to contribute to this starter kit, or add other starter kits by following these [contribution guidelines](../../docs/CONTRIBUTING.md).