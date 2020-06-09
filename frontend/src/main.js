import '@babel/polyfill'
// import 'mutationobserver-shim'
import Vue from "vue";
import App from "./App.vue";

//lib for design
import './plugins/bootstrap-vue'

//navigation for all components
import router from "./router";

// save all data to store
import store from "./store";

//user local storage
import './storage';

Vue.config.productionTip = false;

//init new vue instance
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
