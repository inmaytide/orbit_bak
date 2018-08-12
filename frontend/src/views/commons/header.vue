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
        <Dropdown trigger="click" style="height: 32px; line-height: 32px;" placement="bottom-end">
          <Badge :count="100" overflow-count="99">
            <Avatar icon="ios-person" :src="avatar"/>
          </Badge>
          <DropdownMenu slot="list">
            <DropdownItem>You have a new message</DropdownItem>
          </DropdownMenu>
        </Dropdown>
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
      </div>
    </div>
    <Breadcrumb separator=">">
      <BreadcrumbItem v-for="item in breadcrambs" v-bind:key="item.path" :to="item.path">{{item.text}}</BreadcrumbItem>
    </Breadcrumb>
  </div>
</template>
<style scoped>
  .ivu-menu-item {
    padding: 0 40px;
  }

  .ivu-menu-horizontal.ivu-menu-light:after {
    background: none;
    height: 0;
  }

  .ivu-breadcrumb {
    height: 40px;
    line-height: 40px;
    background-color: white;
    padding-left: 20px;
    border-bottom: 1px #dcdee2 solid;
  }

  .header-nav {
    display: flex;
    justify-content: space-between;
    background-color: white;
    border-bottom: 1px solid #F0F0F0;
    padding-right: 20px;
  }
</style>
<script>
export default {
  name: 'commons-header',
  watch: {
    '$route' (to) {
      console.log(to);
    }
  },
  data () {
    return {
      name: this.$commons.getUser().name,
      avatar: 'https://tse3-mm.cn.bing.net/th?id=OIP.ft0RpajjsNrcu2aRsa00lAHaHa&w=214&h=214&c=7&o=5&dpr=2&pid=1.7',
      breadcrambs: [{text: this.$i18n.t('common.menu.header.home'), path: '/'}]
    };
  }
};
</script>
