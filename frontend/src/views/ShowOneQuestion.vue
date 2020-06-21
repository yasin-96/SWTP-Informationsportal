<template>
  <b-container class="mt-3" v-if="id">
    <!-- <b-row b-if="oneQuestion && isQuestionAreLoaded"> -->
    <b-row>
      <b-col>
        <QuestionCard :qId="question.id" :qHeader="question.header" :qContent="question.content" :qTags="question.tags" :qDate="question.timeStamp" :qFooter="true" :qEdit="true" />

        <!-- 
            By clicking on the title of a question, a page is called up and all information is provided.
            All answers and comments made. 
        -->

        <NewContent bTextSize="lg" :nRows="2" :id="question.id" :nIsAnswer="true" />

        <b-container v-if="!!answers">
          <b-container v-for="aa in answers" :key="aa.id">
            <AnswerCard :nId="id" :aContent="aa.content" :aRating="aa.rating" :aDate="aa.timeStamp" :cId="aa.id" class="pb-3" />
            <!-- <AnswerCard :nId="aa.id" :aContent="aa.content" :aRating="aa.rating" :aDate="aa.timeStamp" :cId="aa.id" class="pb-3" /> -->
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
      default: '',
    },
  },
  data() {
    return {
      isQuestionAreLoaded: false,
      areAnswersLoaded: false,
      paramId: '',
      componentKey: '',
      componentCounter: 0,
    };
  },

  async beforeMount() {
    if (this.id) {
      //TODO remove localStorage maybe 
      // console.info('BMount--');
      // //check if local storage has the key with data, else set the new key & data
      // if (this.$localStore.get('rQuetionId') === this.id.toString()) {
      //   console.log('LOCAL_STORE:', this.$localStore.get('rQuetionId'));
      // } else {
      //   this.$localStore.set('rQuetionId', this.id.toString());
      // }

      console.warn('THIS ID:', this.id);
      // this.paramId = this.id || this.$localStore.get('rQuetionId');
      // this.paramId = this.id;
      // console.warn("PARA ID-Router:", this.$route.params.id);

      await this.$store.dispatch('act_getOneQuestion', this.id);
      await this.$store.dispatch('act_getAllAnswers', this.id);
    }
  },
  computed: {
    ...mapActions(['act_getAllAnswers', 'act_getOneQuestion']),
    ...mapState(['oneQuestion', 'allAnswers', 'reloadAnswers']),

    question(){
      if(this.oneQuestion) {
        return this.oneQuestion;
      }
    },
    answers(){
      if(this.allAnswers){
        return this.allAnswers.listOfAnswers;
      }
    }
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
      if (this.allAnswers) {
        this.areAnswersLoaded = true;
      } else {
        this.areAnswersLoaded = false;
      }
    },

    reloadAnswers() {
      if(this.reloadAnswers) {
        console.warn("reload answer data");
        this.$store.dispatch('act_getAllAnswers', this.id);
      }
    }
  },
};
</script>
