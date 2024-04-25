import { Accordion, AccordionItem } from '@carbon/react';

/**
 * This is the component for our custom response. It just renders an Accordion from Carbon. You can pass the message
 * to this component and use the data from the message to render this if you want the content to be provided by the
 * assistant.
 */
function CustomAccordion() {
  const onClick = (index) => {
    console.log(`You toggled item ${index}`);
  };

  return (
    <div className="accordion-container">
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
    </div>
  );
}

export { CustomAccordion };
