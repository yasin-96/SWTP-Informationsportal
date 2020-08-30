<template>
  <b-container>
    <b-row v-if="!!getQuestionsBasedOnTopics && getQuestionsBasedOnTopics.length > 0">
      <b-col
        sm="12"
        md="6"
        lg="4"
        xl="3"
        v-for="quest in getQuestionsBasedOnTopics"
        :key="quest.id"
        class="mt-4"
      >
        

        <QuestionCard
          :qId="quest.id"
          :qHeader="quest.header"
          :qContent="quest.content"
          :qTags="quest.tags"
          :qDate="quest.timeStamp"
          :qTrimText="true"
          :qFooter="true"
          :qUserId="quest.userId"
          :qUserName="quest.userName"
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
import { mapState, mapActions, mapGetters } from 'vuex';

export default {
  name: 'QuestionsByTopics',
  components: {
    QuestionCard,
  },
  props: {
    /**
     * The tag that will be used for getting all question
     */
    tag: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      //Check if data are loaded and then display the content
      isDataLoaded: false,
    };
  },
  mounted() {
    this.loadData();
  },

  methods: {
    /**
     * Trigger all questions based on most important topics/tags
     */
    async loadData() {
      await this.$store.dispatch('act_getQuestionsBasedOnTopic', this.tag);
    },
  },
  computed: {
    ...mapActions(['act_getAllQuestions']),
    ...mapState(['questionsBasedOnTopics']),
    ...mapGetters(['getQuestionsBasedOnTopics'])
  },
  watch: {
    questionsBasedOnTopics() {
      if (!!this.questionsBasedOnTopics && this.questionsBasedOnTopics.length > 0) {
        this.isDataLoaded = true;
      } else {
        this.isDataLoaded = false;
      }
    },
  },
};
</script>
