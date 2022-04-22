To send the information to the assistant from your web application you can set the value of variables in message context whenever a message is sent to the assistant. In this example we will be setting the name of your user so you can display a customized welcome message with the user's name in it. You could also use this to set other pieces of information about your user that could be used by your assistant. This can be used at any point in time to update any variable used by your assistant.

To set the value of a variable follow these steps:

1. Write a function that can assign the value of a variable to the value you wish to set. In this example, the user's name is hardedcoded to "Cade" but in a production environment, this could look up the user's name from a user profile object that is available to the page code.
```javascript
function preSendHandler(event) {
  // Only do this on messages that request the welcome message.
  if (event.data.input?.text === '') {
    event.data.context.skills['actions skill'] = event.data.context.skills['actions skill'] || {};
    event.data.context.skills['actions skill'].skill_variables = event.data.context.skills['actions skill'].skill_variables || {};
    event.data.context.skills['actions skill'].skill_variables.User_Name = 'Cade';
  }
}
```
2. Register a listener for the `pre:send` event that calls the function to set the value.
```javascript
instance.on({ type: 'pre:send', handler: preSendHandler });
```

TODO: Fill out this file with instructions that can be copied to the main Watson Assistant docs. Once that has been done, then delete this file.