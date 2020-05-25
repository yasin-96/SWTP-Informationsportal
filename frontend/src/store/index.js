import Vue from 'vue';
import Vuex from 'vuex';
import RestCalls from '@/services/RestCalls';
import { convertUnixTimeStampToString } from '@/services/Utils';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    allQuestions: {},
    allAnswers: {},
    allComments: {},

    oneQuestion: {}
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
        console.log("DATA D", d);
        d.timeStamp = convertUnixTimeStampToString(d.timeStamp);
      });

      state.allAnswers = data;
    },

    SET_ALL_COMMENTS(state, data) {
      console.log('SET_ALL_COMMENTS');

      console.log("SET",data)
      console.log("SET comments", data.comments);
      data.comments.forEach((dd) => {
        console.warn(dd);
        dd.timestamp = convertUnixTimeStampToString(dd.timestamp);
      });

      state.allComments = data;
    },

    SET_ONE_QUESTION(state, data) {
      console.log('SET_ONE_QUESTION');

      data.timeStamp = convertUnixTimeStampToString(data.timeStamp);

      state.oneQuestion = data;
    },

    ADD_NEW_COMMENT(state, data) {

    }
  },
  actions: {
    async act_getOneQuestion({ commit }, questionId) {
      console.log('act_getOneQuestion');
      let response = {};

      try {
        response = await RestCalls.getOneQuestion(questionId);

        commit('SET_ONE_QUESTION', response);
      } catch (error) {
        console.error('act_getAllQuestions: ', error.error);
      }
    },

    async act_getAllQuestions({ commit }) {
      console.log('act_getAllQuestions');
      let response = {};

      try {
        response = await RestCalls.getAllQuestions();

        commit('SET_ALL_QUESTIONS', response);
      } catch (error) {
        console.error('act_getAllQuestions: ', error.error);
      }
    },

    async act_getAllAnswers({ commit }, questionId) {
      console.log('act_getAllAnswers');
      let response = {};
      try {
        response = await RestCalls.getAllAnswersToQuestions(questionId);

        commit('SET_ALL_ANSWERS', response);
      } catch (error) {
        console.error('act_getAllAnswers: ', error.error);
      }
    },

    async act_getAllComments({ commit }, questionId) {
      console.log('act_getAllComments');
      let response = {};
      try {
        response = await RestCalls.getAllCommentsToAnswers(questionId);

        commit('SET_ALL_COMMENTS', response);
      } catch (error) {
        console.error('act_getAllComments: ', error.error);
      }
    },


    act_addNewCommentToAnswer({commit}, newComment){
      if(newComment) {
        let oldState = Object.assign(
            {}, this.state.allComments
        );

        oldState.allComments.push(newComment);

        //send to backend
      }
    }
  },
  getters: {
    getListWithAnswers: (state) => {
      return state.allAnswers.listOfAnswers;
    },

    getListWithComments: (state) => {
      return state.allComments.comments;
    }
  },
  modules: {}
});
