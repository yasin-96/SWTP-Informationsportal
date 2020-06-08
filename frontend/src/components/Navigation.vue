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
            <img src="https://www.thm.de/_thm/logos/thm.svg" fluid alt="THM LOGO" />
          </b-nav-form>
        </b-navbar-nav>
        <!-- <b-navbar-nav class="ml-auto">
            <b-nav-item href="#">Link</b-nav-item>
            <b-nav-item href="#" disabled>Disabled</b-nav-item>
          </b-navbar-nav> -->

        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
          <!-- <b-nav-form>
              <b-input-group>
                <b-form-input type="text"></b-form-input>
                <b-input-group-append>
                  <b-button>Suchen</b-button>
                </b-input-group-append>
              </b-input-group>
            </b-nav-form> -->

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
  </b-card>
</template>

<script>
import { BNavbar } from 'bootstrap-vue';

export default {
  name: 'Navigation',
  componetens: {
    'b-navbar': BNavbar,
  },
  data() {
    return {
      thmSVGURl: {
        blank: false,
        src: 'https://www.thm.de/_thm/logos/thm.svg',
        alt: 'THM Logo',
      },
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
