# Contributing

We welcome contributions that show ways to extend Watson Assistant with a custom extension.

Please [review the rules for contributing to this repository](../../CONTRIBUTING.md) before submitting code for information about legal requirements, project start process and pull request process.

If you want to create a new starter kit, please create an issue in this repository to prevent duplicating work the IBM team is already working on and to ensure that your starter kit will be approved before you get started on your work.

## Requirements
Each starter kit should include:

- Instructions on how to set up starter kit in a README.md document
- Instructions on how to tailor starter kit for a specific use case/example
- Appropriate Actions file(s) and OpenAPI spec(s) to allow someone to run the example(s) in their Watson Assistant instance. These JSON files should be properly formatted using a beautification tool.
- Basic screenshot/gif/video of what the starter kit is showing

## Structure
Some starter kits are for a particular service. These starter kits should be houses in an appropiately named folder. There are also several starter kits that are based on a topic like [language-model-conversational-search](./starter-kits/language-model-conversational-search/) that contain integrations with multiple providers. Please consider if your starter kit belongs in an existing topical starter kit or if it should be standalone.

Some starter kits have multiple examples, some for basic use cases and some more advanced. Please make use of a `basic` and `advanced` folder in these instances.

If you have additional assets (images and the like), please put them in an `assets` folder.

All files for Actions should be called `your-starter-kit-name.actions.json`. All files for OpenAPI specs should be called `your-starter-kit-name.openapi.json`. If you have multiple files, simply add a post-fix like `your-starter-kit-name-my-use-case.xxxx.json`.

For any example code, either NodeJS or Python is preferred.