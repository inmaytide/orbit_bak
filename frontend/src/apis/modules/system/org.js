export default {
  all: '/sys/organizations',
  get: function (id) {
    return `/sys/organizations/${id}`;
  }
};
