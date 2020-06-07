import Vue from 'vue';
import VueRouter from 'vue-router';
import ShowAllQuestions from '@/views/ShowAllQuestions.vue';
import ShowOneQuestion from '@/views/ShowOneQuestion.vue';
import About from '@/views/About.vue';
import NotFound from '@/views/NotFound.vue';
import Home from "../views/Home.vue";
import NewQuestion from "@/views/NewQuestion";
// import Home from "../views/Home.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: '/new',
    name: 'NewQuestion',
    component: NewQuestion,
    active: true
  },
  {
    path: '/',
    name: 'Questions',
    component: ShowAllQuestions,
    active: true
  },
  {
    path: '/questions',
    name: 'Questions',
    component: ShowAllQuestions,
    active: true
  },
  {
    path: '/question/:id',
    name: 'ShowOneQuestion',
    component: ShowOneQuestion,
    active: true,
    props: true
  },
  {
    path: '/about',
    name: 'About',
    component: About,
    active: true
  },
  {
    path: '*',
    name: '404',
    component: NotFound,
    active: true
  }
];

const router = new VueRouter({
  mode: 'history',
  // eslint-disable-next-line no-undef
  base: process.env.BASE_URL,
  routes
});

export default router;
