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

export function arraysInObjectTest(req, res) {
  return res.json({
    my_chosen_item: req.body.item, reverse_array: req.body.my_array.reverse(), status: 'POST Array Object',
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


export function contextTooLargeTest(req, res, next) {
  const fakeData = 'x'.repeat(parseInt(process.env.RESPONSE_SIZE_LIMIT, 10) * 5); // Default: 500KB
  const response = {
    data: fakeData
  }

  return res.status(200).send(response);
}

export function contextAlmostTooLargeTest(req, res, next) {
  const fakeData = 'x'.repeat(parseInt(process.env.RESPONSE_SIZE_LIMIT, 10) - 1024); // Default: 99KB

  const response = {
    data: fakeData
  }

  return res.status(200).send(response);
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