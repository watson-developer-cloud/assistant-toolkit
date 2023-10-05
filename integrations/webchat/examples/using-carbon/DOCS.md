Carbon is IBM's open source design system for products and digital experiences. With the IBM Design Language as its foundation, the system consists of working code, design tools and resources, human interface guidelines, and a vibrant community of contributors. You can find more information on the [Carbon website](https://v10.carbondesignsystem.com/). Note that web chat is currently using version 10 of Carbon. You may experience problems if you try to use Carbon 11 with web chat.

# JavaScript

This example demonstrates how to use Carbon using their web components version. This version installs custom HTML elements into the document that load Carbon behavior when you use them. All you need to do is use the appropriate HTML tags which you can find in the documentation for the components you wish to use.

We are going to use a custom response to display a static Carbon accordion panel. The first step is to register a custom response handler with web chat.

```javascript
instance.on({
  type: 'customResponse',
  handler: (event, instance) => {
    // The "user_defined_type" property is just an example; it is not required. You can use any other property or
    // condition you want here. This makes it easier to handle different response types if you have more than
    // one custom response type.
    if (event.data.message.user_defined && event.data.message.user_defined.user_defined_type === 'accordion') {
      accordionHandler(event, instance);
    }
  },
});
```

Then provide a function that renders the accordion using the web components HTML tags.

```javascript
function accordionHandler(event) {
  const { element } = event.data;

  // Just set the inner HTML of the custom element to the HTML expected by Carbon. The custom HTML elements as part
  // of the web components will cause Carbon to attach JavaScript behavior to the elements. This behavior is enabled
  // by the import from carbon-web-components at the top of this file.
  element.innerHTML = `
    <bx-accordion id="accordion">
      <bx-accordion-item title-text="The Everyday Card">
        $300 bonus plus 5% gas station cash back offer. Earn 2% cash back on all other purchases.
      </bx-accordion-item>
      <bx-accordion-item title-text="The Preferred Card">
        $300 bonus plus 5% gas station cash back offer. Earn 5% cash back on all other purchases.
      </bx-accordion-item>
      <bx-accordion-item title-text="The Topaz Card">
        $90 Annual fee. Earn 120,000 bonus points. Earn additional points on every purchase.
      </bx-accordion-item>
    </bx-accordion>
  `;

  // This code shows how to use custom event listeners on custom elements. Here we look up the accordion items we
  // added above and then add a listener for the "bx-accordion-item-toggled" event to each.
  const items = element.querySelectorAll('bx-accordion-item');
  items.forEach((item, index) => {
    item.addEventListener('bx-accordion-item-toggled', () => {
      console.log(`You toggled item ${index}`);
    });
  });
}
```

# React

This example demonstrates how to use Carbon using their React version. Using the `WebChatContainer` component from the `assistant-web-chat-react` library, register a listener for custom events.

```javascript
function App() {
  return <WebChatContainer renderCustomResponse={renderCustomResponse} config={config} />;
}

function renderCustomResponse(event) {
  const { message } = event.data;
  // The "user_defined_type" property is just an example. It is not required or you can use any other property or
  // condition you want here. This makes it easier to handle different response types if you have more than one
  // custom response type.
  if (message.user_defined && message.user_defined.user_defined_type === 'accordion') {
    return <CustomAccordion />;
  }
  return null;
}
```

Then create the component for rendering your custom response that uses the Carbon Accordion components.

```javascript
function CustomAccordion() {
  const onClick = (index) => {
    console.log(`You toggled item ${index}`);
  };

  return (
    <Accordion>
      <AccordionItem title="The Everyday Card" onClick={() => onClick(0)}>
        $300 bonus plus 5% gas station cash back offer. Earn 2% cash back on all other purchases.
      </AccordionItem>
      <AccordionItem title="The Preferred Card" onClick={() => onClick(1)}>
        $300 bonus plus 5% gas station cash back offer. Earn 5% cash back on all other purchases.
      </AccordionItem>
      <AccordionItem title="The Topaz Card" onClick={() => onClick(2)}>
        $90 Annual fee. Earn 120,000 bonus points. Earn additional points on every purchase.
      </AccordionItem>
    </Accordion>
  );
}
```

Note: This document is in a temporary location until this content is moved into the [main IBM watsonx Assistant documentation](https://cloud.ibm.com/docs/watson-assistant?topic=watson-assistant-web-chat-overview) tutorials section.

For complete working code see [the example in our GitHub repository](https://github.com/watson-developer-cloud/assistant-toolkit/tree/master/integrations/webchat/examples/using-carbon).