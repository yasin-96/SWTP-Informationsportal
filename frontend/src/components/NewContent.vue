<template>
  <div class="mt-4 mb-4" v-if="id">
    <b-button-group class="d-flex">
      <!-- Depending on the property, the addition of a new answer or comment is shown  -->

      <!-- Card for adding new Answer  -->
      <b-form-textarea v-if="nIsAnswer" v-model="contentForAnswer" :placeholder="nAnswerText" :rows="nRows" :no-resize="nResize" :size="bTextSize"></b-form-textarea>

      <!-- Card for adding new Comment  -->
      <b-form-textarea v-if="nIsComment" v-model="contentForComment" :placeholder="nCommentText" :rows="nRows" :no-resize="nResize" :size="bTextSize"></b-form-textarea>

      <b-button v-if="nIsAnswer" @click="addNewAnswer()" :variant="nSendColor"><fai :icon="nSendIcon" /></b-button>
      <b-button v-if="nIsComment" @click="addNewComment()" :variant="nSendColor"><fai :icon="nSendIcon" /></b-button>
    </b-button-group>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
export default {
  name: 'NewContent',
  props: {
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
    nAnswerText: {
      type: String,
      default: 'Add new answer ...',
    },
    nIsComment: {
      type: Boolean,
      default: false,
    },
    nCommentText: {
      type: String,
      default: 'Add new comment ..',
    },
    id: {
      type: String,
      required: true,
    },
    qId: {
      type: String,
    },
    nSendColor: {
      type: String,
      default: 'success',
    },
    nSendIcon: {
      type: String,
      default: 'plus-circle',
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
          answerId: '',
          isAnswer: true,
          isComment: false,
          minimalUser: {
            userId: this.getUserId,
            userName: this.getUsersPreferedName,
          },
        };

        console.warn('newWsMessage', newWsMessage);
        await this.$store.dispatch('act_sendStompMessage', newWsMessage);

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
          },
        };
        
        newWsMessage.isComment = true;
        newWsMessage.minimalUser.userId = this.getUserId;
        newWsMessage.minimalUser.userName = this.getUsersPreferedName;

        await this.$store.dispatch('act_sendStompMessage', newWsMessage);

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
