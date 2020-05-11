import Vue from "vue";
import Vuex from "vuex";
import RestCalls from "@/services/RestCalls";
import { convertUnixTimeStampToString } from '@/services/Utils';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    allQuestions: {},

  },
  mutations: {
    SET_ALL_QUESTIONS(state, data){
      console.log("SET_ALL_QUESTIONS");

      Object.keys(data).forEach((d) => {
          data[d].timeStamp = convertUnixTimeStampToString(data[d].timeStamp);
      })

      state.allQuestions = data;
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

      
    }
  },
  modules: {}
});
