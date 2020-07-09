<template>
  <b-container>
    <span v-b-toggle.notifySidebar> <fai icon="bell"></fai> <b-badge variant="light">4</b-badge> </span>
    <b-sidebar id="notifySidebar" title="Notification" text-variant="light" shadow>
      <div v-if="isMessagesLoaded">
        <div v-for="(msg, i) in wsMessages" :key="i">
          <!-- <span>{{i}} {{msg}}</span> -->
          <NotificationCard :questionId="msg.questionId" :question="msg.headerOfQuestion" :user="msg.minimalUser" :isAnswer="msg.isAnswer" :isComment="msg.isComment" :timestamp="msg.timestamp" />
        </div>
      </div>
    </b-sidebar>
  </b-container>
</template>
<script>
import NotificationCard from '@/components/NotificationCard';
import { mapState } from 'vuex';

export default {
  name: 'Sidebar',
  components: {
    NotificationCard,
  },
  data() {
    return { isMessagesLoaded: false };
  },

  computed: {
    ...mapState(['wsMessages']),
  },
  watch: {
    wsMessages() {
      if (this.wsMessages && this.wsMessages.length) {
        console.warn(this.wsMessages)
        this.isMessagesLoaded = true;
      } else {
        this.isMessagesLoaded = false;
      }
    },
  },
};
</script>

<style></style>
