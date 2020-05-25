import Vue from 'vue';
import Vuetify from 'vuetify/lib';

Vue.use(Vuetify);

export default new Vuetify({
    icons: {
        iconfont: 'mdi',
    },
    theme: {
      themes: {
        light: {
          primary: '#7fba25',
          secondary: '#b0bec5',
          accent: '#8c9eff',
          danger: '#b71c1c',
        },
      },
    },
});
