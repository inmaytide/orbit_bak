export default [{
  path: '/system/menu',
  component: (resolve) => require(['../../views/system/menu/menu'], resolve),
  meta: {
    authenticated: true,
    breadcrumbs: [
      {text: 'common.menu.nav.system'},
      {text: 'common.menu.nav.menu'}
    ]
  }
}];
