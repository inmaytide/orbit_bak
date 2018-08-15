import login from '../views/login';
import main from '../views/main/main';

const routers = [
  {
    path: '/',
    component: main,
    children: [{
      path: '/',
      component: (resolve) => require(['../views/home/home'], resolve),
      meta: {
        authenticated: true,
        breadcrumbs: [{text: 'common.menu.header.home', path: '/'}]
      }
    }, {
      path: 'home',
      component: (resolve) => require(['../views/home/home'], resolve),
      meta: {
        authenticated: true,
        breadcrumbs: [{text: 'common.menu.header.home', path: '/'}]
      }
    }, {
      path: 'home/tasks',
      component: (resolve) => require(['../views/home/home'], resolve),
      meta: {
        authenticated: true,
        breadcrumbs: [
          {text: 'common.menu.header.home', path: '/'},
          {text: 'common.menu.header.tasks', path: '/home/tasks'}
        ]
      }
    }, {
      path: 'home/favorites',
      component: (resolve) => require(['../views/home/home'], resolve),
      meta: {
        authenticated: true,
        breadcrumbs: [
          {text: 'common.menu.header.home', path: '/'},
          {text: 'common.menu.header.favorites', path: '/home/favorites'}
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
