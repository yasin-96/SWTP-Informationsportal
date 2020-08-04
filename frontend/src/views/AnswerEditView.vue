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
                    <strong>Frage</strong>
                    erstellt von {{ currentAnswer.userId }}
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
    qId: {
      type: String,
      required: true,
    },
    aId: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      isAnswerLoaded: false,
      isQuestionAreLoaded: false,
      paramId: '',
      componentKey: '',
      componentCounter: 0,
      currentAnswer: {},
      updatedAnswer: {
        id: this.qId,
        listOfAnswers: [],
      },
      mdeConfig: {
        spellChecker: false,
      },
    };
  },

  beforeMount() {
    this.loadData();
  },

  methods: {
    async loadData() {
      if (this.qId && this.aId) {
        await this.$store.dispatch('act_getOneQuestion', this.qId);
        await this.$store.dispatch('act_getOneAnswerToQuestion', { ids: [this.qId, this.aId] });
      }
    },
    goToDetailView() {
      this.$router.push(`/question/${this.$props.qId}`).catch((err) => {});
    },
    async sendUpdatedAnswer() {
      if (this.currentAnswer.content) {
        this.updatedAnswer.listOfAnswers.push({
          id: this.aId,
          content: this.currentAnswer.content,
          rating: 0,
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
