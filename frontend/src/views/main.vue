<template>
  <Layout>
    <Sider :width="240">
      <orbit-menu></orbit-menu>
    </Sider>
    <Layout>
      <Header></Header>
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
import orbitMenu from './menu/menu';

export default {
  components: {
    orbitMenu
  }
};
</script>
