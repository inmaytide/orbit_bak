import Login from '@/components/login'

const routers = [
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/',
    component: Login
  }
]

export default routers
