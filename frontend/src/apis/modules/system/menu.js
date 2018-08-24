export default {
  mineMenus: '/sys/menus/u/{username}',
  list: '/sys/menus',
  exist: '/sys/menus/exist',
  get: '/sys/menus/{id}',
  remove: '/sys/menus/{id}',
  create: '/sys/menus',
  update: '/sys/menus',
  displayName: function (menu, i18n) {
    const key = 'common.menu.nav.' + menu.code;
    let name = i18n.t(key);
    if (name === '' || name === key) {
      name = menu.name;
    }
    return name;
  }
};
