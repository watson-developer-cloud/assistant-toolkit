# MetaWeather Custom Extension

```
** IMPORTANT NOTICE **

The MetaWeather API is currently unavailable. In the meantime, we recommend using a different starter kit to get started with extensions.

Check back for updates on when the API is running again, or visit https://www.metaweather.com/api/ to check the status on your own.
```

MetaWeather is a simple API for querying location weather data. The API supports three use cases:

- GET Location Search: Search for a location by a name (string) OR a lattitude and longitude value. This resturns a Where-On-Earth-ID (woeid).
- GET Location: Use a Where-On-Earth-ID to get a location's 5-day weather forecast.
- GET Location Day: Use a Where-On-Earth-ID and a date to query for a location's historical weather data.

The API reference for MetaWeather is at [https://www.metaweather.com/api/](https://www.metaweather.com/api/). Note that MetaWeather does not use any API keys.

## Basic vs. Advanced Spec
In the `basic` spec, we only feature a single operation, `GET Location`, for simplicity purposes. There will be minimal configuration and no variables necessary for getting started, to help readability and understanding for ease of learning.

In the `advanced` spec/actions folder, we include more complicated OpenAPI concepts such as refs, and using extensions and results in a single skill. This spec introduces better, more creative ways to configure and utilize your extension using actions and OpenAPI specifications. The below usage example describes the functionality of the full advanced jsons.

## Using this Starter Kit
You can use the `metaweather.advanced.openapi.json` specification file to recreate the three endpoints listed above. Using the `basic` files will have limit you to using the `Location` operation, but is simpler. We recommend this for anyone who is using OpenAPI and/or actions for the first time. 

To get started with using the OpenAPI spec, you can upload the `metaweather.advanced.actions.json` file into your Watson Assistant actions. After upload, the actions should be configured to use the fields below for the skill to be fully functional. Follow the instructions [here](../../README.md#configuring-your-actions-skill-to-use-an-extension) and note the information below to do this.

- **Action 1** (basic + advanced). I want to check the weather and have a WOE-ID.
    ```
    Operation: Location
    Parameters:
        - woeid: 1. Great! What is the WOE-ID ...
    ```

- **Action 2** (advanced). I want to check the weather (and do not have a WOE-ID).
    ```
    Operation 1: Location Search
    Parameters:
      - query: 1. Sure thing! What is the name ...

    Operation 2: Location
    Parameters:
      - woeid: location_item_0_woeid
    ```

- **Action 3** (advanced). I have a WOE-ID and want to check historical weather data.
    ```
    Operation: Location Day
    Parameters:
      - woeid: 1. OK, I can help with that. What is your ...
      - year: year
      - month: month
      - date: date
    ```
If you want to know the Where-On-Earth-ID of a particular location, simply use the first action to query using the location name. The Where-On-Earth-ID of the location will be returned to you.

### Example Usage
A conversation using this starter kit could look like the following:<br>

![get-weather](./assets/get-weather.gif)

Free free to contribute to this starter kit, or add other starter kits by following these [contribution guidelines](../../docs/CONTRIBUTING.md).

## Even More Advanced: Using the MetaWeather Extension with Another Extension
Going one step further, extensions are not limited to one per skill -- you can use multiple custom extensions in a skill and even in an action! 

We have published a [Medium blog](https://linktoblog) that showcases this capability. The `metaweather-zendesk.blog.actions.json` found in the `advanced` folder is a two-extension example skill that is used in the blog. To configure this spec, you should use the below operations and parameters.

Here, we assume that you named your MetaWeather extension `MetaWeather` and your Zendesk Support extension `Zendesk`:

- **Action 1** (blog). I want to create a ticket due to weather.
    ```
    Extension 1: MetaWeather
    Operation 1: Location Search
    Parameters:
      - query: 1. Sure thing! What is the name ...

    Extension 2: MetaWeather
    Operation 2: Location Day
    Parameters:
      - woeid: location_item_0_woeid
      - year: year
      - month: month
      - date: date

    Extension 3: Zendesk
    Operation 3: Create Ticket
    Parameters:
      - request.subject: ticket_subject
      - request.comment.body: ticket_request_body
      - request.requester.name: 1. Sure thing. Can I ask for your name?
      - request.requester.email: 2. How about your email?
    ```

### Example Usage
A conversation using this two extension skill could look like the following:<br>
![weather-cancellation](./assets/weather-cancellation.gif)