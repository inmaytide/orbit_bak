<template>
  <div style="width: 200px; height: 100%;">
    <div class="menu-log">
      Orbit
    </div>
    <div class="menus">
      <ul>
        <li v-for="menu in menus" v-bind:key="menu.id" v-if="menu.parent === '0'">
          <Icon :type="menu.icon" size="24" style="padding-right: 10px;"/>
          {{displayName(menu)}}
          <Icon type="ios-arrow-forward" class="menu-arrow-right" size="16"/>
        </li>
      </ul>
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
    height: 70px;
  }

  .menus {
    height: calc(100% - 70px);
    width: 100%;
  }

  .menus ul {
    list-style: none;
  }

  .menus li {
    position: relative;
    padding-left: 10px;
    color: white;
    height: 60px;
    line-height: 60px;
    font-size: 14px;
    user-select: none;
  }

  .menus li:hover, .menus li:hover {
    background-color: #808695!important;
    cursor: pointer;
  }

  .menu-arrow-right {
    position: absolute;
    right: 5px;
    top: 23px;
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
