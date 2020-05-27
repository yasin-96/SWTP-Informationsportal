<template>
  <div class="questionForm">
    <!--
  <h1>Ask new Question, {{ $ store.state.newQuestion }}</h1>
    -->
    <form class="create-new-question" @submit.prevent="onSubmit">
      <p>
        <label for="qTitle">Frage Titel</label>
        <textarea id="qTitle" v-model="qTitle" placeholder="Fragetitle eingeben"></textarea>
      </p>

      <p>
        <label for="qContent">Frage stellen</label>
        <textarea id="qContent" v-model="qContent" placeholder="Beschreibung eingeben"></textarea>
      </p>

      <p>
        <input type="submit" value="Submit">
      </p>
    </form>
  </div>
</template>

<script>
export default {
  name: 'CreateNewQuestion',
  data() {
    return{
      username: null,
      userId: null,
      qTitle: null,
      qContent: null,
      qDate: null
    }
  },
  methods: {
      onSubmit() {
          let newQuestion = {
              userName: this.userName,
              userId: this.userId,
              qTitle: this.qTitle,
              qContent: this.qContent,
              qDate: this.qDate
          }
          this.$emit('question-submitted', newQuestion)
          this.userId = null
          this.qTitle = null
          this.qContent = null
      }
  }
}
</script>