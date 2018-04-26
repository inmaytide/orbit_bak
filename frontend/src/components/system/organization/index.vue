<template>
  <div class="wrapper">
    <Breadcrumb class="breadcrumb">
        <BreadcrumbItem>{{$t('menu.home')}}</BreadcrumbItem>
        <BreadcrumbItem>{{$t('menu.system')}}</BreadcrumbItem>
        <BreadcrumbItem>{{$t('menu.system.organization')}}</BreadcrumbItem>
    </Breadcrumb>
    <div class="toolbar">
      <Button type="primary" icon="plus" size="small" @click="add()">{{$t('common.func.add')}}</Button>
    </div>
    <div class="list">
      <m-tree-grid
        :columns="columns"
        :checkbox="true"
        :data="list">
      </m-tree-grid>
    </div>
  </div>
</template>
<style>
@import url('../../../../static/css/content.css');
</style>
<script>
import {OrganizationService} from './organization.service'
export default {
  name: 'OrganizationIndex',
  created: function () {
    this.service = new OrganizationService()
    this.refresh()
  },
  data () {
    return {
      columns: [
        {label: this.$i18n.t('organization.column.code'), prop: 'code', width: '200'},
        {label: this.$i18n.t('organization.column.name'), prop: 'name', width: '200'},
        {label: this.$i18n.t('organization.column.category'), prop: 'category', width: '200'},
        {label: this.$i18n.t('common.func.operations'), prop: 'op', width: '200'}
      ],
      list: []
    }
  },
  methods: {
    refresh: function () {
      this.service.getData().then(res => (this.list = res))
    }
  }
}
</script>
