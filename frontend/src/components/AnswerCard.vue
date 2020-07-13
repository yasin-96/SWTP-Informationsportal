<template>
  <b-container v-if="nId">
    <b-card id="nId" class="mt-3" :key="nId">
      <!-- Information about user & creation date -->
      <template v-slot:header>
        <b-row class="justify-content-left">
          <b-col>
            <b-button-group>
              <h2><fai icon="user-circle" /></h2>
              <b-button size="sm" disabled variant="white"> </b-button>
              <b-button size="sm" disabled variant="white"> <strong>Antwort </strong> erstellt von {{ aUserName }} <small class="ml-3"> </small> </b-button>
              <b-button size="sm" disabled variant="white">
                <small>
                  <fai icon="clock" />
                  {{ aDate }}
                </small>
              </b-button>
            </b-button-group>
          </b-col>
        </b-row>
      </template>

      <!-- One answer to the question -->
      <Editor ref="mde" class="answerCardEditor" v-model="aContent" :configs="mdeConfig" />

      <!-- Edit or like this Answer -->
      <b-button-group>
        <b-button size="sm" variant="info" @click="increaseRating()">
          <fai icon="thumbs-up" />
        </b-button>
        <b-button size="sm" disabled variant="info">
          {{ aRating }}
        </b-button>
        <b-button v-if="userEdit" size="sm" @click="editAnswer()"><fai icon="edit"></fai></b-button>
      </b-button-group>

      <!-- Area for all Comments -->
      <b-card-body>
        <b-container>
          <CommentCard :cComments="allComments" :cId="cId" :qId="nId" />
        </b-container>
        <b-container> </b-container>
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
    nId: {
      type: String,
      required: true,
    },
    cId: {
      type: String,
      required: true,
    },
    aContent: {
      type: String,
      default: '',
    },
    aRating: {
      type: Number,
      default: 0,
    },
    aDate: {
      type: String,
      default: '',
    },
    aUserId: {
      type: String,
      required: true,
    },
    aUserName: {
      type: String,
      required: true,
    },
    userEdit: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      minCommentRows: 3,
      maxCommentRows: 10,
      changeAnswerObject: {
        id: '',
        listOfAnswers: [],
        timeStamp: Date.parse(new Date()),
      },
      newRating: Number(this.aRating) + 1,
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
     * 
     */
    async increaseRating() {
      // this.changeAnswerObject.id = this.nId || this.$localStore.get('rQuetionId');
      this.changeAnswerObject.id = this.nId;
      let currentDate = Date.parse(new Date());
      // let newRating = Number(this.aRating) + 1;
      console.warn('NEW R', this.newRating);

      this.changeAnswerObject.listOfAnswers = [
        {
          id: this.cId,
          content: this.aContent,
          rating: this.newRating,
          timeStamp: currentDate,
        },
      ];
      //TODO das geht noch nicht in place
      console.warn('INCRESE Rating AWT', this.changeAnswerObject);
      await this.$store.dispatch('increaseRatingForAnswer', this.changeAnswerObject);

      this.changeAnswerObject.listOfAnswers = [];
      //reload page
      // this.$router.go(0);
    },

    /**
     * 
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
    ...mapActions(['act_getAllComments', 'increaseRatingForAnswer']),
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
