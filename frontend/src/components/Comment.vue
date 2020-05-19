<template>
  <v-card>
    <v-card-subtitle class="blue lighten-1 ">
      <h3 class="caption white--text font-weight-light">Title 2</h3>
    </v-card-subtitle>
    <v-card-text>
      <v-container>
      <v-text>
        shlahdjkahdjklhasjkdh
      </v-text>
      </v-container>
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  props: {
    cContent: {
      type: String,
      default: '',
    },
    cUserName: {
      type: String,
      default: '',
    },
    cRating: {
      type: Number,
      default: 0,
    },
    cDate: {
      type: String,
      default: '',
    },
  },
};
</script>

<style lang="scss">
  $border-radius-root: none;
</style>
