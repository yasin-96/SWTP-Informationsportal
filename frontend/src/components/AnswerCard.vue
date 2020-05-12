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
        <v-divider inset></v-divider>
      </v-card-title>
      
      <v-card-text>
        <v-content>
          <p>
            <label>
              {{ qContent }}
            </label>
          </p>
        </v-content>
      </v-card-text>
      

      <v-card-actions>
        <v-list-item class="grow">
          <v-row
            align="center"
            justify="start"
          >
            <v-icon class="mr-1" color="green">
              mdi-thumb-up
            </v-icon>
            <span class="subheading mr-2">15</span>

            <v-icon class="mr-1" color="red">
              mdi-thumb-down
            </v-icon>
            <span class="subheading mr-2">5</span>
          </v-row>
        </v-list-item>
      </v-card-actions>

      <v-divider></v-divider>

      <v-container v-if="qTags.length">
          <Comment />
      </v-container>
    </v-card>
  </v-container>
</template>

<script>

import Comment from "@/components/Comment"

export default {
    name: "AnswerCard",
    components: { Comment },
    props: {
        
        aContent: {
            type: String,
            default: ""
        },
        aRating: {
            type: Number,
            default: 0
        },
        aDate: {
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