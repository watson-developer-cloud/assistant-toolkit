Note: This document is in a temporary location until this content is moved into the [main IBM watsonx Assistant documentation](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-overview) tutorials section.

To display information from an extension in a custom response, follow these steps. The extension used in this tutorial is an API provided by the National Bank of Poland that allows you to obtain the currency exchange rate between a currency of your choosing and the Polish złoty.

This tutorial will make use of two different custom responses. The first custom response will display a custom card that will ask the user to select a currency and an amount that is to be exchanged. When the user clicks the "Exchange" button, it will send a message to the assistant with the values entered. The assistant will call the extension which will obtain the exchange rate and then it will calculate the resulting exchange amount and return that to web chat which will display the result in a second custom response.

This tutorial does not take session history into account. If the user reloads the page containing web chat, the custom responses that asked the user for input will be empty. If you want to save this information in the session history, take a look at the [custom buttons tutorial](https://github.com/watson-developer-cloud/assistant-toolkit/tree/master/integrations/webchat/examples) that demonstrates how to store state. (TODO: Maybe link to the tutorial page in the WA docs instead of the GH page?)

1. Set up the extension using these instructions: [Building a custom extensions](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-build-custom-extension)
2. The first action will simply return information to trigger the custom response asking the user for the currency and amount to be exchanged. Use the JSON editor to send a `user_defined` response to web chat.
```json
{
  "generic": [
    {
      "user_defined": {
        "user_defined_type": "currency_exchange_input"
      },
      "response_type": "user_defined"
    }
  ]
}
```
4. Register a listener for the custom response event. When web chat receives a `user_defined` response from the assistant, it will fire this event.
```javascript
instance.on({ type: 'customResponse', handler: customResponseHandler });
```
Create a custom response handler that will show the correct custom response.
```javascript
function customResponseHandler(event) {
  if (event.data.message.user_defined && event.data.message.user_defined.user_defined_type === 'currency_exchange_input') {
    currencyInputHandler(event, instance);
  } else if (event.data.message.user_defined && event.data.message.user_defined.user_defined_type === 'currency_exchange_output') {
    currencyOutputHandler(event, instance);
  }
}
```
5. Create the `currencyInputHandler` function that will display a custom card that asks the user to enter a currency and amount to be exchanged.
```javascript
function currencyInputHandler(event, instance) {
  const {element} = event.data;

  element.innerHTML = `
    <div class="CurrencyCard">
      <p>What currency would you like to exchange from?</p>
      <div class="CurrencyCard__CurrencyButtons">
        <button type="button" class="CurrencyCard__CurrencyButton bx--tag" data-currency="eur">${currencyLabel('eur')}</button>
        <button type="button" class="CurrencyCard__CurrencyButton bx--tag" data-currency="gbp">${currencyLabel('gbp')}</button>
        <button type="button" class="CurrencyCard__CurrencyButton bx--tag" data-currency="usd">${currencyLabel('usd')}</button>
        <button type="button" class="CurrencyCard__CurrencyButton bx--tag" data-currency="jpy">${currencyLabel('jpy')}</button>
      </div>
      <div class="bx--form-item bx--text-input-wrapper">
      <p>Choose an amount</p>
        <div class="bx--text-input__field-outer-wrapper">
          <div class="bx--text-input__field-wrapper">
            <input type="text" class="CurrencyCard__AmountInput bx--text-input">
          </div>
        </div>
      </div>
      <div class="CurrencyCard__ExchangeButtonContainer">
        <button type="button" class="CurrencyCard__ExchangeButton bx--btn bx--btn--primary" disabled>Exchange</button>
      </div>
    </div>
  `;
  ...
}
```
See the linked example for details how to attach event listeners to the card so it can respond to user interaction.
6. Create a listener for the "Exchange" button which when clicked will send a message to the assistant that contains the information the user has entered. This will set the information as context variables which will then be available to the actions.
```javascript
const message = {
  context: {
    skills: {
      'actions skill': {
        skill_variables: {
          'input_amount': Number(amountToExchange),
          'input_currency': selectedCurrency,
        },
      },
    },
  },
  input: {
    // This is the text the assistant will see.
    text: `DO_EXCHANGE_CALCULATION`,
  },
  history: {
    // This is the label the user will see.
    label: `What is ${amount} ${currencyLabel(selectedCurrency)} in złoty?`,
  }
};
instance.send(message);
```
7. In the second action, call the extension and assign the data from the extension to a session variable. You can find documentation for this at [Accessing extension response data](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-call-extension#extension-access-response). This second action should also return a custom response with a slightly different type to trigger a different card to be display to the user.
```json
{
  "generic": [
    {
      "user_defined": {
        "user_defined_type": "currency_exchange_output"
      },
      "response_type": "user_defined"
    }
  ]
}
```
8. Create a second `currencyOutputHandler` function for displaying the results of the extension.
```javascript
function currencyOutputHandler(event) {
  const { element, fullMessage } = event.data;
  const inputCurrency = fullMessage.context.skills['actions skill'].skill_variables.input_currency;
  const inputAmount = fullMessage.context.skills['actions skill'].skill_variables.input_amount;
  const outputAmount = fullMessage.context.skills['actions skill'].skill_variables.output_amount;
  element.innerHTML = `
    <div class="CurrencyCard">
      <div class="CurrencyCard__ResultInput">
        ${inputAmount} ${currencyLabel(inputCurrency)} equals
      </div>
      <div class="CurrencyCard__ResultOutput">
        ${outputAmount} zł
      </div>
      <div class="CurrencyCard__ResultDivider"></div>
    </div>
  `;
}
```

For complete working code see [the example in our GitHub repository](https://github.com/watson-developer-cloud/assistant-toolkit/tree/master/integrations/webchat/examples/custom-response).