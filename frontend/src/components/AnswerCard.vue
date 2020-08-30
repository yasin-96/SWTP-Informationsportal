<template>
  <b-container v-if="nId">
    <b-card
      header-bg-variant="white"
      footer-bg-variant="white"
      id="nId"
      class="mt-1 shadow rounded"
      :key="nId"
    >
      <!-- Information about user & creation date -->
      <template v-slot:header>
        <b-container>
          <b-row class="justify-content-left">
            <b-col>
              <b-button-group>
                <b-button disabled variant="white">
                  <fai icon="user-circle" size="lg" />
                </b-button>
                <b-button size="sm" disabled variant="white">
                  <strong>Answer</strong>
                  created by {{ aUserName }}
                  <small></small>
                </b-button>
              </b-button-group>
            </b-col>
          </b-row>
        </b-container>
      </template>

      <template v-slot:footer>
        <b-button size="md" disabled variant="white">
          <small>
            <fai icon="clock" />
            {{ aDate }}
          </small>
        </b-button>
      </template>

      <!-- One answer to the question -->
      <Editor ref="mde" class="answerCardEditor" v-model="aContent" :configs="mdeConfig" />

      <!-- Edit or like this Answer -->
      <b-button-group>
        <b-button size="sm" variant="info" @click="increaseRating()">
          <fai icon="thumbs-up" />
        </b-button>
        <b-button size="sm" disabled variant="info">{{ aRating }}</b-button>
        <b-button v-if="userEdit" size="sm" @click="editAnswer()">
          <fai icon="edit"></fai>
        </b-button>
      </b-button-group>

      <!-- Area for all Comments -->
      <b-card-body>
        <b-container>
          <CommentCard :cComments="allComments" :cId="cId" :qId="nId" />
        </b-container>
        <b-container></b-container>
      </b-card-body>
    </b-card>
  </b-container>
</template>

<script>
import CommentCard from '@/components/CommentCard';
import { mapState, mapActions } from 'vuex';
import VueSimplemde from 'vue-simplemde';

import { BIconClock, BIconChatSquareDots } from 'bootstrap-vue';

export default {
  name: 'AnswerCard',
  components: {
    CommentCard,
    Editor: VueSimplemde,
  },
  props: {
    /**
     * The ID of the answer
     */
    nId: {
      type: String,
      required: true,
    },
    /**
     * The id for all comments to this answer
     */
    cId: {
      type: String,
      required: true,
    },
    /**
     * Main content of the answer
     */
    aContent: {
      type: String,
      default: '',
    },
    /**
     * The rating of this answer
     */
    aRating: {
      type: Number,
      default: 0,
    },
    /**
     * The timestamp of this answer
     */
    aDate: {
      type: String,
      default: '',
    },
    /**
     * The id of the user who created this answer
     */
    // aUserId: {
    //   type: String,
    //   required: true,
    // },
    /**
     * The creator of this answer
     */
    aUserName: {
      type: String,
      required: true,
    },
    /**
     * This enables the option to edit the answer, 
     * if the user has created it
     */
    userEdit: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      // All changes for the answer will are temporarily stored here
      

      //settings for markdown editor
      mdeConfig: {
        toolbar: null,
        shortcuts: {},
      },
    };
  },
  beforeMount() {
    this.loadData();
  },
  async mounted() {
    this.$refs.mde.simplemde.togglePreview();
  },
  methods: {
    /**
     * If the property was passed by the response, the comments can also be loaded.
     */
    async loadData() {
      if (this.cId) {
        await this.$store.dispatch('act_getAllComments', this.cId);
      }
    },

    /**
     * Changes the current question by increasing the counter. 
     * After that it is adjusted in the store
     */
    async increaseRating() {

      const changeAnswerObject = {
        id: this.nId,
        answerId: this.cId,
        rating: Number(this.aRating) + 1,
      }

      //TODO das geht noch nicht in place 
      console.warn('INCRESE Rating AWT', changeAnswerObject);
      await this.$store.dispatch('act_updateAnswerFromQuestion', changeAnswerObject);
    },

    /**
     * If the user id matches the author of the question it can be edited. 
     * Here with the user brought to the editing page
     */
    editAnswer() {
      this.$router
        .push({
          path: '/answer/edit',
          query: {
            qId: `${this.nId}`,
            aId: `${this.cId}`,
          },
        })
        .catch((err) => {});
    },
  },
  computed: {
    ...mapActions(['act_getAllComments', 'act_updateAnswerFromQuestion']),
    ...mapState(['allComments']),
  },
};
</script>

<style scoped>
.answerCardEditor >>> .CodeMirror {
  min-height: 0;
  max-height: 250px;
}

.answerCardEditor >>> .CodeMirror {
  background-color: #fff;
  border: none;
}

.answerCardEditor >>> .editor-preview,
.answerCardEditor >>> .editor-preview-side {
  background: #fff;
}
</style>
