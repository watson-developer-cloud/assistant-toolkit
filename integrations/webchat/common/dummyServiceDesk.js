/**
 * This file contains an implementation of a fake service desk using the web chat interface for a custom service desk.
 *
 * The repo https://github.com/watson-developer-cloud/assistant-web-chat-service-desk-starter contains information
 * on creating your own service desk integration.
 */

const MOCK_AGENT_PROFILE_JONATHAN = {
  id: 'Jonathan-id',
  nickname: 'Jonathan',
};

class DummyServiceDesk {
  constructor(callback) {
    this.callback = callback;
  }

  /**
   * Returns the name of this service desk.
   */
  getName() {
    return 'DummyServiceDesk';
  }

  /**
   * Instructs the service desk to start a new chat. This should be called immediately after the service desk
   * instance has been created. It will make the appropriate calls to the service desk and begin communicating back
   * to the calling code using the callback produce to the instance. This may only be called once per instance.
   */
  startChat() {
    this.toldJoke = false;
    setTimeout(async () => {
      await sleep(2000);
      this.callback.agentJoined(MOCK_AGENT_PROFILE_JONATHAN);
      await sleep(2000);
      this.callback.agentTyping(true);
      await sleep(2000);
      this.callback.agentTyping(false);
      const message =
        'Hello! I am a virtual assistant pretending to be a human! In this view, you would normally interact with a' +
        ' human agent from a service desk but for the purposes of this example, I am very limited in what I can say.';
      await this.callback.sendMessageToUser(createMessageResponseForText(message), MOCK_AGENT_PROFILE_JONATHAN.id);
      await this.callback.sendMessageToUser(
        createMessageResponseForText('Ask me to tell you a joke.'),
        MOCK_AGENT_PROFILE_JONATHAN.id,
      );
    });
  }

  /**
   * Sends a message to the agent in the service desk.
   */
  sendMessageToAgent(message) {
    setTimeout(async () => {
      const { text } = message.input;
      if (text.toLowerCase().indexOf('joke') !== -1) {
        await sleep(2000);
        this.callback.agentTyping(true);
        await sleep(2000);
        this.callback.agentTyping(false);

        if (this.toldJoke) {
          await this.callback.sendMessageToUser(
            createMessageResponseForText("I'm sorry but I only know one joke. The joke's on me now."),
            MOCK_AGENT_PROFILE_JONATHAN.id,
          );
        } else {
          this.toldJoke = true;
          await this.callback.sendMessageToUser(
            createMessageResponseForText(
              'A neutron walks into a bar and says to the bartender: "How much for a drink?"',
            ),
            MOCK_AGENT_PROFILE_JONATHAN.id,
          );
          await sleep(2000);
          await this.callback.sendMessageToUser(
            createMessageResponseForText('The bartender says: "For you, no charge."'),
            MOCK_AGENT_PROFILE_JONATHAN.id,
          );
        }
      } else {
        await this.callback.sendMessageToUser(
          createMessageResponseForText("I'm sorry, but I can only tell jokes."),
          MOCK_AGENT_PROFILE_JONATHAN.id,
        );
      }
    });
  }

  /**
   * Tells the service desk to terminate the chat.
   */
  endChat() {}

  /**
   * Tells the service desk if a user has started or stopped typing.
   */
  userTyping() {}
}

/**
 * This is an async function that will simply wait for the given amount of time.
 */
async function sleep(milliseconds) {
  await new Promise((resolve) => {
    setTimeout(resolve, milliseconds);
  });
}

/**
 * Generates a MessageResponse for the given text message sent to the user.
 */
function createMessageResponseForText(text) {
  const messageResponse = {
    output: {
      generic: [
        {
          response_type: 'text',
          text,
        },
      ],
    },
  };

  return messageResponse;
}

window.DummyServiceDesk = DummyServiceDesk;
