# Contributing to this Repo

If you would like to contribute a new extension starter kit to this repository, we ask that you follow these guidelines.

## Process
1. Fork this repository.
1. In a feature branch, add your starter kit under [../starter-kits/](../starter-kits/).
1. Ensure that you have checked off all the items in the [checklist](#checklist) below.
1. Create a PR to this repo to start the review process.

## Checklist
You should have the following items in your starter kit addition:

- [ ] An uploadable OpenAPI spec without errors
    - [ ] Naming convention: `{extension_name}.openapi.json`
- [ ] An actions skill that demonstrates all operations specified in the OpenAPI spec.
    - [ ] Naming convention: `{extension_name}.actions.json`
    - [ ] Action steps are fully linked with the appropriate extension and parameters.
    - [ ] Step responses use full, user friendly sentences.
- [ ] A `README.md` that follows [this template](./TEMPLATE.md).

See the [HubSpot Starter Kit](../starter-kits/hubspot/) as a general reference for what your starter kit addition should look like. We appreciate and welcome all contributions, and will review the PRs shortly after opening!