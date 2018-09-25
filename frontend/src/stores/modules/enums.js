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
  user: {
    status: [
      {label: 'enable', value: 1},
      {label: 'outwork', value: 2},
      {label: 'vacation', value: 3},
      {label: 'disabled', value: 99}
    ]
  }
};

export default {
  state
};
