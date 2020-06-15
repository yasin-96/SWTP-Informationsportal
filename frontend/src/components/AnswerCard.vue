<template>
  <b-container v-if="nId">
    <b-card class="mt-3" :key="nId">
      <!-- Information about user & creation date -->
      <template v-slot:header>
        <b-row class="justify-content-left">
          <b-col cols="3" sm="2" md="2" lg="1">
            <!-- <b-iuser class="mr-2" font-scale="3"></b-iuser> -->
            <h1><fai icon="user-circle" /></h1>
          </b-col>
          <b-col cols="9" sm="10" md="10" lg="11">
            <strong>Antwort</strong> vom User <br /><small class="ml-3">
              <fai icon="clock" />
              {{ aDate }}
            </small>
          </b-col>
        </b-row>
      </template>

      <!-- One answer to the question -->
      <b-card  
        class="mb-1"
        footer-bg-border="white"
        footer-border-variant="white"
      >
        <b-card-text>
          <b-form-textarea id="textarea-plaintext" plaintext :rows="minCommentRows" :value="aContent"> </b-form-textarea>
          <b-button-group>
            <b-button size="sm" variant="info" @click="increaseRating()">
              <fai icon="thumbs-up" />
            </b-button>
            <b-button size="sm" disabled variant="info">
              {{ aRating }}
            </b-button>
          </b-button-group>
        </b-card-text>
      </b-card>

      <b-card-body>
        <!-- Area for all Comments -->
        <b-container>
          <Comment :cComments="allComments" :cId="cId" />
        </b-container>
        <b-container>
        </b-container>
      </b-card-body>
    </b-card>
   
  </b-container>
</template>

<script>
import Comment from '@/components/Comment';
import { mapState, mapActions } from 'vuex';

import { BIconClock, BIconChatSquareDots } from 'bootstrap-vue';

export default {
  name: 'AnswerCard',
  
  components: {
    Comment,
  },

  props: {
    nId: {
      type: String,
      default: '',
    },
    cId: {
      type: String,
      default: '',
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
  },

  data() {
    return {
      minCommentRows: 3,
      maxCommentRows: 10,
      isCommentsAreLoaded: false,
      changeAnswerObject: {
        id: '',
        listOfAnswers: [],
      },
    };
  },

  async beforeMount() {
    if (this.cId) {
      try {
        await this.$store.dispatch('act_getAllComments', this.cId);
      } catch (error) {
        console.error(error.error);
      }
    }
  },

  methods: {
    async increaseRating() {
      this.changeAnswerObject.id = this.nId || this.$localStore.get('rQuetionId');
      this.changeAnswerObject.listOfAnswers.push({
        id: this.cId,
        content: this.aContent,
        rating: this.aRating + 1 ,
        timeStamp: Date.parse(new Date(this.aDate)),
      });
      console.warn("INCRESE Rating AWT", this.changeAnswerObject);
      await this.$store.dispatch('increaseRatingForAnswer', this.changeAnswerObject)
        .then(() => {
          this.changeAnswerObject = "";
          this.changeAnswerObject.listOfAnswers = [];
        });
    }
  },

  computed: {
    ...mapActions(['act_getAllComments', 'increaseRatingForAnswer']),
    ...mapState(['allComments']),
  },

  watch: {
    allComments() {
      if (this.allComments) {
        this.isCommentsAreLoaded = true;
      } else {
        this.isCommentsAreLoaded = false;
      }
    },
  },
};
</script>

<style></style>
