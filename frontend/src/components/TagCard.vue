<template>
  <b-card header-tag="header" header-bg-variant="#DFE8DF" header-border-variant="white" class="bCard">
    <template v-slot:header>
      <b-row class="justify-content-center">
        <b-col>
          <b-button-group>
            <b-button size="sm" disabled variant="white"> </b-button>
            <b-button size="sm" disabled variant="white"> <strong>Topic </strong> </b-button>
            <b-button size="sm" disabled variant="white">
              <small>
                <fai icon="clock" />
                {{ tDate }}
              </small>
            </b-button>
          </b-button-group>
        </b-col>
      </b-row>
    </template>

    <b-card-title class="changeMouse bQuestionLink" @click="goToQuestion(tName)" align-center>
      <b-container>
        <b-row class="justify-content-center">
          {{ tName }}
        </b-row>
      </b-container>
    </b-card-title>
  </b-card>
</template>

<script>
import { BCard, BFormTags } from 'bootstrap-vue';
export default {
  name: 'TagCard',
  components: {
    'b-card': BCard,
    'b-form-tags': BFormTags,
  },
  props: {
    tId: {
      type: String,
      default: '',
    },
    tName: {
      type: String,
      default: '',
    },
    tDate: {
      type: String,
      default: '',
    },
  },
  data() {
    return {};
  },
  methods: {
    /**
     * By clicking on the title of a question, a page is called up and all information is provided.
     */
    goToQuestion(topic) {
      console.log('topic', topic);

      this.$router.push(`/questions/topics/${topic}`).catch((err) => {});
    },
  },
  computed: {
    allTags() {
      return this.qTags.map((tag) => tag.name);
    },
  },
};
</script>

<style>
.changeMouse {
  cursor: pointer;
}
</style>
