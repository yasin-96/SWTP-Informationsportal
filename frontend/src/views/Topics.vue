<template>
 <b-container v-if="!!topics && topics.length">
   <b-row>
     <b-col sm="12" md="6" lg="4" xl="3" v-for="topic in topics" :key="topic.id" class="mt-4">
      <TagCard 
        :tId="topic.id"
        :tName="topic.name"
        :tDate="topic.timeStamp"
      />
     </b-col>
   </b-row>
 </b-container>
</template>

<script>
// @ is an alias to /src
import TagCard from "@/components/TagCard"
import { mapState, mapActions } from 'vuex';

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
    ...mapState(['topicsBasedOnTags']),
    
    topics(){
      if(!!this.topicsBasedOnTags){
        return this.topicsBasedOnTags;
      }
    }
  },
};
</script>
