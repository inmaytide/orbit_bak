<template>
  <Layout>
    <Sider :width="240">
      <commons-menu />
    </Sider>
    <Layout>
      <Header><commons-header /></Header>
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
import commonsMenu from './commons/menu';
import commonsHeader from './commons/header';

export default {
  components: {
    commonsMenu,
    commonsHeader
  }
};
</script>
