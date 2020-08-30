<template>
  <b-container v-if="isDataLoaded">
    <b-row v-if="allQuestions.length > 0">
      <b-col sm="12" md="4" lg="4" xl="4" v-for="quest in allQuestions" :key="quest.id" class="mt-4">
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
     No question
   </b-row>

  </b-container>
</template>

<script>
import QuestionCard from '@/components/QuestionCard';
import { mapState, mapActions } from 'vuex';

export default {
  name: 'ShowAllQuestions',
  components: {
    QuestionCard,
  },
  data() {
    return {
      //Check if data are loaded and then display the content
      isDataLoaded: false,
    };
  },
  beforeMount() {
    this.loadData();
  },
  methods: {
    /**
     * Load all question from store
     */
    async loadData() {
      await this.$store.dispatch('act_getAllQuestions');
    },
  },
  computed: {
    ...mapActions(['act_getAllQuestions']),
    ...mapState(['allQuestions']),
  },
  watch: {
    allQuestions() {
      if (this.allQuestions) {
        this.isDataLoaded = true;
      } else {
        this.isDataLoaded = false;
      }
    },
  },
};
</script>