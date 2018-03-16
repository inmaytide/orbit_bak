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
        this.currentCaptchaName = res.captchaName
        return res.image
      })
  }
  login (token) {
    var body = {
      ...token,
      grant_type: 'password',
      client_id: 'apps',
      scope: 'all',
      client_secret: '59a84cbf83227a35'
    }
    var data = new FormData()
    Object.keys(body).forEach(k => data.append(k, body[k]))
    return axios.post(LOGIN_API.login, data, {
      headers: {
        'Captcha-Name': this.currentCaptchaName
      }
    })
  }
}
