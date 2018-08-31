<template>
  <div class="container-center">
    <div>
      <div class="block-title blue-left-border">{{$t('common.menu.nav.menu')}}</div>
      <div class="block-content">
        <Tree :data="menus" :render="renderNodes"/>
      </div>
    </div>
    <div>
      <div class="block-title blue-left-border">{{$t('common.title.info')}}</div>
      <div class="block-content" style="padding: 10px 30px 10px 10px;">
        <Form ref="inst" :model="current" :rules="rules" :label-width="80">
          <FormItem :label="this.$i18n.t('system.menu.properties.parent')">
            <p class="form-field-value">{{parentName}}</p>
          </FormItem>
          <Row>
            <Col span="12">
              <FormItem :label="this.$i18n.t('system.menu.properties.name')" prop="name">
                <p class="form-field-value" v-if="status === $store.state.enums.FORM_STATUS_CHECK">{{current.name}}</p>
                <i-input v-model="current.name" :maxlength="64" v-if="status !== $store.state.enums.FORM_STATUS_CHECK"/>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem :label="this.$i18n.t('system.menu.properties.code')" prop="code">
                <p class="form-field-value" v-if="status === $store.state.enums.FORM_STATUS_CHECK">{{current.code}}</p>
                <Input v-model="current.code" :maxlength="64" v-if="status !== $store.state.enums.FORM_STATUS_CHECK"/>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem :label="this.$i18n.t('system.menu.properties.sort')" prop="seqOrder">
                <InputNumber v-model="current.seqOrder" :min="1" :max="999999" :precision="0"
                             v-if="status !== $store.state.enums.FORM_STATUS_CHECK "/>
                <p class="form-field-value" v-if="status === $store.state.enums.FORM_STATUS_CHECK">
                  {{current.seqOrder}}</p>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem :label="this.$i18n.t('system.menu.properties.icon')" prop="icon">
                <Select v-model="current.icon" v-if="status !== $store.state.enums.FORM_STATUS_CHECK">
                  <Option :value="icon.value" v-for="icon in $store.state.enums.menu.icons" :key="icon.value">
                    {{icon.label}}
                    <Icon :type="icon.value" size="16" style="float: right;"/>
                  </Option>
                </Select>
                <p class="form-field-value" v-for="icon in $store.state.enums.menu.icons"
                   v-if="status === $store.state.enums.FORM_STATUS_CHECK && icon.value === current.icon"
                   :key="icon.value">{{icon.label}}</p>
              </FormItem>
            </Col>
          </Row>
          <FormItem :label="this.$i18n.t('system.menu.properties.url')" prop="url">
            <Input v-model="current.url" :maxlength="256" v-if="status !== $store.state.enums.FORM_STATUS_CHECK"/>
            <p class="form-field-value" v-if="status === $store.state.enums.FORM_STATUS_CHECK">{{current.url}}</p>
          </FormItem>
          <FormItem :label="this.$i18n.t('system.menu.properties.description')" prop="description">
            <Input type="textarea" :rows="3" v-model="current.description" :maxlength="512"
                   v-if="status !== $store.state.enums.FORM_STATUS_CHECK"/>
            <p class="form-field-value" v-if="status === $store.state.enums.FORM_STATUS_CHECK">
              {{current.description}}</p>
          </FormItem>
          <FormItem style="text-align: right;" v-if="status!==$store.state.enums.FORM_STATUS_CHECK">
            <Button type="primary" @click="save">{{$t('common.btn.save')}}</Button>
            <Button style="margin-left: 8px" @click="editCancel">{{$t('common.btn.cancel')}}</Button>
          </FormItem>
        </Form>
      </div>
    </div>
    <div>
      <div class="block-title blue-left-border">{{$t('system.menu.block.title.func')}}</div>
      <div class="block-content">
        <functions :menu-id="current.id"/>
      </div>
    </div>
  </div>
</template>
<script>
import api from '../../../apis/modules/system/menu.js';
import menuTreeNode from './menu-tree-node';
import functions from './func';

