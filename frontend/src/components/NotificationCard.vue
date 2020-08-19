<template>
  <b-container class="mb-2">
    <b-row>
      <b-col cols="12">
        <b-card bg-variant="white" text-variant="dark" v-if="isAnswer" style="max-width: 300px; max-height: 150px;">
          <b-card-title>
            <b-row class="justify-content-left">
              <b-col>
                <span class="pr-1w"><fai :icon="answerIcon" size="xs"></fai></span> <small>{{ answerTitle }}</small>
              </b-col>

               <b-col cols="mx-auto">
                <small @click="removeNotification()">
                  <span><fai icon="window-close" :style="{ color: '#b94a48'}"></fai></span>
                </small>
              </b-col>
            </b-row>
          </b-card-title>
          <!-- <b-card-sub-title class="pb-2">{{ answerSubTitle }}</b-card-sub-title> -->
          <b-card-text>
            <p>
              The user <b>{{ user.userName }}</b> has answered a question
              <i>
                <b-link :to="{ path: `/question/${questionId}` }"
                  ><span
                    ><strong>{{ question }}</strong></span
                  >
                </b-link>
              </i>
            </p>
          </b-card-text>
        </b-card>

        <b-card bg-variant="warning" text-variant="dark" v-if="isComment" style="max-width: 300px; max-height: 150px;">
          <b-card-title>
            <b-row class="justify-content-left">
              <b-col>
                <span class="pr-1"><fai :icon="commentIcon" size="xs"></fai></span> <small>{{ commentTitle }}</small>
              </b-col>

              <b-col cols="mx-auto">
                <small @click="removeNotification()" >
                  <span> <fai icon="window-close" :style="{ color: '#b94a48' }"></fai></span>
                </small>
              </b-col>
            </b-row>
          </b-card-title>
          <!-- <b-card-sub-title class="">{{ commentSubTitle }}</b-card-sub-title> -->
          <b-card-text>
            <p>
              The user <b>{{ user.userName }}</b> has commented on an answer to question
              <i>
                <b-link :to="{ path: `/question/${questionId}` }"
                  ><span
                    ><strong>{{ question }}</strong></span
                  >
                </b-link>
              </i>
            </p>
          </b-card-text>
        </b-card>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>

import {mapActions, Store } from 'vuex';

export default {
  name: 'NotificationCard',
  props: {
    /**
     * Id of question
     */
    questionId: {
      type: String,
      required: true,
    },

    /**
     * The question where something has just happened
     */
    question: {
      type: String,
      required: true,
    },

    /**
     * The user who answered this 
     */
    user: {
      type: Object,
      required: true,
    },

    /**
     * The message will show as answer
     */
    isAnswer: {
      type: Boolean,
      default: false,
    },

    /**
     * The message will show as comment
     */
    isComment: {
      type: Boolean,
      default: false,
    },

    /**
     * Creation date/time of the message
     */
    timestamp: {
      type: String,
      required: true,
    },

    /**
     * Position of each message in the list
     */
    index: {
      type: Number,
      required: true
    },

    /**
     * The symbol that is displayed for answers
     */
    answerIcon: {
      type: String,
      default: 'comment',
    },

    /**
     * The title for notification if it was a answer
     */
    answerTitle: {
      type: String,
      default: 'New Answer',
    },

    /**
     * The subtitle for notification if it was a answer
     */
    answerSubTitle: {
      type: String,
      default: 'Receive the answer',
    },

    /**
     * The symbol that is displayed for comments
     */
    commentIcon: {
      type: String,
      default: 'comment-dots',
    },

    /**
     * The title for notification if it was a comment
     */
    commentTitle: {
      type: String,
      default: 'New Comment',
    },

    /**
     * The subtitle for notification if it was a comment
     */
    commentSubTitle: {
      type: String,
      default: 'Receive the comment',
    },
  },
  methods: {
    /**
     * Remove the message from sidebare based on the index
     */
    removeNotification(){
      console.warn("INDEX to Remove", this.index)
      this.$store.dispatch('act_removeOneWSMessage', this.index)
    }
  },
  computed: {
    ...mapActions(['act_removeOneWSMessage'])
  }
};
</script>