<template>
  <v-container>
    <v-card
      class="mx-auto"
      raised
      min-width="200"
      max-height="400"
    >
      <v-card-title>
        <v-icon
          large
          left
        >
          mdi-account-circle
        </v-icon>
        <span class="title font-weight-light">
          <v-list-item-content pa-1>
            <v-list-item-title><span class="font-weight-bold">Frage</span> von User</v-list-item-title>
            <v-list-item-subtitle>
              
            <span class="subheading mt-1 mr-2"> 
              <v-icon color="blue" small>
                mdi-clock-outline
              </v-icon>
              {{ qDate }}
              </span>
              </v-list-item-subtitle>
          </v-list-item-content>
        </span>
      </v-card-title>
      
      <v-card-text>
        <v-container class="pl-3 pt-2 pr-3">
          <p>
            <label class="headline font-weight-bold font-weight-black font-italic changeMouse" @click="goToQuestion()"> 
              {{ qHeader }}
            </label>
          </p>
          <p>
            <label>
              {{ qContent }}
            </label>
          </p>
        </v-container>
      </v-card-text>
      
      <v-divider></v-divider>


      <v-card-actions>
        <v-list-item class="grow">
          <v-chip-group
            multiple
            show-arrows
            active-class="primary--text"
          >
            <v-chip v-for="tag in qTags" :key="tag" 
              class="ma-2 caption"
              label
              color="brown lighten-2"
              text-color="white"
            >
              {{ tag }}
            </v-chip>
          </v-chip-group>
          <v-row
            align="center"
            justify="end"
          >
            <v-icon class="mr-1" color="blue">
              mdi-thumb-up
            </v-icon>
            <span class="subheading mr-2">256</span>
          </v-row>
        </v-list-item>
      </v-card-actions>
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
    },
  }, 
  data: () => ({
      //
  }),
  methods: {
    goToQuestion() {
      this.$router.push(`/question/${this.$props.qId}`);
    }
  }
}
</script>

<style>
.changeMouse {
    cursor: pointer;
}
</style>