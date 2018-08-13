<template>
  <Breadcrumb separator=">">
    <BreadcrumbItem v-for="item in breadcrambs" v-bind:key="item.path" :to="item.path">{{item.text}}</BreadcrumbItem>
  </Breadcrumb>
</template>

<script>
export default {
  name: 'commons-breadcrumb',
  data () {
    return {
      breadcrambs: [{text: this.$i18n.t('common.menu.header.home'), path: '/'}]
    };
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
