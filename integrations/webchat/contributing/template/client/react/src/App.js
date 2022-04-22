import './App.css';
import PropTypes from 'prop-types';
import React, { useEffect } from 'react';
import { withWebChat } from '@ibm-watson/assistant-web-chat-react';
import { config } from './config';

/**
 * This example uses the `@ibm-watson/assistant-web-chat-react` library to import web chat into a React application.
 *
 * See https://www.npmjs.com/package/@ibm-watson/assistant-web-chat-react
 *
 * TODO: ADD A COMMENT HERE ABOUT HOW TO FIND THE SPECIAL CODE YOU HAVE ADDED FOR THIS EXAMPLE.
 *
 */

function App({ createWebChatInstance }) {
  useEffect(() => {
    function onWebChatLoad(instance) {
      instance.render();
    }

    // A web chat configuration options object as documented at https://web-chat.global.assistant.watson.cloud.ibm.com/docs.html?to=api-configuration#configurationobject.
    const webChatOptions = {
      ...config,
      onLoad: onWebChatLoad,
    };

    createWebChatInstance(webChatOptions);
  }, []);
  return <div className="App" />;
}

App.propTypes = {
  createWebChatInstance: PropTypes.func.isRequired,
};

export default withWebChat()(App);
