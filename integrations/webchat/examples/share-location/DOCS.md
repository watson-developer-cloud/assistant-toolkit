Note: This document is in a temporary location until this content is moved into the [main IBM watsonx Assistant documentation](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-overview) tutorials section.

If you want an action to generate a response based on the user's location such as displaying Google Maps in an I-frame, follow these steps.

1. Create an action that tells web chat that the user's location needs to be shared with it. You can use the JSON editor to add hidden `user_defined` data to a response. The `"user_defined_type": "share_location"` value is what will tell web chat to get the user's location.
```json
{
  "generic": [
    {
      "values": [
        {
          "text_expression": {
            "concat": [
              {
                "scalar": "Please first share your location with me."
              }
            ]
          }
        }
      ],
      "response_type": "text",
      "selection_policy": "sequential"
    },
    {
      "user_defined": {
        "user_defined_type": "share_location"
      },
      "response_type": "user_defined"
    }
  ]
}
```

2. In web chat, create a handler for the `receive` event that looks for the `user_defined` values you added to your action.
```javascript
function receiveHandler(event, instance) {
  event.data.output?.generic?.forEach(item => {
    if (item.user_defined && item.user_defined.user_defined_type === 'share_location') {
      navigator.geolocation.getCurrentPosition(
        data => takeLocation(data, instance),
        error => getLocationError(error, instance)
      );
    }
  });
}
```

3. Add callbacks for the browser's `getCurrentPosition` function that will send a message to the assistant containing the user's location.
```javascript
function takeLocation(data, instance) {
  instance.send({
    input: {
      text: "Ok I've shared my location."
    },
    context: {
      "skills": {
        "actions skill": {
          "skill_variables": {
            "User_Latitude": data.coords.latitude,
            "User_Longitude": data.coords.longitude,
          },
        }
      }
    },
  });
}

function getLocationError(error, instance) {
  instance.send({ input: { text: 'I was unable or unwilling to share my location.' } });
}
```

4. Create an action that will return an `iframe` response to web chat that contains a Google Maps URL that contains the user's location. You can use the JSON editor to make use of the context variables that were assigned by web chat. You should use your own Google Maps API key.
```json
{
  "generic": [
    {
      "title": "IBM Location",
      "source": "https://www.google.com/maps/embed/v1/directions?key=<your_api_key>&origin=${User_Latitude},${User_Longitude}&destination=IBM",
      "description": "Nearest IBM location",
      "user_defined": {
        "user_defined_type": "google_map"
      },
      "response_type": "iframe"
    }
  ]
}
```

5. Register the `receive` handler with web chat.
```javascript
function onLoad(instance) {
  instance.on({ type: 'receive', handler: receiveHandler });
  instance.render();
}
```

For complete working code see [the example in our GitHub repository](https://github.com/watson-developer-cloud/assistant-toolkit/tree/master/integrations/webchat/examples/share-location).