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
        <menu-form :status="status" :instance="current" @changeStatus="changeStatus" @changeSelected="changeSelected"/>
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
import menuForm from './menu-form';
import form from '../../../utils/form';

export default {
  name: 'menus',
  components: {
    menuTreeNode,
    functions,
    menuForm
  },
  methods: {
    changeStatus (status) {
      this.status = status;
    },
    changeSelected (selected) {
      if (this.$commons.isNull(selected)) {
        this.current = this.menus[0];
        return;
      }
      if (selected.parent === '0') {
        this.current = selected;
      } else {
        const index = this.$commons.index(this.menus, selected.parent);
        this.menus[index].expand = true;
        this.current = selected;
      }
    },
    refresh (selected) {
      this.menus = [];
      this.status = form.STATUS_VIEW;
      this.$http.get(api.common).then(res => {
        this.menus = this.$commons.transform(res);
        this.changeSelected(selected);
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
              this.changeSelected(this.menus, this.menus[index === this.menus.length ? --index : index]);
            } else {
              this.current = {};
            }
          } else {
            const parent = this.menus[index];
            index = this.findIndex(parent.children, data.id);
            parent.children.splice(index, 1);
            if (parent.children.length === 0) {
              this.changeSelected(this.menus, parent);
            } else {
              this.changeSelected(parent.children, parent.children[index === parent.children.length ? --index : index]);
            }
          }
          this.$Notice.success({
            title: this.$i18n.t('common.message.delete_success')
          });
        });
    },
    create (parent) {
      this.changeSelected({parent: parent});
      this.changeStatus(form.STATUS_CREATE);
    },
    edit (inst) {
      this.changeSelected(inst);
      this.changeStatus(form.STATUS_MODIFY);
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
    return {
      status: form.STATUS_VIEW,
      menus: [],
      current: {}
    };
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
