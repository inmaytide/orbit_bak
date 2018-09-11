<template>
  <div class="node">
    <span @click="nodeClick" :class="{'node-selected': selected, 'node-title': true}">{{name}}</span>
    <div class="node-actions" v-if="viewing && selected">
      <Poptip
        confirm
        class="node-action"
        v-if="menu.children.length === 0"
        :title="this.$i18n.t('common.message.delete')"
        @on-ok="remove">
        <a href="javascript:void(0);" style="color: red;">{{$t('common.btn.remove')}}</a>
      </Poptip>
      <a @click="edit" href="javascript:void(0);" class="node-action">{{$t('common.btn.edit')}}</a>
      <Poptip
        confirm
        class="node-action"
        v-if="menu.parent === '0'"
        :title="this.$i18n.t('system.menu.message.choose_root_or_child')"
        :ok-text="this.$i18n.t('system.menu.btn.root')"
        :cancel-text="this.$i18n.t('system.menu.btn.child')"
        @on-ok="createRoot"
        @on-cancel="createChild">
        <a href="javascript:void(0);">{{$t('common.btn.new')}}</a>
      </Poptip>
    </div>
  </div>
</template>
<script>
import form from '../../../utils/form';

export default {
  name: 'menu-tree-node',
  props: {
    name: String,
    menu: Object,
    selected: Boolean,
    status: String
  },
  methods: {
    nodeClick () {
      if (!this.viewing) {
        return;
      }
      this.$emit('nodeClick', this.menu);
    },
    remove () {
      this.$emit('remove', this.menu);
    },
    createRoot () {
      this.$emit('create', {id: '0'});
    },
    createChild () {
      this.$emit('create', this.menu);
    },
    edit () {
      this.$emit('edit', this.menu);
    }
  },
  computed: {
    viewing: function () {
      return form.viewing(this.status);
    }
  }
};
</script>
<style scoped>
  .node {
    display: inline-flex;
    width: 95%;
    cursor: pointer;
    padding: 0 5px 0 0;
    position: relative;
    justify-content: space-between;
  }

  .node-selected {
    background-color: #f8f8f9;
  }

  .node-title {
    font-size: 13px;
    padding: 0 5px 3px 5px;
    color: #515a6e;
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
    width: 100%;
    float: left;
    user-select: none;
  }

  .node-actions {
    position: absolute;
    top: 2px;
    right: 10px;
    z-index: 999;
    padding-right: 5px;
  }

  .node-action {
    float: right;
    margin-left: 5px;
    font-size: 12px;
  }
</style>
<style>
  .node-actions .ivu-poptip-popper {
    width: 300px !important;
  }
</style>
