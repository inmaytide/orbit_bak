<template>
  <div class="login-body">
    <div class="login-wapper">
      <div class="login-title">Orbit&nbsp;Login</div>
      <div class="login-form">
        <Form :model="token" :rules="rules">
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
                <img :src="captchaImagePage" class="login-captcha-image" v-on:click="getCaptche()"/>
              </i-col>
            </Row>
          </FormItem>
          <Button type="primary" :loading="loading" @click="toLoading" long>
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
export default {
  name: 'Login',
  created () {
    this.getCaptche()
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
          { required: true, message: 'Please fill in the user name', trigger: 'blur' }
        ],
        password: [
          { required: true, message: 'Please fill in the password.', trigger: 'blur' },
          { type: 'string', min: 6, message: 'The password length cannot be less than 6 bits', trigger: 'blur' }
        ],
        captcha: [
          { required: true, message: 'Please fill in the user name', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    toLoading () {
      this.loading = true
    },
    getCaptche () {
      this.$http.get('http://127.0.0.1:7001/captcha').then(
        response => {
          this.captchaImagePage = 'data:image/jpeg;base64,' + response.body.image
          console.log(response.body)
        },
        response => {
          console.log(response)
        }
      )
    }
  }
}
</script>
