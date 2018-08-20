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
        <Form ref="inst" :model="current" :rules="rules" :label-width="60" >
          <FormItem :label="this.$i18n.t('system.menu.properties.parent')">
            <Input v-model="current.parent" readonly="readonly" />
          </FormItem>
          <FormItem :label="this.$i18n.t('system.menu.properties.name')" prop="name">
            <i-input v-model="current.name" :maxlength="64" />
          </FormItem>
          <FormItem :label="this.$i18n.t('system.menu.properties.code')" prop="code">
            <Input v-model="current.code" :maxlength="64" />
          </FormItem>
          <FormItem :label="this.$i18n.t('system.menu.properties.sort')" prop="code">
            <Input v-model="current.sort" :maxlength="64" />
          </FormItem>
          <FormItem :label="this.$i18n.t('system.menu.properties.icon')" prop="code">
            <Input v-model="current.icon" :maxlength="64" />
          </FormItem>
          <FormItem :label="this.$i18n.t('system.menu.properties.url')" prop="code">
            <Input v-model="current.url" :maxlength="64" />
          </FormItem>
          <FormItem :label="this.$i18n.t('system.menu.properties.description')" prop="code">
            <Input v-model="current.description" :maxlength="64" />
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
          fontSize: '14px',
          paddingLeft: '4px'
        }
      }, this.displayName(data));
    }
  },
  data () {
    return {
      menus: [],
      current: {
        name: null,
        code: '',
        creator: this.$commons.getUser().id,
        version: 0
      },
      rules: {
        name: [
          {required: true, trigger: 'blur', message: '13123123123123'}
        ]
      }
    };
  },
  mounted () {
    this.$http.get(api.list).then(res => {
      this.menus = this.$commons.transform(res);
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

  .container-center > div:nth-last-child(1) {
    margin-right: 10px;
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
