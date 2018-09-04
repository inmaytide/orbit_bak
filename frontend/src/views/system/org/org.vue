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
        <org-form :status="status"
                  :instance="selected"
                  @changeStatus="changeStatus"
                  @changeSelected="changeSelected"
                  @refresh="refresh"/>
      </div>
    </div>
  </div>
</template>
<script>
import api from '../../../apis/modules/system/org';
import form from '../../../utils/form';
import orgForm from './org-form';
import orgTreeNode from './org-tree-node';

export default {
  name: 'orgs',
  components: {
    orgForm
  },
  data () {
    return {
      status: form.STATUS_VIEW,
      selected: {},
      orgs: [{id: '0', name: this.$i18n.t('common.menu.nav.org'), children: [], expand: true}]
    };
  },
  methods: {
    nodePaths (inst, paths = []) {
      if (inst.parent !== '0') {
        paths.push(inst.parent);
        return this.nodePaths(inst.parentObject, paths);
      }
      return paths;
    },
    expand (paths = [], nodes = []) {
      const id = paths.pop();
      if (typeof id !== 'undefined') {
        nodes.forEach(element => {
          if (element.id === id) {
            element.expand = true;
            this.expand(paths, element.children);
          }
        });
      }
    },
    changeStatus (status) {
      this.status = status;
    },
    changeSelected (inst) {
      this.selected = inst;
      this.expand(this.nodePaths(inst), this.orgs[0].children);
    },
    refresh (selected) {
      this.$http.get(api.common).then(res => {
        this.orgs[0].children = this.$commons.transform(res);
        if (!this.$commons.isNull(selected)) {
          this.changeSelected(selected);
        }
      });
    },
    create (parent) {
      this.changeSelected({
        parent: parent.id,
        parentObject: parent
      });
      this.changeStatus(form.STATUS_CREATE);
    },
    edit (selected) {
      this.changeSelected(selected);
      this.changeStatus(form.STATUS_MODIFY);
    },
    remove (selected) {
      this.$http.delete(api.remove(selected.id)).then(() => {
        const children = selected.parent === '0' ? this.orgs[0].children : selected.parentObject.children;
        let index = this.$commons.index(children, selected.id);
        children.splice(index, 1);
        if (children.length === 0) {
          this.changeSelected(selected.parentObject);
          return;
        }
        this.changeSelected(children[index === 0 ? ++index : --index]);
      });
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
          nodeClick: this.changeSelected,
          create: this.create,
          remove: this.remove,
          edit: this.edit
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
