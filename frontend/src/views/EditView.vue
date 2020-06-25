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
            <b-form-textarea v-model="question.content" :search-input.sync="question.content" id="textarea-large" size="md" rows="4" max-rows="8" :no-resize="true" placeholder="Eine genauere Beschreibung ihrer Frage ..."></b-form-textarea>
          </b-card-text>

          <!-- Show all Tags from Question and its rating -->
          <template v-if="isTagsAreLoaded" v-slot:footer>
            <!-- Tags for this question -->
            <b-form-tags @input="formatter($event)" v-model="question.tags" :remove-on-delete="true" :input-attrs="{ list: 'alltags' }" :input-handlers="{ input: 'alltags' }">
              
            </b-form-tags>

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
  </b-container>
</template>

<script>
import { mapState, mapActions, mapGetters } from 'vuex';

export default {
  name: 'EditView',
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
    };
  },

  async beforeMount() {
    console.info('BMount--');
    //check if local storage has the key with data, else set the new key & data
    if (this.$localStore.get('rQuetionId') === this.id.toString()) {
      console.log('LOCAL_STORE:', this.$localStore.get('rQuetionId'));
    } else {
      this.$localStore.set('rQuetionId', this.id.toString());
    }

    console.warn('THIS ID:', this.id);
    this.paramId = this.id || this.$localStore.get('rQuetionId');
    console.warn('PARA ID:', this.paramId);

    try {
      await this.$store.dispatch('act_getAllTags');
      await this.$store.dispatch('act_getOneQuestion', this.paramId);
    } catch (error) {
      console.error('beforeMount: ', error);
    }
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
  methods: {
    goToDetailView() {
      this.$router.push(`/question/${this.$props.qId}`).catch((err) => {});
    },
    async sendUpdatedQuestion() {
      try {
        let response = await this.$store.dispatch('act_updateCurrentQuestion', this.question);
        this.$router.push(`/question/${response.id}`).catch((err) => {});
       
      } catch (error) {
        console.error(error);
      }
    },
    formatter(newTag){
      console.warn(newTag)
      this.question.tags = newTag.map((tag) => tag.toUpperCase());
      console.warn(this.question.tags)
    }
  },
  
};
</script>
