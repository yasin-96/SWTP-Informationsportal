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
          <b-nav-item class="mt-1" disabled="">| </b-nav-item>
          <b-nav-item class="mt-1" href="/chat/">Chat</b-nav-item>
          <b-nav-item class="mt-1" href="/microblog/microblog">Microblog</b-nav-item>
        </b-navbar-nav>

        <b-navbar-nav class="ml-auto">
          <Searching />
            <b-nav-item-dropdown  right>
              <template v-slot:button-content>
                <template>
                  <b-avatar size="2rem" variant="info" :text="getFirstLetterFromUser"></b-avatar>
                </template>
              </template>
              <b-dropdown-item :href="'/microblog/profile/'+ userUUID ">Profile</b-dropdown-item>
              <b-dropdown-item href="/logout">Sign Out</b-dropdown-item>
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
      //logo from thm 
      thmSVGURl: 'https://www.thm.de/_thm/logos/thm.svg',
      
      //width of logo
      imgX: 92,

      //height of logo
      imgY: 38,
    };
  },

  methods: {
    /**
     * Go the site of the link
     */
    goToLink(link) {
      this.$router.push(`${link}`);
    },
  },
  computed: {
    ...mapGetters(['getFirstLetterFromUser','getUserId']),
    
    /**
     * Show the Avatar from user, if user was loaded
     */
    avatarLetter() {
      if (this.getFirstLetterFromUser) {
        return this.getFirstLetterFromUser;
      } else {
        return '';
      }
    },
    /**
     * Read the userid for set the profile link
     */
    userUUID(){
      if (this.getUserId) {
        return this.getUserId;
      } else {
        return ""
      }
    }
  },
};
</script>