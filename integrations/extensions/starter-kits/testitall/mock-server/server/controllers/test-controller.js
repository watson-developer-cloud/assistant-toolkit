const LONG_MESSAGE = `This is a long message intended to demonstrate streaming large messages in smaller chunks. Breaking down messages into smaller parts helps simulate real-time data transmission over a network. This technique is particularly useful for streaming large files or continuous data streams like logs, chat messages, or live updates. By sending small chunks, we ensure the data is processed and displayed incrementally, providing a smoother and more responsive user experience. Each chunk represents a portion of the entire message, and they are sent sequentially with a slight delay to mimic real-world streaming scenarios. This example uses a delay of 100 milliseconds between each chunk to achieve this effect.`;

// HTTP Methods
export function deleteTest(req, res) {
  return res.json({
    status: 'DELETED',
  });
}

export function getTest(req, res) {
  return res.json({
    status: 'GOT IT',
  });
}


export function putTest(req, res) {
  return res.json({
    status: 'UPDATED',
  });
}

export function postTest(req, res) {
  if (req.params.path_param) {
    return res.json({
      path_param: `POST IT - ${req.params.path_param}`,
      query_param: `POST IT - ${req.query.query_param}`,
      header_param: `POST IT - ${req.header('header_param')}`
    });
  } else {
    return res.json({
      status: 'POST IT',
    });
  }
}

export async function streamTest(req, res) {
  // TODO Don't check the Accept header field since the toolkit only sends application/json
  // Set the headers for the event stream
  res.writeHead(200, {
    'Content-Type': 'text/event-stream',
    'Cache-Control': 'no-cache',
    'Connection': 'keep-alive',
    'Content-Encoding' : 'none',
    'X-Accel-Buffering' : 'no'
  });
  res.flushHeaders();

  const messagePieces = LONG_MESSAGE.match(/.{1,20}/g); // Split the message into chunks of 20 characters

  // send the message chunks with a delay of 100 milliseconds
  let pieceIndex = 0;
  const intervalId = setInterval(() => {
    if (pieceIndex < messagePieces.length) {
      let chunk = {
        results: [{
          generated_text: `${messagePieces[pieceIndex]}`
        }]
      }
      res.write(`data: ${JSON.stringify(chunk)}\n\n`);
      pieceIndex++;
    } else {
      clearInterval(intervalId);
      res.end();
    }
  }, 100);

  // Handle client disconnect
  req.on('close', () => {
    clearInterval(intervalId);
    res.end();
  });
}


export function streamErrorTest(req, res) {
  return res.status(404).send({
    message: 'This is an error!'
  });
}

export function streamTimeoutTest(req, res) {
  // TODO Don't check the Accept header field since the toolkit only sends application/json
  // Set the headers for the event stream
  res.writeHead(200, {
    'Content-Type': 'text/event-stream',
    'Cache-Control': 'no-cache',
    'Connection': 'keep-alive',
    'Content-Encoding' : 'none',
    'X-Accel-Buffering' : 'no'
  });
  res.flushHeaders();

  const messagePieces = LONG_MESSAGE.match(/.{1,20}/g); // Split the message into chunks of 20 characters

  // Send the message chunks with a delay of 100 milliseconds
  let pieceIndex = 0;    
  const intervalId = setInterval(() => {
    if (pieceIndex < messagePieces.length) {
      let chunk = {
        results: [{
          generated_text: `${messagePieces[pieceIndex]}`
        }]
      }
      res.write(`data: ${JSON.stringify(chunk)}\n\n`);
      pieceIndex++;
    } else {
      pieceIndex = 0;    
    }
  }, 100);

  // Stop streaming after 51 seconds
  const timeoutId = setTimeout(() => {
    clearInterval(intervalId);
    res.end();
  }, 51000); // 51 seconds

  req.on('close', () => {
    clearInterval(intervalId);
    clearTimeout(timeoutId);
    res.end();
  });
}

export function patchTest(req, res) {
  return res.json({
    status: 'PATCHED',
  });
}

export function authHeaderTest(req, res) {
  return res.json({
    auth_header: req.header('Authorization') || 'No Authorization header provided',
  });
}

export function arraysInObjectTest(req, res) {
  const my_array = req.body.my_array || [];
  return res.json({
    my_chosen_item: req.body.item, reverse_array: my_array.reverse(), status: 'POST Array Object',
  });
}

export function arraysRootTest(req, res) {
  return res.json({
    reverse_array: req.body.reverse(), status: 'POST Array Root',
  });

}

export function errorTest(req, res) {
  return res.status(404).send({
    message: 'This is an error!'
  });
}


export function contextTooLargeTest(req, res) {
  const fakeData = 'x'.repeat(parseInt(process.env.RESPONSE_SIZE_LIMIT, 10) * 5); // Default: 130KB * 5 = 650KB
  const response = {
    data: fakeData
  }

  return res.status(200).send(response);
}

export function contextAlmostTooLargeTest(req, res) {
  const fakeData = 'x'.repeat(parseInt(process.env.RESPONSE_SIZE_LIMIT, 10) - 1024); // Default: 130KB - 1KB = 129KB

  const response = {
    data: fakeData
  }

  return res.status(200).send(response);
}

export function successfulPostWithNonJSONResponse(req, res) {
  const htmlPage = '<!DOCTYPE html> <html> <body> <h1>My First Heading</h1> <p>My first paragraph.</p> </body> </html>'

  return res.status(200).send(htmlPage);
}

// Other Tests
let counter = 1;

export function propertiesByCounterTest(req, res) {
  const countBy = req.body.counter || counter;
  console.log(`In route - properties by counter - ${countBy}`);
  const returnObject = {};
  returnObject[`${countBy}`] = true;
  // for (let i=0; i<countBy;i++) {
  //   returnObject[`prop ${countBy}-${i}`]= i;
  // }
  if (!req.body.counter) {
    counter++;
  }
  return res.json(returnObject);
}