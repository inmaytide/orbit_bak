const state = {
  menu: {
    icons: [
      {label: 'Settings', value: 'ios-settings'},
      {label: 'Documents', value: 'ios-paper'},
      {label: 'Statistics', value: 'ios-stats'}
    ]
  },
  org: {
    categories: [
      {label: 'group', value: 0},
      {label: 'company', value: 1},
      {label: 'department', value: 2},
      {label: 'station', value: 3}
    ]
  },
  FORM_STATUS_CHECK: 'check',
  FORM_STATUS_CREATE: 'create',
  FORM_STATUS_EDIT: 'edit'
};

export default {
  state
};
