<template>
  <Menu theme="dark" :accordion="true">
    <Submenu name="system" >
      <template slot="title">
        <Icon type="ios-analytics" />
        Navigation One
      </template>
      <MenuGroup title="Item 1">
        <MenuItem name="1-1">Option 1</MenuItem>
        <MenuItem name="1-2">Option 2</MenuItem>
      </MenuGroup>
      <MenuGroup title="Item 2">
        <MenuItem name="1-3">Option 3</MenuItem>
        <MenuItem name="1-4">Option 4</MenuItem>
      </MenuGroup>
    </Submenu>
    <Submenu name="system1" >
      <template slot="title">
        <Icon type="ios-analytics" />
        123123123
      </template>
      <MenuItem name="system1-111">Option 1</MenuItem>
      <MenuItem name="system11-2323">Option 2</MenuItem>
    </Submenu>
  </Menu>
</template>
<script>

export default {
  name: 'orbit-menu',
  mounted () {
  },
  data () {
    return {};
  },
  methods: {}
};
</script>
