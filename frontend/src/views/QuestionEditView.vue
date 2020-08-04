<template>
  <b-container class="mt-3 mb-5">
    <!-- <b-row b-if="oneQuestion && isQuestionAreLoaded"> -->
    <b-row>
      <b-col>
        <b-card
          header-bg-variant="white"
          footer-bg-variant="white"
          style="min-width: 200px; min-height: 300px;"
          class="bCard rounded shadow"
        >
          <!-- Information about user & creation date -->
          <template v-slot:header>
            <b-row class="justify-content-left">
              <b-col>
                <b-button-group>
                  <b-button disabled variant="white">
                    <fai icon="user-circle" size="lg" />
                  </b-button>
                  <b-button size="sm" disabled variant="white"></b-button>
                  <b-button size="sm" disabled variant="white">
                    <strong>Frage</strong>
                    vom {{ oneQuestion.userId }}
                    <small class="ml-3"></small>
                  </b-button>
                </b-button-group>
              </b-col>
            </b-row>
          </template>

          <!-- Question header with content -->
          <b-card-title>
            <b-form-input
              v-model="question.header"
              :search-input.sync="question.header"
              type="text"
              class="form-control"
              id="questionHeader"
              size="lg"
              center
              placeholder="Fragetitel"
            ></b-form-input>
          </b-card-title>

          <!-- Question content -->
          <b-card-text>
            <editor :configs="mdeConfig" v-model="question.content" />
            <b-button size="sm" disabled variant="white">
              <small>
                <fai icon="clock" />
                {{ oneQuestion.timeStamp }}
              </small>
            </b-button>
          </b-card-text>
          <!-- Show all Tags from Question and its rating -->
          <template v-if="isTagsAreLoaded" v-slot:footer>
            <!-- Tags for this question -->
            <b-form-tags
              @input="formatter($event)"
              v-model="question.tags"
              :remove-on-delete="true"
              :input-attrs="{ list: 'alltags' }"
              :input-handlers="{ input: 'alltags' }"
            ></b-form-tags>

            <b-datalist id="alltags" :options="filterTags"></b-datalist>
          </template>
        </b-card>
      </b-col>
    </b-row>
    <!-- Save option -->
    <b-row class="justify-content-center pt-4">
      <b-col cols="12" sm="6" md="6" lg="6">
        <b-button block variant="success" @click="sendUpdatedQuestion()">Save</b-button>
      </b-col>
      <b-col cols="12" sm="6" md="6" lg="6">
        <b-button block variant="danger" @click="goToDetailView()">Abort</b-button>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState, mapActions, mapGetters } from 'vuex';
import VueSimplemde from 'vue-simplemde';
export default {
  name: 'QuestionEditView',
  components: { Editor: VueSimplemde },
  props: {
    id: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      isQuestionAreLoaded: false,
      isTagsAreLoaded: false,
      paramId: '',
      componentKey: '',
      componentCounter: 0,
      question: {
        id: '',
        header: '',
        content: '',
        tags: [],
      },
      mdeConfig: {
        spellChecker: false,
      },
    };
  },
  beforeMount() {
    this.loadData();
  },
  mounted() {
    // this.$refs.mde.simplemde.toggleSideBySide();
  },
  methods: {
    async loadData() {
      if (this.id) {
        await this.$store.dispatch('act_getAllTags');
        await this.$store.dispatch('act_getOneQuestion', this.id);
      }
    },
    goToDetailView() {
      this.$router.push(`/question/${this.$props.qId}`).catch((err) => {});
    },
    async sendUpdatedQuestion() {
      console.warn('QID in EDIT', this.question);
      let response = await this.$store.dispatch('act_updateCurrentQuestion', this.question);
      this.$router
        .go({
          path: `/question/${response.id}`,
          props: response.id,
        })
        .catch((err) => {});
    },
    formatter(newTag) {
      console.warn(newTag);
      this.question.tags = newTag.map((tag) => tag.toUpperCase());
      console.warn(this.question.tags);
    },
  },
  computed: {
    ...mapActions(['act_getOneQuestion', 'act_getAllTags', 'act_updateCurrentQuestion']),
    ...mapState(['oneQuestion', 'allTags']),
    ...mapGetters(['getAllTagName']),
    availableOptions() {
      return this.getAllTagName.filter((opt) => this.question.tags.indexOf(opt) === -1);
    },
    filterTags() {
      if (this.question.tags) {
        return this.getAllTagName.filter((item) => !this.question.tags.includes(item)).map((item) => item.toUpperCase());
      }
      return this.getAllTagName.map((item) => item.toUpperCase());
    },
  },
  watch: {
    oneQuestion() {
      if (this.oneQuestion && !!this.oneQuestion) {
        this.isQuestionAreLoaded = true;

        //set edit question with data from store
        this.question = Object.assign({}, this.oneQuestion);
        this.question.tags = Object.assign(
          [],
          this.question.tags.map((t) => t.name)
        );
        this.question.timeStamp = Date.parse(new Date());
      } else {
        this.isQuestionAreLoaded = false;
      }
    },
    allTags() {
      if (this.allTags && this.allTags.length > 0) {
        console.log('TAGS geladen');
        this.isTagsAreLoaded = true;
      } else {
        console.log('TAGS nicht geladen');
        this.isTagsAreLoaded = false;
      }
    },
  },
};
</script>
