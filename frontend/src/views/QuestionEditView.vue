<template>
  <b-container class="mt-3">
    <!-- <b-row b-if="oneQuestion && isQuestionAreLoaded"> -->
    <b-row>
      <b-col>
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
                <strong>Frage</strong> vom User <br /><small class="ml-3"
                  ><fai icon="clock" />
                  {{ oneQuestion.timeStamp }}
                </small>
              </b-col>
            </b-row>
          </template>

          <!-- Question header with content -->
          <b-card-title>
            <b-form-input v-model="question.header" :search-input.sync="question.header" type="text" class="form-control" id="questionHeader" size="lg" center placeholder="Fragetitel"></b-form-input>
          </b-card-title>

          <!-- Question content -->
          <b-card-text>
            <editor ref="mde" v-model="question.content" />
            <!-- <b-form-textarea v-model="question.content" :search-input.sync="question.content" id="textarea-large" size="md" rows="4" max-rows="8" :no-resize="true" placeholder="Eine genauere Beschreibung ihrer Frage ..."></b-form-textarea>-->
          </b-card-text>

          <!-- Show all Tags from Question and its rating -->
          <template v-if="isTagsAreLoaded" v-slot:footer>
            <!-- Tags for this question -->
            <b-form-tags @input="formatter($event)" v-model="question.tags" :remove-on-delete="true" :input-attrs="{ list: 'alltags' }" :input-handlers="{ input: 'alltags' }"> </b-form-tags>

            <b-datalist id="alltags" :options="filterTags"> </b-datalist>
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
    <div>
      <h1 data-sourcepos="1:1-1:8\">fgfdgd</h1><h2 data-sourcepos="2:1-2:9\">asdasd</h2><hr data-sourcepos="5:1-6:0\"><table data-sourcepos="7:1-10:34\"><thead><tr data-sourcepos="7:1-7:34\"><th data-sourcepos="7:2-7:11\">Column 1</th><th data-sourcepos="7:13-7:22\">Column 2</th><th data-sourcepos="7:24-7:33\">Column 3</th></tr></thead><tbody><tr data-sourcepos="9:1-9:34\"><td data-sourcepos="9:2-9:11\">John</td><td data-sourcepos="9:13-9:22\">Doe</td><td data-sourcepos="9:24-9:33\">Male</td></tr><tr data-sourcepos="10:1-10:34\"><td data-sourcepos="10:2-10:11\">Mary</td><td data-sourcepos="10:13-10:22\">Smith</td><td data-sourcepos="10:24-10:33\">Female</td></tr></tbody></table><p data-sourcepos="12:1-12:34\">Or without aligning the columns...</p><table data-sourcepos="14:1-17:25\"><thead><tr data-sourcepos="14:1-14:34\"><th data-sourcepos="14:2-14:11\">Column 1</th><th data-sourcepos="14:13-14:22\">Column 2</th><th data-sourcepos="14:24-14:33\">Column 3</th></tr></thead><tbody><tr data-sourcepos="16:1-16:21\"><td data-sourcepos="16:2-16:7\">John</td><td data-sourcepos="16:9-16:13\">Doe</td><td data-sourcepos="16:15-16:20\">Male</td></tr><tr data-sourcepos="17:1-17:25\"><td data-sourcepos="17:2-17:7\">Mary</td><td data-sourcepos="17:9-17:15\">Smith</td><td data-sourcepos="17:17-17:24\">Female</td></tr></tbody></table><p data-sourcepos="20:1-20:6\">:tada:</p>\n<p data-sourcepos="23:1-23:159\"><img src="http://image.jimcdn.com/app/cms/image/transf/dimension=1920x400:format=jpg/path/sff0b929dc64ab22e/image/i9e9dc80402e9026a/version/1557436730/image.jpg\" alt="test\"></p>
    </div>
  </b-container>
</template>

<script>
import { mapState, mapActions, mapGetters } from 'vuex';
import VueSimplemde from 'vue-simplemde'
export default {
  name: 'QuestionEditView',
  components: {'Editor': VueSimplemde},
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
        autoinit: "false"
      }
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
      this.$router.push(`/info-portal/question/${this.$props.qId}`).catch((err) => {});
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
