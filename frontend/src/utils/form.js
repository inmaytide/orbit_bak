export default {
  STATUS_VIEW: 'view',
  STATUS_CREATE: 'create',
  STATUS_MODIFY: 'modify',
  isCreating (status) {
    return status === this.STATUS_CREATE;
  },
  viewing (status) {
    return status === this.STATUS_VIEW;
  },
  isModifying (status) {
    return status === this.STATUS_MODIFY;
  },
  isEditing (status) {
    return this.isCreating(status) || this.isModifying(status);
  }
};
