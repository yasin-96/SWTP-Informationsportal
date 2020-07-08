<template>
<b-container class="mb-2">
  <b-card bg-variant="white" text-variant="dark" >
    <!-- <b-card-text v-show="isAnswer"> -->
    <b-card-text>
      New 
      <fai icon="comment"></fai> <span>New Comment from {{ user }} to question <strong>{{question}}</strong></span>
    </b-card-text>
    <!-- <b-card-text v-show="isComment"> -->
    <b-card-text>
      <fai icon="comment-dots"></fai> <span>New Comment from {{ user }} to question <strong>{{question}}</strong></span>
    </b-card-text>
  </b-card>
</b-container>
</template>

<script>
export default {
  name: 'NotificationCard',
  props: {
      question:{
          type: String,
          required: true
      },
      user: {
          type: String,
          required: true
      },
      isAnswer: {
          type: Boolean,
          default: false
      },
      isComment: {
          type: Boolean,
          default: false
      },
      timestamp: {
          type: String,
          required: true
      }
  }
};
</script>

<style></style>
