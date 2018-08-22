import Vue from 'vue';
import Vuex from 'vuex';

import enums from './modules/enums';

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    enums
  }
});
