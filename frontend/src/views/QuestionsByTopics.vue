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
        />
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
// @ is an alias to /src
import QuestionCard from '@/components/QuestionCard';
import { mapState, mapActions } from 'vuex';

export default {
  name: 'QuestionsByTopics',
  components: {
    QuestionCard,
  },
  props: {
    tag: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      isDataLoaded: false,
    };
  },
  mounted() {
    this.loadData();
  },

  methods: {
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
