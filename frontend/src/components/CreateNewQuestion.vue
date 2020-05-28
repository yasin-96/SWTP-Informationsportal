<template>
  <b-container class="mt-5">
    <b-card border-variant="white">
      <b-row class="justify-content-xs-top justify-content-sm-center justify-content-center">
        <b-col xs="12" sm="12" md="12" lg="4">
          <b-card-img :src="image" width="100%" alt="Image"></b-card-img>
        </b-col>
        <b-col>
          <b-card-body>
            <b-card-text>
              <b-container>
                <b-row class="justify-content-xs-botton justify-content-sm-botton justify-content-md-center justify-content-lg-center">
                  <b-col xs="12" sm="12" md="12" lg="10">
                    <!-- <div class="form-group"> -->
                    <h2>
                      <label for="questionHeader">Der Titel ihrer Frage</label>
                    </h2>
                    <b-form-input v-model="newQuestion.header" type="text" class="form-control" id="questionHeader" size="lg" center placeholder="Fragetitel"></b-form-input>
                  </b-col>
                </b-row>
                <b-row class="justify-content-center pt-4">
                  <b-col xs="12" sm="12" md="12" lg="10">
                    <b-form-textarea v-model="newQuestion.content" id="textarea-large" size="md" rows="4" max-rows="8" :no-resize="true" placeholder="Eine genauere Beschreibung ihrer Frage ..."></b-form-textarea>
                  </b-col>
                </b-row>
                <b-row class="justify-content-center pt-4">
                  <b-col v-if="isTagsAreLoaded" xs="12" sm="12" md="12" lg="10">
                      <b-form-tags v-model="newQuestion.tags" size="md" add-on-change no-outer-focus class="mb-2">
                        <template v-slot="{ tags, inputAttrs, inputHandlers, disabled }">
                          <!-- <ul v-if="isTagsAreLoaded" class="list-inline d-inline-block mb-2">
                            <li v-for="(tag,i) in tags" :key="i" class="list-inline-item">
                              <b-form-tag @remove="removeTag(tag)" :title="tag.name" :disabled="disabled" variant="info">{{ tag }}</b-form-tag>
                            </li>



                          </ul> -->
                          <b-form-tags input-id="tags-basic" v-model="newQuestion.tags" class="mb-2"></b-form-tags>
                          <b-form-select v-bind="inputAttrs" v-on="inputHandlers" :disabled="disabled || availableOptions.length === 0" :options="availableOptions">
                            <template v-slot:first>
                              <!-- This is required to prevent bugs with Safari -->
                              <option disabled value="">Choose a tag...</option>
                            </template>
                          </b-form-select>
                        </template>
                      </b-form-tags>
                    <!-- </b-container> -->
                  </b-col>
                </b-row>
                <b-row class="justify-content-center pt-4">
                  <b-col xs="12" sm="12" md="12" lg="10">
                    <b-button size="md" variant="success" @click="createQuestion()">Send</b-button>
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
import { mapState, mapActions, mapGetters } from 'vuex';
export default {
  name: 'CreateNewQuestion',
  components: {},
  async beforeMount() {
    try {
      await this.$store.dispatch('act_getAllTags');
      console.log('beforeMount: ', this.getAllTagName);
    } catch (error) {
      console.error(error);
    }
  },
  data: () => ({
    image: require('./../assets/new_question/questions.svg'),
    selectedTags: [],
    isTagsAreLoaded: false,
    // options: ['Apple', 'Orange', 'Banana', 'Lime', 'Peach', 'Chocolate', 'Strawberry'],
    value: [],

    newQuestion: {
      id: '',
      header: '', 
      content:'',
      tags: []
    }
  }),
  computed: {
    ...mapActions(['act_getAllTags', 'act_creatNewQuestion']),
    ...mapGetters(['getAllTagName']),
    ...mapState(['allTags']),

    availableOptions() {
        return this.getAllTagName.filter(opt => this.newQuestion.tags.indexOf(opt) === -1)
    }
  },
  methods: { 
    async createQuestion(){
      let response = await this.$store.dispatch('act_creatNewQuestion', this.newQuestion);
      console.log("res", response);
      this.$router.push(`/question/${response.id}`);
    }
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

<style></style>
