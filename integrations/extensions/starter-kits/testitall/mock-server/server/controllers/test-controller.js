import * as _ from 'lodash';
import pkg from 'ibm-watson/auth/index.js';
const {IamAuthenticator} = pkg;
let counter = 1;
import NaturalLanguageUnderstandingV1 from 'ibm-watson/natural-language-understanding/v1.js';
// get health of application
export  function deleteTest (req, res, next) {
  // console.log('In route - delete');
  res.json({
    status: 'DELETED',
  });
};

export function getTest (req, res, next) {
 // console.log('In route - get');
  res.json({
    status: 'GOT IT',
  });
};


export  function putTest (req, res, next) {
 // console.log('In route - put');
  res.json({
    status: 'UPDATED',
  });
};

export function preWebhook (req, res, next) {
  console.log('In route - preWebhook');
 

  const nlu = new NaturalLanguageUnderstandingV1({
    // See: https://github.com/watson-developer-cloud/node-sdk#authentication
    authenticator: new IamAuthenticator({ apikey: 'redacted' }),
    version: '2019-07-12',
  });
  
      const payload = req.body.payload;
      const options = {
        html: payload.input.text,
        features: {
          sentiment: {}
        },
      };
      nlu.analyze(options)
     .then(response => {
      console.log(JSON.stringify(response.result, null, 2));
      if (!payload.context.skills['actions skill'].skill_variables){
        payload.context.skills['actions skill'].skill_variables={};
      }
      payload.context.skills['actions skill'].skill_variables.sentiment = response.result.sentiment.document.label
      //   if (!payload.context.integrations.chat){
      //     payload.context.integrations.chat={};
      // }
      // payload.context.integrations.chat.nlu_result = response.result;
     // _.set( payload,"context.skills['actions skill'].user_defined.nlu_result",response.result);
      res.json(req.body);
    })
    .catch(error => console.error(error));
      
  
};

export function postWebhook (req, res, next) {
   console.log('In route - postWebhook');
   res.json(req.body);
 };


 
 export function propertiesByCounterTest (req, res, next) {
  const countBy = req.body.counter || counter;
  console.log(`In route - properties by counter - ${countBy}`);
  const returnObject = {};
  returnObject[`${countBy}`]= true;
  // for (let i=0; i<countBy;i++) {
  //   returnObject[`prop ${countBy}-${i}`]= i;
  // }
  if (!req.body.counter){
    counter++;
  }
  res.json(returnObject);
  
};
 export function arraysInObjectTest (req, res, next) {
 
  console.log('In route - arraysObject');
   
  res.json({
    my_chosen_item: req.body.item,
    reverse_array: req.body.my_array.reverse(),
    status: 'POST Array Object',
  });
  
};

export function arraysRootTest (req, res, next) {
 
  console.log('In route - arraysRoot');
   
  res.json({
    reverse_array: req.body.reverse(),
    status: 'POST Array Root',
  });
  
};

export  function postTest (req, res, next) {
  if (req.params.path_param) {
//    console.log('In route - post with params');
    res.json({
      path_param: `POST IT - ${req.params.path_param}`,
      query_param:`POST IT - ${req.query.query_param}`,
      header_param: `POST IT - ${req.header('header_param')}`
    });
  }
  else {
    console.log('In route - post');
    res.json({
      status: 'POST IT',
    });
  }
};

export function errorTest (req, res, next) {
  // console.log('In route - error');
  res.status(404).send({
    message: 'This is an error!'
 });
};


export function acceptAwareTest (req, res, next) {
  console.log(`header valuse is: ${req.header('accept')}`);
  if (req.header('accept').includes('text/plain')) {
    
     res.type('txt');
    res.send('just text');
  }
  else {
    if (req.header('accept').includes('default')) { //this is ugly... better in actions replace default string with no accept header
      if (req.header('accept').includes('application/json')) {
        
            res.json({
                status: 'DEFAULT IS JSON',
            });
      }
      else {
       
            res.status(400).json({
                status: 'WHERE IS MY DEFAULT  JSON',
            });
      }
    }
    // accept header included application json
    res.json({
      status: 'accept header was json',
    });
  }
};

// export default {
//   preWebhook,
//   postWebhook,
//   postTest,
//   getTest,
//   putTest,
//   deleteTest,
//   errorTest,
//   acceptAwareTest,
//   arraysTest
// }