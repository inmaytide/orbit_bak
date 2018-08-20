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
}];
