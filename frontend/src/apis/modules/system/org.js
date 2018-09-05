export default {
  common: '/sys/organizations',
  exist: '/sys/organizations/exist',
  remove: (id) => `/sys/organizations/${id}`,
  get: (id) => `/sys/organizations/${id}`
};
