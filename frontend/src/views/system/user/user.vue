<template>
  <div class="container-center" style="height: calc(100% - 10px);">
    <user-search-bar />
    <div class="table-container">
      <Table :columns="columns"
             :data="listData"/>
    </div>
  </div>
</template>
<script>
import userSearchBar from './user-search-bar';
import api from '../../../apis/modules/system/user';

export default {
  name: 'users',
  components: {
    userSearchBar
  },
  data () {
    return {
      conditions: {
        pageSize: 10,
        pageNumber: 1
      },
      listData: [],
      columns: [
        {
          type: 'selection',
          width: 60
        }, {
          title: this.$i18n.t('system.user.properties.status'),
          key: 'status'
        }, {
          title: this.$i18n.t('system.user.properties.username'),
          key: 'username'
        }, {
          title: this.$i18n.t('system.user.properties.name'),
          key: 'name'
        }, {
          title: this.$i18n.t('system.user.properties.cellphone'),
          key: 'cellphone'
        }, {
          title: this.$i18n.t('system.user.properties.email'),
          key: 'email'
        }, {
          title: this.$i18n.t('common.label.op')
        }
      ]
    };
  },
  methods: {
    refresh () {
      this.$http.get(api.list, this.conditions).then(res => (this.listData = res));
    }
  },
  mounted () {
    this.refresh();
  }
};
</script>
<style scoped>
.table-container {
  padding: 10px;
  margin: 10px;
  background-color: white;
  height: calc(100% - 65px);
}
</style>
