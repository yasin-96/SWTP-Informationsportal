<template>
  <b-container class="mt-3">
    <!-- <b-row b-if="oneQuestion && isQuestionAreLoaded"> -->
    <b-row>
      <b-col>
        <QuestionCard :qId="oneQuestion.id" :qHeader="oneQuestion.header" :qContent="oneQuestion.content" :qTags="oneQuestion.tags" :qDate="oneQuestion.timeStamp" :qFooter="true" />

        <!-- 
            By clicking on the title of a question, a page is called up and all information is provided.
            All answers and comments made. 
        -->

        <NewContent bTextSize="lg" :nRows="2" :id="oneQuestion.id" :nIsAnswer="true" />

        <b-container v-if="!!allAnswers">
          <b-container v-for="(aa, i) in allAnswers.listOfAnswers" :key="i">
            <AnswerCard :nId="id" :aContent="aa.content" :aRating="aa.Rating" :aDate="aa.timeStamp" :cId="aa.id" class="pb-3" />
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
  props: {
    id: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      isQuestionAreLoaded: false,
      areAnswersLoaded: false,
      paramId: '',
    };
  },
  
  async beforeMount() {
    //check if local storage has the key with data, else set the new key & data
    if (this.$localStore.get('rQuetionId') === this.id.toString()) {
      console.log('LOCAL_STORE:', this.$localStore.get('rQuetionId'));
    } else {
      this.$localStore.set('rQuetionId', this.id.toString());
    }

    try {
      await this.$store.dispatch('act_getOneQuestion', this.id.toString() || this.$localStore.get('rQuetionId'));
      await this.$store.dispatch('act_getAllAnswers', this.id.toString() || this.$localStore.get('rQuetionId'));
    } catch (error) {
      console.error('beforeMount: ', error);
    }
  },
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
