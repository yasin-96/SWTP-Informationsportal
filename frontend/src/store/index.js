import Vue from 'vue';
import Vuex from 'vuex';
import createPersistedState from "vuex-persistedstate";

import RestCalls from '@/services/RestCalls';
import { convertUnixTimeStampToString } from '@/services/Utils';

import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';

/**
 * websocketPort - The port indicates that this is the backend and not the gateway.
 * websocketName - The name which is used to create exactly such a connection to the backend using the name
 * websocketSubcription - The end point for the registration of the subscription
 * stompEndPoint - The end point that is subsequently addressed in the backend to process the data.
 */
const websocketPort = process.env.VUE_APP_WEBSOCKET_PORT;
const websocketName = process.env.VUE_APP_WEBSOCKET_NAME;
const websocketURL = process.env.VUE_APP_WS_URL;
const websocketSubcription = process.env.VUE_APP_WEBSOCKET_SUBCRIPTION;
const stompEndPoint = process.env.VUE_APP_STOMPENDPOINT;

/**
 * To store the message over the websockets, we used a lib that
 * stores everything in the local storage of the browser.
 * 
 * key: this is the key to find the data in the local storage 
 * paths: this is vuex-store object that will be referenced to save each changes in the local storage
 */
const localStore = createPersistedState({
  key: 'info-portal-notification',
  paths: ['wsMessages']
})


Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    //questions
    allQuestions: {},
    oneQuestion: {},
    questionsBasedOnTopics: [],
    activeQuestions: [],
    allQueryData: [],
    reloadQuestions: false,

    //anwser
    allAnswers: {},
    oneAnswer: {},

    //comments
    allComments: [],
    reloadComments: false,

    //tags
    allTags: [],
    topicsBasedOnTags: [],

    //user
    currentUser: {},

    //stomp && websocket connection to backenc
    clientConnection: null,
    socket: null,
    stompClient: null,

    //all messages from websockets will be saved here
    wsMessages: [],
  },
  mutations: {
    //#region questions
      //all mutation for questions

      /**
       * 
       * @param {Object} state 
       * @param {Object} data 
       */
      SET_ALL_QUESTIONS(state, data) {
        console.debug('SET_ALL_QUESTIONS');

        Object.keys(data).forEach((d) => {
          data[d].timeStamp = convertUnixTimeStampToString(data[d].timeStamp);
        });

        state.allQuestions = data;
      },

      /**
       * 
       * @param {Object} state 
       * @param {Obejct} data 
       */
      SET_ONE_QUESTION(state, data) {
        console.debug('SET_ONE_QUESTION');

        data.timeStamp = convertUnixTimeStampToString(data.timeStamp);

        state.oneQuestion = data;
      },

      /**
       * 
       * @param {Object} state 
       * @param {*} data 
       */
      SET_QUESTIONS_BASED_ON_TOPICS(state, data) {
        if (data) {
          Object.keys(data).forEach((d) => {
            data[d].timeStamp = convertUnixTimeStampToString(data[d].timeStamp);
          });
          state.questionsBasedOnTopics = data;
        }
      },

      /**
       * 
       * @param {Object} state 
       * @param {Object} data 
       */
      SET_MOST_ACTIV_QUESTIONS(state, data) {
        if (data) {
          Object.keys(data).forEach((d) => {
            data[d].timeStamp = convertUnixTimeStampToString(data[d].timeStamp);
          });

          state.activeQuestions = data;
        }
      },

      /**
       * 
       * @param {Object} state 
       * @param {Object} data 
       */
      SET_ALL_QUERY_DATA(state, data) {
        console.debug('SET_ALL_QUERY_DATA');
        if (data) {
          Object.keys(data).forEach((d) => {
            data[d].timeStamp = convertUnixTimeStampToString(data[d].timeStamp);
          });
        }
        state.allQueryData = data;
      },

    //#endregion question

    //#region answer
      //all mutation for answers
      
      /**
       * 
       * @param {Obejct} state 
       * @param {Obejct} data 
       */
      SET_ALL_ANSWERS(state, data) {
        console.debug('SET_ALL_ANSWERS');
        state.allAnswers = data;
      },

      /**
       * 
       * @param {Object} state 
       * @param {Object} data 
       */
      SET_ONE_ANSWER_TO_QUESTION(state, data) {
        console.debug('SET_ONE_ANSWER_TO_QUESTION');
        if (data) {
          state.oneAnswer = data;
        }
      },
    //#endregion answers

    //#region comments
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

    //#endregion comments

    //#region tags

    //all tags -> topics
    SET_ALL_TAGS(state, data) {
      console.debug('SET_ALL_TAGS');
      state.allTags = data;
    },

    SET_TOPICS(state, data) {
      if (data) {
        Object.keys(data).forEach((d) => {
          data[d].timeStamp = convertUnixTimeStampToString(data[d].timeStamp);
        });
        state.topicsBasedOnTags = data;
      }
    },

    //#endregion tags

    SET_USER_INFO(state, data) {
      state.currentUser = data;
    },

    ADD_WS_MESSAGE(state, wsReponse) {

      if(wsReponse){
        wsReponse.timestamp = convertUnixTimeStampToString(wsReponse.timestamp);
        state.wsMessages.push(wsReponse);
      }

    },

    REMOVE_WS_MESSAGE(state, index) {
      if(state.wsMessages) {
        state.wsMessages.pop(index);
        // state.wsMessages.forEach(ws => {
        //   console.warn(ws)
        //   // ws.pop(index);
        // })
      }
    }
  },
  actions: {
    //#region question

    //questions
    async act_getAllQuestions({ commit, dispatch }) {
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
          commit('SET_QUESTIONS_BASED_ON_TOPICS', response);
        })
        .catch((error) => {
          console.error(error);
        });
    },

    async act_getMostActiveQuestions({ commit }) {
      console.log('act_getMostActiveQuestions()');
      await RestCalls.getMostActiveQuestions()
        .then((response) => {
          commit('SET_MOST_ACTIV_QUESTIONS', response);
        })
        .catch((error) => {
          console.error(error);
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

    //#endregion question

    //#region answer

    //answers
    async act_getAllAnswers({ commit, dispatch }, questionId) {
      console.log('act_getAllAnswers', questionId);
      await RestCalls.getAllAnswersToQuestions(questionId)
        .then((response) => {
          if (response) {
            response.listOfAnswers.forEach((d) => {
              console.debug('ANSWERS', d);
              d.timeStamp = convertUnixTimeStampToString(d.timeStamp);
            });

            commit('SET_ALL_ANSWERS', response);
          }
        })
        .catch((error) => {
          console.error('act_getAllAnswers: ', error);
        });
    },

    async act_getOneAnswerToQuestion({ commit }, id) {
      console.log('act_getOneAnswerToQuestion', id);
      await RestCalls.getOneAnswerToQuestion(id)
        .then((response) => {
          if (response != null) {
            commit('SET_ONE_ANSWER_TO_QUESTION', response);
          }
        })
        .catch((error) => {
          console.error('act_getOneAnswerToQuestion: ', error);
        });
    },

    async act_updateAnswerFromQuestion({}, updatedAnswerToQuestion) {
      console.log('act_updateAnswerFromQuestion');
      return await RestCalls.setOneAnswerToQuestion(updatedAnswerToQuestion)
        .then((response) => {
          return response;
        })
        .catch((error) => {
          console.error('act_updateAnswerFromQuestion: ', error);
        });
    },

    async act_addNewAnswer({ dispatch }, newAnswer) {
      console.log('act_addNewAnswer', newAnswer);
      await RestCalls.addNewAnswer(newAnswer)
        .then(async (response) => {
          // return response;
          await dispatch('act_getAllAnswers', newAnswer.id);
        })
        .catch((error) => {
          console.error(error);
          return null;
        });
    },

    async increaseRatingForAnswer({ state, dispatch }, answer) {
      console.log('increaseAnswerRating', answer);

      await RestCalls.increaseAnswerRating(answer)
        .then(async () => {
          state.allAnswers = [];
          await dispatch('act_getAllAnswers', answer.id);
        })
        .catch((error) => {
          console.error(error);
        });
    },

    //#endregion answer

    //#region comment
    //comments
    async act_getAllComments({ commit }, answerId) {
      console.log('act_getAllComments');
      await RestCalls.getAllCommentsToAnswers(answerId)
        .then((response) => {
          if (response && response.comments) {
            response.comments.forEach((dd) => {
              console.debug(dd);
              dd.timestamp = convertUnixTimeStampToString(dd.timestamp);
            });
          }

          commit('SET_ALL_COMMENTS', response);
        })
        .catch((error) => {
          console.error('act_getAllComments: ', error);
        });
    },

    async act_addNewComment({ state, dispatch }, newComment) {
      console.log('act_addNewComment', newComment);
      await RestCalls.addNewComment(newComment)
        .then(async (response) => {
          console.log('response', response);
          state.allComments = [];
          await dispatch('act_getAllComments', newComment.id);
        })
        .catch((error) => {
          console.error(error);
        });
    },

    async act_increaseCommentRating({ state, dispatch }, comment) {
      console.log('act_increaseCommentRating', comment);
      await RestCalls.increaseCommentRating(comment)
        .then(async () => {
          //reset all comments
          state.allComments = [];
          await dispatch('act_getAllComments', comment.id);
        })
        .catch((error) => {
          console.error(error);
        });
    },

    //#endregion comment

    //#region tags

    //tags
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

    async act_getCurrentTopics({ commit }) {
      console.log('act_getCurrentTopics()');
      await RestCalls.getCurrentTopics()
        .then((response) => {
          commit('SET_TOPICS', response);
        })
        .catch((error) => {
          console.error(error);
        });
    },

    //#endregion tags

    async act_getUserInfo({ commit }) {
      await RestCalls.getUserInfo().then((response) => {
        commit('SET_USER_INFO', response);
      });
    },

    async act_getUserNameFromID({}, id) {
      return await RestCalls.getUserNameFromId(id)
        .then((response) => {
          console.warn('STORE ID->NAME', response);
          return response.userName;
        })
        .catch((error) => {
          console.error(error);
          return id;
        });
    },

    act_createConnectSocketAndStompClient({ state, commit, dispatch }) {
      // commit('CREATE_NEW_SOCKET', websocketURL);
      // commit('CREATE_NEW_STOMP_CLIENT');

      if (websocketURL) {
        console.warn('WS:', websocketURL);
        state.socket = new SockJS(websocketURL);

        if (state.socket) {
          state.stompClient = Stomp.over(state.socket);

          state.stompClient.connect(
            {},
            async (frame) => {
              state.clientConnection = true;
              console.log('CON:', state.clientConnection);
              console.log('BIN FRAME', frame);

              await state.stompClient.subscribe(websocketSubcription, (response) => {
                //TODO das hier muss in den Store
                //TODO speichern auch im local storage
                let parsedData = JSON.parse(`${response.body}`);

                if (parsedData.body) {
                  
                  // nur hinzufügen, wenn der user drinne ist
                  parsedData.body.listOfUsers.forEach(async (pd) => {
                    console.log('PD', pd);
                    if (pd.id === state.currentUser.id) {

                      //save to store
                      commit('ADD_WS_MESSAGE', parsedData.body );
                     
                    }
                  });

                  console.log('WS:', JSON.parse(`${response.body}`));
                }
              });
            },
            (error) => {
              console.log(error);
              state.clientConnection = false;
            }
          );
        } else {
          state.socket = null;
          state.stompClient = null;
        }
      }
    },

    act_disconnectStompClient({ state }) {
      if (state.stompClient) {
        this.stompClient.disconnect();
      }
    },

    act_toggleConnectionState({ state, dispatch }) {
      state.clientConnection ? dispatch('act_disconnectStompClient') : dispatch('act_createConnectSocketAndStompClient');
    },

    async act_sendStompMessage({ state }, wsMessages) {
      if (wsMessages) {
        console.log('Send message:', wsMessages);
        if (state.stompClient && state.stompClient.connected) {
          console.warn('send to msg');
          await state.stompClient.send(stompEndPoint, JSON.stringify(wsMessages), {});
        }
      }
    },



    async loadNotificationFromLocalStorage({commit}){
      let loadNotification = await this.$localStore.get('notify');

      let parsedJsonObject = JSON.parse(loadNotification);

    },

    act_removeOneWSMessage({commit}, index){
      commit('REMOVE_WS_MESSAGE', index);
    }
  },

  getters: {
    /**
     * returns from store all answers for one question
     */
    getListWithAnswers: (state) => {
      return state.allAnswers.listOfAnswers;
    },

    /**
     * returns from store all comments for all answers
     */
    getListWithComments: (state) => {
      return state.allComments.comments;
    },

    /**
     * returns from store all tags that currently are available
     */
    getAllTagName: (state) => {
      return state.allTags.map((n) => n.name);
    },

    /**
     * returns the uuid from logged in user
     */
    getUserId: (state) => {
      return state.currentUser._id;
    },

    /**
     * The first letters are taken from the first/last name and
     * used as avatar in the navigation bar
     */
    getFirstLetterFromUser: (state) => {
      if (state.currentUser.name) {
        let raw = state.currentUser.name.split(' ');
        return `${raw[0].charAt(0)}${raw[1].charAt(0)}`;
      }
      return '';
    },

    /**
     * returns nicksname from logged in user
     */
    getUsersPreferedName: (state) => {
      return state.currentUser.preferred_username;
    },
  },
  modules: {},

  //load the lib to store websocket messages in local storage of the 
  plugins: [localStore]
});
