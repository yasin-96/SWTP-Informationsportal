import Vue from 'vue';
import VueRouter from 'vue-router';
import ShowAllQuestions from '@/views/ShowAllQuestions.vue';
import ShowOneQuestion from '@/views/ShowOneQuestion.vue';
import Search from '@/views/Search.vue';
import About from '@/views/About.vue';
import NotFound from '@/views/NotFound.vue';
import Home from '@/views/Home';
import Topics from '@/views/Topics';
import NewQuestion from '@/views/NewQuestion';
import QuestionEditView from '@/views/QuestionEditView';
import AnswerEditView from '@/views/AnswerEditView';
import QuestionsByTopics from '@/views/QuestionsByTopics';

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
    path: '/questions/topics/:tag',
    name: 'QuestionsByTopics',
    component: QuestionsByTopics,
    props: (route) => ({ tag: route.params.tag }),
  },
  {
    path: '/question/:id',
    name: 'ShowOneQuestion',
    component: ShowOneQuestion,
    props: (route) => ({ id: route.params.id }),
  },
  {
    path: '/question/edit/:id',
    name: 'QuestionEditView',
    component: QuestionEditView,
    props: (route) => ({ id: route.params.id }),
  },
  {
    path: '/answer/edit',
    name: 'AnswerEditView',
    component: AnswerEditView,
    props: (route) => ({ qId: route.query.qId, aId: route.query.aId }),
  },
  {
    path: '/search',
    name: 'Search',
    component: Search,
    props: (route) => ({ query: route.query.q }),
  },
  {
    path: '/about',
    name: 'About',
    component: About,
  },
  {
    path: '/home',
    name: 'Home',
    component: Home,
  },
  {
    path: '/topics',
    name: 'Topics',
    component: Topics,
  },
  {
    path: '*',
    name: '404',
    component: NotFound,
  },
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
