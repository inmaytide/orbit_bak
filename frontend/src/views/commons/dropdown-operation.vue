<template>
  <Dropdown style="margin-left: 10px;height: 32px; line-height: 32px;" trigger="click" placement="bottom-end">
    <a href="javascript:void(0)" style="font-size: 14px;">
      {{name}}
      <Icon type="ios-arrow-down"></Icon>
    </a>
    <DropdownMenu slot="list">
      <DropdownItem>{{$t('common.menu.header.profile')}}</DropdownItem>
      <DropdownItem>{{$t('common.menu.header.change_password')}}</DropdownItem>
      <DropdownItem divided>{{$t('common.menu.header.sign_out')}}</DropdownItem>
    </DropdownMenu>
  </Dropdown>
</template>
<script>
export default {
  name: 'commons-dropdown-operation',
  data () {
    return {
      name: this.$commons.getUser().name
    };
  }
};
</script>
