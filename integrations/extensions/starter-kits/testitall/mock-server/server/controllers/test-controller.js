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