<template>
  <div class="container-center" style="height: calc(100% - 10px);">
    <user-search-bar @search="search"/>
    <div class="table-container list-users">
      <div class="table-actions" style="padding-bottom: 10px;">
        <Button type="primary" shape="circle" size="small" icon="md-person-add" title="New" @click="$router.push('/system/users/-1')">{{$t('common.btn.new')}}</Button>
      </div>
      <Table stripe
             border
             size="small"
             :columns="columns"
             :data="pager.list"/>
      <div style="display: flex; justify-content: flex-end; padding-top: 10px;">
        <Page :total="pager.total"
              show-elevator
              show-sizer
              show-total
              size="small"
              :page-size-opts="[10, 20, 50, 100]"/>
      </div>
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
      pager: [],
      columns: [
        {
          title: this.$i18n.t('system.user.properties.status'),
          width: 110,
          render: (h, params) => {
            return h('div', {
              class: {
                'user-status': true,
                'user-status-1': params.row.status === 1,
                'user-status-2': params.row.status === 2,
                'user-status-3': params.row.status === 3,
                'user-status-99': params.row.status === 99
              }
            }, this.getStatusLable(params.row.status));
          }
        }, {
          title: this.$i18n.t('system.user.properties.username'),
          render: (h, params) => {
            return h('a', {
              attrs: {
                href: 'javascript:void(0)'
              },
              on: {
                click: () => {
                  this.$router.push(`/system/users/${params.row.id}`);
                }
              }
            }, params.row.username);
          }
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
          title: this.$i18n.t('system.user.properties.qq'),
          key: 'qq'
        }, {
          title: this.$i18n.t('system.user.properties.wechat'),
          key: 'wechat'
        }
      ]
    };
  },
  methods: {
    getStatusLable (value) {
      const arr = this.$store.state.enums.user.status.filter(element => element.value === value);
      return arr.length === 0 ? '' : this.$i18n.t('system.user.status.' + arr[0].label);
    },
    search (conditions) {
      this.conditions = Object.assign(this.conditions, {
        pageNumber: 1,
        ...conditions
      });
      this.refresh();
    },
    refresh () {
      this.$http.get(api.list, this.conditions).then(res => {
        this.pager = res;
      });
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
<style>

  .list-users .ivu-table-wrapper .ivu-table-body tr td:first-child .ivu-table-cell {
    padding: 0;
  }

  .user-status {
    color: white;
    border-radius: 5px;
    width: 80%;
    line-height: 25px;
    text-align: center;
    margin: 0 auto;
  }

  .user-status-1 {
    background-color: lightseagreen;
  }

  .user-status-2, .user-status-3 {
    background-color:#FD571F;
    color: white;
  }

  .user-status-99 {
    background-color: #ddd;
    color: white;
  }
</style>
