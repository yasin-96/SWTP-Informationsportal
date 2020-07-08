<template>
  <b-container>
    <span v-b-toggle.notifySidebar> <fai icon="bell"></fai> <b-badge variant="light">4</b-badge> </span>
    <b-sidebar id="notifySidebar" title="Notification" text-variant="light" shadow>
      <div v-if="isMessagesLoaded && wsMessages.listOfUsers">
        <div v-for="(index, msg) in wsMessages" :key="index">
          <NotificationCard :question="msg.headerOfQuestion" :user="msg.user" :isAnswer="msg.isAnswer" :isComment="msg.isComment" :timestamp="msg.timestamp" />
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
      if (this.wsMessages && this.listOfUsers) {
        this.isMessagesLoaded = true;
      } else {
        this.isMessagesLoaded = false;
      }
    },
  },
};
</script>

<style></style>
