<template>
  <div class="node">
    <span @click="click" :class="{'node-selected': isSelected, 'node-title': true, 'node-title-root': org.id === '0'}">{{name}}</span>
    <div class="node-actions" v-if="isSelected && viewing">
      <Poptip
        confirm
        class="node-action"
        v-if="org.children.length === 0 && org.id !== '0'"
        :title="this.$i18n.t('common.message.delete')"
        @on-ok="remove">
        <a href="javascript:void(0);" style="color: red;">{{$t('common.btn.remove')}}</a>
      </Poptip>
      <a href="javascript:void(0);" class="node-action" v-if="org.id !== '0'">{{$t('common.btn.edit')}}</a>
      <a href="javascript:void(0);" class="node-action" @click="create">{{$t('common.btn.new')}}</a>
    </div>
  </div>
</template>
<script>
import form from '../../../utils/form';

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
      if (!this.viewing) {
        return;
      }
      this.$emit('nodeClick', this.org);
    },
    create () {
      this.$emit('create', this.org);
    },
    remove () {
      this.$emit('remove', this.org);
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
  }
</style>
