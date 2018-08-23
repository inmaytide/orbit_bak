import common from './common';
import menu from './modules/system/menu';
import errors from './errors';

export default {
  errors: errors,
  system: {
    menu: menu
  },
  ...common
};
