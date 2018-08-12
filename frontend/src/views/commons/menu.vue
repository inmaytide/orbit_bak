<template>
  <div style="width: 200px;">
    <div class="menu-log">
      Orbit
    </div>
    <div>
      <Menu theme="dark" :accordion="true" :width="'200'">
        <MenuItem v-for="menu in menus" v-bind:key="menu.id" v-if="menu.parent === '0'" name="menu.code">
          <Icon :type="menu.icon" size="20"/>
          {{displayName(menu)}}
          <Icon type="ios-arrow-forward" size="14" class="menu-arrow-right"/>
        </MenuItem>
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
    letter-spacing: 3px;
    padding: 20px 0 10px 30px;
    user-select: none;
  }

  .menu-arrow-right {
    position: absolute;
    right: 5px;
    top: 18px;
  }
</style>
<script>
import api from '../../apis/menu';

export default {
  name: 'commons-menu',
  mounted () {
    this.$http.get(api.mineMenus.replace('{username}', this.$commons.getUser().username)).then(res => {
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
