<template>
  <div>
    <div class="header-nav">
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
      <div style="height: 60px; line-height: 60px;">
        <commons-dropdown-message />
        <commons-dropdown-operation />
      </div>
    </div>
    <commons-breadcrumb />
  </div>
</template>
<script>
import commonsBreadcrumb from './breadcrumb';
import commonsDropdownMessage from './dropdown-message';
import commonsDropdownOperation from './dropdown-operation';

export default {
  name: 'commons-header',
  components: {
    commonsBreadcrumb,
    commonsDropdownMessage,
    commonsDropdownOperation
  },
  watch: {
    '$route' (to) {
      console.log(to);
    }
  }
};
</script>

<style scoped>
  .header-nav {
    display: flex;
    justify-content: space-between;
    background-color: white;
    border-bottom: 1px solid #F0F0F0;
    padding-right: 20px;
  }
</style>
