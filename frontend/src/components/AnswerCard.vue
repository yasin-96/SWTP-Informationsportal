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
      <b-card>
        <b-card-text>
          <b-form-textarea id="textarea-plaintext" plaintext :rows="minCommentRows"  :value="aContent">
          </b-form-textarea>
          
        </b-card-text>
      </b-card>

      <b-card-body>
        <!-- Area for all Comments -->
        <b-container>
          <Comment :cComments="allComments" :cId="cId" />
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
  beforeMount: async function () {
    console.warn('Run dispatch for comments');
    if (this.cId) {
      try {
        await this.$store.dispatch('act_getAllComments', this.cId);
      } catch (error) {
        console.error(error.error);
      }
    }
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
  data: () => ({
    minCommentRows: 3,
    maxCommentRows: 10,
    isCommentsAreLoaded: false,
  }),
  computed: {
    ...mapActions(['act_getAllComments']),
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
