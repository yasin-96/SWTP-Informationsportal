import Vue from 'vue';
import VueRouter from 'vue-router';
import ShowAllQuestions from '@/views/ShowAllQuestions.vue';
import ShowOneQuestion from '@/views/ShowOneQuestion.vue';
import About from '@/views/About.vue';
import NotFound from '@/views/NotFound.vue';
// import Home from "../views/Home.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: '/questions',
    name: 'Questions',
    component: ShowAllQuestions
  },
  {
    path: '/question/:id',
    name: 'ShowOneQuestion',
    component: ShowOneQuestion,
  },
  {
    path: '/about',
    name: 'About',
    component: About
  },
  {
    path: '*',
    name: '404',
    component: NotFound
  }
];

const router = new VueRouter({
  mode: 'history',
  // eslint-disable-next-line no-undef
  base: process.env.BASE_URL,
  routes
});

export default router;
