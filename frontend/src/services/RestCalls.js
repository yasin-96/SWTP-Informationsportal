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
 * @param {string} baseurl - Gives me the information under which status the server is running. (production or development)
 * @param {string} websocketPort - The port indicates that this is the backend and not the gateway.
 * @param {string} websocketName - The name which is used to create exactly such a connection to the backend using the name
 * @param {string} websocketSubcription - The end point for the registration of the subscription
 * @param {string} stompEndPoint - The end point that is subsequently addressed in the backend to process the data.
 */
const serverConfig = {
  apiProtocol: process.env.VUE_APP_API_PROTOCOL,
  apiServer: process.env.VUE_APP_API_SERVER,
  apiPort: process.env.VUE_APP_API_PORT,
  apiInterface: process.env.VUE_APP_API_INTERFACE,
  apiUrl: process.env.VUE_APP_API_URL,
  apiAddress: process.env.VUE_APP_API_URL,
  uiAddress: process.env.VUE_APP_API_UI_URL,
  softwareDevelopState: process.env.VUE_APP_API_STATE,
  baseurl: process.env.BASE_URL,
  websocketPort: process.env.VUE_APP_WEBSOCKET_PORT,
  websocketName: process.env.VUE_APP_WEBSOCKET_NAME,
  websocketURL: process.env.VUE_APP_WS_URL,
  websocketSubcription: process.env.VUE_APP_WEBSOCKET_SUBCRIPTION,
  stompEndPoint: process.env.VUE_APP_STOMPENDPOINT,
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
  // timeout: 20000,
});


export function printErrorMessage(errorResponse){
  if(errorResponse != null){
    switch(errorResponse.status){
      case 401: 
        console.info(errorResponse.statusText);
        break;
      case 404: 
        console.log(errorResponse.statusText);
        break;
      case 400:
        console.warn(errorResponse.statusText);
        break;
      case 500:
        console.error(errorResponse.statusText, errorResponse);
        break;
      default:
        console.warn(errorResponse.statusText);
        break;
    }
  }
  
}


/*
 * Returns the information under which address the backend is to be delivered.
 * Depending on the start process. These are different under dev and prod.
 */
