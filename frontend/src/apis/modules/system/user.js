export default {
  common: '/sys/users',
  get: (id) => `/sys/users/${id}`,
  exist: '/sys/users/exist',
  newId: '/sys/users/generate-new-id'
};
