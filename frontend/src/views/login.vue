<template>
  <div class="login-body">
    <div class="login-wapper">
      <div class="login-title">Orbit&nbsp;Login</div>
      <div class="login-form">
        <Form ref="token" :model="token" :rules="rules">
          <FormItem prop="username">
            <i-input v-model="token.username" size="large" :maxlength="16" icon="md-person"
                     :title="$t('login.title.username')"/>
          </FormItem>
          <FormItem prop="password">
            <i-input type="password" v-model="token.password" size="large" :maxlength="16" icon="ios-lock-outline"
                     :title="$t('login.title.password')"/>
          </FormItem>
          <FormItem prop="captcha">
            <Row>
              <i-col span="15">
                <i-input v-model="token.captcha" size="large" :maxlength="6" icon="md-image"
                         :title="$t('login.title.captcha')"/>
              </i-col>
              <i-col span="9">
                <img :src="captcha.image" class="login-captcha-image" v-on:click="refreshCaptcha()"/>
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
  .login-body {
    height: 100%;
    width: 100%;
    background: url('../assets/images/login/background.jpg') no-repeat;
    background-size: cover;
    display: flex;
    justify-content: center;
    align-items: center;
    font-family: '微软雅黑', Arial, Helvetica, sans-serif;
  }

  .login-wapper {
    background-color: rgba(51, 51, 51, .3);
    width: 380px;
    height: 350px;
    margin-top: -20px;
    border-radius: 10px;
  }

  .login-title {
    font-family: Papyrus, Verdana, "Lucida Grande", Arial, sans-serif;
    font-size: 30px;
    text-align: center;
    color: white;
    font-weight: bold;
    margin: 35px auto 25px auto;
    letter-spacing: 1px;
  }

  .login-form {
    padding: 0 35px;
  }

  .login-captcha-image {
    height: 36px;
    float: right;
    border-radius: 5px;
    border: 1px solid #dddee1;
  }
</style>
<script>
import commons from '../utils/commons';
import api from '../apis/login';

export default {
  name: 'Login',
  mounted () {
    this.refreshCaptcha();
  },
  data () {
    return {
      loading: false,
      captcha: {
        name: '',
        image: null
      },
      token: {
        username: '',
        password: '',
        captcha: '',
        grant_type: 'password',
        client_id: 'apps',
        scope: 'all',
        client_secret: '59a84cbf83227a35'
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
      this.$refs['token'].validate().then((valid) => {
        if (valid) {
          this.loading = true;
          let data = new FormData();
          const config = {
            headers: {
              'x-captcha-name': this.captcha.name
            }
          };
          Object.keys(this.token).forEach(k => data.append(k, this.token[k]));
          this.$http.post(api.login, data, config).then(res => {
            commons.storeUser(res);
            this.$router.push('index');
          }).catch(() => {
            this.refreshCaptcha();
            this.loading = false;
          });
        }
      });
    },
    refreshCaptcha () {
      this.$http.get(api.captcha)
        .then(res => {
          this.token.captcha = '';
          this.captcha.image = 'data:image/jpeg;base64,' + res.image;
          this.captcha.name = res.captchaName;
        });
    }
  }
};
</script>
