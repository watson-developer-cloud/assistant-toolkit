# Test it ALL

## Goal
This is an example of all the features supported by Watson Assistant when using an Extension.
The OpenApi specification is vast, and hence not all of its features are supported Watson Assistant.
This example describes all the features supported and provides means to test them out.
It is expected that this example will grow over time as more features are supported.

## OpenApi Spec
Features supported:
- Dynamic Base Path in url
- Security: Basic, Bearer, Api key
- Parameters: query, header, path
- Accept Header ( not fully supported via WA Tooling)
- Error handling - when an extension errors
- Timeout handling - when an extension takes more than 30 seconds to execute
- Arrays - both in request and response



## WA Actions Model
The example provides a set of actions to test the specification

## Supporting Server
We provide a very basic node.js server that supports the api described in the open api spec

