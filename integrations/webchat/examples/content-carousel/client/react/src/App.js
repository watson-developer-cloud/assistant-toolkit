import React from 'react';
import { WebChatContainer } from '@ibm-watson/assistant-web-chat-react';
import { config } from './config';
import './App.scss';
import { ContentCarousel } from './ContentCarousel';

/**
 * This example uses the `@ibm-watson/assistant-web-chat-react` library to import web chat into a React application.
 *
 * See https://www.npmjs.com/package/@ibm-watson/assistant-web-chat-react.
 */

function App() {
  return <WebChatContainer renderUserDefinedResponse={renderUserDefinedResponse} config={config} />;
}

/**
 * This is the callback function that will render user defined responses. It will look at the "user_defined_type" message
 * property to determine what user defined response to render and return the appropriate component.
 */
function renderUserDefinedResponse(event, webChatInstance) {
  const { message } = event.data;
  // The "user_defined_type" property is just an example. It is not required or you can use any other property or
  // condition you want here. This makes it easier to handle different response types if you have more than one
  // user defined response type.
  if (message.user_defined && message.user_defined.user_defined_type === 'carousel') {
    return <ContentCarousel message={message} webChatInstance={webChatInstance} />;
  }
  return null;
}

export default App;
