<template>
  <div class="pt-4 pb-4">
    <b-button-group class="d-flex">
      <b-form-textarea v-if="nIsAnswer" v-model="contentForAnswer" placeholder="Add new answer ..." :rows="nRows" :no-resize="nResize" :size="bTextSize"></b-form-textarea>
      <b-form-textarea v-if="nIsComment" v-model="contentForComment" placeholder="Add new comment ..." :rows="nRows" :no-resize="nResize" :size="bTextSize"></b-form-textarea>
      <b-button v-if="nIsAnswer" @click="addNewAnswer()" variant="success"><fai icon="plus-circle" /></b-button>
      <b-button v-if="nIsComment" @click="addNewComment()" variant="success"><fai icon="plus-circle" /></b-button>
    </b-button-group>
  </div>
</template>

<script>
import { mapActions } from 'vuex';
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
      default: '',
    },
  },
  data() {
    return {
      newAnswer: {
        id: '',
        listOfAnswers: [],
      },
      newComment: {
        id: '',
        comments: [],
      },
      contentForAnswer: '',
      contentForComment: '',
    };
  },
  beforeMount() {

    // console.warn('ROUNTER -> ', this.$router.history.current.params.id);
    console.warn('ROUNTER -> ', this.id);
    this.newAnswer.id = this.id;
    this.newComment.id = this.id;
  },

  computed: {
    ...mapActions(['act_addNewAnswer', 'act_addNewComment']),
  },

  methods: {
    async addNewAnswer() {
      if (this.contentForAnswer) {
        this.newAnswer.listOfAnswers.push({
          content: this.contentForAnswer,
          rating: 0,
          timeStamp: Date.parse(new Date()),
        });
        console.log('HIER:!!', this.newAnswer);
        let resp = await this.$store.dispatch('act_addNewAnswer', this.newAnswer);
        // this.$store.dispatch('act_switchLoadingStateForAnswer', true);
        //this.$router.go(`/question/${resp.id}`);
      }
    },

    async addNewComment() {
      if (this.contentForComment) {
        this.newComment.comments.push({
          content: this.contentForComment,
          rating: 0,
          timestamp: Date.parse(new Date()), //TODO
        });
        console.log('HIER:!!', this.newComment);
        let resp = await this.$store.dispatch('act_addNewComment', this.newComment);
        this.$router.go(`/question/${resp.id}`);
      }
    },
  },
};
</script>

<style></style>
