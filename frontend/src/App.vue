<template>
  <b-container id="app" fluid>
    <Navigation />
      <b-container fluid>
        <router-view />
      </b-container>
  </b-container>
</template>


<script>
import Navigation from "@/components/Navigation";

export default {
  name: 'App',
  components: { Navigation },
}
</script>

<style>
body {
  background-color: #DFE8DF;
}
#app {
  padding: 0;
  margin: 0;
}
</style>
