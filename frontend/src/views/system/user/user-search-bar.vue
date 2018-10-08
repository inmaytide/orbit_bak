<template>
  <div class="container-title-bar">
    <div class="conditions">
      {{$t('common.label.keyword')}}&nbsp;
      <Input v-model="conditions.keyword"
             :placeholder="$i18n.t('system.user.search.keyword-placeholder')"
             style="width: 30%;margin-right: 30px;"/>
      <select-organization :width="'45%'" style="margin-right: 30px;" v-model="conditions.organization"/>
      {{$t('system.user.properties.status')}}
      <Select v-model="conditions.status" style="width:20%;">
        <Option :value="'all'">{{$t('system.user.status.all')}}</Option>
        <Option :value="op.value" v-for="op in $store.state.enums.user.status" :key="op.value">
          {{$t('system.user.status.' + op.label)}}
        </Option>
      </Select>
    </div>
    <div class="container-title-bar-actions">
      <Button shape="circle" type="info" @click="search">{{$t('common.btn.search')}}</Button>
      <Button shape="circle" @click="reset">{{$t('common.btn.reset')}}</Button>
    </div>
  </div>
</template>
<script>
import SelectOrganization from '../../../components/select-organization';

export default {
  name: 'user-search-bar',
  components: {
    SelectOrganization
  },
  data () {
    return {
      conditions: {
        keyword: '',
        organization: '',
        status: 'all'
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
        organization: '',
        status: 'all'
      };
      this.search();
    }
  }
};
</script>
<style scoped>

  .conditions {
    width: 70%;
    padding-left: 30px;
    white-space: nowrap;
  }

</style>
