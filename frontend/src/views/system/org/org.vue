<template>
  <div class="container-center">
    <div>
      <div class="block-title blue-left-border">{{$t('common.menu.nav.org')}}</div>
      <div class="block-content">
        <Tree :data="orgs" />
      </div>
    </div>
    <div>
      <div class="block-title blue-left-border">{{$t('common.title.info')}}</div>
      <div class="block-content">
        123123123
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'orgs',
  data () {
    return {
      orgs: []
    };
  }
};
</script>
<style scoped>
  .container-center {
    display: flex;
    justify-content: space-around;
    height: 100%;
  }

  .container-center > div {
    height: calc(100% - 20px);
    margin: 10px 0 10px 10px;
    border-top-left-radius: 3px;
    border-top-right-radius: 3px;
  }

  .container-center > div:nth-child(1) {
    width: 40%;
  }

  .container-center > div:last-child {
    width: 60%;
    margin-right: 10px;
  }

  .block-title {
    height: 40px;
    line-height: 40px;
    padding-left: 15px;
    border-radius: 3px;
    background-color: white;
  }

  .block-content {
    background-color: white;
    margin-top: 4px;
    padding: 1px 8px;
    height: calc(100% - 44px);
    border-radius: 3px;
  }
</style>
