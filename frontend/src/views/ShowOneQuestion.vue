<template>
  <b-container class="mt-3">
    <!-- <b-row b-if="oneQuestion && isQuestionAreLoaded"> -->
    <b-row>
      <b-col>
        <QuestionCard :qId="oneQuestion.id" :qHeader="oneQuestion.header" :qContent="oneQuestion.content" :qTags="oneQuestion.tags" :qDate="oneQuestion.timeStamp" />

        <!-- 
            By clicking on the title of a question, a page is called up and all information is provided.
            All answers and comments made. 
        -->

        <NewContent bTextSize="lg" :nRows="2" :id="oneQuestion.id" :nIsAnswer="true" />

        <b-container v-if="allAnswers && areAnswersLoaded">
          <b-container v-for="(aa, i) in allAnswers.listOfAnswers" :key="i">
            <AnswerCard :nId="oneQuestion.id" :aContent="aa.content" :aRating="aa.Rating" :aDate="aa.timeStamp" :cId="aa.id" class="pb-3" />
          </b-container>
        </b-container>
      </b-col>
      <b-row> </b-row>
    </b-row>
  </b-container>
</template>

<script>
// @ is an alias to /src
import NewContent from '@/components/NewContent';
import QuestionCard from '@/components/QuestionCard';
import AnswerCard from '@/components/AnswerCard';
import { mapState, mapActions, mapGetters } from 'vuex';

export default {
  name: 'ShowAllQuestions',
  components: {
    QuestionCard,
    AnswerCard,
    NewContent,
  },
  async beforeMount() {
    //read id from url

    if (this.$router) {
      this.paramId = this.$router.history.current.params.id;
      // console.warn("ROUTER:",this.$route.history.current.params.id);
      console.warn('ROUTER ->:', this.$router.history.current.params.id);
      console.warn('ROUTER - ID:', this.paramId);

      //load data
      if (this.paramId.length) {
        try {
          await this.$store.dispatch('act_getOneQuestion', this.paramId);
          await this.$store.dispatch('act_getAllAnswers', this.paramId);
        } catch (error) {
          console.error('beforeMount: ', error);
        }
      }
    }
  },
  data: () => ({
    isQuestionAreLoaded: false,
    areAnswersLoaded: false,
    paramId: '',
  }),
  computed: {
    ...mapActions(['act_getOneQuestion', 'act_getAllAnswers']),
    ...mapState(['oneQuestion', 'allAnswers']),
    ...mapGetters(['getListWithAnswers']),
  },
  watch: {
    oneQuestion() {
      if (this.oneQuestion && !this.oneQuestion) {
        this.isQuestionAreLoaded = true;
      } else {
        this.isQuestionAreLoaded = false;
      }
    },

    allAnswers() {
      if (this.allAnswers.listOfAnswers && this.allAnswers.listOfAnswers.length > 0) {
        this.areAnswersLoaded = true;
      } else {
        this.areAnswersLoaded = false;
      }
    },
  },
};
</script>
