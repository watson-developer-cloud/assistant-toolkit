
const axios = require('axios').default;
const qs = require('qs');
const appGUID = "app-guid"; //The Developer Gets this from the action namespace
const apiURL = "app-url"; //The Developer gets this from the IBMRPA server deployed to
const workspaceAPIID = "workspace-app-id"; // Get this from a get request to the server with your UserName (https://us1api.wdgautomation.com/v2.0/account/tenant?UserName={username})
const username = "username" //username you want the bot to log in as
const password = "password" //password the bot uses with your username
const processID = "process-id"//Developer uses a get request for list processes to see https://us1api.wdgautomation.com/v2.0/workspace/{workspace-app-id}/process

async function main(params) {

  var guid = params.guid;

  if (guid == appGUID) {
    var rpaLoginToken = await getIBMRPALoginToken(apiURL, workspaceAPIID, username, password);
    
    //This is the data we send from IBM watsonx Assistant
    var instanceID = params.instanceID;
    var rpaInstanceStatus = await queryInstanceStatus(instanceID, rpaLoginToken);
    
    //Right here tell us what sub attribute you want to return
    rpaInstanceStatus = rpaInstanceStatus.outputs;


    return { 'rpaInstanceStatus': rpaInstanceStatus }
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
  return response.data.access_token;
}

async function queryInstanceStatus(instanceID, token) {

  var config = {
    method: 'get',
    url: 'https://' + apiURL + '.wdgautomation.com/v2.0/workspace/' + workspaceAPIID + '/process/' + processID + '/instance/' + instanceID,
    headers: {
      'Authorization': 'Bearer ' + token
    }
  };

  const response = await axios(config);
  return response.data;
}