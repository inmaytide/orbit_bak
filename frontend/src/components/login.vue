<template>
  <div class="login-body">
    <div class="login-wapper">
      <div class="login-title">Orbit&nbsp;Login</div>
      <div class="login-form">
        <Form ref="token" :model="token" :rules="rules">
          <FormItem prop="username">
            <i-input v-model="token.username" size="large" :maxlength="16" icon="person"
                     :title="$t('login.title.username')"/>
          </FormItem>
          <FormItem prop="password">
            <i-input type="password" v-model="token.password" size="large" :maxlength="16" icon="ios-locked-outline"
                     :title="$t('login.title.password')"/>
          </FormItem>
          <FormItem prop="captcha">
            <Row>
              <i-col span="15">
                <i-input v-model="token.captcha" size="large" :maxlength="6" icon="image"
                         :title="$t('login.title.captcha')"/>
              </i-col>
              <i-col span="9">
                <img :src="captchaImagePage" class="login-captcha-image" v-on:click="getCaptcha()"/>
              </i-col>
            </Row>
          </FormItem>
          <Button type="primary" :loading="loading" @click="login" long size="large">
            <span v-if="!loading">{{ $t('login.btn.login') }}</span>
            <span v-else>{{ $t('common.btn.loading') }}</span>
          </Button>
        </Form>
      </div>
    </div>
  </div>
</template>
<style scoped>
  @import '../../static/css/login.css';
</style>
<script>
import LoginService from './login.service';
import commons from '../commons';

export default {
  name: 'Login',
  created () {
    this.loginService = new LoginService();
    this.getCaptcha();
  },
  data () {
    return {
      loading: false,
      captchaImagePage: '',
      token: {
        username: '',
        password: '',
        captcha: ''
      },
      rules: {
        username: [
          {required: true, message: this.$i18n.t('login.error.empty.username'), trigger: 'blur'}
        ],
        password: [
          {required: true, message: this.$i18n.t('login.error.empty.password'), trigger: 'blur'},
          {type: 'string', min: 6, message: this.$i18n.t('login.error.len.password'), trigger: 'blur'}
        ],
        captcha: [
          {required: true, message: this.$i18n.t('login.error.empty.captcha'), trigger: 'blur'}
        ]
      }
    };
  },
  methods: {
    login () {
      this.$refs['token'].validate()
        .then(valid => {
          if (valid) {
            this.loading = true;
            this.loginService.login(this.token)
              .then(res => {
                commons.storeUser(res);
                this.$router.push('index');
              })
              .catch(error => {
                console.log(error);
                this.$Message.error(this.getLoginFailedMessage(error));
                this.getCaptcha();
                this.loading = false;
              });
          }
        });
    },
    getCaptcha () {
      this.loginService.getCaptcha()
        .then(res => {
          this.token.captcha = '';
          this.captchaImagePage = 'data:image/jpeg;base64,' + res;
        });
    },
    getLoginFailedMessage (error) {
      let key = 'login.error.other';
      if (error.response && error.response.data.error_description === 'Bad captcha') {
        key = 'login.error.wrong.captcha';
      } else if (error.response && error.response.data.error_description === 'Bad credentials') {
        key = 'login.error.wrong.user';
      } else if (error.response && error.response.data.error_description === 'Locked account') {
        key = 'login.error.locked';
      }
      return this.$i18n.t(key);
    }
  }
};
</script>
