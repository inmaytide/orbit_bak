// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import VueRouter from 'vue-router';
import iView from 'iview';
import locale from 'iview/dist/locale/en-US';
import App from './app';
import routers from './routers/main';
import request from './utils/request';
import commons from './utils/commons';
import i18n from './utils/i18n';
import store from './stores';
import 'iview/dist/styles/iview.css';

Vue.config.productionTip = false;
Vue.use(VueRouter);
Vue.use(iView, {locale});

Vue.prototype.$http = request;
Vue.prototype.$commons = commons;

const router = new VueRouter({
  mode: 'history',
  routes: routers
});

router.beforeEach((to, from, next) => {
  iView.LoadingBar.start();
  if (to.meta.authenticated && commons.getToken() == null) {
    next({
      path: '/login',
      query: {redirect: to.fullPath}
    });
  } else {
    next();
  }
});

router.afterEach((to, from, next) => {
  iView.LoadingBar.finish();
  window.scrollTo(0, 0);
});

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  i18n,
  store,
  render: h => h(App)
});
