<template>
  <b-container class="mt-3" >
    <!-- <b-row b-if="oneQuestion && isQuestionAreLoaded"> -->
    <b-row>
        <b-col>
            <QuestionCard v-if="isQuestionAreLoaded" :qId="oneQuestion.id" :qHeader="oneQuestion.header" :qContent="oneQuestion.content" :qTags="oneQuestion.tags" :qDate="oneQuestion.timeStamp" :qFooter="true" :qEdit="true"/>
        </b-col>


    

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
                  {{ currentAnswer.timeStamp }}
                </small>
              </b-col>
            </b-row>
          </template>


          <!-- Answer content -->
          <b-card-text>
            <b-form-textarea v-model="currentAnswer.content" :search-input.sync="currentAnswer.content" id="textarea-large" size="md" rows="4" max-rows="8" :no-resize="true"></b-form-textarea>
          </b-card-text>

         
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
import QuestionCard from '@/components/QuestionCard';

export default {
  name: 'AnswerEditView',
  components: {QuestionCard},
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
      currentAnswer:{},
      updatedAnswer: {
        id: '',
        listOfAnswers: [],
      },
    };
  },

  async beforeMount() {
    console.info('BMount--');
    //check if local storage has the key with data, else set the new key & data
    // if (this.$localStore.get('rQuetionId') === this.id.toString()) {
    //   console.log('LOCAL_STORE:', this.$localStore.get('rQuetionId'));
    // } else {
    //   this.$localStore.set('rQuetionId', this.id.toString());
    // }

    // console.warn('THIS ID:', this.id);
    // this.paramId = this.id || this.$localStore.get('rQuetionId');
    // console.warn('PARA ID:', this.paramId);

    try {
      await this.$store.dispatch('act_getOneQuestion', this.qId);
      await this.$store.dispatch('act_getOneAnswerToQuestion', { ids: [this.qId, this.aId ]});
    } catch (error) {
      console.error('beforeMount: ', error);
    }
  },
  computed: {
    ...mapActions(['act_getOneQuestion','act_getOneAnswerToQuestion', 'act_updateAnswerFromQuestion']),
    ...mapState(['oneAnswer', 'oneQuestion'])
  },
  methods: {
    goToDetailView() {
      this.$router.push(`/question/${this.$props.qId}`).catch((err) => {});
    },
    async sendUpdatedAnswer() {
    //   try {
    //     let response = await this.$store.dispatch('act_updateCurrentQuestion', this.question);
    //     this.$router.push(`/question/${response.id}`).catch((err) => {});
       
    //   } catch (error) {
    //     console.error(error);
    //   }
      if (this.contentForAnswer) {
        this.updatedAnswer.listOfAnswers.push({
          id: '',
          content: this.contentForAnswer,
          rating: 0,
          timeStamp: Date.parse(new Date()),
        });
        console.log('HIER:!!', this.updatedAnswer);
        let response = await this.$store.dispatch('act_updateAnswerFromQuestion', this.updatedAnswer);
        this.$router.go(`/question/${response.id}`);
      }
    },
    
  },
  watch:{
      oneAnswer(){
          if(this.oneAnswer){
              this.currentAnswer = Object.assign({},this.oneAnswer);
              this.isAnswerLoaded = true;
          }
      },
       oneQuestion() {
      if (this.oneQuestion && !this.oneQuestion) {
        this.isQuestionAreLoaded = true;
      } else {
        this.isQuestionAreLoaded = false;
      }
    },
  }
  
};
</script>
