<template></template>

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