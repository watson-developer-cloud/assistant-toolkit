# Coding Standards

To ensure a consistent coding style across the repo, we will enforce a relatively strict standard. We are using 
ESLint and Prettier to enforce most of the coding standards on the repo. Please ensure you have ESLint enabled in 
whatever editor you are using. You can also run the linter using `npm run lint` in the root directory. However, 
there are a number of items that are not covered by those tools and need to be enforced manually. Those rules are 
noted below. The base of our standard is the [Airbnb JavaScript Style Guide](https://github.com/airbnb/javascript) 
but there are a few deviations.

You should install the [ESLint plugin]((https://github.com/Microsoft/vscode-eslint)) for VSCode. For IntelliJ IDEA or
WebStorm, the plugin is installed by default but is disabled and simply needs to be enabled.

## [Comments](#comments)

### [File comments](#file-commments)

All files should begin with a jsdoc block comment (`/**...*/`) that explains the general purpose of the file.

### [Comment Length](#comment-length)

Our prettier settings wrap at the 120 character boundary but it does not handle comments. Comments should wrap the same
at the 120 character margin.

Your editor may help you with this. For VSCode, you can do this with the [Rewrap](https://github.com/stkb/Rewrap) plugin.
For IntelliJ IDEA or WebStorm, you can enable wrapping as you type by enabling the "Wrap on typing" and "Comments / Wrap at right margin"
code style settings. To get re-wrapping for existing comments, you'll need a plugin such as 
[Wrap to Column](https://plugins.jetbrains.com/plugin/7234-wrap-to-column/). 

### [Capitalize Comments](#capitalize-comments)

Comments should begin with a capital letter and end with a period. Comments in JSDoc should be full sentences.

Correct code:
```
// This is a good comment.
```

### [Multi-line Comments](#multi-line-comments)

The airbnb rules don't really cover JSDoc and the rules about multi-line comments don't actually take proper JSDoc into 
account so we deviate from the multi-line comment rules with the following.

Structural items such as classes, interfaces, function, methods, enums, class properties, interface properties, etc. 
should be documented using JSDoc. JSDoc comments are multi-line and use the `/**...*/` form. This will allow you to
use the structured elements of JSDoc such as `@param` or `@returns` tags.

Comments within the body of code should always use line comments (`//`). Do not use JSDoc or block comments (`/*..*/`)
in code such as within the body of a function (unless documenting something like an inner-function).

Correct code:
```
/**
 * This is a JSDoc comment for this function.
 */
function doSomething() {
  // This is a multi-line comment inside a function.
  // Despite this example, multi-line comments should wrap at 120 characters.
  console.log('Hello!');
}
```

### [JSDoc spacing](#jsdoc-spacing)

For JSDoc on functions or properties, there should be a blank line above the JSDoc unless it's the first item in a block
and there should be no blank line between the JSDoc and the item it's documenting.

Correct code:
```
interface SomeInterface {
  /**
   * This is the first property.
   */
  property1;

  /**
   * This is the second property.
   */
  property2;
}
```

There should be a blank line between the JSDoc comment and the first `@` tag and no blank lines after that. Also do not
add spacing to indent or align parts of the comments in the `@` tags.

Correct code:
```
/**
 * This function does some work.
 *
 * @param argument1 This is the first argument. If the comment is too long that
 * it wraps at 120 characters, do not indent.
 * @param longerArgument This is the second argument.
 */
function someFunction(argument1: boolean, longerArgument: boolean) {
}
```

### [All-or-nothing `@param` tags](#all-param-tags)

When documenting functions, there is an all-or-nothing rule for `@param` tags. You are not required to document every
argument of every function but when you do document a argument, you should document all the arguments, even the ones
that are obvious.

# [Naming](#naming)

## [Variable naming (already in airbnb)](#variable-naming)

Don't use single letter variables. This is already in the airbnb standard. You should also
avoid unnecessary abbreviations. While some may be common and most people will be familiar with them, not everyone will 
and using a whole word will be clear to everyone. This means don't use `e`, `err`, `w`, `res`,
`req`, `arr`, etc. Just use `error` / `event`, `widget`, `response` / `result`, `request`, etc. We're not being paid to be 
frugal with bytes.

## [Code](#code)

### [Exports](#exports)

Don't export individual items. Put all exports in a single block at the bottom.

```
function foo1() {
}

function foo2() {
}

function foo3() {
}

export default foo1;
export { foo2, foo3 };
```