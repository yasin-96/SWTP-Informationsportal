<template>
  <b-container class="mb-2">
    <b-card bg-variant="white" text-variant="dark" v-if="isAnswer" title="New Answer" :sub-title="`${user.userName} added something to question`">
      <b-card-text>
        <span class="pr-1"><fai icon="comment"></fai></span>
        <span
          ><strong>{{ question }}</strong></span
        >
      </b-card-text>
    </b-card>

    <b-card bg-variant="warning" text-variant="dark" v-if="isComment" title="New Comment" :sub-title="`${user.userName} added something to answer`">
      <b-card-text>
        <fai icon="comment-dots"></fai>
        <span
          ><strong>{{ question }}</strong></span
        >
      </b-card-text>
    </b-card>
  </b-container>
</template>

<script>
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
  },
};
</script>

<style></style>
