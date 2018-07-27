import Login from '@/components/login';
import Index from '@/components/index/main';
import PermissionIndex from '@/components/system/permission/index';
import OrganizationIndex from '@/components/system/organization/index';

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
        component: PermissionIndex
      },
      {
        path: 'system/organization',
        component: OrganizationIndex
      }
    ]
  }
];

export default routers;
