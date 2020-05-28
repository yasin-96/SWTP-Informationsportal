<template>
  <b-container class="pt-4 pb-4">
    <b-button-group class="d-flex">
      <b-form-textarea v-if="nIsAnswer" v-model="contentForAnswer" placeholder="Add new answer ..." :rows="nRows" :no-resize="nResize" :size="bTextSize"></b-form-textarea>
      <b-form-textarea v-if="nIsComment" v-model="contentForComment" placeholder="Add new comment ..." :rows="nRows" :no-resize="nResize" :size="bTextSize"></b-form-textarea>
      <b-button v-if="nIsAnswer" @click="addNewAnswer()">{{ bText }}</b-button>
      <b-button v-if="nIsComment" @click="addNewComment()">{{ bText }}</b-button>
    </b-button-group>
  </b-container>
</template>

<script>

import {mapActions} from 'vuex'
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
    bText: {
      type: String,
      default: 'Send',
    },
    nResize: {
      type: Boolean,
      default: true,
    },
    bTextSize: {
        type: String,
        default: 'sm'
    },
    nIsAnswer: {
        type: Boolean,
        default: false,
    },
    nIsComment: {
        type: Boolean,
        default: false,
    },
    nId: {
      type: String,
      default: "",
    }
  },
  mounted() {
    console.warn("ROUNTER -> ", this.$router.history.current.params.id);
    this.newAnswer.id = this.$router.history.current.params.id;
    this.newComment.id = this.$router.history.current.params.id;
  },
  data: () => ({
    newAnswer: {
      id: '',
      listOfAnswers: []
    },
    newComment: {
      id: '',
      comments: []
    },
    contentForAnswer: "",
    contentForComment: ""
  }),
  computed: {
    ...mapActions(['act_addNewAnswer', 'act_addNewComment'])
  },
  methods: {
    async addNewAnswer(){
      if(this.contentForAnswer) {
        this.newAnswer.listOfAnswers.push({
          content: this.contentForAnswer,
          rating: 0,
          timeStamp: Date.parse(new Date()),
        });
        console.log("HIER:!!", this.newAnswer);
        let resp = await this.$store.dispatch('act_addNewAnswer', this.newAnswer);
        this.$router.go(`/question/${resp.id}`)
      }
    },

    async addNewComment(){
      if(this.contentForComment) {
        this.newComment.comments.push({
          content: this.contentForComment,
          rating: 0,
          timestamp: Date.parse(new Date()),
        });
        console.log("HIER:!!", this.newComment);
        let resp = await this.$store.dispatch('act_addNewComment', this.newComment);
        this.$router.go(`/question/${resp.id}`)
      }
    }
  }
};
</script>

<style></style>
