<template>
  <b-container>
    <b-button-group>
      <h4>Comments</h4>
      <b-button v-if="hasComments" v-b-toggle="toggleId" dark variant="white">
        <b-icon-toggle-on v-if="toggelCommentBtn" @click="changeToggelIcon()"></b-icon-toggle-on>
        <b-icon-toggle-off v-if="!toggelCommentBtn" @click="changeToggelIcon()"></b-icon-toggle-off>
      </b-button>
    </b-button-group>

    <NewContent v-if="cId" :nRows="2" :nIsComment="true" :id="cId" />

    <b-collapse :id="toggleId" class="mt-2">
      <b-container v-for="(cc, i) in cComments" :key="i" flex-column align-items-start>
        <b-list-group v-for="(c, j) in cc.comments" :key="j">
          <b-list-group-item v-if="cc.id === cId">
            <div class="d-flex w-100 justify-content-between">
              <h5 class="mb-1">{{ c.userName }}</h5>
              <small class="text-muted">{{ c.timestamp }}</small>
            </div>
            <p class="mb-1">
              {{ c.content }}
            </p>
            <p>
              {{ c.rating }}
            </p>
          </b-list-group-item>
        </b-list-group>
      </b-container>
    </b-collapse>
  </b-container>
</template>

<script>
import { mapState, mapActions } from 'vuex';
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
    cId: {
      type: String,
      required: true,
      default: '',
    },
    cComments: {
      type: Array,
      required: true,
      default: new Array(),
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
    iCounter: 0,
  }),
  methods: {
    changeToggelIcon() {
      this.toggelCommentBtn = this.toggelCommentBtn ? false : true;
    },
    getCommentsFromObject(objectWithComment) {
      console.warn('OBj', objectWithComment);
      console.warn('INDEX', this.iCounter);
      console.warn('DAS OBJECT ist:', objectWithComment[this.iCounter].comments);
      if (this.iCounter < this.maxComments) {
        this.iCounter++;
      }
      return objectWithComment[this.iCounter].comments;
    },
  },
  computed: {
    hasComments() {
     return Object.keys(this.cComments).length > 0 ?  true : false;
    }
  },
};
</script>

<style lang="scss">
$border-radius-root: none;
</style>
