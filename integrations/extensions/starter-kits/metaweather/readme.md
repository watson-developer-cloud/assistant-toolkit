# `MetaWeather Custom Extension`

## Background
MetaWeather is a simple API for querying location weather data. The API supports three use cases:

- `GET Location Search`: Search for a location by a name (string) OR a lattitude and longitude value. This returns a Where-On-Earth-ID (woeid).
- `GET Location`: Use a Where-On-Earth-ID to get a location's 5-day weather forecast.
- `GET Location Day`: Use a Where-On-Earth-ID and a date to query for a location's historical weather data.

The API reference for MetaWeather is at [https://www.metaweather.com/api/](https://www.metaweather.com/api/). Note that MetaWeather does not use any API keys.

## Using this Starter Kit
You can use the `metaweather.openapi.json` specification file to recreate the three endpoints listed above. Or you can you upload the `metaweather.actions.json` file into your Watson Assistant actions; this will upload three actions:

- **Action 1**. Search the current weather of a location using a location name.

    **Operation 1**: `Location Search`
    - Parameters:
        - `query`: `1. Sure thing! What is the name ...`

    **Operation 2**: `Location`
    - Parameters:
        - `woeid`: `location_item_0_woeid`

- **Action 2**. Search the current weather of a location using a Where-On-Earth-ID.
    - Operation: `Location`
    - Parameters:
        - `woeid`: `1. Great! What is the WOE-ID ...`

- **Action 3**. Search the past weather of a location using a date and a Where-On-Earth-ID.
    - Operation: `Location Day`
    - Parameters:
        - `woeid`: `1. OK, I can help with that. What is your ...`
        - `year`: `year`
        - `month`: `month`

If you want to know the Where-On-Earth-ID of a particular location, simply use the first action to query using the location name. The Where-On-Earth-ID of the location will be returned to you.

Using this action, a typical conversation may look like this:<br>
![metaweather-convo](./assets/metaweather-convo.png)