import Login from '@/components/login'
import Index from '@/components/index'

const routers = [
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/index',
    name: 'index',
    component: Index
  },
  {
    path: '/',
    component: Login
  }
]

export default routers
