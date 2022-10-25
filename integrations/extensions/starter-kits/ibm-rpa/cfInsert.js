
const axios = require('axios').default;
const qs = require('qs');
const appGUID = "app-guid"; //The Developer Gets this from the action namespace
const apiURL = "api-url"; //The Developer gets this from the IBMRPA server deployed to
const workspaceAPIID = "workspace-app-id"; // Get this from a get request to the server with your UserName ((https://us1api.wdgautomation.com/v2.0/account/tenant?UserName=zachary.silverstein@ibm.com))
const username = "username" //username you want the bot to log in as
const password = "password" //password the bot uses with your username
const processID = "process-id"//Developer uses a get request for list processes to see https://us1api.wdgautomation.com/v2.0/workspace/a1699374-7c93-45e4-a931-e3254968e014/process OR user ggets it manually by going to the process in the web client

async function main(params) {

  var guid = params.guid;

  if (guid == appGUID) {
    var rpaLoginToken = await getIBMRPALoginToken(apiURL, workspaceAPIID, username, password);
    var rpaInstanceID = await postDataToRPA(params, rpaLoginToken)
    return { 'rpaInstanceID': rpaInstanceID }

  }
}

async function getIBMRPALoginToken(apiURL, workspaceAPIID, username, password) {
 
  var data = qs.stringify({
    'grant_type': 'password',
    'username': username,
    'password': password,
    'culture': 'en-us'
  });
  var config = {
    method: 'post',
    url: 'https://' + apiURL + '.wdgautomation.com/v1.0/token',
    headers: {
      'tenantId': workspaceAPIID,
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    data: data
  };

  const response = await axios(config);
  return response.data.access_token
}


async function postDataToRPA(params, token) {

  var payloadObject = {};
  
  //PUT RPA INPUT PARAMS HERE
  payloadObject.rpaInputParam1 = params.rpaInputParam1;
  
  var config = {
    method: 'post',
    url: 'https://' + apiURL + '.wdgautomation.com/v2.0/workspace/' + workspaceAPIID + '/process/' + processID + '/instance',
    headers: {
      'Authorization': 'Bearer ' + token,
      'Content-Type': 'application/json'
    },
    data: { "payload": payloadObject }
  };

  console.log(JSON.stringify(config));
  const response = await axios(config);
  return response.data.id
}