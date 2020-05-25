<template>
  <b-container v-if="allComments.comments">
    <b-card class="mt-3" :key="id">
      
      <!-- Information about user & creation date -->
      <template v-slot:header>
      <b-row class="justify-content-left">
        <b-col cols="3" sm="2" md="2" lg="1">
          <b-iuser class="mr-2" font-scale="3"></b-iuser>
        </b-col>
        <b-col cols="9" sm="10" md="10" lg="11">
          <strong>Antwort</strong> vom User <br /><small class="ml-3"
            ><b-iclock></b-iclock>
            {{ aDate }}
          </small>
        </b-col>
      </b-row>
    </template>

      <b-card-text>
        <b-container>
        {{ aContent }}
        </b-container>
        <!-- Area for all Comments -->
        <b-container>
          <hr />
          <Comment :cComments="allComments.comments" />
        </b-container>
      </b-card-text>
    </b-card>
  </b-container>
</template>

<script>
import Comment from '@/components/Comment';
import { mapState, mapActions, mapGetters } from 'vuex';

import { BIconClock, BIconPeopleCircle, BIconChatSquareDots } from 'bootstrap-vue';

export default {
  name: 'AnswerCard',
  components: {
    Comment,
    'b-iuser': BIconPeopleCircle,
    'b-iclock': BIconClock,
    'b-icomment': BIconChatSquareDots,
  },
  beforeMount: async function () {
    try {
      await this.$store.dispatch('act_getAllComments', this.id);
    } catch (error) {
      console.error(error.error);
    }
  },
  props: {
    id: {
      type: String,
      required: true,
    },
    aContent: {
      type: String,
      default: '',
    },
    aRating: {
      type: Number,
      default: 0,
    },
    aDate: {
      type: String,
      default: '',
    },
  },
  data: () => ({
    //
  }),
  computed: {
    ...mapActions(['act_getAllComments']),
    ...mapState(['allComments']),
    ...mapGetters(['getListWithComments']),

  },
};
</script>

<style>
ul.timeline {
  list-style-type: none;
  position: relative;
}
ul.timeline:before {
  content: '';
  background: #dfdfdf;
  display: inline-block;
  position: absolute;
  left: 16px;
  width: 3px;
  height: 100%;
  z-index: 400;
}
ul.timeline > li {
  margin: 15px 0;
  padding-left: 15px;
}
ul.timeline > li:before {
  content: '';
  background: white;
  display: inline-block;
  position: absolute;
  border-radius: 50%;
  border: 2px solid #721515;
  margin-top: 40px;
  left: 8px;
  width: 20px;
  height: 20px;
  z-index: 400;
}
</style>
