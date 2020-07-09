//TODO: gegen prüfen ob das noch gebraucht wird?

//save notification with this


import Vue from 'vue';
import VueLocalStorage from 'vue-localstorage'

// Or you can specify any other name and use it via this.$ls, this.$whatEverYouWant
Vue.use(VueLocalStorage, {
  name: 'localStore',
  bind: true //created computed members from your variable declarations,
});

