import axios from 'axios'

const LOGIN_API = {
  getCaptcha: process.env.ROOT_API + 'captcha?_='
}

let currentCaptchaName

export default class LoginService {
  getCaptcha () {
    return axios.get(LOGIN_API.getCaptcha + Date.now())
      .then(res => {
        currentCaptchaName = res.captchaName
        return res.image
      })
  }
  login (token) {
    axios.get('', {
      headers: {'Captcha-Name': currentCaptchaName}
    })
  }
}
