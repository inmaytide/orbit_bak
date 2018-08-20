<template>
  <Layout>
    <Sider :width="'180'">
      <commons-menu :active="active"/>
    </Sider>
    <Layout>
      <Header style="padding-right: 0!important; height: 100px;">
        <commons-header :active="active" :breadcrumbs="breadcrumbs" />
      </Header>
      <Content style="height: calc(100% - 100px);">
        <router-view></router-view>
      </Content>
    </Layout>
  </Layout>
</template>
<style scoped>
  .ivu-layout {
    height: 100%;
    width: 100%;
  }
</style>
<script>
import commonsMenu from './menu';
import commonsHeader from './header';

export default {
  components: {
    commonsMenu,
    commonsHeader
  },
  data () {
    return {
      active: this.$route.meta.active,
      breadcrumbs: this.$route.meta.breadcrumbs
    };
  },
  watch: {
    '$route' () {
      this.breadcrumbs = this.$route.meta.breadcrumbs;
      this.active = this.$route.meta.active;
    }
  }
};
</script>
