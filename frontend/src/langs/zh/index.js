import common from './common';
import menu from './modules/system/menu';
import func from './modules/system/func';
import errors from './errors';

export default {
  errors: errors,
  system: {
    menu: menu,
    func: func
  },
  ...common
};
