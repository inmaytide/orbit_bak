import login from '../views/login';
import main from '../views/main';

const routers = [
  {
    path: '/',
    component: main,
    children: [{
      path: '/',
      component: (resolve) => require(['../views/home/home'], resolve),
      meta: {
        authenticated: true
      }
    }, {
      path: 'home',
      component: (resolve) => require(['../views/home/home'], resolve)
    }]
  },
  {
    path: '/login',
    component: login
  }
];

export default routers;
