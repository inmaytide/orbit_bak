<template>
  <div class="login-body">
    <div class="login-wapper">
      <div class="login-title">Orbit Login</div>
      <div class="login-form">
        <Form :model="token" :rules="rules">
          <FormItem prop="username">
            <i-input v-model="token.username" size="large" :maxlength="16">
              <Icon type="person" slot="prepend" style='width: 22px;'></Icon>
            </i-input>
          </FormItem>
          <FormItem prop="password">
            <i-input type="password" v-model="token.password" size="large" :maxlength="16">
              <Icon type="ios-locked-outline" slot="prepend" style='width: 22px;'></Icon>
            </i-input>
          </FormItem>
          <FormItem prop="captcha">
            <i-input v-model="token.captcha" size="large" :maxlength="6">
              <Icon type="image" slot="prepend" style='width: 22px;'></Icon>
            </i-input>
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
  data () {
    return {
      loading: false,
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
        ]
      }
    }
  },
  methods: {
    toLoading () {
      this.loading = true
    }
  }
}
</script>
