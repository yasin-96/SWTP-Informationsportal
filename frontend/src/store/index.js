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

    oneAnswerWasChanged: false
  },
  mutations: {
    SET_ALL_QUESTIONS(state, data) {
      console.debug('SET_ALL_QUESTIONS');

      Object.keys(data).forEach((d) => {
        data[d].timeStamp = convertUnixTimeStampToString(data[d].timeStamp);
      });

      state.allQuestions = data;
    },

    SET_ONE_QUESTION(state, data) {
      console.debug('SET_ONE_QUESTION');

      data.timeStamp = convertUnixTimeStampToString(data.timeStamp);

      state.oneQuestion = data;
    },

    SET_QUESTIONS_BASED_ON_TOPICS(state, data) {
      if (data) {
        Object.keys(data).forEach((d) => {
          data[d].timeStamp = convertUnixTimeStampToString(data[d].timeStamp);
        });
        state.questionsBasedOnTopics = data;
      }
    },

    SET_MOST_ACTIV_QUESTIONS(state, data) {
      if (data) {
        Object.keys(data).forEach((d) => {
          data[d].timeStamp = convertUnixTimeStampToString(data[d].timeStamp);
        });

        state.activeQuestions = data;
      }
    },

    SET_ALL_QUERY_DATA(state, data) {
      console.debug('SET_ALL_QUERY_DATA');
      if (data) {
        Object.keys(data).forEach((d) => {
          data[d].timeStamp = convertUnixTimeStampToString(data[d].timeStamp);
        });
      }
      state.allQueryData = data;
    },

    //all mutation for answers
    SET_ALL_ANSWERS(state, data) {
      console.debug('SET_ALL_ANSWERS');

      state.reloadAnswers = state.reloadAnswers ? false : true;

      if (data) {
        data.listOfAnswers.forEach((d) => {
          console.debug('ANSWERS', d);
          d.timeStamp = convertUnixTimeStampToString(d.timeStamp);
        });
      }

      state.allAnswers = Object.assign([], data);
    },

    SET_ONE_ANSWER_TO_QUESTION(state, data) {
      console.debug('SET_ONE_ANSWER_TO_QUESTION');
      if (data) {
        state.oneAnswer = data;
      }
    },

    //all mutation for comments
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

    //all tags -> topics
    SET_ALL_TAGS(state, data) {
      console.debug('SET_ALL_TAGS');
      state.allTags = data;
    },

    SET_ALL_QUERY_DATA(state, data) {
      console.debug('SET_ALL_QUERY_DATA');
      if(data){
        Object.keys(data).forEach((d) => {
          data[d].timeStamp = convertUnixTimeStampToString(data[d].timeStamp);
        });
      }
      state.allQueryData = data;
    },
  },
  actions: {
    //questions
    async act_getAllQuestions({ commit }) {
      console.log('act_getAllQuestions');
      await RestCalls.getAllQuestions()
        .then((response) => {
          commit('SET_ALL_QUESTIONS', response);
        })
        .catch((error) => {
          console.error('act_getAllQuestions: ', error);
        });
    },

    async act_getOneQuestion({ commit }, questionId) {
      console.log('act_getOneQuestion()');
      console.log('act_getOneQuestion()', questionId);
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

    async act_getQuestionsBasedOnTopic({ commit }, topic) {
      console.log('act_getQuestionsBasedOnTopic()');
      await RestCalls.getQuestionBasedOnTopic(topic)
        .then((response) => {
          if (response != null) {
            commit('SET_ONE_QUESTION', response);
          }
        })
        .catch((error) => {
          console.error('act_getAllQuestions: ', error);
        });
    },

    async act_getMostActiveQuestions({ commit }) {
      console.log('act_getMostActiveQuestions()');
      await RestCalls.getMostActiveQuestions()
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
      console.log('act_getAllQuestionsByQuery', query);
      await RestCalls.getAllDataByQuery(query)
        .then((response) => {
          // console.warn('getAllDataByQuery', response);
          // if (response != null) {
          commit('SET_ALL_QUERY_DATA', response);

          // }
        })
        .catch((error) => {
          console.error('act_getAllDataByQuery: ', error);
        });
    },

    async act_creatNewQuestion({}, newQuestion) {
      console.log('act_creatNewQuestion()', newQuestion);
      return await RestCalls.addNewQuestion(newQuestion)
        .then((response) => {
          return response;
        })
        .catch((error) => {
          console.error(error);
          return null;
        });
    },

    async act_updateCurrentQuestion({}, questionWithNewData) {
      console.log('act_updateCurrentQuestion()', questionWithNewData);
      return await RestCalls.updateCurrentQuestion(questionWithNewData)
        .then((response) => {
          return response;
        })
        .catch((error) => {
          console.error(error);
          return null;
        });
    },

    //answers
    async act_getAllAnswers({ commit }, questionId) {
      console.log('act_getAllAnswers', questionId);
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

    async act_updateAnswerFromQuestion({}, updatedAnswerToQuestion) {
      console.log('act_updateAnswerFromQuestion');
      await RestCalls.setOneAnswerToQuestion(updatedAnswerToQuestion)
        .then((response) => {
          return response;
        })
        .catch((error) => {
          console.error(error);
        });
    },

    async act_addNewAnswer({}, newAnswer) {
      console.log('act_addNewAnswer', newAnswer);
      return await RestCalls.addNewAnswer(newAnswer)
        .then((response) => {
          return response;
        })
        .catch((error) => {
          console.error(error);
          return null;
        });
    },

    async increaseRatingForAnswer({ dispatch }, answer) {
      console.log('increaseAnswerRating', answer);
      await RestCalls.increaseAnswerRating(answer)
        .then(() => {
          dispatch('act_getAllAnswers', answer.id);
        })
        .catch((error) => {
          console.error(error);
        });
    },

    //comments
    async act_getAllComments({ commit }, answerId) {
      console.log('act_getAllComments');
      await RestCalls.getAllCommentsToAnswers(answerId)
        .then((response) => {
          commit('SET_ALL_COMMENTS', response);
        })
        .catch((error) => {
          console.error('act_getAllComments: ', error);
        });
    },

    async act_addNewComment({}, newComment) {
      console.log('act_addNewComment', newComment);
      return await RestCalls.addNewComment(newComment)
        .then((response) => {
          return response;
        })
        .catch((error) => {
          console.error(error);
        });
    },

    async act_increaseCommentRating({ commit }, comment) {
      console.log('act_increaseCommentRating', comment);
      return await RestCalls.increaseCommentRating(comment)
        .then(() => {
          dispatch('act_getAllComments', comment.id);
        })
        .catch((error) => {
          console.error(error);
      });
    },
    

    async act_updateCurrentQuestion({ commit }, questionWithNewData) {
      console.log('act_updateCurrentQuestion()', questionWithNewData);
      return await RestCalls.updateCurrentQuestion(questionWithNewData)
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
