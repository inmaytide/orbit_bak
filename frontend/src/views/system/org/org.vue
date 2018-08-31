<template>
  <div class="container-center">
    <div>
      <div class="block-title blue-left-border">{{$t('common.menu.nav.org')}}</div>
      <div class="block-content">
        <Tree :data="orgs" :render="treeRender"/>
      </div>
    </div>
    <div>
      <div class="block-title blue-left-border">{{$t('common.title.info')}}</div>
      <div class="block-content" style="padding: 10px 30px 10px 10px;">
        <org-form :status="status" :instance="selected" />
      </div>
    </div>
  </div>
</template>
<script>
import api from '../../../apis/modules/system/org';
import orgForm from './org-form';
import orgTreeNode from './org-tree-node';

export default {
  name: 'orgs',
  components: {
    orgForm
  },
  data () {
    return {
      status: this.$store.state.enums.FORM_STATUS_CHECK,
      selected: {},
      orgs: [{id: '0', name: this.$i18n.t('common.menu.nav.org'), children: []}]
    };
  },
  methods: {
    refresh () {
      this.$http.get(api.all).then(res => {
        this.orgs.children = this.$commons.transform(res);
      });
    },
    nodeClick (inst) {
      this.selected = inst;
    },
    treeRender (h, {root, node, data}) {
      return h(orgTreeNode, {
        props: {
          name: data.name,
          org: Object.assign({}, data),
          status: this.status,
          isSelected: this.selected.id === data.id
        },
        on: {
          nodeClick: this.nodeClick
        }
      });
    }
  },
  mounted () {
    this.refresh();
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
    height: calc(100% - 20px);
    margin: 10px 0 10px 10px;
    border-top-left-radius: 3px;
    border-top-right-radius: 3px;
  }

  .container-center > div:nth-child(1) {
    width: 35%;
  }

  .container-center > div:last-child {
    width: 65%;
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
