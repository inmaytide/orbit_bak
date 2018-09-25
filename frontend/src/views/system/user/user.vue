<template>
  <div class="container-center" style="height: calc(100% - 10px);">
    <user-search-bar @search="search"/>
    <div class="table-container">
      <div class="table-actions" style="padding-bottom: 10px;">
        <Button type="primary" shape="circle" size="small" icon="md-person-add" title="New">{{$t('common.btn.new')}}</Button>
      </div>
      <Table stripe
             border
             size="small"
             :columns="columns"
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
    search (conditions) {
      this.conditions = Object.assign(this.conditions, {
        pageNumber: 1,
        ...conditions
      });
      this.refresh();
    },
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
