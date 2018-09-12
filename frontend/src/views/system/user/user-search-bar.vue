<template>
  <div class="container-search-bar">
    <div style="width: 70%;" class="basic-conditions">
      {{$t('common.label.keyword')}}&nbsp;
      <Input v-model="conditions.keyword"
             :placeholder="$i18n.t('system.user.search.keyword-placeholder')"
             style="width: 35%;margin-right: 40px;"/>
      {{$t('common.label.org')}}&nbsp;
      <Select v-model="conditions.organization"
              style="width:30%;">
      </Select>
    </div>
    <div class="actions">
      <Button shape="circle" type="info" @click="search">{{$t('common.btn.search')}}</Button>
      <Button shape="circle" @click="reset">{{$t('common.btn.reset')}}</Button>
    </div>
  </div>
</template>
<script>
export default {
  name: 'user-search-bar',
  data () {
    return {
      conditions: {
        keyword: '',
        organization: ''
      }
    };
  },
  methods: {
    search () {
      this.$emit('search', this.conditions);
    },
    reset () {
      this.conditions = {
        keyword: '',
        organization: ''
      };
      this.search();
    }
  }
};
</script>
<style scoped>
  .container-search-bar {
    width: calc(100%- 20px);
    height: 45px;
    background-color: white;
    border-top-left-radius: 3px;
    border-bottom-left-radius: 3px;
    border-left: 3px solid #2db7f5;
    display: flex;
    justify-content: space-between;
    line-height: 45px;
    margin: 10px;
  }

  .basic-conditions {
    width: 65%;
    padding-left: 30px;
  }

  .actions {
    width: 35%;
    text-align: right;
    padding-right: 20px;
  }

  .actions button {
    margin-right: 10px;
    padding: auto 10px;
  }

  .actions a {
    margin-right: 15px;
  }
</style>
