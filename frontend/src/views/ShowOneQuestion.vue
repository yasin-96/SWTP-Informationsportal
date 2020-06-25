<template>
  <b-container class="mt-3" v-if="id && isDataLoaded.question">
    <b-row>
      <b-col>
        <QuestionCard :qId="oneQuestion.id" :qHeader="oneQuestion.header" :qContent="oneQuestion.content" :qTags="oneQuestion.tags" :qDate="oneQuestion.timeStamp" :qFooter="true" :qEdit="true" :displayContent="true"/>

        <!-- 
            By clicking on the title of a question, a page is called up and all information is provided.
            All answers and comments made. 
        -->

        <NewContent bTextSize="lg" :nRows="2" :id="oneQuestion.id" :nIsAnswer="true" />

        <b-container v-if="isDataLoaded.answers">
          <b-container v-for="(answer, index) in allAnswers.listOfAnswers" :key="index">
            <AnswerCard :nId="oneQuestion.id" :aContent="answer.content" :aRating="answer.rating" :aDate="answer.timeStamp" :cId="answer.id" class="pb-3" />
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
      isDataLoaded: {
        question: false,
        answers: false,
      },
    };
  },

  beforeMount() {
    this.loadData();
  },
  computed: {
    ...mapActions(['act_getAllAnswers', 'act_getOneQuestion']),
    ...mapState(['oneQuestion', 'allAnswers']),
  },
  methods: {
    async loadData() {
      if (this.id) {
        console.info('THIS ID:', this.id);
        await this.$store.dispatch('act_getOneQuestion', this.id);
        await this.$store.dispatch('act_getAllAnswers', this.id);
      }
    },
  },
  watch: {
    oneQuestion() {
      if (this.oneQuestion && !!this.oneQuestion) {
        this.isDataLoaded.question = true;
      } else {
        this.isDataLoaded.question = false;
      }
    },
    allAnswers() {
      if (this.allAnswers && this.allAnswers.listOfAnswers) {
        this.isDataLoaded.answers = true;
      } else {
        this.isDataLoaded.answers = false;
      }
    },
  },
};
</script>
