export default {
  common: '/sys/organizations',
  exist: '/sys/organizations/exist',
  remove: function (id) {
    return `/sys/organizations/${id}`;
  },
  get: function (id) {
    return `/sys/organizations/${id}`;
  }
};
