/* eslint-disable no-undef */
import axios from 'axios';
import http from 'http';

/* Only for develop and debugging */
let isDebugEnabled = process.env.VUE_APP_API_STATE === 'dev' ? true : false;
console.log('process.env.VUE_APP_API_STATE: ' + process.env.VUE_APP_API_STATE);
console.warn('isDebugEnabled: ' + isDebugEnabled);

/**
 * Defines Information about server
 *
 * @param {string} apiProtocol - The protocol for the server e.g http/https
 * @param {string} apiServer - The server ip address /name
 * @param {string} apiPort - The port interface of the server
 * @param {string} apiInterface - Interface to send the requests to the server
 * @param {string} apiUrl - The complete url with the previous parameters
 * @param {string} apiAddress - The address to the server to answer the requests
 * @param {string} softwareDevelopState - Gives me the information under which status the server is running. (production or development)
 */
const serverConfig = {
  apiProtocol: process.env.VUE_APP_API_PROTOCOL,
  apiServer: process.env.VUE_APP_API_SERVER,
  apiPort: process.env.VUE_APP_API_PORT,
  apiInterface: process.env.VUE_APP_API_INTERFACE,
  apiUrl: process.env.VUE_APP_API_URL,
  apiAddress: process.env.VUE_APP_API_URL,
  softwareDevelopState: process.env.VUE_APP_API_STATE
};

/* Set: Axios Instance
 * These are the properties for axios given with to send requests.
 */
const client = new axios.create({
  baseURL: serverConfig.apiAddress,
  httpAgent: new http.Agent({ keepAlive: true }),
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json'
  }
});

const cancelToken = axios.CancelToken;
const source = cancelToken.source();

/*
 * Returns the information under which address the backend is to be delivered.
 * Depending on the start process. These are different under dev and prod.
 */
switch (serverConfig.softwareDevelopState) {
  case 'dev':
    isDebugEnabled = true;
    console.log(`REST-API is addressed for DEVELOPMENT at the address: ${serverConfig.apiAddress} .`);
    break;

  case 'release':
    isDebugEnabled = false;
    console.log(`REST-API is addressed PRODUCTIVELY at the address: ${serverConfig.apiAddress} angesprochen.`);
    break;

  default:
    isDebugEnabled = false;
    console.error('No <state> selected! Please do not use Frontend productively and contact the responsible developer immediately!');
    serverConfig.apiAddress = '';
    break;
}

export default {
  async getOneQuestion(qId) {
    console.warn(qId);
    try {
      let serverResponse = await client.get(`/questionById/${qId}`, {
        cancelToken: source.token
      });
      return serverResponse.data;
    } catch (error) {
      if (axios.isCancel(error)) {
        console.log('Request canceled', error.message);
        source.cancel('getOneQuestion() canceled');
      }
      console.error('No Data: ', error);
      return -1;
    }
  },

  async getAllQuestions() {
    console.info('getAllQuestions()');
    try {
      let serverResponse = await client.get('/allQuestions', {
        cancelToken: source.token
      });
      return serverResponse.data;
    } catch (error) {
      if (axios.isCancel(error)) {
        console.log('Request canceled', error.message);
      }
      console.error('No Data: ', error.data);
      source.cancel('Operati on canceled by the user.');
      return -1;
    }
  },

  async getAllAnswersToQuestions(questionId) {
    console.info('getAllAnswersToQuestions():', questionId);
    try {
      let serverResponse = await client.get(`/answersByQuestionId/${questionId}`, {
        cancelToken: source.token
      });
      source.cancel('Request finished:');
      return serverResponse.data;
    } catch (error) {
      if (axios.isCancel(error)) {
        console.log('Request canceled: getAllAnswersToQuestions', error.message);
        source.cancel('Request canceled: getAllAnswersToQuestions');
      }
      console.error('No Data: ', error.data);
      return -1;
    }
  },

  async getAllCommentsToAnswers(qId) {
    console.info('getAllCommentsToAnswers()', qId);
    try {
      let serverResponse = await client.get(`/commentsByAnswerId/${qId}`);
      source.cancel('Request finished:');

      return serverResponse.data;
    } catch (error) {
      if (axios.isCancel(error)) {
        console.log('Request canceled: getAllCommentsToAnswers()', error.message);
        source.cancel('Request canceled: getAllCommentsToAnswers()');
      }
      console.error('No Data: ', error.data);
      return -1;
    }
  }
};
