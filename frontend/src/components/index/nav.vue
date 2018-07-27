<template>
  <Menu theme="light" active-name="1">
        <Menu-item name="1">
        <Icon type="ios-speedometer"  size="16"/>
        Dashboard
        </Menu-item>
        <Submenu name="2">
        <template slot="title">
            <Icon type="ios-gear" size="16"></Icon>
            System
        </template>
        <Menu-item name="2-1" v-on:click.native="changeContent('/index/system/menu')">Permission</Menu-item>
        <Menu-item name="2-2" v-on:click.native="changeContent('/index/system/organization')">Organization</Menu-item>
        </Submenu>
    </Menu>
</template>
<script>
export default {
  name: 'IndexNav',
  methods: {
    changeContent (url) {
      this.$router.push(url);
    }
  }
};
</script>
