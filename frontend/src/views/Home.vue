// <template>
//  <v-container>
//    <v-row>
//      <v-col v-for="quest in allQuestions" :key="quest.id">
//       <QuestionCard 
//         :qId="quest.id"
//         :qHeader="quest.header"
//         :qContent="quest.content"
//         :qTags="quest.tags"
//         :qDate="quest.timeStamp"
//       />
//      </v-col>
//    </v-row>
//    <v-row>
//      <v-col v-for="aa in allAnswers" :key="aa">
//       <AnswerCard 
//         :aContent="aa.content"
//         :aRating="aa.Rating"
//         :aDate="aa.timeStamp"
//       />
//      </v-col>
//    </v-row>
//  </v-container>
// </template>

// <script>
// // @ is an alias to /src
// import QuestionCard from "@/components/QuestionCard"
// import AnswerCard from "@/components/AnswerCard"
// import { mapState, mapActions } from 'vuex';

// export default {
//   name: "Home",
//   components: {
//     QuestionCard,
//     AnswerCard
//   },
//   beforeMount: async function() {
//     try {
//       await this.$store.dispatch('act_getAllQuestions')
//       // await this.$store.dispatch('act_getAllAnswers', "adedae95-c8cb-44b3-a8fb-ece4416b08c1");
//     } catch(error) {
//       console.error("beforeMount: ", error);
//     } 
//   },
//   computed: {
//     ...mapActions(['act_getAllQuestions', 'act_getAllAnswers']),
//     ...mapState(['allQuestions', 'allAnswers']),
//   },
// };
// </script>
