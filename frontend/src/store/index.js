import Vue from "vue";
import Vuex from "vuex";
import RestCalls from "@/services/RestCalls";
import { convertUnixTimeStampToString } from '@/services/Utils';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    allQuestions: {},

    allAnswers: {},

    allComments: {}

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
    }
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
        console.error("act_getAllQuestions: ", error.error);
      }
    }
  },
  modules: {}
});
