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
      .get(`${routes.question}/questionById/${qId}`)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
        return null;
      });
  },

  /**
   * Queries all questions from the backend 
   */
  async getAllQuestions() {
    console.debug('RestCall: getAllQuestions()');
    return await client
      .get(`${routes.question}/allQuestions`)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
        return null;
      });
  },

  /**
   * Queries the backend for all questions that match the search query
   * @param {Strings} searchQuery [all queries delimitered by space]
   */
  async getAllDataByQuery(searchQuery) {
    console.debug(`RestCall: getAllDataByQuery(${searchQuery})`);
    return await client
      .get(`${routes.question}/query/`, { params: { searchQuery } })
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
        return new Array();
      });
  },

  /**
   * Requests from the backend all answers of a question, based on the id 
   * @param {String} questionId [id of one question]
   */
  async getAllAnswersToQuestions(questionId) {
    console.debug(`RestCall: getAllAnswersToQuestions(${questionId})`);
    return await client
      .get(`${routes.answer}/answersByQuestionId/${questionId}`)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        console.error(error);
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
      .post(`${routes.answer}/answerTobeEdited`, id.ids)
      .then((response) => {
        console.log(response.data);
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
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
      .put(`${routes.answer}`, updatedAnswer)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
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
      .get(`${routes.comment}/commentsByAnswerId/${answerId}`)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
        return null;
      });
  },

  /**
   * Queries from the backend all tags
   */
  async getAllTags() {
    console.info('RestCall: getAllTags()');
    return await client
      .get(`${routes.tag}/getAllTags`)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
        if (axios.isCancel(error)) {
          console.log(error.message);
        } else {
          console.error('No Data: ', error);
        }
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
      .post(`${routes.question}/newQuestion`, newQuestion)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
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
      .put(`${routes.question}`, updatedQuestion)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
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
      .post('/answer', newAnswer)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        console.error('addNewAnswer():', error);
        return null;
      });
  },

  /**
   * Send updated answer that has increased rating to the backend
   * @param {Answer} answer [answer to increase rating]
   */
  async increaseAnswerRating(answer) {
    console.debug(`RestCall: increaseAnswerRating(${answer})`);
    return await client
      .post('/answer/increaseRating', answer)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        console.error(error);
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
      .post(`${routes.comment}/newComments`, newComment)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        console.error(error);
        return null;
      });
  },

  /**
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
        console.error('increaseCommentRating():', error);
        return null;
      });
  },

  /**
   * Queries from the backend all questions that are current relevant and regarded
   */
  async getMostActiveQuestions() {
    console.debug('RestCall: getMostActiveQuestions()');
    return await client
      .get('/question/getMostActiveQuestions')
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
        return null;
      });
  },

  /**
   * Queries from the backend all questions with most famouse tags
   */
  async getCurrentTopics() {
    console.debug('RestCall: getCurrentTopics()');
    return await client
      .get('/tagsWithMostQuestions')
      .then((response) => {
        console.warn('Topics', response.data);
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
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
      .get(`${routes.question}/questionByTag/${tag}`)
      .then((response) => {
        console.warn('Topics', response.data);
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
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
        console.error(error);
      });
  },

  /**
   * Queries from the backend the user, based on the id
   * @param {String} id [id of user]
   */
  async getUserNameFromId(id) {
    return await client
      .post(`${routes.user}/userNameFromId`, id)
      .then((response) => {
        console.log('Name from id all:', response);
        console.log('Name from id:', response.data);
        return response.data;
      })
      .catch((error) => {
        console.error(error);
        return id;
      });
  },
};
