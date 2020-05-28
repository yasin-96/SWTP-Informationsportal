<template>
  <b-container>
    <b-button-group>
      <h4>Comments</h4>
      <b-button v-b-toggle="toggleId" dark variant="white">
        <b-icon-toggle-on v-if="toggelCommentBtn" @click="changeToggelIcon()"></b-icon-toggle-on>
        <b-icon-toggle-off v-if="!toggelCommentBtn" @click="changeToggelIcon()"></b-icon-toggle-off>
      </b-button>
    </b-button-group>

    <NewContent v-if="nId" 
      :nRows="2"
      :nIsComment="true"
      :nId="nId"
    />

    <b-collapse v-if="cComments" :id="toggleId" class="mt-2">
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
    nId:{
      type: String,
      required: true,
      default: ''
    },
    cComments: {
      type: Array,
      required: true,
      default: [],
    },
  },
  beforeMount() {
    if (this.cComments) {
      this.cComments.forEach((cc, index) => {
        console.log('BM:', index, cc);
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
