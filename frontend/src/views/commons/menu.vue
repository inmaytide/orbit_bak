<template>
  <div>
    <div class="menu-log">
      Orbit
    </div>
    <div>
      <Menu theme="dark" :accordion="true">
        <Submenu v-for="menu in menus" v-bind:key="menu.id" v-if="menu.parent === '0'" name="menu.code">
          <template slot="title">
            <Icon :type="menu.icon" size="18"/>
            {{displayName(menu)}}
          </template>
          <MenuItem v-for="submenu in menus" v-bind:key="submenu.id" v-if="menu.id === submenu.parent" name="submenu.code">
            {{displayName(submenu)}}
          </MenuItem>
        </Submenu>
      </Menu>
    </div>
  </div>
</template>
<style scoped>
  .menu-log {
    font-family: Papyrus, Verdana, "Lucida Grande", Arial, sans-serif;
    font-size: 26px;
    text-align: left;
    color: white;
    font-weight: bold;
    letter-spacing: 1px;
    padding: 20px 0 10px 15px;
    user-select: none;
  }
</style>
<script>
import api from '../../apis/menu';

export default {
  name: 'commons-menu',
  mounted () {
    this.$http.get(api.mineMenus).then(res => {
      this.menus = res;
    });
  },
  data () {
    return {
      menus: []
    };
  },
  methods: {
    displayName (menu) {
      let name = this.$i18n.t('common.menu.nav.' + menu.code);
      if (name === '' || name === menu.code) {
        name = menu.name;
      }
      return name;
    }
  }
};
</script>
