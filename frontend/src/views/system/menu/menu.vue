<template>
  <div class="container-center">
    <search-bar @search="search" :placeholder="$t('system.menu.search_text_placeholder')"/>
  </div>
</template>
<script>
import searchBar from '../..//commons/searchbar';

export default {
  name: 'menus',
  components: {
    searchBar
  },
  methods: {
    search (conditions) {
      console.log(conditions);
    }
  }
};
</script>
