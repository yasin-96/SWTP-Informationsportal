import Vue from "vue";
import Vuex from "vuex";
import RestCalls from "@/services/RestCalls";
import { convertUnixTimeStampToString } from '@/services/Utils';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    allQuestions: {},
    allAnswers: {},
    allComments: {},

    oneQuestion:{},
    oneAnswers:{},
    oneComments:{},
  },
  mutations: {
    SET_ALL_QUESTIONS(state, data){
      console.log("SET_ALL_QUESTIONS");

      Object.keys(data).forEach((d) => {
          data[d].timeStamp = convertUnixTimeStampToString(data[d].timeStamp);
      })

      state.allQuestions = data;
    },

    SET_ALL_ANSWERS(state, data) {
      console.log("SET_ALL_ANSWERS");

      Object.keys(data).forEach((d) => {
        data[d].timeStamp = convertUnixTimeStampToString(data[d].timeStamp);
      });

      state.allAnswers = data;
    },

    SET_ALL_COMMENTS(state, data) {
      console.log("SET_ALL_COMMENTS");

      Object.keys(data).forEach((d) => {
        data[d].timeStamp = convertUnixTimeStampToString(data[d].timeStamp);
      });

      state.allComments = data;
    },

    SET_ONE_QUESTION(state, data){
      console.log("SET_ONE_QUESTION");

      // Object.keys(data).forEach((d) => {
      //     data[d].timeStamp = convertUnixTimeStampToString(data[d].timeStamp);
      // })

      state.oneQuestion = data;
    },

    SET_ONE_ANSWER(state, data) {
      console.log("SET_ONE_ANSWER");

      // Object.keys(data).forEach((d) => {
      //   data[d].timeStamp = convertUnixTimeStampToString(data[d].timeStamp);
      // });

      state.oneAnswers = data;
    },

    SET_ONE_COMMENT(state, data) {
      console.log("SET_ONE_COMMENT");

      Object.keys(data).forEach((d) => {
        data[d].timeStamp = convertUnixTimeStampToString(data[d].timeStamp);
      });

      state.oneComments = data;
    },



  },
  actions: {
    async act_getAllQuestions( { commit }) {
      console.log("act_getAllQuestions")
      let response = {};

      try {
        response = await RestCalls.getAllQuestions();

        commit("SET_ALL_QUESTIONS", response);

      } catch(error) {
        console.error("act_getAllQuestions: ", error);
      }
    },

    async act_getAllAnswers( { commit }, questionId) {
      console.log("act_getAllAnswers")
      let response = {};
      try {
        response = await RestCalls.getAllAnswersToQuestions(query);

        commit("SET_ALL_ANSWERS", response);

      } catch(error) {
        console.error("act_getAllAnswers: ", error.error);
      }
    },


    async act_getAllComments( { commit }, questionId) {
      console.log("act_getAllComments")
      let response = {};
      try {
        response = await RestCalls.getAllCommentsToAnswers(query);

        commit("SET_ALL_COMMENTS", response);

      } catch(error) {
        console.error("act_getAllComments: ", error.error);
      }
    }
  },
  modules: {}
});
