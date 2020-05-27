<template>
  <div class="newQuestion">
    <CreateNewQuestion @question-submitted="addQuestion"></CreateNewQuestion>
  </div>
</template>

<script>
import CreateNewQuestion from "@/components/CreateNewQuestion";

export default {
  components: {
    CreateNewQuestion
  },
  data() {
      return{
       questions: []
      }
  },
  methods: {
      addQuestion(newQuestion) {
        this.questions.push(newQuestion)
    }
  }
}
</script>

<style>
    #qTitle {
        width: 100%;
        height: 50px;
        padding: 12px 20px;
        box-sizing: border-box;
        border: 2px solid rgb(232, 238, 238);
        border-radius: 4px;
        background-color: #ffffff;
        font-size: 16px;
        resize: none;
    }
    #qContent {
        width: 100%;
        height: 200px;
        padding: 12px 20px;
        box-sizing: border-box;
        border: 2px solid rgb(232, 238, 238);
        border-radius: 4px;
        background-color: #ffffff;
        font-size: 16px;
        resize: none;
    }
    .questionForm {
        border-radius: 5px;
        background-color: #f2f2f2;
        padding: 20px;
        margin: 120px;
        margin-top: 40px;
    }
    input[type=text], select, textarea {
        width: 100%;
        padding: 12px;
        border: 1px solid #ccc;
        border-radius: 4px;
        resize: vertical;
    }
    label {
        padding: 12px 12px 12px 0;
        display: inline-block;
    }
    input[type=submit] {
        background-color: #7bbb15;
        color: white;
        padding: 12px 20px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        width: 150px;
    }
</style>