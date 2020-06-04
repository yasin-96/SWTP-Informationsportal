<template>
  <div id="newQuestionButton">
    <button class="btn" @click="goToLink('newQuestion')">Stell Deine Frage</button>
  </div>
</template>

<script>
import CreateNewQuestion from "@/components/CreateNewQuestion";

export default {
  components: {
    CreateNewQuestion
  },
  methods: {
    goToLink(link){
      this.$router.push(`/${link}`);
    }
  }
}
</script>

<style>
</style>