<template>
  <b-container v-if="isDataLoaded">
    <b-row>
      <b-col sm="12" md="6" lg="4" xl="3" v-for="quest in allQuestions" :key="quest.id" class="mt-4">
        <QuestionCard :qId="quest.id" :qHeader="quest.header" :qContent="quest.content" :qTags="quest.tags" :qDate="quest.timeStamp"/>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
// @ is an alias to /src
import QuestionCard from '@/components/QuestionCard';
import { mapState, mapActions } from 'vuex';

export default {
  name: 'ShowAllQuestions',
  components: {
    QuestionCard,
  },
  data() {
    return {
      isDataLoaded: false,
    };
  },
  beforeMount() {
    this.loadData();
  },
  methods: {
    async loadData() {
      await this.$store.dispatch('act_getAllQuestions');
    },
  },
  computed: {
    ...mapActions(['act_getAllQuestions']),
    ...mapState(['allQuestions']),
  },
  watch: {
    allQuestions() {
      if (this.allQuestions) {
        this.isDataLoaded = true;
      } else {
        this.isDataLoaded = false;
      }
    },
  },
};
</script>
