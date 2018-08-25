<template>
  <div>
    <CellGroup @on-click="select">
      <Cell>
        <a slot="extra" style="padding: 1px 5px;">{{$t('common.btn.new')}}</a>
      </Cell>
      <Cell :selected="selected === element.id"
            v-for="element in elements"
            :key="element.id"
            :title="element.name"
            :label="element.code"
            :name="element.id">
        <a v-if="selected === element.id" slot="extra" style="padding: 1px 5px; color: red;">{{$t('common.btn.remove')}}</a>
      </Cell>
    </CellGroup>
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
      elements: [],
      selected: ''
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
    },
    select (id) {
      console.log(id);
      this.selected = id;
    }
  },
  mounted () {
    this.refresh();
  }
};
</script>
