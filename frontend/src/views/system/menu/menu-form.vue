<template>
  <Form ref="inst" :model="instance" :rules="rules" :label-width="80">
    <FormItem :label="this.$i18n.t('system.menu.properties.parent')">
      <p class="form-field-value">{{parentName}}</p>
    </FormItem>
    <Row>
      <Col span="12">
        <FormItem :label="this.$i18n.t('system.menu.properties.name')" prop="name">
          <p class="form-field-value" v-if="!editing">{{instance.name}}</p>
          <i-input v-model="instance.name" :maxlength="64" v-if="editing"/>
        </FormItem>
      </Col>
      <Col span="12">
        <FormItem :label="this.$i18n.t('system.menu.properties.code')" prop="code">
          <p class="form-field-value" v-if="!editing">{{instance.code}}</p>
          <Input v-model="instance.code" :maxlength="64" v-if="editing"/>
        </FormItem>
      </Col>
    </Row>
    <Row>
      <Col span="12">
        <FormItem :label="this.$i18n.t('system.menu.properties.sort')" prop="seqOrder">
          <InputNumber v-model="instance.seqOrder" :min="1" :max="999999" :precision="0" v-if="editing "/>
          <p class="form-field-value" v-if="!editing">{{instance.seqOrder}}</p>
        </FormItem>
      </Col>
      <Col span="12">
        <FormItem :label="this.$i18n.t('system.menu.properties.icon')" prop="icon">
          <Select v-model="instance.icon" v-if="editing">
            <Option :value="icon.value" v-for="icon in $store.state.enums.menu.icons" :key="icon.value">
              {{icon.label}}
              <Icon :type="icon.value" size="16" style="float: right;"/>
            </Option>
          </Select>
          <p class="form-field-value"
             v-for="icon in $store.state.enums.menu.icons"
             v-if="!editing && icon.value === instance.icon"
             :key="icon.value">{{icon.label}}</p>
        </FormItem>
      </Col>
    </Row>
    <FormItem :label="this.$i18n.t('system.menu.properties.url')" prop="url">
      <Input v-model="instance.url" :maxlength="256" v-if="editing"/>
      <p class="form-field-value" v-if="!editing">{{instance.url}}</p>
    </FormItem>
    <FormItem :label="this.$i18n.t('system.menu.properties.description')" prop="description">
      <Input type="textarea" :rows="3" v-model="instance.description" :maxlength="512" v-if="editing"/>
      <p class="form-field-value" v-if="!editing">{{instance.description}}</p>
    </FormItem>
    <FormItem style="text-align: right;" v-if="editing">
      <Button type="primary" @click="save">{{$t('common.btn.save')}}</Button>
      <Button style="margin-left: 8px" @click="cancel">{{$t('common.btn.cancel')}}</Button>
    </FormItem>
  </Form>
</template>
<script>
import api from '../../../apis/modules/system/menu';
import form from '../../../utils/form';

export default {
  name: 'menu-form',
  props: {
    instance: Object,
    status: status
  },
  methods: {
    save () {
      this.$refs['inst'].validate().then(isValid => {
        if (isValid) {
          if (this.status === this.$store.state.enums.STATUS_CREATE) {
            this.current.creator = this.$commons.getUser().id;
            this.$http.post(api.create, this.current).then(this.refresh);
          } else if (this.status === this.$store.state.enums.STATUS_EDIT) {
            this.current.updater = this.$commons.getUser().id;
            this.$http.put(api.create, this.current).then(this.refresh);
          }
        }
      });
    },
    cancel () {
      this.$Modal.confirm({
        title: this.$i18n.t('common.message.confirm_cancel'),
        onOk: () => {
          this.$emit('changeStatus', form.STATUS_VIEW);
          if (form.isCreating(this.status)) {
            this.$emit('changeSelected', this.instance.parentObject);
            return;
          }
          this.$http.get(api.get(this.instance.id)).then(res => this.$emit('changeSelected', res));
        }
      });
    }
  },
  data () {
    const __exists = (rule, value, callback) => {
      const message = this.$i18n.t('system.menu.errors.code_not_repeat').toString();
      this.$http.get(api.exist, {code: this.instance.code, ignore: this.instance.id})
        .then(res => res.exist ? callback(new Error(message)) : callback());
    };
    return {
      rules: {
        name: [
          {required: true, trigger: 'blur', message: this.$i18n.t('errors.field_not_empty')}
        ],
        code: [
          {required: true, trigger: 'blur', message: this.$i18n.t('errors.field_not_empty')},
          {validator: __exists, trigger: 'blur'}
        ]
      }
    };
  },
  computed: {
    parentName: function () {
      if (this.$commons.isNull(this.instance) || this.instance.parent === '0') {
        return this.$i18n.t('common.menu.nav.root');
      }
      return api.displayName(this.instance.parentObject, this.$i18n);
    },
    editing: function () {
      return form.isEditing(this.status);
    }
  }
};
</script>
