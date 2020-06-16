import Vue from 'vue';
import VueRouter from 'vue-router';
import ShowAllQuestions from '@/views/ShowAllQuestions.vue';
import ShowOneQuestion from '@/views/ShowOneQuestion.vue';
import Search from '@/views/Search.vue';
import About from '@/views/About.vue';
import NotFound from '@/views/NotFound.vue';
import Home from "../views/Home.vue";
import NewQuestion from "@/views/NewQuestion";

Vue.use(VueRouter);

const routes = [
  {
    path: '/new',
    name: 'NewQuestion',
    component: NewQuestion,
  },
  {
    path: '/questions',
    name: 'Questions',
    component: ShowAllQuestions,
  },
  {
    path: '/question/:id',
    name: 'ShowOneQuestion',
    component: ShowOneQuestion,
    props: true
  },
  {
    path: '/search',
    name: 'Search',
    component: Search,
    props: (route) => ({ query: route.query.q })
  },
  {
    path: '/about',
    name: 'About',
    component: About,
  },
  {
    path: '*',
    name: '404',
    component: NotFound,
  }
];

const router = new VueRouter({
  mode: 'history',
  // eslint-disable-next-line no-undef
  base: process.env.BASE_URL,
  // other options could be: 'reload', 'throw' and default to `throw` to avoid breaking changes
  duplicateNavigationPolicy: 'reload',
  routes,
});

export default router;
