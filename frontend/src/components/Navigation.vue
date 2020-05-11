<template>
  <nav>
    <v-app-bar
      color="#7fba25"
      dark
    >
      <v-app-bar-nav-icon></v-app-bar-nav-icon>
      <v-spacer></v-spacer>
      <v-toolbar-title class="display-1 justify-center"> Informationsportal</v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn icon>
        <v-icon>mdi-magnify</v-icon>
      </v-btn>

      <template v-slot:extension>
        <v-tabs align->
          <v-tab>Startseite</v-tab>
        </v-tabs>
      </template>
    </v-app-bar>
  </nav>
</template>

<script>
  export default {
    data: () => ({

    }),
  }
</script>

<script>
  export default {
    name: 'Navigation',
    data: () => ({

    }),
  }
</script>

<style>

</style>