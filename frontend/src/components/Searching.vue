<template>
  <b-container>
    <b-row>
      <b-col>
        <b-input-group>
          <b-input-group-append>
            <b-button variant="success" @click="searchData()">
              <fai icon="search" />
            </b-button>
          </b-input-group-append>
          <b-form-input v-model="searchInput" placeholder="Search by tags"></b-form-input>
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
  methods: {
    searchData() {
      this.$router
        .push({
          path: '/search',
          query: {
            q: `${String(this.searchInput).toUpperCase()}`,
          },
        })
        .catch((err) => {});
    },
  },
  computed: { ...mapActions(['act_getAllDataByQuery']) },
};
</script>

<style></style>
