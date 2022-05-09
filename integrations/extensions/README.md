# Watson Assistant Extensions Starter Kit

## Introducing Extensions :tada:

Welcome to Watson Assistant Custom Extensions starter kits!

Custom extensions eliminate the need to create a proxy or a middleware layer between your assistant and other APIs by connecting REST API calls to your skill from within your assistant build. Moreover, all of your custom extensions are organized for ease of access via our integrations catalog, and you can work with them in the same way you work with variables when building your bot. In short, this feature combines the flexibility and power of integrating with APIs, with a significantly improved ease-of-use for developer and content authors alike.

These starter kits provide everything you need to create assistants that use custom extensions to access external REST APIs. Each include:

- A valid OpenAPI 3.0 specification describing the REST API of a third-party service. These OpenAPI documents have been tested and validated with Watson Assistant.
- An actions skill JSON file that uses the extension created from the OpenAPI spec. You can import this file into an assistant and then edit these actions to access the extension you create.
- A README containing API specific instructions on how to set up the extension.

For our full docs on how to create and customize extensions, visit [Building a Custom Extension](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-build-custom-extension).

### How to Use This Repository
These starter kits are here as a reference for you to build your own specs and skills, act as a spring board for demos or POCs, and overall make the learning process easier.

In that vein, we're providing and maintaining **two** main "tutorial" starter kits: `HubSpot` and `MetaWeather`. The files under the **basic** folder will help you get your first custom extension started, quick and simple. Once you've familiarized yourself, the **advanced** files provide examples for more complex OpenAPI specs and actions skills that showcase the full capabilities of extensions.

Then we have our **additional starter kits**. These focus on showcasing the breadth of custom extensions &mdash; use these as inspiration, further examples, or starting points to build out cool extensions.

## Jump to...
1. [All Available Starter Kits](./starter-kits/)
    - [MetaWeather](./starter-kits/metaweather/)
    - [HubSpot](./starter-kits/hubspot/)
    - [Zendesk Support](./starter-kits/zendesk-support/)
    - [Spotify](./starter-kits/spotify/)
    - [Service Now](./starter-kits/servicenow/)
    - [IBM App Connect](./starter-kits/appconnect/)
1. [Create a Custom Extension](#getting-started)
1. [How to Contribute](./docs/CONTRIBUTING.md)

## Getting Started
1. Select a starter kit for your custom extension.
    - We recommend you start with [MetaWeather basic](./starter-kits/metaweather/basic) if this is your first custom extension.
1. Navigate to the appropriate directory and download the OpenAPI spec (ending in `.openapi.json`) and actions skill (ending in `.skill.json`). 
1. Create an extension.
    - Go to your Watson Assistant instance.
    - Navigate to the **Integrations** page.
    - Click `Build custom extension` > `Next`
    - Give your extension a name and a description, then hit `Next`.
    - Upload the OpenAPI file from Step 2 and click `Next` > `Finish`. You should now see your extension created in the `Extensions` section of the catalog.
    <br><br>
    ![create-extension](./assets/create-extension.gif)
1. Add your extension to your assistant.
    - Find your extension under `Extensions` and click `Add +` > `Confirm`.
    - This will take you through Authentication configuration. *If you selected a starter kit with Auth*, find the `Pre-Req` section(s) within the starter kit specific README on how to obtain any keys. Otherwise, simply leave the Auth method as `No authentication`, click through and hit `Finish`, then exit.
    - You should now see a green check mark next to your successfully added extension!
1. Finally, configure your actions skill to use the added extension. There are two ways you can do this: <br>
    - [Method 1](#method-1-best-for-simple-actions-skills) - best for simple actions skills.
    - [Method 2](#method-2-best-for-lengthy-actions-skills) - best for lengthy actions skills (recommended for advanced only). <br>
    ```
    Note: we will soon be automatically linking your extension to the uploaded skill, so sit tight for updates on a more streamlined experience.
    ```
1. You now have a working extension! Go to the **Preview** page and send any message supported by the uploaded actions skill to test it out.

Feel free to modify the skill and response variables to get a better grasp of how to use extensions. If you're ready to see more complex OpenAPI specs, features, such as security, you can move on to [Advanced Usage](./docs/ADVANCED_USAGE.md).

## Configuring Your Actions Skill to use an Extension
### **Method 1**: For simple actions skills
1. Upload your actions skill.
    - Navigate to the **Actions** tab in Watson Assistant.
    - Click the cog icon on the top right corner.
    - Select your actions skill file from the previous step, and click `Upload`.
1. Link your extension to your skill.
    - Navigate back to **Actions** and select the action you would like to add the extension to.
    - Find the step(s) that have `Use an extension` as the step result. You should see an alert on these steps, showing that the extension needs to be linked.
    - Select `Edit Extension` and choose your extension, and reselect the operation and its respective parameters based on what is listed in the `Using this Starter Kit` section of the extension specific README.
    - Click `Save`.            
### **Method 2**: For lengthy actions skills
1. Obtain the ID for your created extension.
    - From the **Integrations** page, open the Developer Tools tab (usually available under your browser menu) and click on the `Network` tab.
    - Find your extension under `Extensions` and click `Open` > `Confirm`.
    - In the **dev tools** tab, click on the request starting with a uuid - i.e. `00671ee7-26e6-43ec-9783-51a9f80bbcf3`. Go to the `Preview` tab and look for the field `config`  > `catalog_item_id`. **Copy this value - this is your extension's ID**. You can now close the dev tools tab.
    <br><br>
    ![add-extension-get-id](./assets/add-extension-get-id.gif)
1. Modify your actions skill.
    - Open the `{selected_extension}.skill.json` file in a text editor.
    - Search on `catalog_item_id` and replace all uuid values with your extension ID from the previous step.
    - Save the file.

    **Important Note**: If you have multiple custom extensions, make sure you replace the correct `catalog_item_id` for each respective action.
    <br>
    ![modify-skill](./assets/modify-skill.gif)

1. Upload your modified actions skill.
    - Navigate to the **Actions** tab in Watson Assistant.
    - Click the cog icon on the top right corner.
    - Select your actions skill file from the previous step, and click `Upload`.
