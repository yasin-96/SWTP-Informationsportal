import Vue from 'vue';

import { BootstrapVue } from 'bootstrap-vue';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';

import { library } from '@fortawesome/fontawesome-svg-core';
import { faUserCircle, faClock, faThumbsUp, faThumbsDown, faComment, faCommentAlt, faCommentDots, faPlusCircle } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon, FontAwesomeLayers, FontAwesomeLayersText } from '@fortawesome/vue-fontawesome';

library.add(faUserCircle, faClock, faThumbsUp, faThumbsDown, faComment, faCommentAlt, faCommentDots, faPlusCircle);

Vue.component('fai', FontAwesomeIcon);
Vue.component('falayers', FontAwesomeLayers);
Vue.component('falt', FontAwesomeLayersText);

Vue.use(BootstrapVue);
