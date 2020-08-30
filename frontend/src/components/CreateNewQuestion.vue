<template>
  <b-container background="primary" class="mb-5">
    <b-card border-variant="white" class="rounded shadow"> 
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
                  <b-col xs="12" sm="12" md="12" lg="12">
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
      //image for card
      image: require('./../assets/new_question/questions.svg'),

      // plain question object that will used to store the whole information
      newQuestion: {
        id: '',
        header: '',
        content: '',
        tags: [],
      },

      //config for markdown editor
      mdeConfig: {
        placeholder: "Beschreibung ihrer Frage ...",
        spellChecker: false
      },
    };
  },
  beforeMount() {
    this.loadData();
  },
  methods: {
    /**
     * Trigger to add content from markdown editor to the quesion object
     */
    onEditorChange(){
      console.warn(this.$refs.mdeEditor.invoke('getValue'));
      this.newQuestion.content = this.$refs.mdeEditor.invoke('getValue');
    },
    /**
     * Trigger to request the tags from backend
     */
    async loadData(){
      await this.$store.dispatch('act_getAllTags');
    },
    /**
     * Create the new Question and go this view
     */
    async createQuestion() {
      const questionId = await this.$store.dispatch('act_createNewQuestion', this.newQuestion);
      this.$router.push(`/question/${questionId}`);
    },
    /**
     * To prevent errors, the tags are all capitalized. 
     * The input when entering a tag is automatically converted
     */
    formatter(newTag) {
      console.warn(newTag);
      this.newQuestion.tags = newTag.map((tag) => tag.toUpperCase());
      console.warn(this.newQuestion.tags);
    },
  },
  computed: {
    ...mapActions(['act_getAllTags', 'act_createNewQuestion']),
    ...mapGetters(['getAllTagName']),
    ...mapState(['allTags']),
    
    /**
     * If all values for the question creation are available, the send button is activated.
     */
    enableSendButton() {
      return this.newQuestion.header && this.newQuestion.content && this.newQuestion.tags.length > 0 ? false : true;
    },
    
    /**
     * The tag selection is filtered by the already selected and the still available ones. 
     * All those that have already been selected are automatically no longer offered for selection.
     */
    filterTags() {
      if (this.newQuestion.tags) {
        return this.getAllTagName.filter((item) => !this.newQuestion.tags.includes(item)).map((item) => item.toUpperCase());
      }
      return this.getAllTagName.map((item) => item.toUpperCase());
    },
  },
};
</script>