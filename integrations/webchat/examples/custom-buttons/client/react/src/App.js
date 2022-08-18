import React, { useCallback, useState } from 'react';
import { WebChatContainer } from '@ibm-watson/assistant-web-chat-react';
import { config } from './config';
import { CustomButtons } from './CustomButtons';

/**
 * This example uses the `@ibm-watson/assistant-web-chat-react` library to import web chat into a React application.
 *
 * See https://www.npmjs.com/package/@ibm-watson/assistant-web-chat-react.
 */

function App() {
  const [instance, setInstance] = useState(null);

  // We only want to allow the most recent custom response message to be enabled. This value keeps track of which
  // message is allowed to show as enabled.
  const [enabledMessage, setEnabledMessage] = useState(null);

  const preSendHandler = useCallback(() => {
    // When the user sends a message, we need to clear the message that is allowed to show enabled messages.
    setEnabledMessage(null);
  }, []);

  const preReceiveHandler = useCallback((event) => {
    const message = event.data;

    // Set this message as the most recent message that is allowed to appear as enabled.
    setEnabledMessage(message);

    // This will convert responses that contain buttons into custom responses so we can render them using custom code.
    if (message.output.generic) {
      message.output.generic.forEach((messageItem) => {
        // Message items that contain buttons have a response type of "option".
        if (messageItem.response_type === 'option') {
          // This will turn this response into a custom response that will be rendered by our custom response handler.
          messageItem.response_type = 'user_defined';
          messageItem.user_defined = { user_defined_type: 'custom_buttons' };
        }
      });
    }
  }, []);

  const onBeforeRender = useCallback((instance) => {
    setInstance(instance);

    // Make sure that we disable the buttons each time the user sends a message.
    instance.on({ type: 'pre:send', handler: preSendHandler });

    // We will need to listen for the "pre:receive" event so we can convert the buttons (options) responses into
    // custom responses.
    instance.on({ type: 'pre:receive', handler: preReceiveHandler });
  });

  const renderCustomResponse = useCallback((event) => {
    return doRenderCustomResponse(event, enabledMessage, instance);
  });

  return (
    <WebChatContainer renderCustomResponse={renderCustomResponse} config={config} onBeforeRender={onBeforeRender} />
  );
}

/**
 * This is the callback function that will render custom responses. It will look at the "user_defined_type" message
 * property to determine what custom response to render and return the appropriate component.
 */
function doRenderCustomResponse(event, enabledMessage, instance) {
  const { message, fullMessage } = event.data;
  // The "user_defined_type" property is just an example. It is not required or you can use any other property or
  // condition you want here. This makes it easier to handle different response types if you have more than one
  // custom response type.
  if (message.user_defined && message.user_defined.user_defined_type === 'custom_buttons') {
    return (
      <CustomButtons
        message={message}
        fullMessage={fullMessage}
        disabled={fullMessage !== enabledMessage}
        instance={instance}
      />
    );
  }
  return null;
}

export default App;
