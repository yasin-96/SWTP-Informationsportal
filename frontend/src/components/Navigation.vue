<template>
  <div class="navigationBar navbar-dark">
    <b-navbar toggleable="lg">
      <b-navbar-brand to="/">
        <img
          src="https://www.thm.de/_thm/logos/thm.svg"
          fluid
          :width="imgX"
          :height="imgY"
          alt="THM LOGO"
        />
      </b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item class="mt-1" to="/home">Home</b-nav-item>
          <b-nav-item class="mt-1" to="/new">New</b-nav-item>
          <b-nav-item class="mt-1" to="/questions">General</b-nav-item>
          <b-nav-item class="mt-1" to="/topics">Topics</b-nav-item>
          <b-nav-item class="mt-1" to="/about">About</b-nav-item>
          <b-nav-item class="mt-1">
            <Sidebar />
          </b-nav-item>
        </b-navbar-nav>

        <b-navbar-nav class="ml-auto">
          <Searching />
          <b-nav-item-dropdown right>
            <template v-slot:button-content>
              <b-avatar size="2rem" variant="success" :text="getFirstLetterFromUser"></b-avatar>
            </template>
            <b-dropdown-item to="#">Profile</b-dropdown-item>
            <b-dropdown-item to="/logout">Sign Out</b-dropdown-item>
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
import Searching from '@/components/Searching';
import Sidebar from '@/components/Sidebar';
import { BNavbar } from 'bootstrap-vue';
import { mapGetters } from 'vuex';

export default {
  name: 'Navigation',
  components: {
    Searching,
    'b-navbar': BNavbar,
    Sidebar,
  },
  data() {
    return {
      thmSVGURl: 'https://www.thm.de/_thm/logos/thm.svg',
      disableSearchBar: true,
      searchInput: '',
      isHovered: false,
      imgX: 92,
      imgY: 38,
    };
  },

  methods: {
    goToLink(link) {
      this.$router.push(`${link}`);
    },
    handleHover(hovered) {
      this.isHovered = hovered;
    },
  },
  computed: {
    ...mapGetters(['getFirstLetterFromUser']),
    avatarLetter() {
      if (this.getFirstLetterFromUser) {
        return this.getFirstLetterFromUser;
      } else {
        return '';
      }
    },
  },
};
</script>
<style>
</style>
