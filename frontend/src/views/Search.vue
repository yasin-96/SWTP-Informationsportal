<template>
  <b-container class="mt-3">
    <!-- <b-row b-if="oneQuestion && isQuestionAreLoaded"> -->
    <b-row >
      <b-col sm="12" md="12" lg="12" xl="12" class="mt-4" v-for="(item,i) in allQueryData" :key="i">
        <QuestionCard :qId="item.id" :qHeader="item.header" :qContent="item.content" :qTags="item.tags" :qDate="item.timeStamp" />
      </b-col>
      <b-row> </b-row>
    </b-row>
  </b-container>
</template>

<script>
// @ is an alias to /src
import QuestionCard from '@/components/QuestionCard';
import { mapState, mapActions } from 'vuex';
export default {
  name: 'Search',
  components: {
    QuestionCard,
  },
  props: {
    query: {
      type: String,
      required: true,
    },
  },
  data() {
    return {};
  },

  async beforeMount() {
    //check if local storage has the key with data, else set the new key & data
    if (this.$localStore.get('searchQuery') === this.query) {
      console.log('LOCAL_STORE:', this.$localStore.get('searchQuery'));
    } else {
      this.$localStore.set('searchQuery', this.query);
    }

    try {
      await this.$store.dispatch('act_getAllDataByQuery', this.query.toString() || this.$localStore.get('rQuetionId'));
    } catch (error) {
      console.error('beforeMount: ', error);
    }
  },
  
  computed: { ...mapState(['allQueryData']), ...mapActions(['act_getAllDataByQuery']) },
};
</script>

<style></style>
