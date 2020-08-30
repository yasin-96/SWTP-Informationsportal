<template>
  <b-container v-if="this.query">
    <b-row v-if="!!getQueryData && getQueryData.length > 0">
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
          header="Kein Ergebnis bei der Suche"
          header-border-variant="danger"
          header-text-variant="danger"
          align="center"
        >
          <b-jumbotron bg-variant="danger" text-variant="white" border-variant="dark">{{this.query}}</b-jumbotron>
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
import { mapState, mapActions, mapGetters } from 'vuex';
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
     *
     */
    async sendQuery() {
      if (this.query.length) {
        console.warn('Query:', this.query);
        await this.$store.dispatch('act_getAllDataByQuery', this.query.toUpperCase().trim());
      }
    },
  },
  computed: { 
    ...mapActions(['act_getAllDataByQuery']),
    ...mapGetters(['getQueryData']) 
  },
};
</script>