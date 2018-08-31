<template>
  <Form ref="inst" :model="instance" :rules="rules" :label-width="80">
    <FormItem :label="this.$i18n.t('system.menu.properties.parent')">
      <p class="form-field-value">{{parentName}}</p>
    </FormItem>
  </Form>
</template>
<script>
// import api from '../../../apis/modules/system/org';

export default {
  name: 'org-form',
  props: {
    status: String,
    instance: Object
  },
  computed: {
    parentName: function () {
      if (this.$commons.isNull(this.instance) || this.instance.id === '0') {
        return '';
      }
      return this.instance.parentObject.name;
    }
  }
};
</script>
