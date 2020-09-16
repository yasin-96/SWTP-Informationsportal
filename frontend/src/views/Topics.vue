<template>
 <b-container>
   <b-row v-if="!!getTopicsBasedOnTags && getTopicsBasedOnTags.length > 0">
     <b-col sm="12" md="6" lg="4" xl="4" v-for="topic in getTopicsBasedOnTags" :key="topic.id" class="mt-4">
      <TagCard 
        :tId="topic.id"
        :tName="topic.name"
        :tDate="topic.timeStamp"
      />
     </b-col>
   </b-row>

   <b-row v-else>
     <b-col sm="12" md="12" lg="12" xl="12">
       <b-card border-variant="info" header="Information" header-border-variant="info" header-text-variant="info" align="center">
        <b-card-text>
          <p>No topics have been created yet.</p>
          <p>New topics are automatically created when creating a question via the tag list.</p>
          <p>To create a new question click on the <router-link to="/new">link</router-link></p>
        </b-card-text>
      </b-card>
     </b-col>
   </b-row>
 </b-container>
</template>

<script>
import TagCard from "@/components/TagCard"
import { mapActions, mapGetters } from 'vuex';

export default {
  name: "Topics",
  components: {
    TagCard
  },
  async beforeMount() {
    console.warn("TOPICS.vue -> act_getCurrentTopics");
    await this.$store.dispatch('act_getCurrentTopics');
  },
  computed: {
    ...mapActions(['act_getCurrentTopics',]),
    ...mapGetters(['getTopicsBasedOnTags'])
  },
};
</script>