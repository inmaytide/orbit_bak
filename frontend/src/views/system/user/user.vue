<template>
  <div class="container-center">
    <user-search-bar />
    <Table></Table>
  </div>
</template>
<script>
import userSearchBar from './user-search-bar';

export default {
  name: 'users',
  components: {
    userSearchBar
  }
};
</script>
