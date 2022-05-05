To change the launcher or home screen texts based on the current page the user is visiting or another client condition, follow these steps.

1. The first step is to determine what page the user is on. How this is done can vary a lot by application and you can customize this however is appropriate for your application but a simple mechanism is to check for a URL parameter.

```javascript
const page = new URLSearchParams(window.location.search).get('page');
```

2. When web chat is loaded, we can check this value to make the changes that are appropriate to each page. You can use any condition you want here.

```javascript
if (page === 'cards') {
  // Update the launcher and home screen to present information specific to credit cards.
  ...
} else if (page === 'account') {
  // Update the launcher and home screen to present information specific to the user's account.
  ...
}
```

3. To change the text presented on the launcher, use the `updateLauncherGreetingMessage` instance function. This will change the default that is displayed on the launcher (which normally appears after 15 seconds).
```javascript
instance.updateLauncherGreetingMessage("I see you're interested in credit cards! Let me know if I can help.");
```

4. To change the texts presented on the home screen, use the `updateHomeScreenConfig` instance function. This will change the greeting text, ensure that the home screen is enabled so it can be shown, and change the three buttons that appear on the home screen. This will override any home screen that may be set from the web chat integration setup page in Watson Assistant.
```javascript
instance.updateHomeScreenConfig({
  is_on: true,
  greeting: 'What can I tell you about credit cards?',
  starters: {
    is_on: true,
    buttons: [
      { label: 'Card interest rates' },
      { label: 'Cards with rewards' },
      { label: 'Business cards' }
    ]
  }
});
```

For complete working code see [the example in our GitHub repository](https://github.com/watson-developer-cloud/assistant-toolkit/tree/master/integrations/webchat/examples/change-launcher-and-home-screen-text).