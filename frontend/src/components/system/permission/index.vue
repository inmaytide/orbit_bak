<template>
  <div class="wrapper">
    <Breadcrumb class="breadcrumb">
        <BreadcrumbItem>Home</BreadcrumbItem>
        <BreadcrumbItem>System</BreadcrumbItem>
        <BreadcrumbItem>Permission</BreadcrumbItem>
    </Breadcrumb>
    <div class="list">
      <zk-table
        :columns="columns"
        :data="data"
        :selection-type="false"
        :tree-type="true"
        :expand-type="false"
        :border="true"
        row-class-name="line"></zk-table>
    </div>
  </div>
</template>
<style>
@import url('../../../../static/css/content.css');

.line {
  padding: 3px 5px!important;
  height: 25px!important;
}

.zk-table__cell-inner {
  padding: 5px 10px!important;
}
.zk-checkbox-wrapper {
  padding-left: 8px;
}
.zk-icon-plus-square-o:before {
  content: '\E633'!important;
}
.zk-icon-minus-square-o:before {
  content: '\E635'!important;
}
</style>

<script>
import PermissionService from './permission.service'
export default {
  name: 'PermissionIndex',
  created: function () {
    this.service = new PermissionService()
    this.service.getData()
      .then(res => {
        this.data = res
      })
      .catch(err => {
        console.log(err)
      })
  },
  data () {
    return {
      columns: [
        {label: 'Name', prop: 'name', width: '25%'},
        {label: 'Code', prop: 'code', width: '20%'},
        {label: 'Category', prop: 'category', width: '10%'},
        {label: 'Operation', type: 'template', width: '45%'}
      ],
      data: [{name: '123123', code: '213213123', category: 'BUTTON', children: [{name: '123123', code: '213213123', category: 'BUTTON'}]}, {name: '123123', code: '213213123', category: 'BUTTON'}]
    }
  }
}
</script>
