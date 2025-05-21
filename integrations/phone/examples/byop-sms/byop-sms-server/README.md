# Bring Your Own Provider (BYOP) SMS Test Server

## Overview

This repository provides a lightweight Express server for testing Bring Your Own Provider (BYOP) SMS integrations. You can configure your BYOP integration to send messages to this server by setting its endpoint URL accordingly.

The server listens for incoming messages at:

```

http://localhost:8080/byop-sms

````

When a message is received, the server sends a simulated user SMS message to the specified webhook URL.

## Getting Started

**Requirements**
- Latest version of [Node.js](https://nodejs.org/en/about/previous-releases)

### Clone the Repository

```bash
git clone https://github.com/watson-developer-cloud/assistant-toolkit.git
````

### Launch the Server Locally

1. Navigate to the server directory:

   ```bash
   cd assistant-toolkit/integrations/phone/examples/byop-sms/byop-sms-server
   ```

2. Install the dependencies:

   ```bash
   npm ci
   ```

3. Start the server:

   ```bash
   npm start
   ```

4. You should see the following confirmation:

   ```
   [byop sms server]: Listening on http://localhost:8080
   ```

### Make the Server Publicly Accessible

To test the server with Watson Assistant, it must be reachable from the internet. You can use [ngrok](https://ngrok.com/) for this:

```bash
ngrok http 8080
```

## Configuration Options

| Variable             | Description                                           |
| -------------------- | ----------------------------------------------------- |
| `WEBHOOK_URL`        | The webhook URL from your BYOP SMS integration        |
| `VERIFICATION_TOKEN` | The verification token from your BYOP SMS integration |
| `PORT`               | (Optional) The HTTP port the server should use        |

