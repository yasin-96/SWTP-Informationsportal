<template>
  <v-container>
    <v-card
      class="mx-auto"
      raised
      shaped
      max-width="400"
    >
      <v-card-title>
        <v-icon
          large
          left
        >
          mdi-account-circle
        </v-icon>
        <span class="title font-weight-light">
          <v-list-item-content>
            <v-list-item-title><span class="font-weight-bold">Frage</span> von User</v-list-item-title>
          </v-list-item-content>
        </span>
      </v-card-title>
      <v-divider></v-divider>
      <v-card-text class="headline font-weight-bold font-italic">
        {{ qHeader }}
      </v-card-text>
      <v-card-text class="subtitle-1">
        {{ qContent }}
      </v-card-text>

      <v-card-actions>
        <v-list-item class="grow">
          <v-row
            align="center"
            justify="start"
          >
            <v-icon class="mr-1" color="blue">
              mdi-clock
            </v-icon>
            <span class="subheading mr-2"> {{ qDate }}</span>
          </v-row>
          <v-row
            align="center"
            justify="end"
          >
            <v-icon class="mr-1" color="red">
              mdi-heart
            </v-icon>
            <span class="subheading mr-2">256</span>
          </v-row>
        </v-list-item>
      </v-card-actions>

      <v-divider></v-divider>

      <v-container v-if="qTags.length">
        <v-icon color="brown  darken-2" >mdi-label</v-icon>
          <v-chip v-for="tag in qTags" :key="tag"
            class="ma-2 caption"
            label
            color="brown lighten-2"
            text-color="white"
          >
            {{ tag }}
          </v-chip>
      </v-container>
    </v-card>
  </v-container>
</template>

<script>
export default {
  name: "QuestionCard",
  props: {
    qId: {
      type: String,
          default: "",
          length: 24
    },
    qHeader:{
      type: String,
      default: ""
    },
    qContent: {
        type: String, 
        default: ""
    },
    qTags: {
        type: Array,
        default: Array
    },
    qDate: {
        type: String,
        default: ""
    }
  }, 
  data: () => ({
      //
  }),
}
</script>

<style>

</style>