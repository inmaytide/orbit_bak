export default {
  listByMenuId: function (menuId) {
    return `/sys/menus/${menuId}/functions`;
  }
};
