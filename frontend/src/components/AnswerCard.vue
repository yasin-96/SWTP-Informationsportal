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
            <b-button size="sm" disabled variant="white"> <strong>Antwort </strong> erstellt von {{ aUserId }} <small class="ml-3"> </small> </b-button>
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
      <!-- <b-card class="mb-1" footer-bg-border="white" footer-border-variant="white">
        <b-card-text> -->
          
          <Editor ref="mde" class="answerCardEditor" v-model="aContent" :configs="mdeConfig"/>


          <!-- <b-form-textarea id="textarea-plaintext" plaintext :rows="minCommentRows" :value="aContent"> </b-form-textarea> -->
          <b-button-group>
            <b-button size="sm" variant="info" @click="increaseRating()">
              <fai icon="thumbs-up" />
            </b-button>
            <b-button size="sm" disabled variant="info">
              {{ aRating }}
            </b-button>
            <b-button v-if="userEdit" size="sm" @click="editAnswer()"><fai icon="edit"></fai></b-button>
          </b-button-group>
        <!-- </b-card-text>
      </b-card> -->

      <b-card-body>
        <!-- Area for all Comments -->
        <b-container>
          <Comment :cComments="allComments" :cId="cId" />
        </b-container>
        <b-container> </b-container>
      </b-card-body>
    </b-card>
  </b-container>
</template>

<script>
import Comment from '@/components/Comment';
import { mapState, mapActions } from 'vuex';
import VueSimplemde from 'vue-simplemde';

import { BIconClock, BIconChatSquareDots } from 'bootstrap-vue';

export default {
  name: 'AnswerCard',
  components: {
    Comment,
    Editor: VueSimplemde
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
      required: true
    },
    userEdit: {
      type: Boolean,
      default: false
    }
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
        // styleSelectedText: false,
        shortcuts: {},
      },
    };
  },
  beforeMount() {
    this.loadData();
  },
  mounted() {
    this.$refs.mde.simplemde.togglePreview();
  },
  methods: {
    async loadData() {
      if (this.cId) {
        await this.$store.dispatch('act_getAllComments', this.cId);
      }
    },

    async increaseRating() {
      this.changeAnswerObject.id = this.nId || this.$localStore.get('rQuetionId');
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
      console.warn('INCRESE Rating AWT', this.changeAnswerObject);
      await this.$store.dispatch('increaseRatingForAnswer', this.changeAnswerObject);

      //reload page
      this.$router.go(0);
    },
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
