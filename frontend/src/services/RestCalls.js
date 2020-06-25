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
  timeout: 20000,
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
  /**
   *
   * @param {String} qId
   */
  async getOneQuestion(qId) {
    console.debug(`RestCall: getOneQuestion(${qId})`);
    return await client
      .get(`/questionById/${qId}`)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
        return null;
      });
  },

  async getAllQuestions() {
    console.debug('RestCall: getAllQuestions()');
    return await client
      .get('/allQuestions')
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
        return null;
      });
  },

  async getAllDataByQuery(searchQuery) {
    console.debug(`RestCall: getAllDataByQuery(${searchQuery})`);
    return await client
      .get(`/question/query/`,  { params: { searchQuery}})
      .then((response) => {
        console.log(response.data);
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
        return new Array();
      });
  },

  async getAllAnswersToQuestions(questionId) {
    console.debug(`RestCall: getAllAnswersToQuestions(${questionId})`);
    return await client
      .get(`/answersByQuestionId/${questionId}`)
      .then((response) => {
        // console.log('getAllAnswersToQuestions:', response.data);
        return response.data;
      })
      .catch((error) => {
        console.error(error);
        return null;
      });
  },

  //TODO endpoint warte noch auf hinweis
  async getOneAnswerToQuestion(ids) {
    console.debug(`RestCall: getOneAnswerToQuestion(${ids})`);
    return await client
      .post('/answer/answerTobeEdited', ids )
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
        return null;
      });
  },

  //TODO endpoint
  async setOneAnswerToQuestion(updatedAnswer) {
    console.debug('setOneAnswerToQuestion():', updatedAnswer);
    return await client
      .put('/answersByQuestionId/', updatedAnswer)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
        return null;
      });
  },

  async getAllCommentsToAnswers(answerId) {
    console.info(`RestCall: getAllCommentsToAnswers(${answerId})`);
    return await client
      .get(`/commentsByAnswerId/${answerId}`)
      .then((response) => {
        console.warn('RESP:', response.data);
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
        return null;
      }).finally(()=> {
        console.log("getAllCommentsToAnswers() :> axios close ");
      });
  },

  async getAllTags() {
    console.info('RestCall: getAllTags()');
    return await client
      .get(`/getAllTags`)
      .then((response) => {
        console.warn('RESP:', response);
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
   *
   * @param {*} newQuestion
   */
  async addNewQuestion(newQuestion) {
    console.debug(`RestCall: addNewQuestion()`, newQuestion);
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

  /**
   *
   * @param {*} updatedQuestion
   */
  async updateCurrentQuestion(updatedQuestion) {
    console.debug(`RestCall: updateCurrentQuestion()`, updatedQuestion);
    return await client
      .put('/question', updatedQuestion)
      .then((response) => {
        console.log('updateCurrentQuestion', response.data);
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
        return null;
      });
  },

  /**
   *
   * @param {*} newQuestion
   */
  async addNewAnswer(newQuestion) {
    console.debug(`RestCall: addNewAnswer(${newQuestion})`);
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

  /**
   *
   * @param {Object} answer
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
   *
   * @param {Object} newComment
   */
  async addNewComment(newComment) {
    console.debug(`RestCall: addNewComment(${newComment})`);
    return await client
      .post('/newComments', newComment)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        console.error(error);
        return null;
      });
  },

  /**
   *
   * @param {Object} comment
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
   *
   * @param {Object} question
   */
  async postNewQuestion(question) {
    console.debug(`RestCall: postNewQuestion(${question})`);
    return await client
      .post(`/newQuestion`, question)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        console.error(error);
        return null;
      });
  },

  /**
   *
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
   *
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
   *
   * @param {String} tag
   */
  async getQuestionBasedOnTopic(tag) {
    console.debug(`RestCall: getQuestionBasedOnTopic(${tag})`);
    return await client
      .get(`/questionByTag/${tag}`)
      .then((response) => {
        console.warn('Topics', response.data);
        return response.data;
      })
      .catch((error) => {
        console.error('No Data: ', error);
        return null;
      });
  },

  // async getRendertHtmlFromMarkdown(mdText) {
  //   console.debug('Parse MD');
  //   return await axios({
  //     methode: 'POST',
  //     headers: { 'Content-Type': 'application/json' },
  //     url: ' https://gitlab.com/api/v4/markdown',
  //     data: { text: mdText, gfm: true },
  //   }).then((response) => {
  //     console.warn('Data raw', response);
  //     console.warn('Data->data', response.data);
  //     return response.data;
  //   });
  // },
};
