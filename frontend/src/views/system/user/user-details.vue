<template>
  <div class="container-center form-user">
    <div class="container-title-bar">
      <div class="container-title">{{$t('common.title.' + status)}}{{$t('system.user.label.detail')}}</div>
      <div class="container-title-bar-actions">
        <Button shape="circle" type="info" @click="search">{{$t('common.btn.save')}}</Button>
        <Button shape="circle" @click="back">{{$t('common.btn.back')}}</Button>
      </div>
    </div>
  </div>
</template>
<script>
import form from '../../../utils/form';
import api from '../../../apis/modules/system/user';

export default {
  name: 'user-details',
  data () {
    return {
      status: form.STATUS_VIEW,
      instance: {
        id: this.$route.params.id
      }
    };
  },
  methods: {
    get (id) {
      this.$http.get(api.get(id)).then(res => (this.instance = res));
    },
    init () {
      if (this.instance.id === '-1') {
        this.status = form.STATUS_CREATE;
        delete this.instance.id;
      } else {
        this.get(this.instance.id);
      }
    },
    back () {
      this.$router.back();
    }
  },
  created () {
    this.init();
  }
};
</script>
