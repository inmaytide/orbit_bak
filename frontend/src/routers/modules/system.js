export default [{
  path: '/system/menu',
  component: (resolve) => require(['../../views/system/menu/menu'], resolve),
  meta: {
    authenticated: true,
    active: 'system',
    breadcrumbs: [
      {text: 'common.menu.nav.system'},
      {text: 'common.menu.nav.menu'}
    ]
  }
}, {
  path: '/system/org',
  component: (resolve) => require(['../../views/system/org/org'], resolve),
  meta: {
    authenticated: true,
    active: 'system',
    breadcrumbs: [
      {text: 'common.menu.nav.system'},
      {text: 'common.menu.nav.org'}
    ]
  }
}, {
  path: '/system/users',
  component: (resolve) => require(['../../views/system/user/user'], resolve),
  meta: {
    authenticated: true,
    active: 'system',
    breadcrumbs: [
      {text: 'common.menu.nav.system'},
      {text: 'common.menu.nav.user'}
    ]
  }
}];