switch (serverConfig.softwareDevelopState) {
  case 'dev':
    isDebugEnabled = true;
    console.debug(`REST-API is addressed for DEVELOPMENT at the address: ${serverConfig.apiAddress} .`);
    console.debug(`REST-API is addressed for DEVELOPMENT at the address: ${serverConfig.baseUrl} .`);
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

/* All routes for backen */
const routes = {
    question: "/question",
    answer: "/answer",
    comment: "/comment",
    user: "/user",
    tag: "/tag"
};



export default {
  /**
   * Queries from the backend one question, based on the id
   * @param {String} qId [id of question]
   */
  async getOneQuestion(qId) {
    console.debug(`RestCall: getOneQuestion(${qId})`);
    return await client
      .get(`${routes.question}/id/${qId}`)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        printErrorMessage(error.response);
        return null;
      });
  },

  /**
   * Queries all questions from the backend 
   */
  async getAllQuestions() {
    console.debug('RestCall: getAllQuestions()');
    return await client
      .get(`${routes.question}/all`)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        printErrorMessage(error.response);
        return null;
      });
  },

  /**
   * Queries from the backend all questions based on this tag
   * @param {String} tag [tag for query]
   */
  async getQuestionBasedOnTopic(tag) {
    console.debug(`RestCall: getQuestionBasedOnTopic(${tag})`);
    return await client
      .get(`${routes.question}/tag/${tag}`)
      .then((response) => {
        console.warn('Topics', response.data);
        return response.data;
      })
      .catch((error) => {
        printErrorMessage(error.response);
        return null;
      });
  },

  /**
   * Send a new question object to the backend
   * @param {Object} newQuestion [question to create]
   */
  async addNewQuestion(newQuestion) {
    console.debug(`RestCall: addNewQuestion()`, newQuestion);
    return await client
      .post(`${routes.question}/new`, newQuestion)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        printErrorMessage(error.response);
        return null;
      });
  },

  /**
   * Send new information for question to the backend
   * @param {Object} updatedQuestion [new changes for this question]
   */
  async updateCurrentQuestion(updatedQuestion) {
    console.debug(`RestCall: updateCurrentQuestion()`, updatedQuestion);
    return await client
      .put(`${routes.question}/update`, updatedQuestion)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        printErrorMessage(error.response);
        return null;
      });
  },

  /**
   * Queries the backend for all questions that match the search query
   * @param {Strings} searchQuery [all queries delimitered by space]
   */
  async getAllDataByQuery({query}) {
    console.debug(`RestCall: getAllDataByQuery(${query})`);
    return await client
      .get(`${routes.question}/query/`, { params: { query } })
      .then((response) => {
        console.info("getAllDataByQuery-> ", response);
        return response.data;
      })
      .catch((error) => {
        printErrorMessage(error.response);
        return new Array();
      });
  },

  /**
   * Queries from the backend all questions that are current relevant and regarded
   */
  async getMostActiveQuestions() {
    console.debug('RestCall: getMostActiveQuestions()');
    return await client
      .get(`${routes.question}/active`)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        printErrorMessage(error.response);
        return null;
      });
  },


   /**
   * Send new answer for one question to the backend
   * @param {*} newAnswer [answer to add]
   */
  async addNewAnswer(newAnswer) {
    console.debug(`RestCall: addNewAnswer(${newAnswer})`);
    return await client
      .post('/answer/new', newAnswer)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        printErrorMessage(error.response);
        return null;
      });
  },

  /**
   * Requests an answer to a question from the backend, based on the ids
   * @param {Object} id [contains the id of the question and the answer to be fetched]
   */
  async getOneAnswerToQuestion(id) {
    console.debug(`RestCall: getOneAnswerToQuestion(${id})`, id);
    return await client
      .get(`${routes.answer}/edit`, { params: { qId: id.questionId, aId: id.answerId }})
      .then((response) => {
        console.log(response.data);
        return response.data;
      })
      .catch((error) => {
        printErrorMessage(error.response);
        return null;
      });
  },

  /**
   * 
   * @param {Answer} updatedAnswer 
   */
  async setOneAnswerToQuestion(updatedAnswer) {
    console.debug('setOneAnswerToQuestion():', updatedAnswer);
    return await client
      .patch(`${routes.answer}/update`, updatedAnswer)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        printErrorMessage(error.response);
        return null;
      });
  },

  /**
   * Requests from the backend all answers of a question, based on the id 
   * @param {String} questionId [id of one question]
   */
  async getAllAnswersToQuestions(questionId) {
    console.debug(`RestCall: getAllAnswersToQuestions(${questionId})`);
    return await client
      .get(`${routes.answer}/question/${questionId}`)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        printErrorMessage(error.response);
        return null;
      });
  },



  /**
   * Queries from the backend all tags
   */
  async getAllTags() {
    console.info('RestCall: getAllTags()');
    return await client
      .get(`${routes.tag}/all`)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        printErrorMessage(error.response);
        return null;
      });
  },

  /**
   * Queries from the backend all questions with most famouse tags
   */
  async getCurrentTopics() {
    console.debug('RestCall: getCurrentTopics()');
    return await client
      .get(`${routes.tag}/questions`)
      .then((response) => {
        console.warn('Topics', response.data);
        return response.data;
      })
      .catch((error) => {
        printErrorMessage(error.response);
        return null;
      });
  },



  /**
   * Queries from the backend all comments on a question, based on the id
   * @param {String} answerId [id of one answer]
   */
  async getAllCommentsToAnswers(answerId) {
    console.info(`RestCall: getAllCommentsToAnswers(${answerId})`);
    return await client
      .get(`${routes.comment}/answer/${answerId}`)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        printErrorMessage(error.response);
        return null;
      });
  },

  /**
   * Send updated comment that has increased rating to the backend
   * @param {Comment} comment [comment to increase rating]
   */
  async updateComment(comment) {
    console.debug(`RestCall: updateComment(${comment})`);
    return await client
      .patch('/comment/update', comment)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        printErrorMessage(error.response);
        return null;
      });
  },

  /**
   * Send a new comment object to the backend 
   * @param {Comment} newComment [new comment for one answer]
   */
  async addNewComment(newComment) {
    console.debug(`RestCall: addNewComment(${newComment})`);
    return await client
      .post(`${routes.comment}/new`, newComment)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        printErrorMessage(error.response);
        return null;
      });
  },

  /**
   * TODO kann das weg?
   * Send updated comment that has increased rating to the backend
   * @param {Comment} comment [comment to increase rating]
   */
  async increaseCommentRating(comment) {
    console.debug(`RestCall: increaseCommentRating(${comment})`);
    return await client
      .post('/comment/increaseRating', comment)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        printErrorMessage(error.response);
        return null;
      });
  },


  /**
   * Queries from the backend all information about the user
   */
  async getUserInfo() {
    return await client
      .get('/user/info')
      .then((response) => {
        console.log('userinfo:', response.data);
        return response.data;
      })
      .catch((error) => {
        printErrorMessage(error.response);
      });
  },

  /**
   * Queries from the backend the user, based on the id
   * @param {String} id [id of user]
   */
  async getUserNameFromId(id) {
    return await client
      .get(`${routes.user}/name`, { params: { id: id }})
      .then((response) => {
        console.log('Name from id all:', response);
        console.log('Name from id:', response.data);
        return response.data;
      })
      .catch((error) => {
        printErrorMessage(error.response);
        return id;
      });
  },
};
