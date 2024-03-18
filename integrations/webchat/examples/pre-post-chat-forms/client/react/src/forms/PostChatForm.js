import React, { useCallback, useState } from 'react';
import { Button, Form, SecondaryButton, TextArea } from '@carbon/react';
import {
  FaceDissatisfied,
  FaceDissatisfiedFilled,
  FaceNeutral,
  FaceNeutralFilled,
  FaceSatisfied,
  FaceSatisfiedFilled,
} from '@carbon/react/icons';
import { PropTypes } from 'prop-types';

/**
 * The form used for asking the user how their chat was.
 */
function PostChatForm({ onSubmit, onCancel }) {
  const [inputValue, setInputValue] = useState('');
  const [satisfactionRating, setSatisfactionRating] = useState(0);

  const isDissatisfiedSelected = satisfactionRating === 1;
  const isNeutralSelected = satisfactionRating === 2;
  const isSatisfiedSelected = satisfactionRating === 3;

  const faceSatisfied = isSatisfiedSelected ? <FaceSatisfiedFilled size={32} /> : <FaceSatisfied size={32} />;
  const faceNeutral = isNeutralSelected ? <FaceNeutralFilled size={32} /> : <FaceNeutral size={32} />;
  const faceDissatisfied = isDissatisfiedSelected ? (
    <FaceDissatisfiedFilled size={32} />
  ) : (
    <FaceDissatisfied size={32} />
  );

  const onChange = useCallback((event) => {
    setInputValue(event.target.value);
  }, []);

  return (
    <div className="PostChatForm ChatForm">
      <Form>
        <div className="ChatForm__Upper">
          <div className="ChatForm__Title">
            <span className="ChatForm__TitleText">
              See you next time.
              <br />
              How did we do?
            </span>
          </div>
          <div className="PostChatForm__RatingButtonsContainer">
            <Button className="PostChatForm__RatingButton" kind="ghost" onClick={() => setSatisfactionRating(3)}>
              {faceSatisfied}
            </Button>
            <Button className="PostChatForm__RatingButton" kind="ghost" onClick={() => setSatisfactionRating(2)}>
              {faceNeutral}
            </Button>
            <Button className="PostChatForm__RatingButton" kind="ghost" onClick={() => setSatisfactionRating(1)}>
              {faceDissatisfied}
            </Button>
          </div>
          <TextArea
            className="PostChatForm__FeedbackOptional"
            id="feedback-text"
            labelText="If you have any more additional feedback, please write it here. (optional)"
            onChange={onChange}
            rows={3}
          />
        </div>
      </Form>
      <div className="PreChatForm__ButtonContainer">
        <SecondaryButton onClick={onCancel}>Close</SecondaryButton>
        <Button
          onClick={() => {
            onSubmit(satisfactionRating, inputValue.trim());
          }}
          disabled={satisfactionRating === 0}
        >
          Submit feedback
        </Button>
      </div>
    </div>
  );
}

PostChatForm.propTypes = {
  onSubmit: PropTypes.func.isRequired,
  onCancel: PropTypes.func.isRequired,
};

export { PostChatForm };
