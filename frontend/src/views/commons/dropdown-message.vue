<template>
  <Dropdown trigger="click" style="height: 32px; line-height: 32px;" placement="bottom-end">
    <Badge :count="100" overflow-count="99">
      <Avatar icon="ios-person" :src="avatar"/>
    </Badge>
    <DropdownMenu slot="list">
      <DropdownItem>You have a new message</DropdownItem>
    </DropdownMenu>
  </Dropdown>
</template>

<script>
export default {
  name: 'commons-dropdown-message',
  data () {
    return {
      avatar: 'https://tse3-mm.cn.bing.net/th?id=OIP.ft0RpajjsNrcu2aRsa00lAHaHa&w=214&h=214&c=7&o=5&dpr=2&pid=1.7'
    };
  }
};
</script>

<style scoped>
  .ivu-menu-item {
    padding: 0 40px;
  }
  .ivu-menu-horizontal.ivu-menu-light:after {
    background: none;
    height: 0;
  }
</style>
