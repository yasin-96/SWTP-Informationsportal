<template>
  <b-container>
    <b-row>
      <b-col>
        <b-input-group prepend="" class="mt-3">
          <b-form-input :hidden="disable" v-model="searchInput" placeholder="Search by tags, header or content ..."></b-form-input>
          <b-input-group-append>
            <b-button :hidden="disable" variant="outline-success" @click="searchData()">
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
          default: true
      }
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
      let response = this.$store.dispatch('act_getAllDataByQuery', this.searchInput);
      console.warn(response);
    },
  },
};
</script>

<style></style>
