<template>
  <div>
    <ul>
      <li v-for="element in elements" :key="element.id">{{element.name}}</li>
    </ul>
  </div>
</template>
<script>
import api from '../../../apis/modules/system/func.js';

export default {
  name: 'functions',
  props: {
    menuId: String
  },
  data () {
    return {
      elements: []
    };
  },
  watch: {
    menuId () {
      this.refresh();
    }
  },
  methods: {
    refresh () {
      if (!this.$commons.isNull(this.menuId)) {
        this.$http.get(api.listByMenuId(this.menuId))
          .then(res => (this.elements = res));
      }
    }
  },
  mounted () {
    this.refresh();
  }
};
</script>
