# Template instructions for client/javascript

This template is based off of a simple inline javascript example inside an html file. The entry point is [index.html](./index.html) and the server listens on port 3000. By default, requests are [proxied](https://github.com/http-party/http-server) to `http://localhost:3001` to avoid CORS issues if you are using a server for this example. You can edit this in [package.json](./package.json).


## Using this template

1. Update the title in [package.json](./package.json) and [index.html](./index.html).
2. Update the comment at the top of [index.html][./index.html] to give a general overview of what is unique to this example where 
it says `ADD A COMMENT HERE ABOUT HOW TO FIND THE SPECIAL CODE YOU HAVE ADDED FOR THIS EXAMPLE.`.
3. Add an appropriate Actions skill at [client/javascript/action-skill.json](./client/javascript/action-skill.json).
4. Add the following block to the [README.md](../../README.md) at the root of this example under the `### Client` header:

```
#### Running the Javascript Example

1. Upload [client/javascript/action-skill.json](./client/javascript/action-skill.json) to an Assistant
2. Add the required configuration items from the web chat integration in your assistant to [client/javascript/index.html](./client/javascript/index.html).
3. `cd client/javascript`
4. `npm install`
5. `npm run start`
6. The application will be available at `localhost:3000`.

To view the code, start from [client/javascript/index.html][./client/javascript/index.html]

```

5. Delete this file.