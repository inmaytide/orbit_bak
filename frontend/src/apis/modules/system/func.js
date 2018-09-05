export default {
  exist: '/sys/functions/exist',
  listByMenuId: (menuId) => `/sys/menus/${menuId}/functions`,
  save: (menuId) => `/sys/menus/${menuId}/functions`,
  remove: (id) => `/sys/functions/${id}`
};
