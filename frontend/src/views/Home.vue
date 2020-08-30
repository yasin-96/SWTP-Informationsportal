<template>
  <b-container v-if="isDataLoaded">
    <b-row v-if="activeQuestions.length > 0">
      <b-col sm="12" md="4" lg="4" xl="4" v-for="quest in activeQuestions" :key="quest.id" class="mt-4">
        <QuestionCard
          :qId="quest.id"
          :qHeader="quest.header"
          :qContent="quest.content"
          :qTags="quest.tags"
          :qDate="quest.timeStamp"
          :qUserId="quest.userId"
          :qUserName="quest.userName"
          :qFooter="true"
        />
      </b-col>
    </b-row>
    <b-row v-else>
      No Question
    </b-row>
  </b-container>
</template>

<script>
// @ is an alias to /src
import QuestionCard from '@/components/QuestionCard';
import { mapState, mapActions } from 'vuex';

export default {
  name: 'Home',
  components: {
    QuestionCard,
  },
  data() {
    return {
      //Check if questions are loaded and then display the content
      isDataLoaded: false,
    };
  },
  beforeMount() {
    this.loadData();
  },
  methods: {
    /**
     * Load all questions that are currently very popular 
     */
    async loadData() {
      await this.$store.dispatch('act_getMostActiveQuestions');
    },
  },
  computed: {
    ...mapActions(['act_getMostActiveQuestions']),
    ...mapState(['activeQuestions']),
  },
  watch: {
    activeQuestions() {
      if (this.activeQuestions) {
        this.isDataLoaded = true;
      } else {
        this.isDataLoaded = false;
      }
    },
  },
};
</script>
