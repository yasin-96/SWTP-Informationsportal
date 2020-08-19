import Vue from 'vue';


//Style from bootstrap
import { BootstrapVue } from 'bootstrap-vue';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';

//Add custom style
import '@/assets/css/infop.css';

//Choose icons to important
import { library } from '@fortawesome/fontawesome-svg-core';
import { faUserCircle, faClock, faThumbsUp, faThumbsDown, faComment, faCommentAlt, faCommentDots, faPlusCircle, faSearch, faSearchMinus, faSearchPlus, faExclamationTriangle, faEdit, faPen, faBell, faWindowClose, faTags, faEnvelope } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon, FontAwesomeLayers, FontAwesomeLayersText } from '@fortawesome/vue-fontawesome';

//Load style for markdown editor
import 'simplemde/dist/simplemde.min.css';

//Load icons to library
library.add(faUserCircle, faClock, faThumbsUp, faThumbsDown, faComment, faCommentAlt, faCommentDots, faPlusCircle, faSearch, faSearchPlus, faSearchMinus, faExclamationTriangle, faEdit, faPen, faBell, faWindowClose, faTags, faEnvelope);

Vue.component('fai', FontAwesomeIcon);
Vue.component('falayers', FontAwesomeLayers);
Vue.component('falt', FontAwesomeLayersText);

Vue.use(BootstrapVue);
