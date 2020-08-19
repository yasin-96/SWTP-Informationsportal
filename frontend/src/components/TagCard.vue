<template>
  <b-card header-bg-variant="white" footer-bg-variant="white" class="bCard shadow rounded">
    <template v-slot:header>
      <b-row class="justify-content-center">
        <b-col>
          <b-button-group>
            <b-button size="sm" disabled variant="white">
              <small>
                <fai icon="tags" />
              </small>
            </b-button>
            <b-button size="sm" disabled variant="white">
              <strong>Topic</strong>
            </b-button>
          </b-button-group>
        </b-col>
      </b-row>
    </template>
    <template v-slot:footer>
      <b-button size="sm" disabled variant="white">
        <small>
          <fai icon="clock" />
          {{ tDate }}
        </small>
      </b-button>
    </template>

    <b-card-title class="changeMouse bQuestionLink" @click="goToQuestion(tName)" align-center>
      <b-container>
        <b-row class="justify-content-center">{{ tName }}</b-row>
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
    /**
     * The id of tag
     */
    tId: {
      type: String,
      default: '',
    },
    /**
     * Name of tag
     */
    tName: {
      type: String,
      default: '',
    },
    /**
     * Creation date of tag
     */
    tDate: {
      type: String,
      default: '',
    },
  },
  methods: {
    /**
     * By clicking on the title of a tag, a page is called up and all information is provided.
     */
    goToQuestion(topic) {
      console.log('topic', topic);

      this.$router.push(`/questions/topics/${topic}`).catch((err) => {});
    },
  },
};
</script>

<style>
.changeMouse {
  cursor: pointer;
}
</style>
