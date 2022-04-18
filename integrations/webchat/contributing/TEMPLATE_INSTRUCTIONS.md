# Using the code example template

All code examples should use the template in the [template](./template) folder. This file contains the step by step instructions on how to make the `template` work for your use case.

## Step 1: Copy the template folder

First, you should copy the contents from the template folder into a new folder inside [examples](../examples) folder. The folder should be named something that explains what the tutorial is about, and should be in `kebob-case-like-this`. This new folder should now have a `client` folder, a `server` folder, and a `README.md` file.


## Step 2: Choose your technology stack

You will see a variety of options under for both client and server side code examples under the [client](./template/client) and [server](./template/server) folders. Currently we offer three template types for your client side code, and a NodeJS with Express example for server side code. Most examples will only require client side code.

### Picking your client side code

You can feel free to include multiple solutions in your example. If you want to show how someone can achieve functionality with both React and with native javascript, go for it!

If you are building a code example using javascript without needing a build (you are not importing libraries from NPM and are not using any styling libraries like SASS), you can use the [client/javascript](./template/client/javascript).

If you are going to be writing an example that doesn't include React, but does include the need to import libraries or SASS files or other procedures that require the client to be build, you can use the [client/javascript-with-build](./templates/client/javascript-with-build) folder.

Finally, if you are making an example with React, use the [client/react](./templates/client/react) folder.

**Delete any folders in client you are not going to use!**

### Picking your server side code

Most tutorials, won't need a server. **If you do not need a server, please delete the whole server folder from your example.** If you do need a server, we currently only have a [server/nodejs-express](./template/server/nodejs-express) template. If you wish to make a PR to these templates for common languages like Java, Python or Ruby, PRs are welcome!

## Step 3: Updating the README.md

There are instructions inside the [README.md](./template/README.md) file on updates needed to that file. The sections under `## Running Locally` will be handled in Step 4.

## Step 4: Follow the TEMPLATE_INSTRUCTIONS.md for each client and server template you have not deleted

Each of the folders inside client and server will have their own `TEMPLATE_INSTRUCTIONS.md` file with specific instructions on how to update the example to match the specific needs brought to bore by the technologies chosen.