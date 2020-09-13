<template>
  <b-container v-if="!!query">
    <b-row v-if="!!getQueryData && getQueryData.length > 0">
      <b-col cols>
        <b-card
          border-variant="success"
          :header="`Result for search`"
          header-border-variant="success"
          header-text-variant="success"
          align="center"
        >
          <b-jumbotron bg-variant="success" size="lg" text-variant="white" border-variant="dark">{{query}}</b-jumbotron>
        </b-card>
      </b-col>
      
      <b-col
        sm="12"
        md="12"
        lg="12"
        xl="12"
        class="mt-4"
        v-for="(item, i) in getQueryData"
        :key="i"
      >
        <QuestionCard
          :qId="item.id"
          :qHeader="item.header"
          :qContent="item.content"
          :qTags="item.tags"
          :qDate="item.timeStamp"
          :qUserId="item.userId"
          :qUserName="item.userName"
        />
      </b-col>
      <b-row></b-row>
    </b-row>
    <b-row v-else>
      <b-col cols="auto"></b-col>
      <b-col cols>
        <b-card
          border-variant="danger"
          header="No result in the search"
          header-border-variant="danger"
          header-text-variant="danger"
          align="center"
        >
          <b-jumbotron bg-variant="danger" text-variant="white" border-variant="dark">{{query}}</b-jumbotron>
        </b-card>
      </b-col>
      <b-col cols="auto"></b-col>
    </b-row>
  </b-container>

  <b-container v-else>
    <b-card
      border-variant="info"
      header="Information"
      header-border-variant="info"
      header-text-variant="info"
      align="center"
    >
      <b-card-text>No search term available!</b-card-text>
    </b-card>
  </b-container>
</template>

<script>
import QuestionCard from '@/components/QuestionCard';
import { mapActions, mapGetters } from 'vuex';
export default {
  name: 'Search',
  components: {
    QuestionCard,
  },
  props: {
    /**
     * Query for searching data
     */
    query: {
      type: String,
      required: true,
    },
  },
  methods: {
    /**
     * Find question based on search query that is one or more topics(tags)
     */
    async sendQuery() {
      //if (!!this.query && this.query.length > 0) {
        console.warn('Query:', this.query);
        await this.$store.dispatch('act_getAllDataByQuery', { query: this.query });
      //}
    },
  },
  computed: { 
    ...mapActions(['act_getAllDataByQuery']),
    ...mapGetters(['getQueryData']) 
  },
  watch: {
    query(){
      this.sendQuery()
    }
  }
};
</script>