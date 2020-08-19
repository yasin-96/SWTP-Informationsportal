<template>
  <div>
    <span v-b-toggle.notifySidebar>
      <fai icon="envelope" class="mr-1"></fai>
    </span>
    <span> 
      <b-badge class="pa-auto" variant="light">{{ wsMessages.length != 0 ? wsMessages.length : '' }}</b-badge>
    </span>
    <b-sidebar id="notifySidebar" title="Notification" text-variant="light" shadow>
      <div v-if="wsMessages.length">
        <div v-for="(msg, i) in wsMessages" :key="i">
          <!-- <span>{{i}} {{msg}}</span> -->
          <NotificationCard
            :questionId="msg.questionId"
            :question="msg.headerOfQuestion"
            :user="msg.minimalUser"
            :isAnswer="msg.isAnswer"
            :isComment="msg.isComment"
            :timestamp="msg.timestamp"
            :index="i"
          />
        </div>
      </div>
    </b-sidebar>
  </div>
</template>
<script>
import NotificationCard from '@/components/NotificationCard';
import { mapState } from 'vuex';

export default {
  name: 'Sidebar',
  components: {
    NotificationCard,
  },
  computed: {
    ...mapState(['wsMessages']),
  },
};
</script>

<style></style>
