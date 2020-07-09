<template>
  <div class="pt-4 pb-4" v-if="id">
    <b-button-group class="d-flex">
      <!-- Depending on the property, the addition of a new answer or comment is shown  -->

      <!-- Card for adding new Answer  -->
      <b-form-textarea v-if="nIsAnswer" v-model="contentForAnswer" placeholder="Add new answer ..." :rows="nRows" :no-resize="nResize" :size="bTextSize"></b-form-textarea>

      <!-- Card for adding new Comment  -->
      <b-form-textarea v-if="nIsComment" v-model="contentForComment" placeholder="Add new comment ..." :rows="nRows" :no-resize="nResize" :size="bTextSize"></b-form-textarea>

      <b-button v-if="nIsAnswer" @click="addNewAnswer()" variant="success"><fai icon="plus-circle" /></b-button>
      <b-button v-if="nIsComment" @click="addNewComment()" variant="success"><fai icon="plus-circle" /></b-button>
    </b-button-group>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
// import send from '@/mixins/socketStomp.js';
// import connect from '@/mixins/socketStomp.js';
// import disconnect from '@/mixins/socketStomp.js';
// import tickleConnection from '@/mixins/socketStomp.js';

export default {
  name: 'NewContent',
  props: {
    nPlaceholder: {
      type: String,
      default: 'asd',
    },
    nRows: {
      type: Number,
      default: 3,
    },
    nResize: {
      type: Boolean,
      default: true,
    },
    bTextSize: {
      type: String,
      default: 'sm',
    },
    nIsAnswer: {
      type: Boolean,
      default: false,
    },
    nIsComment: {
      type: Boolean,
      default: false,
    },
    id: {
      type: String,
      required: true,
    },
    qId: {
      type: String,
    },
  },
  data() {
    return {
      newAnswer: {
        id: this.id,
        listOfAnswers: [],
      },
      newComment: {
        id: this.id,
        comments: [],
      },
      contentForAnswer: '',
      contentForComment: '',
      // newWsMessage: {
      //   questionId: this.id,
      //   answerId: this.id,
      //   isAnswer: false,
      //   isComment: false,
      //   minimalUser: {
      //     userId: '',
      //     userName: '',
      //   }
      // },
    };
  },
  methods: {
    /**
     *
     */
    async addNewAnswer() {
      if (this.contentForAnswer) {
        this.newAnswer.listOfAnswers.push({
          content: this.contentForAnswer,
          rating: 0,
          timeStamp: Date.parse(new Date()),
        });
        console.log('HIER:!!', this.newAnswer);

        //add the answer
        await this.$store.dispatch('act_addNewAnswer', this.newAnswer);

        // websocket message
        let newWsMessage = {
          questionId: this.id,
          answerId: "",
          isAnswer: true,
          isComment: false,
          minimalUser: {
            userId: this.getUserId,
            userName: this.getUsersPreferedName,
          }
        }


        // this.newWsMessage.isAnswer = true;
        // this.newWsMessage.minimalUser.userId = this.getUserId;
        // this.newWsMessage.minimalUser.userName = this.getUsersPreferedName;

        await this.$store.dispatch('act_sendStompMessage', this.newWsMessage);

        //scroll to this answer
        window.scrollTo(0, document.body.scrollHeight);

        // clear ansers old content
        this.contentForAnswer = '';
        this.newAnswer.listOfAnswers = [];
        // this.newWsMessage.isAnswer = false;
      }
    },

    /**
     *
     */
    async addNewComment() {
      if (this.contentForComment) {
        this.newComment.comments.push({
          content: this.contentForComment,
          rating: 0,
          timestamp: Date.parse(new Date()), //TODO
        });
        console.log('HIER:!!', this.newComment);

        //add the comments
        await this.$store.dispatch('act_addNewComment', this.newComment);

        // websocket message

        let newWsMessage = {
          questionId: this.qId,
          answerId: this.id,
          isAnswer: false,
          isComment: true,
          minimalUser: {
            userId: this.getUserId,
            userName: this.getUsersPreferedName,
          }
        }
        this.newWsMessage.isComment = true;
        this.newWsMessage.minimalUser.userId = this.getUserId;
        this.newWsMessage.minimalUser.userName = this.getUsersPreferedName;

        await this.$store.dispatch('act_sendStompMessage', this.newWsMessage);

        //clear comments old value
        this.contentForComment = '';
        this.newComment.comments = [];
        // this.newWsMessage.isComment = false;
      }
    },
  },
  computed: {
    ...mapActions(['act_addNewAnswer', 'act_addNewComment', 'act_getAllAnswers', 'act_getAllComments']),
    ...mapGetters(['getUsersPreferedName', 'getUserId']),
  },
};
</script>

<style></style>
