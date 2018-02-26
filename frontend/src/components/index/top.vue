<template>
  <div class="header">
    <div class="header-logo">
        <Icon type="social-freebsd-devil" color="white" size="30"></Icon>
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
</template>
<script>
import commons from '../../commons'
export default {
  name: 'IndexTop',
  data () {
    return {
      name: commons.getProfile().name
    }
  }
}
</script>
