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
  softwareDevelopState: process.env.VUE_APP_API_STATE,
};

/* Set: Axios Instance
 * These are the properties for axios given with to send requests.
 */
const client = new axios.create({
  baseURL: serverConfig.apiAddress,
  httpAgent: new http.Agent({ keepAlive: true }),
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json',
  },
});

// const cancelToken = axios.CancelToken;
// const source = cancelToken.source();

/*
 * Returns the information under which address the backend is to be delivered.
 * Depending on the start process. These are different under dev and prod.
 */
switch (serverConfig.softwareDevelopState) {
  case 'dev':
    isDebugEnabled = true;
    console.debug(`REST-API is addressed for DEVELOPMENT at the address: ${serverConfig.apiAddress} .`);
    break;

  case 'release':
    isDebugEnabled = false;
    console.debug(`REST-API is addressed PRODUCTIVELY at the address: ${serverConfig.apiAddress} angesprochen.`);
    break;

  default:
    isDebugEnabled = false;
    console.error('No <state> selected! Please do not use Frontend productively and contact the responsible developer immediately!');
    serverConfig.apiAddress = '';
    break;
}

export default {
  async getOneQuestion(qId) {
    console.debug('RestCall: getOneQuestion()', qId);
    return await client
      .get(`/questionById/${qId}`)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
        return null;
      }).finally(()=> {
        console.log("getOneQuestion() :> axios close ");
      });
  },

  async getAllQuestions() {
    console.debug('getAllQuestions()');
    return await client
      .get('/allQuestions')
      .then((response) => {
        // console.log(response.data);
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
        return null;
      });
  },

  async getAllDataByQuery(searchQuery) {
    console.debug('getAllDataByQuery()', searchQuery);
    return await client
      .post('/question/query', searchQuery) //TODO: muss angepasst werden !!
      .then((response) => {
        // console.log(response.data);
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
        return null;
      });
  },

  async getAllAnswersToQuestions(questionId) {
    console.debug('getAllAnswersToQuestions():', questionId);
    return await client
      .get(`/answersByQuestionId/${questionId}`)
      .then((response) => {
        // console.log('getAllAnswersToQuestions:', response.data);
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
        return null;
      });
  },

  async getAllCommentsToAnswers(answerId) {
    console.info('getAllCommentsToAnswers()', answerId);
    return await client
      .get(`/commentsByAnswerId/${answerId}`)
      .then((response) => {
        console.warn('RESP:', response.data);
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
        return null;
      });
  },

  async getAllTags() {
    console.info('getAllTags()');
    return await client
      .get(`/getAllTags`)
      .then((response) => {
        console.warn('RESP:', response);
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
        return null;
      });
  },

  async addNewQuestion(newQuestion) {
    console.warn('newQuestion', newQuestion);
    return await client
      .post('/newQuestion', newQuestion)
      .then((response) => {
        console.log('addNewQuestion', response.data);
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
        return null;
      });
  },

  async addNewAnswer(newQuestion) {
    console.warn('addNewAnswer()', newQuestion);
    return await client
      .post('/answer', newQuestion)
      .then((response) => {
        console.log('addNewAnswer', response.data);
        return response.data;
      })
      .catch((error) => {
        console.error('addNewAnswer():', error);
        return null;
      });
  },

  async addNewComment(newComment) {
    console.warn('addNewComment()', newComment);
    return await client
      .post('/newComments', newComment)
      .then((response) => {
        console.log('addNewComment', response.data);
        return response.data;
      })
      .catch((error) => {
        console.error('addNewComment():', error);
        return null;
      });
  },
};
