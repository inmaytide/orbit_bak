import axios from 'axios'

const LOGIN_API = {
  getCaptcha: process.env.API_ROOT + 'captcha?_=',
  login: process.env.API_ROOT + 'oauth/token'
}

export default class {
  constructor () {
    this.currentCaptchaName = ''
  }
  getCaptcha () {
    return axios.get(LOGIN_API.getCaptcha + Date.now())
      .then(res => {
        this.currentCaptchaName = res.data.captchaName
        return res.data.image
      })
  }
  login (token) {
    return axios.post(LOGIN_API.login, {}, {
      headers: {'Captcha-Name': this.currentCaptchaName},
      params: {
        ...token,
        grant_type: 'password',
        client_id: 'apps',
        scope: 'all',
        client_secret: '59a84cbf83227a35'
      }
    })
  }
}
