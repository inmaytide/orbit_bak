<template>
  <Form ref="inst" :model="instance" :rules="rules" :label-width="80">
    <FormItem :label="this.$i18n.t('system.org.properties.parent')">
      <p class="form-field-value">{{parentName}}</p>
    </FormItem>
    <Row>
      <Col span="12">
        <FormItem :label="this.$i18n.t('system.org.properties.name')" prop="name">
          <p class="form-field-value" v-if="!editing">{{instance.name}}</p>
          <i-input v-model="instance.name" :maxlength="64" v-if="editing"/>
        </FormItem>
      </Col>
      <Col span="12">
        <FormItem :label="this.$i18n.t('system.org.properties.code')" prop="code">
          <p class="form-field-value" v-if="!editing">{{instance.code}}</p>
          <Input v-model="instance.code" :maxlength="64" v-if="editing"/>
        </FormItem>
      </Col>
    </Row>
    <Row>
      <Col span="12">
        <FormItem :label="this.$i18n.t('system.org.properties.category')" prop="category">
          <Select v-model="instance.category" v-if="editing">
            <Option v-for="op in $store.state.enums.org.categories"
                    :value="op.value"
                    :key="op.value">{{$t('system.org.categories.' + op.label)}}</Option>
          </Select>
          <p class="form-field-value"
             v-for="op in $store.state.enums.org.categories"
             v-if="!editing && op.value === instance.category"
             :key="op.value">{{$t('system.org.categories.' + op.label)}}</p>
        </FormItem>
      </Col>
      <Col span="12">
        <FormItem :label="this.$i18n.t('system.org.properties.telephone')" prop="telephone">
          <p class="form-field-value" v-if="!editing">{{instance.telephone}}</p>
          <Input v-model="instance.telephone" :maxlength="64" v-if="editing"/>
        </FormItem>
      </Col>
    </Row>
    <FormItem :label="this.$i18n.t('system.org.properties.remark')" prop="remark">
      <Input type="textarea" :rows="3" v-model="instance.remark" :maxlength="512" v-if="editing"/>
      <p class="form-field-value" v-if="!editing">
        {{instance.remark}}</p>
    </FormItem>
    <FormItem style="text-align: right;" v-if="editing">
      <Button type="primary" @click="save">{{$t('common.btn.save')}}</Button>
      <Button style="margin-left: 8px" @click="cancel">{{$t('common.btn.cancel')}}</Button>
    </FormItem>
  </Form>
</template>
<script>
import api from '../../../apis/modules/system/org';
import form from '../../../utils/form';

export default {
  name: 'org-form',
  props: {
    status: String,
    instance: Object
  },
  data () {
    const __exists = (rule, value, callback) => {
      this.$http.get(api.exist, {code: this.instance.code, ignore: this.instance.id})
        .then(res => {
          if (res.exist) {
            callback(new Error(this.$i18n.t('system.menu.errors.code_not_repeat').toString()));
          } else {
            callback();
          }
        });
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
  methods: {
    save () {
      this.$refs['inst'].validate().then(isValid => {
        if (isValid) {
          const method = form.isCreating(this.status) ? this.$http.post : this.$http.put;
          const body = Object.assign({}, this.instance);
          delete body.parentObject;
          method(api.common, body).then(res => {
            res.parentObject = this.instance.parentObject;
            this.$emit('changeStatus', form.STATUS_VIEW);
            this.$emit('refresh', res);
          });
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
  computed: {
    parentName: function () {
      if (this.$commons.isNull(this.instance) || this.instance.id === '0') {
        return '';
      }
      return this.instance.parentObject.name;
    },
    editing: function () {
      return form.isEditing(this.status);
    }
  }
};
</script>
