// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import VueRouter from 'vue-router'
import iView from 'iview'
import App from './app'
import routers from './router'
import 'iview/dist/styles/iview.css'
import {i18n, loadLanguage} from './i18n-setup'

Vue.config.productionTip = false

Vue.use(VueRouter)
Vue.use(iView)

const router = new VueRouter({
  mode: 'history',
  routes: routers
})

router.beforeEach((to, from, next) => {
  loadLanguage().then(() => next())
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  i18n,
  render: h => h(App)
})
