<template>
  <b-card header-tag="header" header-bg-variant="#DFE8DF" header-border-variant="white" style="min-width: 200px; min-height: 300px;" class="bCard">
    <!-- Information about user & creation date -->
    <template v-slot:header>
      <b-row class="justify-content-left">
        <b-col cols="3" sm="2" md="2" lg="1">
          <!-- <b-iuser class="mr-2" font-scale="3"></b-iuser> -->
          <h1><fai icon="user-circle" /></h1>
          <!-- <b-avatar variant="primary" text="BV"></b-avatar> -->
        </b-col>
        <b-col cols="9" sm="10" md="10" lg="11">
          <strong>Frage</strong> erstell am <br /><small class="ml-3"
            ><fai icon="clock" />
            {{ qDate }}
          </small>
          <b-button v-if="qEdit" @click="editQuestion()"><fai icon="edit"></fai></b-button>
        </b-col>
      </b-row>
    </template>

    <!-- Question header with content -->
    <b-card-title class="changeMouse bQuestionLink" @click="goToQuestion()">
      {{ qHeader }}
    </b-card-title>

    <!-- Question content -->
    <b-card-text>
      <p v-if="qTrimText">{{ qContent.substr(0, qShowMaxText) + ' .....' }}</p>
      <p v-else>{{ qContent }}</p>
    </b-card-text>

    <!-- Show all Tags from Question and its rating -->
    <template v-if="qTags && qFooter" v-slot:footer>
      <b-form-tags v-model="allTags" tag-pills disabled class="mb-2" placeholder> </b-form-tags>
    </template>
  </b-card>
</template>

<script>
import { BCard, BFormTags } from 'bootstrap-vue';
export default {
  name: 'QuestionCard',
  components: {
    'b-card': BCard,
    'b-form-tags': BFormTags,
  },
  props: {
    qId: {
      type: String,
      default: '',
      length: 24,
    },
    qHeader: {
      type: String,
      default: '',
    },
    qContent: {
      type: String,
      default: '',
    },
    qTags: {
      type: Array,
      default: Array,
    },
    qDate: {
      type: String,
      default: '',
    },
    qFooter: {
      type: Boolean,
      default: false,
    },
    qShowMaxText: {
      type: Number,
      default: 50,
    },
    qTrimText: {
      type: Boolean,
      default: false,
    },
    qEdit: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    /**
     * By clicking on the title of a question, a page is called up and all information is provided.
     */
    goToQuestion() {
      this.$router.push(`/question/${this.$props.qId}`).catch(err => {});
    },
    editQuestion(){
      this.$router.push(`/question/edit/${this.$props.qId}`).catch(err => {});
    }
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
