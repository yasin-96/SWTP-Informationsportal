import Vue from 'vue';
import Vuex from 'vuex';
import RestCalls from '@/services/RestCalls';
import { convertUnixTimeStampToString } from '@/services/Utils';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    allQuestions: {},

    //one question with all answers and comments
    oneQuestion: {},
    allAnswers: {},
    allComments: [],

    allTags: [],

    allQueryData: [],
  },
  mutations: {
    SET_ALL_QUESTIONS(state, data) {
      console.debug('SET_ALL_QUESTIONS');

      Object.keys(data).forEach((d) => {
        data[d].timeStamp = convertUnixTimeStampToString(data[d].timeStamp);
      });

      state.allQuestions = data;
    },

    SET_ALL_ANSWERS(state, data) {
      console.debug('SET_ALL_ANSWERS');

      data.listOfAnswers.forEach((d) => {
        console.debug('ANSWERS', d);
        d.timeStamp = convertUnixTimeStampToString(d.timeStamp);
      });

      state.allAnswers = data;
    },

    SET_ALL_COMMENTS(state, data) {
      if (data) {
        console.debug('SET_ALL_COMMENTS');

        console.debug('SET', data);
        console.debug('COMMENTS', data.comments);
        data.comments.forEach((dd) => {
          console.debug(dd);
          dd.timestamp = convertUnixTimeStampToString(dd.timestamp);
        });

        if (!state.allComments) {
          state.allComments = new Array();
          state.allComments = data;
        } else {
          state.allComments.push(data);
        }
      }
    },

    SET_ONE_QUESTION(state, data) {
      console.debug('SET_ONE_QUESTION');

      data.timeStamp = convertUnixTimeStampToString(data.timeStamp);

      state.oneQuestion = data;
    },

    SET_ALL_TAGS(state, data) {
      console.debug('SET_ALL_TAGS');
      state.allTags = data;
    },

    SET_ALL_QUERY_DATA(state, data) {
      console.debug('SET_ALL_QUERY_DATA');
      state.allQueryData = data;
    }
  },
  actions: {
    async act_getOneQuestion({ commit }, questionId) {
      console.log('act_getOneQuestion()');
      await RestCalls.getOneQuestion(questionId)
        .then((response) => {
          if (response != null) {
            commit('SET_ONE_QUESTION', response);
          }
        })
        .catch((error) => {
          console.error('act_getAllQuestions: ', error);
        });
    },

    async act_getAllQuestions({ commit }) {
      console.log('act_getAllQuestions');
        await RestCalls.getAllQuestions()
        .then((response) => {
          if (response != null) {
            commit('SET_ALL_QUESTIONS', response);
          }
        })
        .catch((error) => {
          console.error('act_getAllQuestions: ', error);
        });
      
    },

    async act_getAllDataByQuery({ commit }, query) {
      console.log('act_getAllQuestionsByQuery');
      if (!!query) {
        await RestCalls.getAllDataByQuery(query)
          .then((response) => {
            if (response != null) {
              commit('SET_ALL_QUERY_DATA', response);
            }
          })
          .catch((error) => {
            console.error('act_getAllDataByQuery: ', error);
          });
      }
    },

    async act_getAllAnswers({ commit }, questionId) {
      console.log('act_getAllAnswers');
        await RestCalls.getAllAnswersToQuestions(questionId)
          .then((response) => {
            if (response != null) {
              commit('SET_ALL_ANSWERS', response);
            }
          })
          .catch((error) => {
            console.error('act_getAllAnswers: ', error);
          });
    },

    async act_getAllComments({ commit }, answerId) {
      console.log('act_getAllComments');
        await RestCalls.getAllCommentsToAnswers(answerId)
          .then((response) => {
            if (response != null) {
              commit('SET_ALL_COMMENTS', response);
            }
          })
          .catch((error) => {
            console.error('act_getAllComments: ', error);
          });
    },

    async act_getAllTags({ commit }) {
      await RestCalls.getAllTags()
        .then((response) => {
          if (response != null) {
            commit('SET_ALL_TAGS', response);
          }
        })
        .catch((error) => {
          console.error('act_getAllTags(): ', error);
        });
    },

    async act_creatNewQuestion({ commit }, newQuestion) {
      console.log('act_creatNewQuestion()', newQuestion);
      return await RestCalls.addNewQuestion(newQuestion)
        .then((response) => {
          return response;
        })
        .catch((error) => {
          console.error(error);
        });
    },

    async act_addNewAnswer({ commit }, newAnswer) {
      console.log('act_addNewAnswer', newAnswer);
      return await RestCalls.addNewAnswer(newAnswer)
        .then((response) => {
          return response;
        })
        .catch((error) => {
          console.error(error);
        });
    },

    async act_addNewComment({ commit }, newComment) {
      console.log('act_addNewComment', newComment);
      return await RestCalls.addNewComment(newComment)
        .then((response) => {
          return response;
        })
        .catch((error) => {
          console.error(error);
        });
    },
  },

  getters: {
    getListWithAnswers: (state) => {
      return state.allAnswers.listOfAnswers;
    },

    getListWithComments: (state) => {
      return state.allComments.comments;
    },

    getAllTagName: (state) => {
      return state.allTags.map((n) => n.name);
    },
  },
  modules: {},
});
