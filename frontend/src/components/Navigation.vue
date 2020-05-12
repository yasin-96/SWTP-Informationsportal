<template>
  <nav>
    <v-app-bar
      color="#7fba25"
      dark
      class="overflow-hidden"
    >
      <v-app-bar-nav-icon></v-app-bar-nav-icon>
      <v-spacer></v-spacer>
      <v-toolbar-title class="display-1"> Informationsportal</v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn icon>
        <v-icon>mdi-magnify</v-icon>
      </v-btn>

      <template v-slot:extension>
        <v-tabs>
          <v-spacer></v-spacer>
          <v-tab @click="goToLink('questions')">
            <v-icon>
              mdi-home
            </v-icon>
              Startseite
          </v-tab>
          <v-tab @click="goToLink('notification')">
            Mitteilungen</v-tab>
          <v-spacer></v-spacer>
        </v-tabs>
      </template> 
    </v-app-bar>
  </nav>
</template>

<script>
  export default {
    name: 'Navigation',
    data: () => ({

    }),
    methods: {
      goToLink(link){
        this.$router.push(`/${link}`);
      }
    }
  }
</script>

<style>

</style>