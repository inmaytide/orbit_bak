import Login from '@/components/login'
import Index from '@/components/index/main'
import MenuIndex from '@/components/system/menu/index'

const routers = [
  {
    path: '/',
    component: Login
  },
  {
    path: '/login',
    component: Login
  },
  {
    path: '/index',
    component: Index,
    children: [
      {
        path: 'system/menu',
        component: MenuIndex
      }
    ]
  }
]

export default routers
