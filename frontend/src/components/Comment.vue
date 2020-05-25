<template>
  <b-container>
    <b-button-group>
      <h4>Comments</h4>
      <b-button v-b-toggle="toggleId" dark variant="white">
        <b-icon-toggle-on v-if="toggelCommentBtn" @click="changeToggelIcon()"></b-icon-toggle-on>
        <b-icon-toggle-off v-if="!toggelCommentBtn" @click="changeToggelIcon()"></b-icon-toggle-off>
      </b-button>
    </b-button-group>

    <!-- <b-container class="pt-4 pb-4">
      <b-button-group class="d-flex">
        <b-form-textarea v-model="newComment.content" placeholder="Add new comment..." rows="2" no-resize></b-form-textarea>
        <b-button v-b-toggle="toggleId">Senden</b-button>
      </b-button-group>
    </b-container> -->

    <NewContent 
      :nPlaceholder='addNewComentText'
      :nRows="2"
    />

    <b-collapse :id="toggleId" class="mt-2">
      <b-list-group>
        <b-list-group-item v-for="(cc, i) in cComments" :key="i" flex-column align-items-start>
          <div class="d-flex w-100 justify-content-between">
            <h5 class="mb-1">{{ cc.userName }}</h5>
            <small class="text-muted">{{ cc.timestamp }}</small>
          </div>
          <p class="mb-1">
            {{ cc.content }}
          </p>
          <p>
            {{ cc.rating }}
          </p>
        </b-list-group-item>
      </b-list-group>
    </b-collapse>
  </b-container>
</template>

<script>
import { v4 as uuidv4 } from 'uuid';
import { BCard, BIconToggleOff, BIconToggleOn } from 'bootstrap-vue';
import NewContent from '@/components/NewContent';
export default {
  name: 'Comment',
  components: {
    NewContent,
    'b-card': BCard,
    'b-icon-toggle-off': BIconToggleOff,
    'b-icon-toggle-on': BIconToggleOn,
  },
  props: {
    cComments: {
      type: Array,
      required: true,
      default: [],
    },
  },
  beforeCreate() {
    if (this.cComments) {
      this.cComments.forEach((cc, index) => {
        console.warn('BM:', index, cc);
        this.toggleCounter.push(`collapse-comment-id-${this.maxComments++}`);
      });
    }
  },
  data: () => ({
    toggelCommentBtn: false,
    maxComments: 0,
    toggleId: uuidv4(),
    toggleCounter: new Array(),
    newComment: {
      content: '',
      userName: '',
      rating: 0,
      timestamp: Date.parse(new Date()),
    },
    addNewComentText: 'Add new comment ...'
  }),
  methods: {
    changeToggelIcon() {
      this.toggelCommentBtn = this.toggelCommentBtn ? false : true;
    },
  },
};
</script>

<style lang="scss">
  $border-radius-root: none;
</style>
