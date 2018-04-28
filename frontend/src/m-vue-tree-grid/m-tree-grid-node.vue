<template>
  <tbody>
    <tr class="ivu-table-row">
      <td class="ivu-table-column-center" v-if="checkbox" style="width: 55px;">
        <div class="ivu-table-cell">
          <Checkbox v-model="data.checked" @on-change="checkedChanged()"/>
        </div>
      </td>
      <td>
        <div class="ivu-table-cell ivu-table-cell-with-expand" style="text-align:left;padding-left: 10px;">
          <div class="ivu-table-cell-expand" :class="expand ? 'ivu-table-cell-expand-expanded' : ''"  :style="{'margin-left': level * 10 + 'px', 'margin-right': '7px', 'display':'inline-block'}">
            <i v-if="!loading" class="ivu-icon ivu-icon-ios-arrow-right" @click="expand = true"></i>
            <i v-if="loading" class="ivu-icon ivu-icon-load-a"></i>
          </div>
          <span style="display:inline-block;">{{data[columns[0].prop]}}</span>
        </div>
      </td>
      <td v-for="n in columns.length - 1" :key="n">
          <div class="ivu-table-cell">{{data[columns[n].prop]}}</div>
      </td>
    </tr>
    <m-tree-grid-node></m-tree-grid-node>
  </tbody>
</template>
<style>
@keyframes loading {
    0% {
        transform: rotate(0deg) scale(1,1);
    }
    100% {
        transform: rotate(360deg) scale(1,1);
    }
}
.ivu-icon-load-a {
  animation: loading 0.5s infinite linear;
}
</style>

<script>
import axios from 'axios'
export default {
  name: 'MTreeGridNode',
  componentName: 'm-tree-grid-node',
  props: {
    columns: {
      type: Array,
      required: true
    },
    checkbox: {
      type: Boolean,
      required: true
    },
    data: {
      type: Object,
      required: true
    },
    level: {
      type: Number,
      default: 1
    },
    loading: {
      type: Boolean,
      default: false
    },
    async: {
      type: Boolean,
      default: false
    },
    asyncApi: {
      type: String
    }
  },
  watch: {
    expand: function (value) {
      if (value && this.async) {
        this.loading = true
        axios.get(this.asyncApi + '?parent=' + this.data['id'])
          .then(res => {
            this.data.children = res
            this.loading = false
          })
      }
    }
  },
  data () {
    return {
      expand: false
    }
  },
  methods: {
    checkedChanged: function () {
      this.$emit('checkedChange', this.data.checked)
    }
  }
}
</script>
