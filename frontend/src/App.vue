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
import send from '@/mixins/socketStomp.js';
import connect from '@/mixins/socketStomp.js';
import disconnect from '@/mixins/socketStomp.js';
import tickleConnection from '@/mixins/socketStomp.js';
import { mapState, mapActions } from 'vuex';

export default {
  name: 'App',
  components: { Navigation },
  mixins: [send, connect, disconnect, tickleConnection],
  data() {
    return {
      initAllData: null,
      initUserData: null,
    };
  },
  created() {
    //connect to Socket
    this.connect();

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
    //reload data each 5 min
    pullUserWithIntervall() {
      this.initUserData = setInterval(async () => {
        console.log('pullUserWithIntervall()');
        await this.refreshUser();
      }, 300000);
    },

    //reload data each 10 min
    pullAllDataWithIntervall() {
      this.initAllData = setInterval(async () => {
        console.log('pullAllDataWithIntervall()');
        await this.reloadAllData();
        // }, 600000);
      }, 60000);
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
    },
    async refreshUser() {
      //load user data
      await this.$store.dispatch('act_getUserInfo');
    },
  },
  computed: {
    ...mapActions(['act_getUserInfo', 'act_getAllQuestions', 'act_getMostActiveQuestions', '', 'act_getAllTags', 'act_getCurrentTopics']),
  },
};
</script>

<style>
/* 
.editor-preview .editor-preview-active >>> h1 {
  font-size: 40px;
} */
</style>
