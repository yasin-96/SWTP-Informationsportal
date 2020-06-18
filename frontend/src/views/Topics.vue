<template>
 <b-container v-if="questions.length">
   <b-row>
     <b-col sm="12" md="6" lg="4" xl="3" v-for="quest in questions" :key="quest.id" class="mt-4">
      <QuestionCard 
        :qId="quest.id"
        :qHeader="quest.header"
        :qContent="quest.content"
        :qTags="quest.tags"
        :qDate="quest.timeStamp"
        :qTrimText="true"
      />
     </b-col>
   </b-row>
 </b-container>
</template>

<script>
// @ is an alias to /src
import QuestionCard from "@/components/QuestionCard"
import { mapState, mapActions } from 'vuex';

export default {
  name: "Topics",
  components: {
    QuestionCard
  },
  async beforeMount() {
    try {
      await this.$store.dispatch('act_getCurrentTopics');
    } catch(error) {
      console.error("beforeMount: ", error);
    } 
  },
  computed: {
    ...mapActions(['act_getCurrentTopics',]),
    ...mapState(['questionsBasedOnTopics']),
    
    questions(){
      if(!!this.questionsBasedOnTopics){
        return this.activeQuestions;
      }
    }
  },
  watch: { 
    questionsBasedOnTopics(){
      this.$forceUpdate();
    },
  }
};
</script>
