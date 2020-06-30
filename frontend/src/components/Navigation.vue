<template>
  <div class="shadow-sm p-3 mb-5 bg-white rounded">
    <div>
      <!-- <b-navbar toggleable="md" class="mt-2">
        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-form>
              <img src="https://www.thm.de/_thm/logos/thm.svg" fluid alt="THM LOGO" />
            </b-nav-form>
          </b-navbar-nav>

          <!-- Right aligned nav items 
          <b-navbar-nav class="ml-auto">
            <Searching />
            <b-nav-item-dropdown right>
              <!-- Using 'button-content' slot -
              <template v-slot:button-content>
                <b-avatar rounded variant="primary" text="BV"></b-avatar>
              </template>
              <b-dropdown-item to="#">Profile</b-dropdown-item>
              <b-dropdown-item to="/logout">Sign Out</b-dropdown-item>
            </b-nav-item-dropdown>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar> -->

      <b-navbar>
        <b-navbar-nav>
          <b-collapse>
            <b-nav-item-dropdown right>
              <template v-slot:button-content>
                <b-avatar variant="primary" text="BV"></b-avatar>
              </template>
              <b-dropdown-item href="#">Profile</b-dropdown-item>
              <b-dropdown-item to="/logout">Sign Out</b-dropdown-item>
            </b-nav-item-dropdown>
          </b-collapse>
        </b-navbar-nav>
      </b-navbar>
    </div>
    <div>
      <div class="mt-4">
        <b-navbar toggleable="md" type="light" variant="">
          <b-navbar-nav>
            <!-- TODO image not there -->
            <b-navbar-brand to="/">
                <img src="https://www.thm.de/_thm/logos/thm.svg" fluid alt="THM LOGO" />
            </b-navbar-brand>
            <b-nav-item to="/home">Home</b-nav-item>
            <b-nav-item to="/new">New</b-nav-item>
            <b-nav-item to="/questions">General</b-nav-item>
            <b-nav-item to="/topics">Topics</b-nav-item>
            <b-nav-item to="/about">About</b-nav-item>
          </b-navbar-nav>
          <b-navbar-nav class="ml-auto">
            <Searching />
          </b-navbar-nav>
        </b-navbar>
      </div>
    </div>
  </div>
</template>

<script>
import Searching from '@/components/Searching';
import { BNavbar } from 'bootstrap-vue';
import { mapActions } from 'vuex';

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
      searchInput: '',
      isHovered: false,
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
};
</script>
<style>
.nav-item:hover {
  background-color: rgb(223, 229, 230);
}
</style>
