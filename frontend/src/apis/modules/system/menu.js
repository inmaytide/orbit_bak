export default {
  mineMenus: (username) => `/sys/menus/u/${username}`,
  common: '/sys/menus',
  exist: '/sys/menus/exist',
  get: (id) => `/sys/menus/${id}`,
  remove: (id) => `/sys/menus/${id}`,
  displayName: function (menu, i18n) {
    const key = 'common.menu.nav.' + menu.code;
    let name = i18n.t(key);
    if (name === '' || name === key) {
      name = menu.name;
    }
    return name;
  }
};
