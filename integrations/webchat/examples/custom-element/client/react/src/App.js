import React from 'react';
import './App.css';
import { WebChatCustomElement } from '@ibm-watson/assistant-web-chat-react';
import { config } from './config';

/**
 * This example uses the `@ibm-watson/assistant-web-chat-react` library to import web chat into a React application.
 *
 * See https://www.npmjs.com/package/@ibm-watson/assistant-web-chat-react.
 */

function App() {
  return <WebChatCustomElement className="WebChatContainer" config={config} />;
}

export default App;
