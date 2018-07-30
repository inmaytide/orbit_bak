// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import VueRouter from 'vue-router';
import VueI18n from 'vue-i18n';
import iView from 'iview';
import App from './app';
import routers from './routers';
import 'iview/dist/styles/iview.css';
import axios from 'axios';
import commons from './commons';

Vue.config.productionTip = false;

Vue.use(VueRouter);
Vue.use(iView);
Vue.use(VueI18n);

const router = new VueRouter({
  mode: 'history',
  routes: routers
});

const locales = {
  'en-US': require('./langs/en.json')
};

let lang = navigator.language;
if (!lang) {
  lang = navigator.browserLanguage;
}

const i18n = new VueI18n({
  locale: lang,
  messages: locales
});

axios.interceptors.request.use(config => {
  const authorization = commons.getAuthorization();
  if (authorization != null) {
    config.headers.Authorization = authorization;
  }
  return config;
}, error => commons.errorHandler(error));

axios.interceptors.response.use(response => response.data, err => commons.errorHandler(err));

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  i18n,
  render: h => h(App)
});
