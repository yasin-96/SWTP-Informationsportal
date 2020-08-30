<template>
  <b-container>
    <b-row v-if="!!getAllQuestions && getAllQuestions.length > 0">
      <b-col sm="12" md="4" lg="4" xl="4" v-for="quest in getAllQuestions" :key="quest.id" class="mt-4">
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
          <p>No questions have been created yet.</p>
          <p>To create a new question click on the <router-link to="/new">link</router-link></p>
        </b-card-text>
      </b-card>
     </b-col>
   </b-row>

  </b-container>
</template>

<script>
import QuestionCard from '@/components/QuestionCard';
import { mapActions, mapGetters } from 'vuex';

export default {
  name: 'ShowAllQuestions',
  components: {
    QuestionCard,
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
    ...mapGetters(['getAllQuestions'])
  },
};
</script>