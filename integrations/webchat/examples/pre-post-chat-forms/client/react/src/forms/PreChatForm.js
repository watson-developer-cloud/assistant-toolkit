import { Button, Form, SecondaryButton, TextInput } from 'carbon-components-react';
import React, { useCallback, useState } from 'react';
import { PropTypes } from 'prop-types';

/**
 * This is a simple component that asks the user to enter a name.
 */
function PreChatForm({ onSubmit, onCancel }) {
  const [inputValue, setInputValue] = useState('');

  const onChange = useCallback((event) => {
    setInputValue(event.target.value);
  }, []);

  return (
    <div className="PreChatForm ChatForm">
      <div className="ChatForm__Upper">
        <Form>
          <div className="ChatForm__Title">
            <div className="ChatForm__TitleBar" />
            <span className="ChatForm__TitleText">Let&apos;s get started.</span>
          </div>
          <div className="EnterNameForm__Name">
            <TextInput
              value={inputValue}
              id="enter-name-form"
              labelText="Please enter your name"
              placeholder="Required"
              onChange={onChange}
            />
          </div>
        </Form>
      </div>
      <div className="PreChatForm__ButtonContainer">
        <SecondaryButton onClick={onCancel}>Cancel</SecondaryButton>
        <Button
          onClick={() => {
            onSubmit(inputValue.trim());
          }}
          disabled={!inputValue.trim()}
        >
          Start chat
        </Button>
      </div>
    </div>
  );
}

PreChatForm.propTypes = {
  onSubmit: PropTypes.func.isRequired,
  onCancel: PropTypes.func.isRequired,
};

export { PreChatForm };
