import Vue from 'vue';

import { BootstrapVue } from 'bootstrap-vue';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';

//Add custom style
import '@/assets/css/infop.css';

import { library } from '@fortawesome/fontawesome-svg-core';
import { faUserCircle, faClock, faThumbsUp, faThumbsDown, faComment, faCommentAlt, faCommentDots, faPlusCircle, faSearch, faSearchMinus, faSearchPlus, faExclamationTriangle, faEdit, faPen, faBell, faWindowClose} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon, FontAwesomeLayers, FontAwesomeLayersText} from '@fortawesome/vue-fontawesome';

//VUE Markdown editor
// import 'codemirror/lib/codemirror.css';
// import '@toast-ui/editor/dist/toastui-editor.css';
// import { Editor } from '@toast-ui/vue-editor';
import 'simplemde/dist/simplemde.min.css';

library.add(faUserCircle, faClock, faThumbsUp, faThumbsDown, faComment, faCommentAlt, faCommentDots, faPlusCircle, faSearch, faSearchPlus, faSearchMinus, faExclamationTriangle, faEdit, faPen, faBell, faWindowClose);

Vue.component('fai', FontAwesomeIcon);
Vue.component('falayers', FontAwesomeLayers);
Vue.component('falt', FontAwesomeLayersText);

Vue.use(BootstrapVue);
