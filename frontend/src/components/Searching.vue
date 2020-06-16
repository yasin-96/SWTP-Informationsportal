<template>
  <b-container>
    <b-row>
      <b-col>
        <b-input-group>
          <b-form-input v-model="searchInput" placeholder="Search by tags, header or content ..."></b-form-input>
          <b-input-group-append>
            <b-button variant="outline-success" @click="searchData()">
              <fai icon="search" />
            </b-button>
          </b-input-group-append>
        </b-input-group>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapActions } from 'vuex';

export default {
  name: 'Searching',
  props: {
    disable: {
      type: Boolean,
      default: true,
    },
  },
  data() {
    return {
      enableSearchOptions: true,
      searchInput: '',
    };
  },
  computed: { ...mapActions(['act_getAllDataByQuery']) },
  methods: {
    searchData() {
      this.$router
        .push({
          path: '/search',
          query: {
            q: `${this.searchInput}`,
          },
        })
        .catch((err) => {});
    },
  },
};
</script>

<style></style>
