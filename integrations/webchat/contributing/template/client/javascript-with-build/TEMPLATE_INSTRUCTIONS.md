# Template instructions for client/javascript-with-build

This template is based off of a non-React javascript build using the [Parcel](https://parceljs.org/) library. Parcel will allow you to import `ts`, `js`, `css`, `scss`, etc files with no additional configuration. The entry point is [index.js](./index.js) and the server listens on port 3000. By default, requests to `/api` are [proxied](https://github.com/http-party/http-server) to `http://localhost:3001/api` to avoid CORS issues if you are using a server for this example. You can edit this in [package.json](./package.json).


## Using this template

**NOTE: You should only use the `javascript-with-build` or the `javascript` example, not both.**

1. Delete the existing [javascript][../javascript] folder and rename this folder to `javascript`.
2. Update the title in [package.json](./package.json) and [index.html](./index.html).
3. Update the comment at the top of [index.js][./index.js] to give a general overview of what is unique to this example where 
it says `ADD A COMMENT HERE ABOUT HOW TO FIND THE SPECIAL CODE YOU HAVE ADDED FOR THIS EXAMPLE.`.
4. Delete the [styles.scss] file and the reference in `index.js` if you are not going to use it.
5. Add an appropriate Actions skill at [client/javascript/action-skill.json](./client/javascript/action-skill.json).
6. Add the following block to the [README.md](../../README.md) at the root of this example under the `### Client` header:

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