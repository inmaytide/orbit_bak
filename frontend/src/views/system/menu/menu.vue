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
            <Input v-model="current.parent" :readonly="true" :disabled="status === 'check'"/>
          </FormItem>
          <Row>
            <Col span="12">
              <FormItem :label="this.$i18n.t('system.menu.properties.name')" prop="name">
                <i-input v-model="current.name" :maxlength="64" :disabled="status === 'check'"/>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem :label="this.$i18n.t('system.menu.properties.code')" prop="code">
                <Input v-model="current.code" :maxlength="64" :disabled="status === 'check'"/>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem :label="this.$i18n.t('system.menu.properties.sort')" prop="seqOrder">
                <InputNumber v-model="current.seqOrder" :min="1" :max="999999" :precision="0" v-if="status !== 'check' "/>
                <Input v-model="current.seqOrder" :disabled="true" v-if="status === 'check'"/>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem :label="this.$i18n.t('system.menu.properties.icon')" prop="icon">
                <Select v-model="current.icon" v-if="status !== 'check'">
                  <Option :value="icon.value" v-for="icon in this.$store.state.enums.menu.icons" :key="icon.value">
                    {{icon.label}} <Icon :type="icon.value" size="16" style="float: right;"/>
                  </Option>
                </Select>
                <Input v-for="icon in this.$store.state.enums.menu.icons" v-if="status === 'check' && icon.value === current.icon" :key="icon.value"  :value="icon.label" :disabled="true"/>
              </FormItem>
            </Col>
          </Row>
          <FormItem :label="this.$i18n.t('system.menu.properties.url')" prop="url">
            <Input v-model="current.url" :maxlength="256" :disabled="status === 'check'"/>
          </FormItem>
          <FormItem :label="this.$i18n.t('system.menu.properties.description')" prop="description">
            <Input type="textarea" :rows="3" v-model="current.description" :maxlength="512" :disabled="status === 'check'"/>
          </FormItem>
          <FormItem style="text-align: right;" v-if="status!=='check'">
            <Button type="primary">Submit</Button>
            <Button style="margin-left: 8px">Cancel</Button>
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

export default {
  name: 'menus',
  methods: {
    displayName (menu) {
      let name = this.$i18n.t('common.menu.nav.' + menu.code);
      if (name === '' || name === menu.code) {
        name = menu.name;
      }
      return name;
    },
    search (conditions) {
      console.log(conditions);
    },
    renderNodes (h, {root, node, data}) {
      return h('span', {
        style: {
          fontSize: '13px',
          paddingLeft: '4px',
          cursor: 'pointer'
        },
        on: {
          click: () => {
            this.$http
              .get(api.get.replace('{id}', data.id))
              .then(res => (this.current = res));
          }
        }
      }, this.displayName(data));
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
      status: 'check',
      menus: [],
      current: {
        name: null,
        code: '',
        creator: this.$commons.getUser().id,
        version: 0
      },
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
    this.$http.get(api.list).then(res => {
      this.menus = this.$commons.transform(res);
      this.current = this.menus[0];
    });
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
    width: 25%;
  }

  .container-center > div:nth-child(2) {
    width: 45%;
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
