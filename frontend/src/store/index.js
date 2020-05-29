import Vue from 'vue';
import Vuex from 'vuex';
import RestCalls from '@/services/RestCalls';
import { convertUnixTimeStampToString } from '@/services/Utils';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    allQuestions: {},
    allAnswers: {},
    allComments: [],
    oneQuestion: {},
    allTags: [],
  },
  mutations: {
    SET_ALL_QUESTIONS(state, data) {
      console.log('SET_ALL_QUESTIONS');

      Object.keys(data).forEach((d) => {
        data[d].timeStamp = convertUnixTimeStampToString(data[d].timeStamp);
      });

      state.allQuestions = data;
    },

    SET_ALL_ANSWERS(state, data) {
      console.log('SET_ALL_ANSWERS');

      data.listOfAnswers.forEach((d) => {
        console.log('ANSWERS', d);
        d.timeStamp = convertUnixTimeStampToString(d.timeStamp);
      });

      state.allAnswers = data;
    },

    SET_ALL_COMMENTS(state, data) {
      if (data) {
        console.log('SET_ALL_COMMENTS');

        console.log('SET', data);
        console.log('COMMENTS', data.comments);
        data.comments.forEach((dd) => {
          console.log(dd);
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
      console.log('SET_ONE_QUESTION');

      data.timeStamp = convertUnixTimeStampToString(data.timeStamp);

      state.oneQuestion = data;
    },

    SET_ALL_TAGS(state, data) {
      console.log('SET_ALL_TAGS');
      if (data) {
        state.allTags = data;
      }
    },
  },
  actions: {
    async act_getOneQuestion({ commit }, questionId) {
      console.log('act_getOneQuestion()');
      let response = {};

      try {
        response = await RestCalls.getOneQuestion(questionId);
        commit('SET_ONE_QUESTION', response);
      } catch (error) {
        console.error('act_getAllQuestions: ', error);
      }
    },

    async act_getAllQuestions({ commit }) {
      console.log('act_getAllQuestions');
      let response = {};

      try {
        response = await RestCalls.getAllQuestions();

        commit('SET_ALL_QUESTIONS', response);
      } catch (error) {
        console.error('act_getAllQuestions: ', error);
      }
    },

    async act_getAllAnswers({ commit }, questionId) {
      console.log('act_getAllAnswers');
      let response = {};
      try {
        response = await RestCalls.getAllAnswersToQuestions(questionId);

        commit('SET_ALL_ANSWERS', response);
      } catch (error) {
        console.error('act_getAllAnswers: ', error);
      }
    },

    async act_getAllComments({ commit }, questionId) {
      console.log('act_getAllComments');
      let response = {};
      try {
        response = await RestCalls.getAllCommentsToAnswers(questionId);

        if(response != null){
          commit('SET_ALL_COMMENTS', response);
        }
      } catch (error) {
        console.error('act_getAllComments: ', error);
      }
    },

    async act_getAllTags({ commit }) {
      let response;
      try {
        response = await RestCalls.getAllTags();
        commit('SET_ALL_TAGS', response);
      } catch (error) {
        console.error('act_getAllTags(): ', error);
      }
    },

    async act_creatNewQuestion({ commit }, newQuestion) {
      console.log('act_creatNewQuestion()', newQuestion);
      try {
        let response = await RestCalls.addNewQuestion(newQuestion);
        return response;
      } catch (error) {
        console.error(error);
      }
    },

    async act_addNewAnswer({ commit }, newAnswer) {
      console.log('act_addNewAnswer', newAnswer);
      try {
        let response = await RestCalls.addNewAnswer(newAnswer);
        return response;
      } catch (error) {
        console.error(error);
      }
    },

    async act_addNewComment({ commit }, newComment) {
      console.log('act_addNewComment', newComment);
      try {
        let response = await RestCalls.addNewComment(newComment);
        return response;
      } catch (error) {
        console.error(error);
      }
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
