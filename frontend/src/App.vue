<template>
  <b-container id="app" fluid>
    <Navigation />
    <b-container fluid class="mt-5">
      <router-view />
    </b-container>
  </b-container>
</template>

<script>
import Navigation from '@/components/Navigation';
import RestCalls from '@/services/RestCalls';
import { mapState, mapActions } from 'vuex';

export default {
  name: 'App',
  components: { Navigation },
  data() {
    return {
      initAllData: null,
      initUserData: null,
    };
  },
  created() {
    //connect to Socket
    // this.connect();

    this.$store.dispatch('act_createConnectSocketAndStompClient');

    //run once
    this.reloadAllData();
    this.refreshUser();

    console.warn('VUE INSTANCE created()');

    this.pullUserWithIntervall();
    this.pullAllDataWithIntervall();
  },
  beforeDestroy() {
    console.warn('VUE INSTANCE beforeDestroy()');
    clearInterval(this.initAllData);
    clearInterval(this.initUserData);
  },
  methods: {

    //TODO check if ws connection is not died

    //reload data each 4 min
    pullUserWithIntervall() {
      this.initUserData = setInterval(async () => {
        console.log('pullUserWithIntervall()');
        await this.refreshUser();
      }, 240000);
    },

    //reload data each 2 min
    pullAllDataWithIntervall() {
      this.initAllData = setInterval(async () => {
        console.log('pullAllDataWithIntervall()');
        await this.reloadAllData();
      }, 150000);
    },

    async reloadAllData() {
      //questions for home
      await this.$store.dispatch('act_getMostActiveQuestions');

      //general
      await this.$store.dispatch('act_getAllQuestions');

      //all tags
      await this.$store.dispatch('act_getCurrentTopics');

      //topics
      await this.$store.dispatch('act_getAllTags');

      //check notifications
      await this.$store.dispatch('act_checkWSMessageWithQuestion');
    },
    async refreshUser() {
      //load user data
      await this.$store.dispatch('act_getUserInfo');
    },
  },
  computed: {
    ...mapState(['clientConnection']),
    ...mapActions(['act_getUserInfo', 'act_getAllQuestions', 'act_getMostActiveQuestions', '', 'act_getAllTags', 'act_getCurrentTopics', 'act_createConnectSocketAndStompClient', 'act_checkWSMessageWithQuestion']),
  },
  watch: {
    clientConnection() {
      console.warn("clientConnection -> ",this.clientConnection);
      if (!this.clientConnection) {
        this.$store.dispatch('act_createConnectSocketAndStompClient');
      }
    },
  },
};
</script>