<template>
  <b-card
    header-tag="header"
    header-bg-variant="white"
    footer-bg-variant="white"
    style="min-width: 200px; min-height: 300px;"
    class="bCard"
  >
    <!-- Information about user & creation date -->
    <template v-slot:header>
      <b-row class="justify-content-left">
        <b-col>
          <b-button-group>
            <h2>
              <fai icon="user-circle" />
            </h2>
            <b-button size="sm" disabled variant="white"></b-button>
            <b-button size="sm" disabled variant="white">
              <strong>Frage</strong>
              erstellt von {{ qUserName }}
              <small class="ml-3"></small>
            </b-button>
            <b-button v-if="!qFooter" size="sm" disabled variant="white">
              <small>
                <fai icon="clock" />
                {{ qDate }}
              </small>
            </b-button>
          </b-button-group>
        </b-col>
        <b-col cols="mx-auto">
          <b-button variant="outline-secondary" v-if="userEdit" size="sm" @click="editQuestion()">
            <fai icon="pen"></fai>
          </b-button>
        </b-col>
      </b-row>
    </template>

    <!-- Question header with content -->
    <b-card-title class="changeMouse bQuestionLink" @click="goToQuestion()">{{ qHeader }}</b-card-title>

    <!-- Question content -->
    <b-card-text>
      <!-- Displayed for edited/detailed view -->
      <Editor
        v-show="displayContent"
        class="questionCardEditor"
        ref="mde"
        v-model="qContent"
        :configs="mdeConfig"
      />
    </b-card-text>

    <!-- Show all Tags from Question and its rating -->
    <template v-if="qTags && qFooter" v-slot:footer>
      <b-form-tags v-model="allTags" tag-pills disabled class="mb-2" placeholder></b-form-tags>
    </template>

    <!-- Show all Tags from Question and its rating -->
    <template v-if="qFooter" v-slot:footer>
      //TODO TIMESTAMP hier anzeigen
      <b-button size="sm" disabled variant="white">
        <small>
          <fai icon="clock" />
          {{ qDate }}
        </small>
      </b-button>
    </template>
  </b-card>
</template>

<script>
import { BCard, BFormTags } from 'bootstrap-vue';
import VueSimplemde from 'vue-simplemde';

export default {
  name: 'QuestionCard',
  components: {
    'b-card': BCard,
    'b-form-tags': BFormTags,
    Editor: VueSimplemde,
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
      default: [],
    },
    qDate: {
      type: String,
      default: '',
    },
    qFooter: {
      type: Boolean,
      default: false,
    },
    displayContent: {
      type: Boolean,
      default: false,
    },
    qUserId: {
      type: String,
      required: true,
    },
    qUserName: {
      type: String,
      required: true,
    },
    qEdit: {
      type: Boolean,
      default: false,
    },
    userEdit: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      mdeConfig: {
        toolbar: null,
        // styleSelectedText: false,
        shortcuts: {},
      },
      test: '',
    };
  },
  async mounted() {
    this.$refs.mde.simplemde.togglePreview();
  },
  methods: {
    /**
     * By clicking on the title of a question, a page is called up and all information is provided.
     */
    goToQuestion() {
      this.$router.push(`/question/${this.$props.qId}`).catch((err) => {});
    },
    editQuestion() {
      this.$router.push(`/question/edit/${this.$props.qId}`).catch((err) => {});
    },
  },
  computed: {
    allTags() {
      return this.qTags.map((tag) => tag.name);
      this.test = getRendertHtmlFromMarkdown(this.qContent);
    },
  },
};
</script>

<style scoped>
.questionCardEditor >>> .CodeMirror {
  background-color: #fff;
  border: none;
}

.questionCardEditor >>> .editor-preview,
.editor-preview-side {
  background: #fff;
}

.changeMouse {
  cursor: pointer;
}
</style>
