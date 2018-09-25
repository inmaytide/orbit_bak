<template>
  <div style="display: inline-block;" :style="{width: width}" >
    <div class="label" v-if="showLabel">{{label}}</div>
    <Cascader v-model="value"
              style="width:80%; display: inline-block"
              :data="options"
              :change-on-select="true"
              :render-format="displayFormat"
              filterable/>
  </div>
</template>
<script>
import api from '../apis/modules/system/org';

export default {
  name: 'select-organization',
  props: {
    'show-paths': {type: Boolean, default: false},
    'show-label': {type: Boolean, default: true},
    label: {type: String, default: 'Organization'},
    width: {type: String, default: '300px;'}
  },
  methods: {
    load () {
      this.$http.get(api.common).then(res => (this.options = this.$commons.transformOptions(res)));
    },
    displayFormat (labels) {
      if (this.showPaths) {
        return labels.join('/');
      } else {
        return labels[labels.length - 1];
      }
    }
  },
  created () {
    this.load();
  },
  model: {
    prop: 'value',
    event: 'changed'
  },
  watch: {
    value () {
      if (this.value.length === 0) {
        this.$emit('changed', '');
      } else {
        this.$emit('changed', this.value[this.value.length - 1]);
      }
    }
  },
  data () {
    return {
      options: [],
      value: []
    };
  }
};
</script>
<style scoped>
  .label {
    white-space: nowrap;
    text-overflow: ellipsis;
    display: inline-block;
  }
</style>
