<template>
  <v-container>
    <v-row v-if="getListWithComments">
      <v-col>
        getListWithComments
      </v-col>
    </v-row>
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
      await this.$store.dispatch('act_getAllComments', this.paramId);
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
