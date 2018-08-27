export default {
  exist: '/sys/functions/exist',
  listByMenuId: function (menuId) {
    return `/sys/menus/${menuId}/functions`;
  },
  save: function (menuId) {
    return `/sys/menus/${menuId}/functions`;
  },
  remove: function (id) {
    return `/sys/functions/${id}`;
  }
};
