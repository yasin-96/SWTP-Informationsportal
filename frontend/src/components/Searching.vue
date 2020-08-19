<template>
  <div class="mt-1">
    <b-row>
      <b-col>
        <b-input-group class="roundedCornerLeft">
          <b-input-group-append > 
            <b-button variant="success" v-on:enter="searchData()" @click="searchData()">
              <fai icon="search" />
            </b-button>
          </b-input-group-append>
          <b-form-input v-model="searchInput" placeholder="Search by tags"></b-form-input>
        </b-input-group>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import { mapActions } from 'vuex';

//TODO trigger für enter einfügen

export default {
  name: 'Searching',
  data() {
    return {
      //input of search textfield
      searchInput: '',
    };
  },
  methods: {
    /**
     * The search query is sent to the backend to get a result
     */
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
