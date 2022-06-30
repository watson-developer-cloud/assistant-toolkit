The existing docs (https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-security) cover explanations for how to set up security but there's a few tweaks we should make:

1. We should probably move that architecture diagram much lower. It is pretty intimidating if that is the first thing you see and those details are probably not things that most of our customers need to worry about.
2. I think this article could benefit from starting off with a very high level overview of the steps that are required to enable security. To enable security you basically need to do three things:
   1. turn on security in the web chat settings
   2. create an endpoint on your server that can generate a JWT
   3. pass the JWT to web chat

--

This example also covers a second slightly more specific security use case described here:

If you want Watson Assistant to be able to perform operations that are specific to authenticated users and you need to be able to handle the case where a user may begin a session unauthenticated but then later authenticate, follow the steps below. There are multiple ways of accomplishing this task; below is only one example, and it is based on a NodeJS Express server.

Watson Assistant does not allow you to change the user ID that is associated with a session in the middle of a session. In order to support the use case where users may authenticate in the middle of the session, you will need to create a "fake" user ID that can be associated with the user and which does not change for the life of the session. Then you can provide the real user ID as part of the custom user payload inside a JWT so the Assistant can perform operations specific to your authenticated user. You could use a context variable instead of the user payload of a JWT but doing so is not secure; context variables have the potential to be modified by your users whereas the contents of a JWT may not be modified.

1. Create an anonymous user ID and store it in a cookie. This will allow you to ensure that you are using the same user ID when talking with Watson Assistant both for the life of a session and over time. It is generally recommended using a cookie that lasts for at least 45 days. If you do not use a cookie or do not store it for more than 30 days, this may result in your users having different IDs each time they use web chat which may incur an MAU that you will be billed for. Note that this technique will trigger an MAU for the same user if that user uses a different device, if they clear their cookies or if they are using a private browsing session.

```javascript
function getOrSetAnonymousID(request, response) {
  let anonymousID = request.cookies['ANONYMOUS-USER-ID'];
  if (!anonymousID) {
    anonymousID = `anon-${uuid()}`;
  }

  response.cookie('ANONYMOUS-USER-ID', anonymousID, {
    expires: new Date(Date.now() + 1000 * 60 * 60 * 24 * 45), // 45 days.
    httpOnly: true,
  });

  return anonymousID;
}
```

2. When creating your JWT, assign the `sub` value to the anonymous user ID and use the `user_payload` to store the real user's ID from session info data (if the user is authenticated).
```javascript
const jwtContent = {
  sub: anonymousUserID,
  user_payload: {
    name: 'Anonymous',
    custom_user_id: anonymousUserID,
  },
};

if (sessionInfo) {
  jwtContent.user_payload.name = sessionInfo.userName;
  jwtContent.user_payload.custom_user_id = sessionInfo.customUserID;
}
```

3. In your action, you can access the real user ID by assigning the value to a variable using an expression
```text
${system_integrations.chat.private.user_payload}.custom_user_id
```

For complete working code see [the example in our GitHub repository](https://github.com/watson-developer-cloud/assistant-toolkit/tree/master/integrations/webchat/examples/web-chat-security).