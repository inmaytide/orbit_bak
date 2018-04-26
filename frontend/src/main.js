// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import VueRouter from 'vue-router'
import iView from 'iview'
import App from './app'
import routers from './router'
import 'iview/dist/styles/iview.css'
import ZkTable from 'vue-table-with-tree-grid'
import MTreeGrid from '../src/m-vue-tree-grid/m-tree-grid'
import {i18n, loadLanguage} from './i18n-setup'
import axios from 'axios'
import commons from './commons'

Vue.config.productionTip = false

Vue.component(MTreeGrid.name, MTreeGrid)
Vue.use(VueRouter)
Vue.use(iView)
Vue.use(ZkTable)
Vue.use(MTreeGrid)

const router = new VueRouter({
  mode: 'history',
  routes: routers
})

router.beforeEach((to, from, next) => {
  loadLanguage().then(() => next())
})

axios.interceptors.request.use(config => {
  const authorization = commons.getAuthorization()
  if (authorization != null) {
    config.headers.Authorization = authorization
  }
  return config
}, error => commons.errorHandler(error))

axios.interceptors.response.use(response => response.data, err => commons.errorHandler(err))

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  i18n,
  render: h => h(App)
})
