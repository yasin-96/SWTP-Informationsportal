<template>
  <b-container background="primary">
    <b-card border-variant="white">
      <b-row class="justify-content-xs-top justify-content-sm-center justify-content-center">
        <!-- Imgage for card -->
        <b-col xs="12" sm="12" md="12" lg="5">
          <b-card-img :src="image" width="100%" alt="Image"></b-card-img>
        </b-col>

        <!-- Question setup -->
        <b-col>
          <b-card-body>
            <b-card-text>
              <b-container>
                <!-- Title  -->
                <b-row class="justify-content-xs-botton justify-content-sm-botton justify-content-md-center justify-content-lg-center text-center">
                  <b-col xs="12" sm="12" md="12" lg="12">
                    <!-- <div class="form-group"> -->
                    <h2>
                      <label for="questionHeader">Der Titel ihrer Frage</label>
                    </h2>
                    <b-form-input v-model="newQuestion.header" type="text" class="form-control" id="questionHeader" size="lg" center placeholder="Fragetitel"></b-form-input>
                  </b-col>
                </b-row>
                <b-row class="justify-content-center pt-4">
                  <!-- <b-col xs="12" sm="12" md="12" lg="12">
                    <b-form-textarea v-model="newQuestion.content" id="textarea-large" size="md" rows="4" max-rows="8" :no-resize="true" placeholder="Eine genauere "></b-form-textarea>
                  </b-col> -->
                  <b-col xs="12" sm="12" md="12" lg="12">
                    <editor v-model="newQuestion.content" :configs="mdeConfig"/>
                  </b-col>
                </b-row>

                <!-- Tags for this question -->
                <b-row class="justify-content-center pt-4">
                  <b-col v-if="isTagsAreLoaded" xs="12" sm="12" md="12" lg="12">
                    <b-form-tags @input="formatter($event)" v-model="newQuestion.tags" :remove-on-delete="true" :input-attrs="{ list: 'alltags' }" :input-handlers="{ input: 'alltags' }"> </b-form-tags>

                    <b-datalist id="alltags" :options="filterTags"> </b-datalist>
                  </b-col>
                </b-row>

                <!-- Create option -->
                <b-row class="justify-content-center pt-4">
                  <b-col xs="12" sm="12" md="12" lg="12">
                    <b-button block size="md" variant="success" :disabled="enableSendButton" @click="createQuestion()">Send</b-button>
                  </b-col>
                </b-row>
              </b-container>
            </b-card-text>
          </b-card-body>
        </b-col>
      </b-row>
    </b-card>
  </b-container>
</template>

<script>
import VueSimplemde from 'vue-simplemde'
import { mapState, mapActions, mapGetters } from 'vuex';
export default {
  name: 'CreateNewQuestion',
  components: {'Editor': VueSimplemde},
  data() {
    return {
      image: require('./../assets/new_question/questions.svg'),
      isTagsAreLoaded: false,
      newQuestion: {
        id: '',
        header: '',
        content: '',
        tags: [],
      },
      mdeConfig: {
        placeholder: "Beschreibung ihrer Frage ...",
        spellChecker: false
      },
      editorOptions: {

      },
    };
  },
  beforeMount() {
    this.loadData();
  },
  methods: {
    onEditorChange(){
      console.warn(this.$refs.mdeEditor.invoke('getValue'));
      this.newQuestion.content = this.$refs.mdeEditor.invoke('getValue');
    },
    async loadData(){
      await this.$store.dispatch('act_getAllTags');
    },

    async createQuestion() {
      let response = await this.$store.dispatch('act_creatNewQuestion', this.newQuestion);
      console.log('res', response);
      this.$router.push(`/question/${response.id}`);
    },
    formatter(newTag) {
      console.warn(newTag);
      this.newQuestion.tags = newTag.map((tag) => tag.toUpperCase());
      console.warn(this.newQuestion.tags);
    },
  },
  computed: {
    ...mapActions(['act_getAllTags', 'act_creatNewQuestion']),
    ...mapGetters(['getAllTagName']),
    ...mapState(['allTags']),

    availableOptions() {
      return this.getAllTagName.filter((opt) => this.newQuestion.tags.indexOf(opt) === -1);
    },
    enableSendButton() {
      return this.newQuestion.header && this.newQuestion.content && this.newQuestion.tags.length > 0 ? false : true;
    },

    filterTags() {
      if (this.newQuestion.tags) {
        return this.getAllTagName.filter((item) => !this.newQuestion.tags.includes(item)).map((item) => item.toUpperCase());
      }
      return this.getAllTagName.map((item) => item.toUpperCase());
    },
  },
  watch: {
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