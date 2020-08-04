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
    questionId: {
      type: String,
      required: true,
    },
    question: {
      type: String,
      required: true,
    },
    user: {
      type: Object,
      required: true,
    },
    isAnswer: {
      type: Boolean,
      default: false,
    },
    isComment: {
      type: Boolean,
      default: false,
    },
    timestamp: {
      type: String,
      required: true,
    },
    index: {
      type: Number,
      required: true
    },

    answerIcon: {
      type: String,
      default: 'comment',
    },

    answerTitle: {
      type: String,
      default: 'New Answer',
    },

    answerSubTitle: {
      type: String,
      default: 'Receive the answer',
    },

    commentIcon: {
      type: String,
      default: 'comment-dots',
    },

    commentTitle: {
      type: String,
      default: 'New Comment',
    },

    commentSubTitle: {
      type: String,
      default: 'Receive the comment',
    },
  },
  methods: {
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

<style></style>
