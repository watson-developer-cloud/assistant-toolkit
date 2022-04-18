# Template instructions for client/react

This template is based off of a simplified version of [Create React App](https://create-react-app.dev). The entry point is [src/index.js](./src/index.js) and the server listens on port 3000. By default, requests are [proxied](https://create-react-app.dev/docs/proxying-api-requests-in-development/) to `http://localhost:3001` to avoid CORS issues if you are using a server for this example. You can edit this in [package.json](./package.json). This project also makes use of the [@ibm-watson/assistant-web-chat-react](https://github.com/watson-developer-cloud/assistant-web-chat-react) to load web chat.


## Using this template

1. Update the title in [package.json](./package.json) and [src/public/index.html](./src/public/index.html).
2. Update the comment at the top of [src/App.js][./src/App.js] to give a general overview of what is unique to this example where 
it says `ADD A COMMENT HERE ABOUT HOW TO FIND THE SPECIAL CODE YOU HAVE ADDED FOR THIS EXAMPLE.`.
3. Add an appropriate Actions skill at [action-skill.json](./action-skill.json).
4. Add the following block to the [README.md](../../README.md) at the root of this example under the `### Client` header:

```
#### Running the React Example

1. Upload [client/react/action-skill.json](./client/react/action-skill.json) to an Assistant
2. Add the required configuration items from the web chat integration in your assistant to [client/react/src/config.js](./client/react/src/config.js).
3. `npm install`
4. `npm run start`
5. The application will be available at `localhost:3000`.

To view the code, start from [client/react/src/index.js][./client/react/src/index.js]

```

5. Delete this file.