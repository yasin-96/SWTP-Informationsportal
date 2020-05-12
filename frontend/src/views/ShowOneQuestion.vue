<template>
 <v-container>
    <v-row>
        <QuestionCard 
            :qId="quest.id"
            :qHeader="quest.header"
            :qContent="quest.content"
            :qTags="quest.tags"
            :qDate="quest.timeStamp"
        />
    </v-row>
    <v-row>
     <v-col v-for="aa in allAnswers" :key="aa">
      <AnswerCard 
        :aContent="aa.content"
        :aRating="aa.Rating"
        :aDate="aa.timeStamp"
      />
     </v-col>
   </v-row>
 </v-container>
</template>

<script>
// @ is an alias to /src
import QuestionCard from "@/components/QuestionCard";
import AnswerCard from "@/components/AnswerCard";
import { mapState, mapActions } from 'vuex';

export default {
  name: "ShowAllQuestions",
  components: {
    QuestionCard,
    AnswerCard,
  },
  beforeMount: async function() {

      let para = this.$router.params.id
      console.log(para);  

    try {
      await this.$store.dispatch('act_getOneAnswers', para);
    } catch(error) {
      console.error("beforeMount: ", error);
    } 
  },
  computed: {
    ...mapActions(['act_getAllQuestions', 'act_getAllAnswers']),
    ...mapState(['allQuestions', 'allAnswers']),
  },
};
</script>
