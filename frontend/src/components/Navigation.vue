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
            <img src="https://www.thm.de/_thm/logos/thm.svg" fluid alt="THM LOGO">
          </b-nav-form>
        </b-navbar-nav>
        <!-- <b-navbar-nav class="ml-auto">
            <b-nav-item href="#">Link</b-nav-item>
            <b-nav-item href="#" disabled>Disabled</b-nav-item>
        </b-navbar-nav>-->

        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
          <!-- <b-nav-form>
              <b-input-group>
                <b-form-input type="text"></b-form-input>
                <b-input-group-append>
                  <b-button>Suchen</b-button>
                </b-input-group-append>
              </b-input-group>
          </b-nav-form>-->

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

    <div style="margin-top: 40px">
      <b-navbar toggleable="lg" type="light" variant="light">
        <b-navbar-nav ul>
          <b-navbar-brand>Informationsportal</b-navbar-brand>
          <b-nav-item href="/home">Home</b-nav-item>
          <b-nav-item href="/new">Neue Frage stellen</b-nav-item>
          <b-nav-item href="/questions">Alle Fragen anzeigen</b-nav-item>
          <b-nav-item href="/">Aktuelle Themen</b-nav-item>
          <b-nav-item href="/about">About</b-nav-item>
        </b-navbar-nav>

        <b-navbar-nav class="ml-auto" >
          <b-nav-form>
            <b-form-input size="sm" class="mr-sm-2" placeholder="Frage suchen"></b-form-input>
            <b-icon icon="search" scale="1.5" class="my-2 my-sm-0" type="submit"></b-icon>
            
          </b-nav-form>
        </b-navbar-nav>
      </b-navbar>
    </div>
  </b-card>
</template>

<script>
import { BNavbar } from 'bootstrap-vue';
import Vue from 'vue'
import { BootstrapVue, BootstrapVueIcons } from 'bootstrap-vue'

Vue.use(BootstrapVue)
Vue.use(BootstrapVueIcons)

export default {
  name: 'Navigation',
  componetens: {
    'b-navbar': BNavbar,
  },
  data: () => ({
    thmSVGURl: {
      blank: false,
      src: 'https://www.thm.de/_thm/logos/thm.svg',
      alt: 'THM Logo',
    },
  }),
  methods: {
    goToLink(link) {
      this.$router.push(`/${link}`);
    },
  },
};
</script>
<style></style>
