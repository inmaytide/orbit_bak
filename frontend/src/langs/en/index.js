import common from './common';
import system from './modules/system/system';
import errors from './errors';

export default {
  errors: errors,
  ...system,
  ...common
};
