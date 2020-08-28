<template>
  <b-container class="mt-3">
    <!-- <b-row b-if="oneQuestion && isQuestionAreLoaded"> -->
    <b-row>
      <!-- <b-col>
        <QuestionCard v-if="!!answer" :qId="answer.id" :qHeader="answer.header" :qContent="answer.content" :qTags="answer.tags" :qDate="answer.timeStamp" :qFooter="true" :qEdit="false" />
      </b-col>-->

      <b-col>
        <b-card
          header-bg-variant="white"
          style="min-width: 200px; min-height: 300px;"
          class="bCard rounded shadow"
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
                    <strong>Answer</strong>
                    created by {{ currentAnswer.userName }}
                    <small class="ml-3"></small>
                  </b-button>
                  <b-button size="sm" disabled variant="white">
                    <small>
                      <fai icon="clock" />
                      {{ currentAnswer.timeStamp }}
                    </small>
                  </b-button>
                </b-button-group>
              </b-col>
            </b-row>
          </template>

          <!-- Answer content -->
          <b-card-text>
            <Editor v-model="currentAnswer.content" :configs="mdeConfig" />
          </b-card-text>
        </b-card>
      </b-col>
    </b-row>
    <!-- Save option -->
    <b-row class="justify-content-center pt-4">
      <b-col cols="12" sm="6" md="6" lg="6">
        <b-button block variant="success" @click="sendUpdatedAnswer()">Save</b-button>
      </b-col>
      <b-col cols="12" sm="6" md="6" lg="6">
        <b-button block variant="danger" @click="goToDetailView()">Abort</b-button>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState, mapActions, mapGetters } from 'vuex';
import QuestionCard from '@/components/QuestionCard';
import VueSimplemde from 'vue-simplemde';
export default {
  name: 'AnswerEditView',
  components: { QuestionCard, Editor: VueSimplemde },
  props: {
    /**
     * The id of the question
     */
    qId: {
      type: String,
      required: true,
    },

    /**
     * The id for getting all answers to this question
     */
    aId: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      //Check if answer are loaded and then display the content
      isAnswerLoaded: false,

      //Check if answer are loaded and then display the content
      isQuestionAreLoaded: false,

      //Copy of all answer from store 
      currentAnswer: {},

      //All changes will be stored here
      updatedAnswer: {
        id: this.qId,
        listOfAnswers: [],
      },

      //Config for markdown editor
      mdeConfig: {
        spellChecker: false,
      },
    };
  },

  beforeMount() {
    this.loadData();
  },

  methods: {
    /**
     * Trigger data for the question based on the question id
     * Question and the answers to this will be loaded
     */
    async loadData() {
      if (this.qId && this.aId) {
        await this.$store.dispatch('act_getOneQuestion', this.qId);
        await this.$store.dispatch('act_getOneAnswerToQuestion', { ids: [this.qId, this.aId] });
      }
    },

    /**
     * Page will navigate to the view site of a question.
     * Editing page is left
     */
    goToDetailView() {
      this.$router.push(`/question/${this.$props.qId}`).catch((err) => {});
    },

    /**
     * Send new change of a answer
     */
    async sendUpdatedAnswer() {
      if (this.currentAnswer.content) {
        this.updatedAnswer.listOfAnswers.push({
          id: this.aId,
          content: this.currentAnswer.content,
          rating: 0,
          userId: this.getUserId,
          userName: this.getUsersPreferedName,
          timeStamp: Date.parse(new Date()),
        });
        let response = await this.$store.dispatch('act_updateAnswerFromQuestion', this.updatedAnswer);
        this.$router.push(`/question/${response.id}`);
      }
    },
  },
  computed: {
    ...mapActions(['act_getOneQuestion', 'act_getOneAnswerToQuestion', 'act_updateAnswerFromQuestion']),
    ...mapState(['oneAnswer', 'oneQuestion']),
    ...mapGetters(['getUserId', 'getUsersPreferedName']),
  },
  watch: {
    oneAnswer() {
      if (this.oneAnswer) {
        this.currentAnswer = Object.assign({}, this.oneAnswer);
      }
    },
    oneQuestion() {
      if (this.oneQuestion) {
        this.isQuestionAreLoaded = true;
      } else {
        this.isQuestionAreLoaded = false;
      }
    },
  },
};
</script>
