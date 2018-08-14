import login from '../views/login';
import main from '../views/main';
import i18n from '../utils/i18n';

const routers = [
  {
    path: '/',
    component: main,
    children: [{
      path: '/',
      component: (resolve) => require(['../views/home/home'], resolve),
      meta: {
        authenticated: true,
        breadcrumbs: [{text: i18n.t('common.menu.header.home'), path: '/'}]
      }
    }, {
      path: 'home',
      component: (resolve) => require(['../views/home/home'], resolve),
      meta: {
        authenticated: true,
        breadcrumbs: [{text: i18n.t('common.menu.header.home'), path: '/'}]
      }
    }, {
      path: 'home/tasks',
      component: (resolve) => require(['../views/home/home'], resolve),
      meta: {
        authenticated: true,
        breadcrumbs: [
          {text: i18n.t('common.menu.header.home'), path: '/'},
          {text: i18n.t('common.menu.header.tasks'), path: '/home/tasks'}
        ]
      }
    }, {
      path: 'home/favorites',
      component: (resolve) => require(['../views/home/home'], resolve),
      meta: {
        authenticated: true,
        breadcrumbs: [
          {text: i18n.t('common.menu.header.home'), path: '/'},
          {text: i18n.t('common.menu.header.favorites'), path: '/home/favorites'}
        ]
      }
    }]
  },
  {
    path: '/login',
    component: login
  }
];

export default routers;
