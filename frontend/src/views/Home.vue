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
  .btn {
      background-color: rgb(243, 245, 243); 
      border: none;
      padding: 14px 40px;
      text-align: center;
      text-decoration: none;
      display: inline-block;
      font-size: 20px;
      margin: 4px 2px;
      cursor: pointer;
      border-radius: 12px;
    } 
  #newQuestionButton{
    background-color: #7bbb15;
    height: 250px;
    padding: 120px;
    text-align: center;
    color: #7bbb15;
  }
</style>