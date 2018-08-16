<template>
  <Layout>
    <Sider :width="'180'">
      <commons-menu />
    </Sider>
    <Layout>
      <Header style="padding-right: 0!important; height: 100px;"><commons-header /></Header>
      <Content>
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
  }
};
</script>
