/* eslint-disable react/forbid-prop-types */
import { Button } from 'carbon-components-react';
import cx from 'classnames';

import './CustomButtons.css';
import { useState } from 'react';
import { PropTypes } from 'prop-types';

/**
 * This is the component for our custom response. It just renders an Accordion from Carbon. You can pass the message
 * to this component and use the data from the message to render this if you want the content to be provided by the
 * assistant.
 */
function CustomButtons({ message, fullMessage, disabled, instance }) {
  return (
    <div className="CustomButtons">
      {message.options.map((messageItem, index) => {
        return (
          <CustomButton
            key={messageItem.label}
            messageItem={messageItem}
            fullMessage={fullMessage}
            disabled={disabled}
            instance={instance}
            index={index}
          />
        );
      })}
    </div>
  );
}

/**
 * This renders an individual button.
 */
function CustomButton({ messageItem, fullMessage, disabled, instance, index }) {
  const { label } = messageItem;

  // This "selectedIndex" value is something we set in session history when the user clicks the button. Checking
  // it here means we can reload the selected state for the button even after the user reloads the page and
  // the conversation is loaded from history.
  const [isSelected, setIsSelected] = useState(
    fullMessage.history && fullMessage.history.user_defined && fullMessage.history.user_defined.selectedIndex === index,
  );

  return (
    <Button
      className={cx('CustomButton', { 'CustomButton--selected': isSelected })}
      onClick={() => {
        // Mark the button as selected.
        setIsSelected(true);

        // Send a message to the assistant with the label of the button. You can also add "{ silent: true }" as the second
        // argument if you don't want this message to appear as a "sent" bubble from the user. See
        // https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-instance-methods for more
        // information about the instance functions.
        instance.send({ input: { text: label } });

        // Send an update event to the assistant. This will update session history for this message so it remembers what
        // button was clicked.
        instance.updateHistoryUserDefined(fullMessage.id, { selectedIndex: index });
      }}
      disabled={disabled}
    >
      {label}
    </Button>
  );
}

CustomButtons.propTypes = {
  message: PropTypes.object.isRequired,
  fullMessage: PropTypes.object.isRequired,
  disabled: PropTypes.bool.isRequired,
  instance: PropTypes.object.isRequired,
};

CustomButton.propTypes = {
  messageItem: PropTypes.object.isRequired,
  fullMessage: PropTypes.object.isRequired,
  disabled: PropTypes.bool.isRequired,
  instance: PropTypes.object.isRequired,
  index: PropTypes.number.isRequired,
};

export { CustomButtons };
