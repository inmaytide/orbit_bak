<template>
  <div class="login-body">
    <div class="login-wapper">
      <div class="login-title">Orbit&nbsp;Login</div>
      <div class="login-form">
        <Form ref="token" :model="token" :rules="rules">
          <FormItem prop="username">
            <i-input v-model="token.username" size="large" :maxlength="16" icon="person" />
          </FormItem>
          <FormItem prop="password">
            <i-input type="password" v-model="token.password" size="large" :maxlength="16" icon="ios-locked-outline" />
          </FormItem>
          <FormItem prop="captcha">
            <Row>
              <i-col span="15">
                <i-input v-model="token.captcha" size="large" :maxlength="6" icon="image" />
              </i-col>
              <i-col span="9">
                <img :src="captchaImagePage" class="login-captcha-image" v-on:click="getCaptcha()"/>
              </i-col>
            </Row>
          </FormItem>
          <Button type="primary" :loading="loading" @click="login" long>
            <span v-if="!loading">Login</span>
            <span v-else>Loading...</span>
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
import LoginService from './login.service'

export default {
  name: 'Login',
  created () {
    this.loginService = new LoginService()
    this.getCaptcha()
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
          { required: true, message: this.$i18n.t('login.error.username.empty'), trigger: 'blur' }
        ],
        password: [
          { required: true, message: this.$i18n.t('Please input your password'), trigger: 'blur' },
          { type: 'string', min: 6, message: this.$i18n.t('login.error.password.len'), trigger: 'blur' }
        ],
        captcha: [
          { required: true, message: this.$i18n.t('login.error.captcha.empty'), trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    login () {
      this.$refs['token'].validate()
        .then(valid => {
          if (valid) {
            this.loading = true
            this.loginService.login(this.token)
              .then(res => {
                console.log(res)
              })
              .catch(error => {
                this.loading = false
                this.getCaptcha()
                if (error.response && error.response.data.error_description === 'Bad captcha') {
                  this.$Message.error('Bad captcha')
                }
              })
          }
        })
    },
    getCaptcha () {
      this.loginService.getCaptcha()
        .then(res => {
          this.token.captcha = ''
          this.captchaImagePage = 'data:image/jpeg;base64,' + res
        })
        .catch(error => {
          console.log(error)
        })
    }
  }
}
</script>
