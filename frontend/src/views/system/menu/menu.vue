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
                <p class="form-field-value" v-if="status === $store.state.enums.FORM_STATUS_CHECK">{{current.seqOrder}}</p>
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
                   v-if="status === $store.state.enums.FORM_STATUS_CHECK && icon.value === current.icon" :key="icon.value">{{icon.label}}</p>
              </FormItem>
            </Col>
          </Row>
          <FormItem :label="this.$i18n.t('system.menu.properties.url')" prop="url">
            <Input v-model="current.url" :maxlength="256" v-if="status !== $store.state.enums.FORM_STATUS_CHECK"/>
            <p class="form-field-value" v-if="status === $store.state.enums.FORM_STATUS_CHECK">{{current.url}}</p>
          </FormItem>
          <FormItem :label="this.$i18n.t('system.menu.properties.description')" prop="description">
            <Input type="textarea" :rows="3" v-model="current.description" :maxlength="512" v-if="status !== $store.state.enums.FORM_STATUS_CHECK"/>
            <p class="form-field-value" v-if="status === $store.state.enums.FORM_STATUS_CHECK">{{current.description}}</p>
          </FormItem>
          <FormItem style="text-align: right;" v-if="status!==$store.state.enums.FORM_STATUS_CHECK">
            <Button type="primary" @click="saveInformation">{{$t('common.btn.save')}}</Button>
            <Button style="margin-left: 8px" @click="cancelEditInformation">{{$t('common.btn.cancel')}}</Button>
          </FormItem>
        </Form>
      </div>
    </div>
    <div>
      <div class="block-title blue-left-border">{{$t('system.menu.block.title.func')}}</div>
      <div class="block-content">
      </div>
    </div>
  </div>
</template>
<script>
import api from '@/apis/menu.js';
import menuTreeNode from './tree-node';

export default {
  name: 'menus',
  components: {
    menuTreeNode
  },
  methods: {
    refreshMenus (selected) {
      this.status = this.$store.state.enums.FORM_STATUS_CHECK;
      this.$http.get(api.list).then(res => {
        this.menus = this.$commons.transform(res);
        if (typeof selected === 'undefined') {
          this.current = this.menus[0];
        }
      });
    },
    cancelEditInformation () {
      this.$Modal.confirm({
        title: this.$i18n.t('common.message.confirm_cancel'),
        onOk: () => {
          if (!this.current.id) {
            const parent = this.current.parent;
            const filtered = this.menus.filter(menu => menu.id === parent);
            this.current = filtered.length === 0 ? this.menus[0] : filtered[0];
            this.modifyParetName(this.current);
            this.status = this.$store.state.enums.FORM_STATUS_CHECK;
          } else {
            this.$http.get(api.get.replace('{id}', this.current.id))
              .then(res => (this.current = res));
            this.status = this.$store.state.enums.FORM_STATUS_CHECK;
          }
        }
      });
    },
    saveInformation () {
      this.$refs['inst'].validate().then(isValid => {
        if (isValid) {
          let action = null;
          if (this.status === this.$store.state.enums.FORM_STATUS_CREATE) {
            this.current.creator = this.$commons.getUser().id;
            action = this.$http.post(api.create, this.current);
          } else if (this.status === this.$store.state.enums.FORM_STATUS_EDIT) {
            this.current.updater = this.$commons.getUser().id;
            action = this.$http.put(api.update, this.current);
          }
          if (action != null) {
            action.then(res => {
              this.refreshMenus(res);
            });
          }
        }
      });
    },
    modifyParetName (data) {
      const parent = this.menus.filter(menu => menu.id === data.parent);
      if (parent.length > 0) {
        this.parentName = api.displayName(parent[0], this.$i18n);
      } else {
        this.parentName = this.$i18n.t('common.menu.nav.root');
      }
    },
    nodeClick (data) {
      this.current = data;
      this.modifyParetName(data);
    },
    remove (data) {
      this.$http
        .delete(api.remove.replace('{id}', data.id))
        .then(() => {
          let $menus = this.menus;
          if (data.parent !== '0') {
            const filtered = $menus.filter(menu => menu.id === data.parent);
            $menus = filtered.length > 0 ? filtered[0].children : [];
          }
          $menus.splice($menus.indexOf(data), 1);
          if (this.menus.length === 0) {
            this.current = {};
          }
          this.$Notice.success({
            title: this.$i18n.t('common.message.delete_success')
          });
        });
    },
    create (parent) {
      this.current = {parent: parent};
      this.modifyParetName(this.current);
      this.status = this.$store.state.enums.FORM_STATUS_CREATE;
    },
    edit (menu) {
      this.current = menu;
      this.modifyParetName(menu);
      this.status = this.$store.state.enums.FORM_STATUS_EDIT;
    },
    renderNodes (h, {root, node, data}) {
      return h(menuTreeNode, {
        props: {
          name: api.displayName(data, this.$i18n),
          menu: Object.assign({}, data),
          selected: this.current.id === data.id
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
      parentName: this.$i18n.t('common.menu.nav.root'),
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
    this.refreshMenus();
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
