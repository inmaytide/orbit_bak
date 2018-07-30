<template>
  <Layout class="layout">
    <Header>
      <index-top />
    </Header>
    <Layout class="content">
      <Sider class="nav-warpper" width="240" :style="{background: '#fff', borderRight: '1px solid #DDDEE1'}">
        <index-nav />
      </Sider>
      <Content :style="{background: '#fff'}">
        <router-view></router-view>
      </Content>
    </Layout>
  </Layout>
</template>
<style>
@import url("../../assets/css/index.css");
</style>
<script>
import IndexTop from './top';
import IndexNav from './nav';
export default {
  name: 'Index',
  components: {
    'index-top': IndexTop,
    'index-nav': IndexNav
  }
};
</script>
