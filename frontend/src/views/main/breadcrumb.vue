<template>
  <Breadcrumb separator=">">
    <BreadcrumbItem v-for="item in breadcrumbs" v-bind:key="item.path" :to="item.path">{{$t(item.text)}}</BreadcrumbItem>
  </Breadcrumb>
</template>

<script>
export default {
  name: 'commons-breadcrumb',
  props: {
    breadcrumbs: Array
  }
};
</script>

<style scoped>
  .ivu-breadcrumb {
    height: 40px;
    line-height: 40px;
    background-color: white;
    padding-left: 20px;
    border-bottom: 1px #dcdee2 solid;
  }
</style>
