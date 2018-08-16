<template>
  <div class="container-search-bar">
    <div style="width: 70%;" class="basic-conditions">
      {{$t('common.label.keyword')}}&nbsp;
      <Input v-model="conditions.keyword" :placeholder="placeholder" style="width: 35%;margin-right: 40px;"/>
      {{$t('common.label.org')}}&nbsp;
      <Select v-model="conditions.organization" style="width:30%;">
      </Select>
    </div>
    <div class="actions">
      <a>
        <Icon type="ios-arrow-down"/>
        {{$t('common.btn.advanced_search')}}</a>
      <Button shape="circle" type="info" @click="search">{{$t('common.btn.search')}}</Button>
      <Button shape="circle" @click="reset">{{$t('common.btn.reset')}}</Button>
    </div>
  </div>
</template>
<script>
export default {
  name: 'search-bar',
  props: {
    placeholder: String
  },
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
    width: 100%;
    height: 50px;
    background-color: white;
    border-top-left-radius: 3px;
    border-bottom-left-radius: 3px;
    border-left: 3px solid #2db7f5;
    display: flex;
    justify-content: space-between;
    line-height: 50px;
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
    width: 80px;
    margin-right: 10px;
  }

  .actions a {
    margin-right: 15px;
  }
</style>
