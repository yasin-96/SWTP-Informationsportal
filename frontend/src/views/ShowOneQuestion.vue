<template>
  <v-container>
    <v-row v-if="oneQuestion">
      <v-col>
        <QuestionCard 
          :qId="oneQuestion.id" 
          :qHeader="oneQuestion.header" 
          :qContent="oneQuestion.content" 
          :qTags="oneQuestion.tags" 
          :qDate="oneQuestion.timeStamp"
        />
      </v-col>
    </v-row>
    <v-row v-if="allAnswers && allAnswers.listOfAnswers">
      <v-col v-for="aa in allAnswers.listOfAnswers" :key="aa">
        <AnswerCard :id="paramId" :aContent="aa.content" :aRating="aa.Rating" :aDate="aa.timeStamp" />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
// @ is an alias to /src
import QuestionCard from '@/components/QuestionCard';
import AnswerCard from '@/components/AnswerCard';
import { mapState, mapActions, mapGetters } from 'vuex';

export default {
  name: 'ShowAllQuestions',
  components: {
    QuestionCard,
    AnswerCard
  },
  beforeMount: async function() {
    this.paramId = JSON.parse(JSON.stringify(this.$route.params.id));
    try {
      await this.$store.dispatch('act_getOneQuestion', this.paramId);
      await this.$store.dispatch('act_getAllAnswers', this.paramId);
    } catch (error) {
      console.error('beforeMount: ', error);
    }
  },
  data: () => ({
    paramId: ""
  }),
  computed: {
    ...mapActions(['act_getOneQuestion', 'act_getAllAnswers']),
    ...mapState(['oneQuestion', 'allAnswers']),
    ...mapGetters(['getListWithAnswers'])
  },
};
</script>
