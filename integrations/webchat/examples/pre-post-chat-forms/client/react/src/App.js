import React, { useState } from 'react';
import { Button } from '@carbon/react';
import { WebChatContainer } from '@ibm-watson/assistant-web-chat-react';
import { config } from './config';
import './App.scss';
import { FormContainer } from './FormContainer';

/**
 * This example uses the `@ibm-watson/assistant-web-chat-react` library to import web chat into a React application.
 *
 * See https://www.npmjs.com/package/@ibm-watson/assistant-web-chat-react.
 */

function App() {
  const [instance, setInstance] = useState(null);

  return (
    <>
      <p>Click this button to clear session information and restart the example.</p>
      <Button onClick={onRestart}>Restart</Button>
      <WebChatContainer config={config} onBeforeRender={setInstance} />
      {instance && <FormContainer instance={instance} />}
    </>
  );
}

/**
 * Clears the session and reloads the browse so we can get a clean state.
 */
function onRestart() {
  sessionStorage.clear();
  window.location.reload();
}

export default App;
