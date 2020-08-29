<template>
  <b-container v-if="isDataLoaded">
    <b-row>
      <b-col
        sm="12"
        md="6"
        lg="4"
        xl="3"
        v-for="quest in questionsBasedOnTopics"
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
  </b-container>
</template>

<script>
import QuestionCard from '@/components/QuestionCard';
import { mapState, mapActions } from 'vuex';

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
  },
  watch: {
    questionsBasedOnTopics() {
      if (this.questionsBasedOnTopics && this.questionsBasedOnTopics.length > 0) {
        this.isDataLoaded = true;
      } else {
        this.isDataLoaded = false;
      }
    },
  },
};
</script>
