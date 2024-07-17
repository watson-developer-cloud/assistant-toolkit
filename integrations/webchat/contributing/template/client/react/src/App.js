import './App.css';
import React, { useState } from 'react';
import { WebChatContainer } from '@ibm-watson/assistant-web-chat-react';
import { config } from './config';

/**
 * This example uses the `@ibm-watson/assistant-web-chat-react` library to import web chat into a React application.
 *
 * See https://www.npmjs.com/package/@ibm-watson/assistant-web-chat-react.
 *
 * TODO: ADD A COMMENT HERE ABOUT HOW TO FIND THE SPECIAL CODE YOU HAVE ADDED FOR THIS EXAMPLE.
 */

function App() {
  // If you need access to the instance later, you can set it using this.
  // TODO: Remove this if not needed.
  const [instance, setInstance] = useState(null);

  // You can also provide a custom onBeforeRender if you need to call instance method to enable customizations for
  // web chat before it is rendered.
  return (
    <>
      <div className="App">Hello World!</div>
      <WebChatContainer
        renderUserDefinedResponse={renderUserDefinedResponse}
        onBeforeRender={setInstance}
        config={config}
      />
      ;
    </>
  );
}

// TODO: Remove this if not needed.
/**
 * This is the callback function that will render user defined responses. It will look at the "user_defined_type" message
 * property to determine what user defined response to render and return the appropriate component.
 */
function renderUserDefinedResponse(event) {
  const { message } = event.data;
  // The "user_defined_type" property is just an example. It is not required or you can use any other property or
  // condition you want here. This makes it easier to handle different response types if you have more than one
  // user defined response type.
  if (message.user_defined && message.user_defined.user_defined_type === 'my_custom_type') {
    return <div>Insert custom component here!</div>;
  }
  return null;
}

export default App;
