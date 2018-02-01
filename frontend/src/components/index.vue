<template>
  <Layout class="layout">
    <Header>
      <div class="header">
        <div class="header-logo">
          <Icon type="social-freebsd-devil" color="white" size="50"></Icon>
        </div>
        <div class="header-title">Orbit Admin</div>
        <div class="header-profile">
          <Dropdown placement="bottom-end">
            <Badge count="3">
              <Avatar class="profile-avatar" icon="person" size="large"/>
            </Badge>
            <DropdownMenu slot="list">
              <DropdownItem>您有一个新的任务，请注意处理</DropdownItem>
            </DropdownMenu>
          </Dropdown>
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
      <Sider class="nav-warpper" width="240">
        <Menu theme="light" active-name="1">
          <Menu-item name="1">
            <Icon type="ios-speedometer"  size="16"/>
            Dashboard
          </Menu-item>
          <Submenu name="2">
            <template slot="title">
              <Icon type="ios-gear" size="16"></Icon>
              System Management
            </template>
            <Menu-item name="2-1">Permission Management</Menu-item>
          </Submenu>
        </Menu>
      </Sider>
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
