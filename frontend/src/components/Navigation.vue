<template>
  <b-card>
    <b-navbar toggleable="md">
      <!-- <b-navbar variant="faded" type="light">
          <b-navbar-brand tag="h1" class="mb-0">Informationsportal</b-navbar-brand>
        </b-navbar>
         -->
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-form>
            <img :src="thmSVGURl" fluid alt="THM LOGO" />
          </b-nav-form>
        </b-navbar-nav>
        <!-- <b-navbar-nav class="ml-auto">
            <b-nav-item href="#">Link</b-nav-item>
            <b-nav-item href="#" disabled>Disabled</b-nav-item>
          </b-navbar-nav> -->

        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
          <b-button :hidden="!disableSearchBar" squared size="md" :pressed.sync="disableSearchBar">
            <fai icon="search" class="mr-2"/>
            <span>Search</span>
          </b-button>
          <b-button variant="danger" :hidden="disableSearchBar" squared size="md" :pressed.sync="disableSearchBar">
            <fai icon="search-minus" class="mr-2"/>
            <span>Abort</span>
          </b-button>
          <b-nav-item-dropdown right>
            <!-- Using 'button-content' slot -->
            <template v-slot:button-content>
              <b-avatar variant="primary" text="BV"></b-avatar>
            </template>
            <b-dropdown-item href="#">Profile</b-dropdown-item>
            <b-dropdown-item href="#">Sign Out</b-dropdown-item>
          </b-nav-item-dropdown>
        </b-navbar-nav>

       
      </b-collapse>
    </b-navbar>
    <Searching :disable="disableSearchBar"/>
  </b-card>
</template>

<script>
import Searching from "@/components/Searching";
import { BNavbar } from 'bootstrap-vue';
import {mapActions} from 'vuex';

export default {
  name: 'Navigation',
  components: { 
    Searching,
    'b-navbar': BNavbar,
  },
  data() {
    return {
      thmSVGURl: 'https://www.thm.de/_thm/logos/thm.svg',
      disableSearchBar: true, 
      searchInput: ''
    };
  },

  methods: {
    goToLink(link) {
      this.$router.push(`/${link}`);
    },
  },
};
</script>
<style></style>
