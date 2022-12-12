import { useCallback, useEffect, useRef, useState } from 'react';
import ReactDOM from 'react-dom';
import { PropTypes } from 'prop-types';
import { PostChatForm } from './forms/PostChatForm';
import { PreChatForm } from './forms/PreChatForm';
import './FormContainer.css';

/**
 * This component is responsible for managing the pre-chat and post-chat forms. When mounted, it will add listeners
 * for the appropriate window events so it can show the forms at the appropriate time.
 */
function FormContainer({ instance }) {
  // This is the resolve function for resolving the promises returned by our open and close event handlers. Those
  // promises will remain unresolved while the pre-chat or post-chat forms are being displayed and they will only be
  // resolved once those forms are closed. Web chat will wait for each promise to be resolved before completing the
  // open or close event.
  const promiseResolveRef = useRef();

  // The name that the user entered into the pre-chat form. We will save this so we can send it when the first message
  // is sent. We also save this in session storage so it'll be available if the user reloads the page and starts a
  // new Watson Assistant session.
  const fullNameRef = useRef(sessionStorage.getItem('FULL_NAME'));

  // This indicates if the user has sent a (non-welcome) message to the assistant. We use this so we only show the
  // post-chat form if the user has actually interacted with the assistant.
  const hasSentMessageRef = useRef(false);

  // The container displays both a pre-chat and post-chat form. This variable tells us which of the two is currently
  // visible (or null for none).
  const [currentPanel, setCurrentPanel] = useState(null);

  // We want to keep track of the window open event when it occurs so we can use it to cancel the opening if the
  // user clicks cancel.
  const windowOpenEvent = useRef();

  // When this component is mounted, add the listeners for the events.
  useEffect(() => {
    // Add listeners that will use a custom panel to display custom content when web chat is opened or closed. You
    // can use window:close instead of window:pre:close but window:pre:close gives you the option of including a
    // cancel button to allow the user to keep web chat open. You can also use the agent start and end chat events
    // to accomplish the same thing when users are interacting with a human agent from a service desk. Using
    // "window:open" allows us to display a custom panel after the web chat window has been opened; with
    // "window:pre:open" web chat will not be open yet and you can't show a custom panel if web chat is closed.
    instance.on({ type: 'window:open', handler: windowOpenHandler });
    instance.on({ type: 'window:pre:close', handler: preCloseHandler });
    // This listener will be used to send the information gathered in the pre-chat form to the assistant.
    instance.on({ type: 'pre:send', handler: preSendHandler });

    instance.customPanels.getPanel().hostElement.classList.add('HostElement');

    return () => {
      instance.off({ type: 'window:open', handler: windowOpenHandler });
      instance.off({ type: 'window:pre:close', handler: preCloseHandler });
      instance.off({ type: 'pre:send', handler: preSendHandler });
    };
  }, [instance]);

  // Update the state of the custom panel based on the "newCurrentPanel" value. It will open or close it as necessary as
  // well as resolve the open promise if there is one.
  function handleCurrentPanel(newCurrentPanel) {
    const customPanel = instance.customPanels.getPanel();
    if (newCurrentPanel) {
      customPanel.open({ hidePanelHeader: true, disableAnimation: true });
    } else {
      // If there is a promise waiting for the custom panel to close, then resolve it now.
      if (promiseResolveRef.current) {
        promiseResolveRef.current();
        promiseResolveRef.current = null;
      }
      customPanel.close();
    }
  }

  // When the current panel/form being shown changes, open or close the custom panel.
  function changePanel(newPanel) {
    setCurrentPanel(newPanel);
    handleCurrentPanel(newPanel);
  }

  // This useEffect will detect when the custom panel changes state and it will open or close it as necessary as
  // well as resolve the open promise if there is one.
  useEffect(() => {
    handleCurrentPanel(currentPanel);
  }, [currentPanel, instance]);

  /**
   * This function is called during the opening process for web chat.
   */
  function windowOpenHandler(event) {
    if (!fullNameRef.current) {
      // Save a reference to this event. We can use this to cancel the open if the user clicks the cancel button.
      windowOpenEvent.current = event;

      // Return a Promise which web chat will wait for before completing the opening process. Our custom code will
      // resolve this Promise after the user completes the pre-chat form. If they click cancel, we can use the event
      // provided here to cancel the open and close web chat.
      return new Promise((resolve) => {
        promiseResolveRef.current = resolve;
        changePanel('pre-chat');
      });
    }
    return null;
  }

  /**
   * This function is called before web chat begins the closing process.
   */
  function preCloseHandler() {
    if (hasSentMessageRef.current && !sessionStorage.getItem('POST_CHAT_SHOWN')) {
      // Set a session variable so we don't show this form again.
      sessionStorage.setItem('POST_CHAT_SHOWN', 'true');

      // Return a Promise which web chat will wait for before completing the closing process. Our custom code will
      // resolve this Promise after the user completes the post-chat form. You could include a cancel button in the
      // form if you wanted to allow the user to go back without actually closing web chat. But this example just
      // lets the user optionally provide feedback.
      return new Promise((resolve) => {
        promiseResolveRef.current = resolve;
        changePanel('post-chat');
      });
    }
    return null;
  }

  /**
   * This handler is called before web chat sends a message to the assistant. We will use this to send the name the
   * user entered when the welcome node is requested.
   */
  function preSendHandler(event) {
    // This code only runs for the initial welcome message, but you could set the variable on any message that is sent
    // to the assistant.
    if (event.data.history && event.data.history.is_welcome_request) {
      // Make sure these objects exist but don't override them if they already do.
      event.data.context.skills['actions skill'] = event.data.context.skills['actions skill'] || {};
      event.data.context.skills['actions skill'].skill_variables =
        event.data.context.skills['actions skill'].skill_variables || {};

      // Set the name that was previously entered by the user.
      event.data.context.skills['actions skill'].skill_variables.User_Name = fullNameRef.current;
    } else {
      // Set this flag for a non-welcome message so we can now show the post-chat form if the user closes the web
      // chat.
      hasSentMessageRef.current = true;
    }
  }

  const onPreChatSubmit = useCallback((fullName) => {
    // Grab the name the user entered so we can use it later.
    fullNameRef.current = fullName;

    // Save the full name in session storage. This will allow us to preserve this value in the event that the page
    // is reloaded and a new session with Watson Assistant is started.
    sessionStorage.setItem('FULL_NAME', fullName);

    changePanel(null);
  }, []);

  const onPreChatCancel = useCallback(() => {
    // Cancel the open event which will cause web chat to close.
    if (windowOpenEvent.current) {
      windowOpenEvent.current.cancelOpen = true;
      windowOpenEvent.current = null;
    }
    changePanel(null);
  }, []);

  const onPostChatSubmit = useCallback((satisfactionRating, feedback) => {
    // Here you would send the feedback to whatever system is appropriate for your application. If you want to send
    // the information to Watson Assistant, you can use the web chat "instance.send" function. Or you can call an
    // endpoint of your own to save this information.
    console.log(`The user selected a rating of "${satisfactionRating}" and optional feedback "${feedback}".`);

    changePanel(null);
  }, []);

  const onPostChangeCancel = useCallback(() => {
    changePanel(null);
  }, []);

  return (
    <CustomPanelPortal hostElement={instance.customPanels.getPanel().hostElement}>
      {currentPanel === 'pre-chat' && <PreChatForm onSubmit={onPreChatSubmit} onCancel={onPreChatCancel} />}
      {currentPanel === 'post-chat' && <PostChatForm onSubmit={onPostChatSubmit} onCancel={onPostChangeCancel} />}
    </CustomPanelPortal>
  );
}

/**
 * This is the component that will attach a React portal to the given host element for a custom panel.
 */
function CustomPanelPortal({ hostElement, children }) {
  return ReactDOM.createPortal(children, hostElement);
}

FormContainer.propTypes = {
  instance: PropTypes.shape({
    customPanels: PropTypes.shape({
      getPanel: PropTypes.func,
    }),
    on: PropTypes.func,
    off: PropTypes.func,
  }),
};

export { FormContainer };
