<template>
  <div class="navigationBar navbar-dark">
    <div>
      <b-navbar>
        <b-navbar-nav>
          <!-- <b-collapse> -->
            
          <!-- </b-collapse> -->
        </b-navbar-nav>
      </b-navbar>
    </div>
    <div>
      <div class="mt-4">
        <b-navbar toggleable="md">
          <b-navbar-nav>
            <b-navbar-brand to="/">
              <img src="https://www.thm.de/_thm/logos/thm.svg" fluid  :width="imgX" :height="imgY" alt="THM LOGO" />
            </b-navbar-brand>
            <b-nav-item to="/home">Home</b-nav-item>
            <b-nav-item to="/new">New</b-nav-item>
            <b-nav-item to="/questions">General</b-nav-item>
            <b-nav-item to="/topics">Topics</b-nav-item>
            <b-nav-item to="/about">About</b-nav-item>
            <b-nav-item> 
              <Sidebar />
            </b-nav-item>
          </b-navbar-nav>
          <b-navbar-nav class="ml-auto">
            <Searching />
            <b-nav-item-dropdown right>
              <template v-slot:button-content>
                <b-avatar variant="success" :text="getFirstLetterFromUser"></b-avatar>
              </template>
              <b-dropdown-item to="#">Profile</b-dropdown-item>
              <b-dropdown-item to="/logout">Sign Out</b-dropdown-item>
            </b-nav-item-dropdown>
          </b-navbar-nav>
          
        </b-navbar>
      </div>
    </div>
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
    Sidebar
  },
  data() {
    return {
      thmSVGURl: 'https://www.thm.de/_thm/logos/thm.svg',
      disableSearchBar: true,
      searchInput: '',
      isHovered: false,
      imgX: 92,
      imgY: 38
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
  computed: { ...mapGetters(['getFirstLetterFromUser']),
  avatarLetter() {
    if(this.getFirstLetterFromUser) {
      return this.getFirstLetterFromUser;
    } else {
      return ""
    }
  } },
};
</script>
<style>

</style>
