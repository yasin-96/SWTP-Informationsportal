<template>
  <v-container>
    <!-- <v-row v-if="oneQuestion && isQuestionAreLoaded"> -->
    <v-row>
      <v-col cols="12">
        <QuestionCard 
          :qId="oneQuestion.id" 
          :qHeader="oneQuestion.header" 
          :qContent="oneQuestion.content" 
          :qTags="oneQuestion.tags" 
          :qDate="oneQuestion.timeStamp"
        />

        <!-- 
            By clicking on the title of a question, a page is called up and all information is provided.
            All answers and comments made. 
        -->
        <v-container v-if="allAnswers && areAnswersLoaded">
          <v-content v-for="(aa, i) in allAnswers.listOfAnswers" :key="i">
              <AnswerCard :id="paramId" :aContent="aa.content" :aRating="aa.Rating" :aDate="aa.timeStamp" />
          </v-content>
        </v-container>
      </v-col>
       <v-row >
    </v-row>
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
  async beforeMount() {

    //read id from url
    this.paramId = JSON.parse(JSON.stringify(this.$route.params.id));

    //load data
    try {
      await this.$store.dispatch('act_getOneQuestion', this.paramId);
      await this.$store.dispatch('act_getAllAnswers', this.paramId);
    } catch (error) {
      console.error('beforeMount: ', error);
    }
  },
  data: () => ({
    isQuestionAreLoaded: false,
    areAnswersLoaded: false,
    paramId: ""
  }),
  computed: {
    ...mapActions(['act_getOneQuestion', 'act_getAllAnswers']),
    ...mapState(['oneQuestion', 'allAnswers']),
    ...mapGetters(['getListWithAnswers'])
  },
  watch: {
    
    
    oneQuestion() {
      if( this.oneQuestion && !this.oneQuestion) {
        this.isQuestionAreLoaded = true
      } else{
        this.isQuestionAreLoaded = false
      }
    },
    
    allAnswers(){
      if(this.allAnswers.listOfAnswers && this.allAnswers.listOfAnswers.length > 0){
        this.areAnswersLoaded = true;
      } else {
        this.areAnswersLoaded = false;
      }
    }

  }
};
</script>
