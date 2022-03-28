# Watson Assistant Extensions Starter Kit

## Introducing Extensions :tada:

These starter kits provide everything you need to create assistants that use custom extensions to access external services using REST APIs.

Each starter kit includes:

- A valid OpenAPI 3.0 specification describing the REST API of a third-party service. These OpenAPI documents have been tested and validated with Watson Assistant.
- An actions skill JSON file that uses the extension created from the OpenAPI spec. You can import this file into an assistant and then edit these actions to access the extension you create.
- A README containing API specific instructions on how to set up the extension.

More starter kits will be added in the future.

## Jump to...
1. [All Available Starter Kits](./starter-kits/)
    - [MetaWeather](./starter-kits/metaweather/)
    - [HubSpot](./starter-kits/hubspot/)
1. [Create a Working Custom Extension](#getting-started)
1. [Supported Features](#supported-features)


## Getting Started
This guide is for using a **starter kit** for your custom extension. For our full official documentation, visit [Building a Custom Extension](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-build-custom-extension). 

1. Select a starter kit for your custom extension. 
1. Navigate to the appropriate directory and download the OpenAPI spec (ending in `.openapi.json`) and actions skill (ending in `.skill.json`). 
1. Create an extension.
    - Go to your Watson Assistant instance.
    - Navigate to the **Integrations** page.
    - Click `Build custom extension` > `Next`
    - Give your extension a name and a description, then hit `Next`.
    - Upload the OpenAPI file from Step 2 and click `Next` > `Finish`. You should now see your extension created in the `Extensions` section of the catalog.
1. Link your extension to the uploaded skill. There are two ways you can do this: <br>
    - [Method 1](#method-1-best-for-simple-actions-skills) - best for simple actions skills.
    - [Method 2](#method-2-best-for-lengthy-actions-skills) - best for lengthy actions skills. 
1. You now have a working extension! Go to the **Preview** page and send any message supported by the uploaded actions skill to test it out.

Feel free to modify the skill and response variables to get a better grasp of how to use extensions. If you're ready to see more complex features and Auth usage, you can move on to [Advanced Usage](./docs/ADVANCED_USAGE.md).

## Supported Features
Details on the full features and limitations are described in our official documentation page: https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-build-custom-extension#build-custom-extension-openapi-file

## Linking your extension to an actions skill
### **Method 1**: best for simple actions skills.
1. Upload your actions skill.
    - Navigate to the **Actions** page in Watson Assistant.
    - Click the cog icon on the top right corner.
    - Select your actions skill file from the previous step, and click `Upload`.
1. Link your extension to your skill.
    - Navigate back to **Actions** and select the action you would like to add the extension to.
    - Find the step(s) that have `Use an extension` as the step result. You should see an alert on these steps, showing that the extension needs to be linked.
    - Select `Edit Extension` and choose your extension, and reselect the operation and its respective parameters, mimicking the parameters of the initial (errored) configuration.
    - `Save`.            
### **Method 2**: best for lengthy actions skills.
1. Obtain the id for your created extension.
    - From the **Integrations** page, open the dev tools tab (shortcuts: `CMD + OPTIONS + i` for Mac, `CTRL + SHIFT + j` for Linux/Windows) and click on the `Network` tab.
    - Find your extension under `Extensions` and click `Add +` > `Confirm`.
    - In the **dev tools** tab, click on the request starting with a uuid - i.e. `00671ee7-26e6-43ec-9783-51a9f80bbcf3`. Go to the `Preview` tab and look for the field `config`  > `catalog_item_id`. **Copy this value - this is your extension's id**. You can now close the dev tools tab.
    - Click through the default settings, then click `Finish` and `Close`. You should now see a green check mark next to your extension, indicating that you successfully added your catalog item to your assistant.
1. Modify your actions skill.
    - Open the `{selected_extension}.skill.json` file in a text editor.
    - Search on `catalog_item_id` and replace all uuid values with your extension id from the previous step.
    - Save the file.
1. Upload your modified actions skill.
    - Navigate to the Actions tab in Watson Assistant.
    - Click the cog icon on the top right corner.
    - Select your actions skill file from the previous step, and click `Upload`.
