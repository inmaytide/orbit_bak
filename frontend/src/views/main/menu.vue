<template>
  <div class="container">
    <div class="menu-log">
      Orbit
    </div>
    <div class="menus">
      <ul>
        <li v-for="menu in menus"
            v-bind:key="menu.id"
            v-bind:class="{active: active === menu.code}"
            @mouseover="menuMouseover" @mouseout="menuMouseout">
          <Icon :type="menu.icon" size="22" style="padding-right: 10px; margin-top: -5px;"/>
          <a :href="menu.url">{{displayName(menu)}}</a>
          <Icon v-if="menu.children.length > 0" type="ios-arrow-forward" class="menu-arrow-right" size="16"/>
          <div class="menu-child" v-if="menu.children.length > 0">
            <ul>
              <li v-for="submenu in menu.children" v-bind:key="submenu.id" v-on:click="to(submenu)">
                {{displayName(submenu)}}
              </li>
            </ul>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>
<script>
import api from '../../apis/menu';

export default {
  name: 'commons-menu',
  mounted () {
    this.$http.get(api.mineMenus.replace('{username}', this.$commons.getUser().username)).then(res => {
      this.menus = this.$commons.transform(res);
    });
  },
  props: {
    active: String
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
    },
    menuMouseover (e) {
      const menu = e.currentTarget;
      const childMenu = menu.querySelector('.menu-child');
      childMenu.style.display = 'block';
      const mtop = menu.offsetTop;
      if (mtop > 500) {
        childMenu.style.top = 'auto';
        childMenu.style.bottom = 0;
      }
    },
    menuMouseout (e) {
      const menu = e.currentTarget;
      const childMenu = menu.querySelector('.menu-child');
      childMenu.style.display = 'none';
    },
    to (menu) {
      this.$router.push(menu.url);
    }
  }
};
</script>
<style scoped>
  a {
    color: unset;
    text-decoration: none;
  }

  .container {
    width: 180px;
    height: 100%;
    background-color: #515a6e;
  }

  .menu-log {
    font-family: Papyrus, Verdana, "Lucida Grande", Arial, sans-serif;
    font-size: 26px;
    text-align: left;
    color: white;
    font-weight: bold;
    letter-spacing: 1px;
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
    height: 46px;
    line-height: 46px;
    font-size: 14px;
    user-select: none;
    color: #AFB3B6;
    cursor: pointer;
  }

  .menus>ul>li:hover {
    color: white;
  }

  .menus>ul>li:hover:after {
    border: 8px solid transparent;
    border-right: 8px solid white;
    width: 0;
    height: 0;
    position: absolute;
    top: 16px;
    right: 0;
    content: " ";
  }

  .menus>ul>li.active {
    background-color: #2D8CF0 !important;
    color: white;
  }

  .menu-arrow-right {
    position: absolute;
    right: 15px;
    top: 16px;
  }

  .menu-child {
    display: none;
    z-index: 999;
    position: absolute;
    top: 0;
    left: 180px;
    width: 160px;
    background-color: white;
    box-shadow: 0 1px 6px rgba(0, 0, 0, .3);
  }

  .menu-child>ul>li {
    height: 40px;
    line-height: 40px;
    color: #515a6e;
    white-space: nowrap;
  }

  .menu-child>ul>li:hover {
    background-color: #2db7f5;
    color: white;
    position: relative;
    padding-left: 10px;
  }
</style>
