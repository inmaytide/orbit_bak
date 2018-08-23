<template>
  <div class="node" @mouseover="showActions=true" @mouseout="showActions=false">
    <span @click="nodeClick" :class="{'node-selected': selected, 'node-title': true}">{{name}}</span>
    <div class="node-actions">
      <Poptip
        confirm
        class="node-action"
        v-if="showActions && menu.children.length === 0"
        :title="this.$i18n.t('common.message.delete')"
        @on-ok="remove">
        <a href="javascript:void(0);">{{$t('common.btn.remove')}}</a>
      </Poptip>
      <a @click="edit" href="javascript:void(0);" class="node-action" v-if="showActions">{{$t('common.btn.edit')}}</a>
      <Poptip
        confirm
        class="node-action"
        v-if="showActions && menu.parent === '0'"
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
export default {
  name: 'menu-tree-node',
  props: {
    name: String,
    menu: Object,
    selected: Boolean
  },
  data () {
    return {
      showActions: false
    };
  },
  methods: {
    nodeClick () {
      this.$emit('nodeClick', this.menu);
    },
    remove () {
      this.$emit('remove', this.menu);
    },
    createRoot () {
      this.$emit('create', '0');
    },
    createChild () {
      this.$emit('create', this.menu.id);
    },
    edit () {
      this.$emit('edit', this.menu);
    }
  }
};
</script>
<style scoped>
  .node {
    display: inline-block;
    width: 95%;
    cursor: pointer;
    padding: 0 5px 3px 0px;
    position: relative;
  }

  .node:hover {
    background-color: #fcfcfc;
  }

  .node-selected {
    background-color: #d5e8fc;
  }

  .node-title {
    font-size: 13px;
    padding: 0 5px 3px 5px;
    color: #515a6e;
  }

  .node-actions {
    position: absolute;
    top: 2px;
    right: 0;
    z-index: 9999;
    background-color: rgba(255, 255, 255, .8);
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
    width: 300px!important;
  }
</style>
