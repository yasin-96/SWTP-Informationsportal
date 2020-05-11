<template>
 <v-container>
   <v-row>
     <v-col v-for="quest in allQuestions" :key="quest.id">
      <QuestionCard 
        :qId="quest.id"
        :qHeader="quest.header"
        :qContent="quest.content"
        :qTags="quest.tags"
        :qDate="quest.timeStamp"
      />
     </v-col>
   </v-row>
 </v-container>
</template>

<script>
// @ is an alias to /src
import QuestionCard from "@/components/QuestionCard"
import { mapState, mapActions } from 'vuex';

export default {
  name: "Home",
  components: {
    QuestionCard
  },
  beforeMount: async function() {
    try {
      await this.$store.dispatch('act_getAllQuestions');
    } catch(error) {
      console.error("beforeMount: ", error);
    } 
  },
  computed: {
    ...mapActions(['act_getAllQuestions']),
    ...mapState(['allQuestions']),
  },
};
</script>
