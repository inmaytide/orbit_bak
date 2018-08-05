<template>
  <Menu mode="horizontal" active-name="home">
    <MenuItem name="home">
      {{$t('common.menu.header.home')}}
    </MenuItem>
    <MenuItem name="tasks">
      {{$t('common.menu.header.tasks')}}
    </MenuItem>
    <MenuItem name="favorites">
      {{$t('common.menu.header.favorites')}}
    </MenuItem>
  </Menu>
</template>
<script>
export default {
  name: 'commons-header'
};
</script>
