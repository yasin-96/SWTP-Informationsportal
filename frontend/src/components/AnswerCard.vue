<template>
  <v-container>
    <v-card
      class="mx-auto"
      :key="id"
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
            <v-list-item-title><span class="font-weight-bold">Antwort</span> von User</v-list-item-title>
            <v-list-item-subtitle>
              
            <span class="subheading mt-1 mr-2"> 
              <v-icon color="blue" small>
                mdi-clock-outline
              </v-icon>
              {{ aDate }}
              </span>
              </v-list-item-subtitle>
          </v-list-item-content>
        </span>
      </v-card-title>
      
      <v-card-text>
        <v-container class="pl-3 pt-2 pr-3">
          <p>
            <label>
              {{ aContent }}
            </label>
          </p>
        </v-container>
        <v-container>
            <v-icon class="mr-1" color="primary">
              mdi-thumb-up
            </v-icon>
            <span class="subheading mr-2">256</span> 
            <v-icon class="mr-1" color="danger">
              mdi-thumb-down
            </v-icon>
            <span class="subheading mr-2">256</span>
        </v-container>
        
      </v-card-text>
      
      

      <v-divider></v-divider>
      
      <!-- Area for all Comments -->
      <v-container>
          <v-row>
              <v-col> 
              </v-col>
          </v-row>
      </v-container>
    </v-card>
  </v-container>
</template>





<script>
import Comment from '@/components/Comment';
import { mapState, mapActions, mapGetters } from 'vuex';


export default {
  name: 'AnswerCard',
  components: { Comment },
  beforeMount: async function() {
    try {
      await this.$store.dispatch('act_getAllComments', this.id);
    } catch (error) {
      console.error(error.error);
    }
  },
  props: {
    id: {
      type: String,
      required: true
    },
    aContent: {
      type: String,
      default: ''
    },
    aRating: {
      type: Number,
      default: 0
    },
    aDate: {
      type: String,
      default: ''
    }
  },
  data: () => ({
    //
  }),
  computed: {
    ...mapActions(['act_getAllComments']),
    ...mapState(['allComments']),
    ...mapGetters(['getListWithComments'])
  }
};
</script>

<style></style>