export default {
  name: 'menus',
  components: {
    menuTreeNode,
    functions
  },
  methods: {
    findIndex (menus, condition) {
      if (this.$commons.isEmptyArray(menus)) {
        return -1;
      }
      const len = menus.length;
      for (let i = 0; i < len; i++) {
        if (menus[i].id === condition) {
          return i;
        }
      }
      return -1;
    },
    selectNode (menus, selected) {
      if (this.$commons.isNull(selected)) {
        this.current = menus[0];
        return;
      }
      const filtered = menus.filter(menu => menu.id === selected.id);
      this.current = filtered.length === 0 ? menus[0] : filtered[0];
    },
    refresh (selected) {
      this.menus = [];
      this.status = this.$store.state.enums.FORM_STATUS_CHECK;
      this.$http.get(api.list).then(res => {
        this.menus = this.$commons.transform(res);
        if (this.$commons.isNull(selected) || selected.parent === '0') {
          this.selectNode(this.menus, selected);
          return;
        }
        const parent = this.menus.filter(menu => menu.id === selected.parent)[0];
        parent.expand = true;
        this.selectNode(parent.children, selected);
      });
    },
    editCancel () {
      this.$Modal.confirm({
        title: this.$i18n.t('common.message.confirm_cancel'),
        onOk: () => {
          if (this.status === this.$store.state.enums.FORM_STATUS_CREATE) {
            this.selectNode(this.menus, {id: this.current.parent});
            this.status = this.$store.state.enums.FORM_STATUS_CHECK;
          } else {
            this.$http.get(api.get.replace('{id}', this.current.id)).then(res => (this.current = res));
            this.status = this.$store.state.enums.FORM_STATUS_CHECK;
          }
        }
      });
    },
    save () {
      this.$refs['inst'].validate().then(isValid => {
        if (isValid) {
          if (this.status === this.$store.state.enums.FORM_STATUS_CREATE) {
            this.current.creator = this.$commons.getUser().id;
            this.$http.post(api.create, this.current).then(this.refresh);
          } else if (this.status === this.$store.state.enums.FORM_STATUS_EDIT) {
            this.current.updater = this.$commons.getUser().id;
            this.$http.put(api.create, this.current).then(this.refresh);
          }
        }
      });
    },
    nodeClick (data) {
      this.current = data;
    },
    remove (data) {
      this.$http
        .delete(api.remove.replace('{id}', data.id))
        .then(() => {
          const conditions = data.parent === '0' ? data.id : data.parent;
          let index = this.findIndex(this.menus, conditions);
          if (data.parent === '0') {
            this.menus.splice(index, 1);
            if (this.menus.length > 0) {
              this.selectNode(this.menus, this.menus[index === this.menus.length ? --index : index]);
            } else {
              this.current = {};
            }
          } else {
            const parent = this.menus[index];
            index = this.findIndex(parent.children, data.id);
            parent.children.splice(index, 1);
            if (parent.children.length === 0) {
              this.selectNode(this.menus, parent);
            } else {
              this.selectNode(parent.children, parent.children[index === parent.children.length ? --index : index]);
            }
          }
          this.$Notice.success({
            title: this.$i18n.t('common.message.delete_success')
          });
        });
    },
    create (parent) {
      this.current = {parent: parent};
      this.status = this.$store.state.enums.FORM_STATUS_CREATE;
    },
    edit (menu) {
      this.current = menu;
      this.status = this.$store.state.enums.FORM_STATUS_EDIT;
    },
    renderNodes (h, {root, node, data}) {
      return h(menuTreeNode, {
        props: {
          name: api.displayName(data, this.$i18n),
          menu: Object.assign({}, data),
          selected: this.current.id === data.id,
          status: this.status
        },
        on: {
          nodeClick: this.nodeClick,
          remove: this.remove,
          create: this.create,
          edit: this.edit
        }
      });
    }
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
      status: this.$store.state.enums.FORM_STATUS_CHECK,
      menus: [],
      current: {},
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
  mounted () {
    this.refresh();
  },
  computed: {
    parentName: function () {
      if (this.$commons.isNull(this.current) || this.current.parent === '0') {
        return this.$i18n.t('common.menu.nav.root');
      }
      return api.displayName(this.current.parentObject, this.$i18n);
    }
  }
};
</script>
<style scoped>
  .container-center {
    display: flex;
    justify-content: space-around;
    height: 100%;
  }

  .container-center > div {
    width: 33.333%;
    height: calc(100% - 20px);
    margin: 10px 0 10px 10px;
    border-top-left-radius: 3px;
    border-top-right-radius: 3px;
  }

  .container-center > div:nth-child(1) {
    width: 23%;
  }

  .container-center > div:nth-child(2) {
    width: 47%;
  }

  .container-center > div:nth-last-child(1) {
    margin-right: 10px;
    width: 30%;
  }

  .block-title {
    height: 40px;
    line-height: 40px;
    padding-left: 15px;
    border-radius: 3px;
    background-color: white;
  }

  .block-content {
    background-color: white;
    margin-top: 4px;
    padding: 1px 8px;
    height: calc(100% - 44px);
    border-radius: 3px;
  }
</style>
