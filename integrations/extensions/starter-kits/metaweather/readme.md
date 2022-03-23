
## Background

MetaWeather is a simple API for querying location weather data. The API supports three use cases:

- Search for a location by a name (string) OR a lattitude and longitude value. This returns a Where-On-Earth-ID (woeid).
- Use a Where-On-Earth-ID to get a location's 5-day weather forecast.
- Use a Where-On-Earth-ID and a date to query for a location's historical weather data.

The API reference for MetaWeather is at [https://www.metaweather.com/api/](https://www.metaweather.com/api/).


## Using this starter kit

MetaWeather does not use any API keys.

You can use the `openapi-spec.json` specification file to recreate the three endpoints listed above. Or you can you upload the `wa-actions.json` file into your Watson Assistant actions; this will upload three actions:

- Search the current weather of a location using a location name.
- Search the current weather of a location using a Where-On-Earth-ID.
- Search the past weather of a location using a date and a Where-On-Earth-ID.

If you want to know the Where-On-Earth-ID of a particular location, simply use the first action to query using the location name. The Where-On-Earth-ID of the location will be returned to you.

