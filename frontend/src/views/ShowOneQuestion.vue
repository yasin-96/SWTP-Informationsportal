<template>
  <b-container class="mt-3" v-if="id && isDataLoaded.question">
    <b-row>
      <b-col>
        <!-- 
            By clicking on the title of a question, a page is called up and all information is provided.
            All answers and comments made. 
        -->
        <QuestionCard
          :qId="oneQuestion.id"
          :qHeader="oneQuestion.header"
          :qContent="oneQuestion.content"
          :qTags="oneQuestion.tags"
          :qDate="oneQuestion.timeStamp"
          :qUserId="oneQuestion.userId"
          :qUserName="oneQuestion.userName"
          :qFooter="true"
          :qEdit="true"
          :displayContent="true"
          :userEdit="oneQuestion.userId === getUserId"
        />

        <!-- Add new Answer to this Question -->
        <NewContent bTextSize="lg" :nRows="2" :id="oneQuestion.id" :nIsAnswer="true" class="rounded shadow" />


        <!-- Display all available Answers  -->
        <b-container v-if="!!allAnswers && !!getListWithAnswers">
          <b-container v-for="(answer, index) in getListWithAnswers" :key="index" class="pb-3">
            <AnswerCard v-if="answer.id"
              :nId="oneQuestion.id"
              :aContent="answer.content"
              :aRating="answer.rating"
              :aDate="answer.timeStamp"
              :cId="answer.id"
              :aUserId="answer.userId"
              :aUserName="answer.userName"
              :userEdit="oneQuestion.userId === getUserId"
            />
          </b-container>
        </b-container>
      </b-col>
      <b-row></b-row>
    </b-row>
  </b-container>
</template>

<script>
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
    /**
     * Id of the question 
     */
    id: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      //Checked if the question and all answers to this are loaded
      isDataLoaded: {
        question: false,
        answers: false,
      },
    };
  },
  mounted() {
    this.loadData();
  },
  beforeMount(){
    setTimeout(
      this.loadData(), 2000
    )
  },
  computed: {
    ...mapActions(['act_getAllAnswers', 'act_getOneQuestion']),
    ...mapGetters(['getUserId', 'getListWithAnswers']),
    ...mapState(['oneQuestion', 'allAnswers']),
  },
  methods: {
    /**
     * All data for displaying the question and answer is requested asynchronously.
     */
    async loadData() {
      if (!!this.id) {
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
      if (this.allAnswers) {
        this.isDataLoaded.answers = true;
      } else {
        this.isDataLoaded.answers = false;
      }
    },
  },
};
</script>
