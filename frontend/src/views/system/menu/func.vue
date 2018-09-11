<template>
  <div>
    <CellGroup @on-click="select">
      <Poptip v-model="modelVisible" placement="bottom-end" style="float: right;" :width="300">
        <div slot="content">
          <Form ref="inst" :model="current" :rules="rules" :label-width="45" :show-message="false">
            <FormItem :label="$t('system.func.properties.name')" style="margin-bottom: 7px;" prop="name">
              <Input v-model="current.name" size="small"></Input>
            </FormItem>
            <FormItem :label="$t('system.func.properties.code')" style="margin-bottom: 7px;" prop="code">
              <Input v-model="current.code" size="small"></Input>
            </FormItem>
            <FormItem style="text-align: right; margin-bottom: 0;">
              <Button type="primary" size="small" @click="save()" :loading="saveButtonLoading">{{$t('common.btn.save')}}</Button>
              <Button style="margin-left: 8px" size="small" @click="modelVisible = false" :disabled="saveButtonLoading">{{$t('common.btn.cancel')}}
              </Button>
            </FormItem>
          </Form>
        </div>
        <Cell style="display:flex; justify-content: center;" name="actions">
          <icon type="md-add" style="color: #2d8cf0;"/>
          <a style="padding: 1px 0; user-select: none; font-size: 12px;" href="javascript:void(0);">{{$t('common.btn.new')}}</a>
        </Cell>
      </Poptip>
      <Cell :selected="selected === element.id"
            v-for="element in elements"
            :key="element.id"
            :title="element.name"
            :label="element.code"
            :name="element.id">
        <a v-if="selected === element.id" slot="extra"
           style="padding: 1px 5px; color: red;" @click="remove(element.id)">{{$t('common.btn.remove')}}</a>
      </Cell>
    </CellGroup>
  </div>
</template>
<script>
import api from '../../../apis/modules/system/func.js';

export default {
  name: 'functions',
  props: {
    menuId: String
  },
  data () {
    const validateCode = (rule, value, callback) => {
      this.$http.get(api.exist, {code: this.current.code, ignore: this.current.id})
        .then(res => {
          if (res.exist) {
            callback(new Error(this.$i18n.t('system.menu.errors.code_not_repeat').toString()));
          } else {
            callback();
          }
        });
    };
    return {
      elements: [],
      selected: '',
      current: {},
      modelVisible: false,
      saveButtonLoading: false,
      rules: {
        name: [
          {required: true, trigger: 'blur', message: this.$i18n.t('errors.field_not_empty')}
        ],
        code: [
          {required: true, trigger: 'blur', message: this.$i18n.t('errors.field_not_empty')},
          {validator: validateCode, trigger: 'blur'}
        ]
      }
    };
  },
  watch: {
    menuId () {
      this.refresh();
    }
  },
  methods: {
    refresh () {
      if (!this.$commons.isNull(this.menuId)) {
        this.$http.get(api.listByMenuId(this.menuId))
          .then(res => (this.elements = res));
      }
    },
    select (id) {
      if (id !== 'actions') {
        this.selected = id;
      }
    },
    save () {
      this.saveButtonLoading = true;
      this.$refs['inst'].validate().then(isValid => {
        if (isValid) {
          this.$http.post(api.save(this.menuId), this.current)
            .then((res) => {
              this.elements.push(res);
              this.modelVisible = false;
              this.$refs['inst'].resetFields();
              this.saveButtonLoading = false;
            });
        } else {
          this.saveButtonLoading = false;
        }
      }).catch(() => (this.saveButtonLoading = false));
    },
    remove (id) {
      this.$Modal.confirm({
        title: this.$i18n.t('common.message.delete'),
        onOk: () => {
          this.$http.delete(api.remove(id))
            .then(() => {
              const len = this.elements.length;
              let index = -1;
              for (let i = 0; i < len; i++) {
                if (this.elements[i].id === id) {
                  index = i;
                }
              }
              this.elements.splice(index, 1);
            });
        }
      });
    }
  },
  mounted () {
    this.refresh();
  }
}
;
</script>
