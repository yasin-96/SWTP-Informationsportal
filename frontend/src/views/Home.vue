<template>
 <v-container>
    <QuestionCard />
 </v-container>
</template>

<script>
// @ is an alias to /src
import QuestionCard from "@/components/QuestionCard"

export default {
  name: "Home",
  components: {
    QuestionCard
  }
};
</script>
