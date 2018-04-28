<template>
  <div class="ivu-table-wrapper">
    <div class="ivu-table ivu-table-border ivu-table-small">
      <div class="ivu-table-header">
        <table cellpadding="0" cellspacing="0" border="0" :style="{width: width}">
          <thead>
            <tr>
              <th class="ivu-table-column-center" v-if="checkbox" style="width: 55px;">
                <div class="ivu-table-cell">
                  <Checkbox :title="selectAllTitle" :inline="true" v-model="isCheckedAll" @on-change="checkedAll()"></Checkbox>
                </div>
              </th>
              <th v-for="(col, index) in columns" v-bind:key="index">
                <div class="ivu-table-cell"><span>{{col.label}}</span></div>
              </th>
            </tr>
          </thead>
        </table>
      </div>
      <div class="ivu-table-body">
        <table cellspacing="0" cellpadding="0" border="0" :style="{width: width}">
          <tbody class="ivu-table-tbody">
            <tr class="ivu-table-row" v-if="this.items_.length == 0">
              <td :colspan="this.colspan" class="ivu-table-column-center">
                <div class="ivu-table-cell"><span>{{noDataText}}</span></div>
              </td>
            </tr>
            <m-tree-grid-node
              v-for="(inst, index) in items_"
              :key="index"
              :checkbox="checkbox"
              :columns="columns"
              :data="inst"
              :async="async"
              :asyncApi="asyncApi"
              v-on:checkedChange="itemCheckedChange">
            </m-tree-grid-node>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>
<style>
@import './css/style.css';
</style>
<script>
import MTreeGridNode from './m-tree-grid-node'
import axios from 'axios'
export default {
  name: 'MTreeGrid',
  componentName: 'm-tree-grid',
  components: {
    MTreeGridNode: MTreeGridNode
  },
  props: {
    width: {
      type: String,
      default: '100%'
    },
    columns: {
      type: Array
    },
    noDataText: {
      type: String,
      default: 'No Data'
    },
    selectAllTitle: {
      type: String,
      default: 'Select All'
    },
    checkbox: {
      type: Boolean,
      default: false
    },
    items: {
      type: Array,
      default: function () {
        return []
      }
    },
    async: {
      type: Boolean,
      default: false
    },
    asyncApi: {
      type: String,
      required: this.async
    }
  },
  created () {
    if (this.items.length === 0 && this.asyncApi == null) {
      throw new Error()
    }
    if (this.async && this.asyncApi == null) {
      throw new Error()
    }

    if (this.items.length === 0) {
      axios.get(this.asyncApi).then(this.initItems)
    } else {
      this.initItems(this.items)
    }
  },
  data () {
    return {
      colspan: this.columns.length + (this.checkbox ? 1 : 0),
      isCheckedAll: false,
      items_: []
    }
  },
  methods: {
    checkedAll: function () {
      this.items_.forEach(inst => {
        inst.checked = this.isCheckedAll
      })
    },
    itemCheckedChange: function (itemChecked) {
      if (!itemChecked) {
        this.isCheckedAll = false
      }
    },
    initItems: function (items) {
      this.items_ = items
      if (this.items_.length > 0) {
        if (this.checkbox) {
          this.items_ = this.items_.map(item => {
            return Object.assign({}, item, {
              checked: this.isCheckedAll
            })
          })
        }
      }
    }
  }
}
</script>
