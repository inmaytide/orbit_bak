<template>
  <Layout class="layout">
    <Header>
      <div class="header">
        <div class="header-logo">
          <Icon type="social-freebsd-devil" color="white" size="50"></Icon>
        </div>
        <div class="header-title">Orbit Admin</div>
        <div class="header-profile">
          <Badge count="3">
            <Avatar class="profile-avatar" icon="person" size="large"/>
          </Badge>
          <Dropdown>
            <div class="profile-name">
              {{name}}
              <Icon type="arrow-down-b" size="12"></Icon>
            </div>
            <DropdownMenu slot="list">
              <DropdownItem>Profile</DropdownItem>
              <DropdownItem>Settings</DropdownItem>
              <DropdownItem divided>Exit</DropdownItem>
            </DropdownMenu>
          </Dropdown>
        </div>
      </div>
    </Header>
    <Layout class="content">
      <Sider hide-trigger class="nav-warpper">Sider</Sider>
      <Content>Content</Content>
    </Layout>
  </Layout>
</template>
<style>
@import url('../../static/css/index.css');
</style>
<script>
import commons from '../commons'
export default {
  name: 'Index',
  data () {
    return {
      name: commons.getProfile().name
    }
  }
}
</script>
