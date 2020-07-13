<template>
  <div>
    <!-- Add new comment -->
    <NewContent v-if="cId" :nRows="2" :nIsComment="true" :id="cId" :qId="qId" />

    <b-card no-body class="mb-1" v-if="hasComments">
      <!-- Toggle all comments -->
      <b-card-header header-tag="header" class="p-1" role="tab" variant="white">
        <b-button block v-b-toggle="toggleId" variant="white"> {{ toggleText }} </b-button>
      </b-card-header>


      <b-collapse :id="toggleId" ref="b-collaps-comments">
        <div v-for="(cc, i) in cComments" :key="i" flex-column align-items-start>
          <div v-for="c in cc.comments" :key="c.id">
            <b-card class="rounded-0" header-tag="header" footer-bg-variant="white" footer-border-variant="white" v-if="cc.id === cId">
              <b-card-sub-title>
                <div class="d-flex justify-content-between">
                  <h5 class=""><fai icon="comment-alt" /> {{ c.userName }} </h5>
                </div>
              </b-card-sub-title>
              <b-card-text>
                <b-form-textarea id="textarea-plaintext" plaintext :rows="minCommentRows" :value="c.content"> </b-form-textarea>
              </b-card-text>

              <template v-slot:footer>
                <div class="d-flex justify-content-between">
                  <b-button-group>
                    <b-button size="sm" variant="info" @click="increaseRating(c)">
                      <fai icon="thumbs-up" />
                    </b-button>
                    <b-button size="sm" disabled variant="info">
                      {{ c.rating }}
                    </b-button>
                  </b-button-group>
                  <small class="text-muted">{{ c.timestamp }}</small>
                </div>
              </template>
            </b-card>
          </div>
        </div>
      </b-collapse>
    </b-card>
  </div>
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
    qId: {
      type: String,
      required: true,
    },
    cId: {
      type: String,
      required: true,
    },
    cComments: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      minCommentRows: 3,
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
      toggleText: 'Show Comments',
      iCounter: 0,
    };
  },
  async beforeMount() {
    if (this.cComments) {
      this.cComments.forEach((cc, index) => {
        console.log('BM:', index, cc);
        this.toggleCounter.push(`collapse-comment-id-${this.maxComments++}`);
      });
    }
  },
  methods: {
    changeText() {
      this.toggelCommentBtn = this.toggelCommentBtn ? false : true;
      this.toggleText = this.toggleText === 'Show Comments' ? 'Hide Comments' : 'Show Comments';
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
    async increaseRating(comment, cardId) {
      let newRating = Number(comment.rating) + 1;

      console.warn('OBJ', comment);

      let newComment = {
        id: this.cId,
        comments: [
          {
            id: comment.id,
            content: comment.content,
            userName: comment.userName,
            rating: newRating,
            timestamp: Date.parse(new Date()),
          },
        ],
        timestamp: Date.parse(new Date()),
      };
      await this.$store.dispatch('act_increaseCommentRating', newComment);
    },
  },
  computed: {
    ...mapActions(['act_increaseCommentRating']),
    hasComments() {
      return Object.keys(this.cComments).length > 0 ? true : false;
    },
  },
};
</script>

//style lang="scss">
// $border-radius-root: none;
// </style>
