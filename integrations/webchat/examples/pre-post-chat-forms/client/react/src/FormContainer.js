import { useCallback, useEffect, useRef, useState } from 'react';
import ReactDOM from 'react-dom';
import { PropTypes } from 'prop-types';
import { PostChatForm } from './forms/PostChatForm';
import { PreChatForm } from './forms/PreChatForm';
import './FormContainer.css';

/**
 * This component is responsible for managing the pre-chat and post-chat forms. When mounted, it will add listeners
 * for the appropriate view change events so it can show the forms at the appropriate time.
 */
function FormContainer({ instance }) {
  // This is the resolve function for resolving the promises returned by our open and close event handlers. Those
  // promises will remain unresolved while the pre-chat or post-chat forms are being displayed and they will only be
  // resolved once those forms are closed. Web chat will wait for each promise to be resolved before completing the
  // view change event.
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

  // We want to keep track of the view change event when it occurs so we can use it to cancel the change if the
  // user clicks cancel.
  const viewChangeEvent = useRef();

  // When this component is mounted, add the listeners for the events.
  useEffect(() => {
    // Add a change listener to handle the pre-chat form. Using the change listener allows us to show the pre-chat
    // form after the main window has opened. You can use the agent:pre:startChat event to accomplish the
    // same thing when the user is interacting with an agent.
    instance.on({ type: 'view:change', handler: viewChangeHandler });

    // Add a pre-change listener to handle the post-chat form. Using the pre-change listener allows us to show the
    // post-chat form before the main window has actually closed. You can use the agent:pre:endChat event to accomplish
    // the same thing when the user is interacting with an agent.
    instance.on({ type: 'view:pre:change', handler: viewPreChangeHandler });

    // This listener will be used to send the information gathered in the pre-chat form to the assistant.
    instance.on({ type: 'pre:send', handler: preSendHandler });

    instance.customPanels.getPanel().hostElement.classList.add('HostElement');

    return () => {
      instance.off({ type: 'view:change', handler: viewChangeHandler });
      instance.off({ type: 'view:pre:change', handler: viewPreChangeHandler });
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
   * This function is called after a view change has occurred.
   */
  function viewChangeHandler(event) {
    // If we haven't yet gotten a name from the user and the main window is opening, then show a pre-chat form.
    const mainWindowOpening = !event.oldViewState.mainWindow && event.newViewState.mainWindow;
    if (!fullNameRef.current && mainWindowOpening) {
      // Save a reference to this event. We can use this to cancel the open if the user clicks the cancel button.
      viewChangeEvent.current = event;

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
   * This function is called before a view change has occurred.
   */
  function viewPreChangeHandler(event) {
    // If the launcher is opening, the user has sent a message, and we have not asked the user for feedback, then
    // display a post-chat form. This will cover the case where web chat is minimized from either the main window or from
    // a tour.
    const launcherOpening = !event.oldViewState.launcher && event.newViewState.launcher;
    const tourOrMainWindowOpen = event.oldViewState.mainWindow || event.oldViewState.tour;
    if (
      launcherOpening &&
      tourOrMainWindowOpen &&
      hasSentMessageRef.current &&
      !sessionStorage.getItem('POST_CHAT_SHOWN')
    ) {
      // Set a session variable so we don't show this form again.
      sessionStorage.setItem('POST_CHAT_SHOWN', 'true');

      // To show the pre-chat form, we need to make sure that only the main window is open. This may have the effect
      // of cancelling an event closing the main window. Then the user can fill out the form and the form will
      // need to issue a second view change to close to the launcher. We won't use a Promise to wait in this case.
      Object.keys(event.newViewState).forEach((key) => {
        event.newViewState[key] = false;
      });
      event.newViewState.mainWindow = true;

      changePanel('post-chat');
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
      // Note: If you are using a dialog skill instead of an actions skill, then replace "actions skill" with
      // "main skill" and replace "skill_variables" with "user_defined".
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
    if (viewChangeEvent.current) {
      viewChangeEvent.current.cancelViewChange = true;
      viewChangeEvent.current = null;
    }
    changePanel(null);
  }, []);

  const onPostChatSubmit = useCallback((satisfactionRating, feedback) => {
    // Here you would send the feedback to whatever system is appropriate for your application. If you want to send
    // the information to Watson Assistant, you can use the web chat "instance.send" function. Or you can call an
    // endpoint of your own to save this information.
    console.log(`The user selected a rating of "${satisfactionRating}" and optional feedback "${feedback}".`);

    changePanel(null);

    // Once the user has completed the form, then close everything to the launcher.
    instance.changeView('launcher');
  }, []);

  const onPostChangeCancel = useCallback(() => {
    changePanel(null);
    // Once the user has completed the form, then close everything to the launcher.
    instance.changeView('launcher');
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
    changeView: PropTypes.func,
  }),
};

export { FormContainer };
