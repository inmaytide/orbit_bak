<template>
  <div class="node">
    <span @click="click" :class="{'node-selected': isSelected, 'node-title': true, 'node-title-root': org.id === '0'}">{{name}}</span>
    <div class="node-actions" v-if="isSelected && status === $store.state.enums.FORM_STATUS_CHECK">
      <Poptip
        confirm
        class="node-action"
        v-if="org.children.length === 0 && org.id !== '0'"
        :title="this.$i18n.t('common.message.delete')"
        @on-ok="remove">
        <a href="javascript:void(0);" style="color: red;">{{$t('common.btn.remove')}}</a>
      </Poptip>
      <a href="javascript:void(0);" class="node-action" v-if="org.id !== '0'">{{$t('common.btn.edit')}}</a>
      <a href="javascript:void(0);" class="node-action">{{$t('common.btn.new')}}</a>
    </div>
  </div>
</template>
<script>
export default {
  name: 'menu-tree-node',
  props: {
    name: String,
    org: Object,
    isSelected: Boolean,
    status: String
  },
  methods: {
    click () {
      this.$emit('nodeClick', this.org);
    },
    remove () {

    }
  },
  data () {
    return {};
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

  .node-title-root {
    font-weight: bold;
  }

  .node-actions {
    position: absolute;
    top: 2px;
    right: 10px;
    z-index: 9999;
    padding-right: 5px;
  }

  .node-action {
    float: right;
    margin-left: 5px;
    font-size: 12px;
    background-color: #FFFFFF;
  }
</style>
