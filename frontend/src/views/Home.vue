<template>
  <b-container>
    <b-row v-if="!!getActiveQuestions && getActiveQuestions.length > 0">
      <b-col sm="12" md="4" lg="4" xl="4" v-for="quest in getActiveQuestions" :key="quest.id" class="mt-4">
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
     <b-col sm="12" md="12" lg="12" xl="12">
       <b-card border-variant="info" header="Information" header-border-variant="info" header-text-variant="info" align="center">
        <b-card-text>
          <p>No active questions was founded yet.</p>
          <p>To create a new question click on the <router-link to="/new">link</router-link></p>
        </b-card-text>
      </b-card>
     </b-col>
   </b-row>
  </b-container>
</template>

<script>
// @ is an alias to /src
import QuestionCard from '@/components/QuestionCard';
import { mapActions, mapGetters } from 'vuex';

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
    ...mapGetters(['getActiveQuestions'])
  },
};
</script>
