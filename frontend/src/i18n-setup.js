import Vue from 'vue'
import VueI18n from 'vue-i18n'
import axios from 'axios'

Vue.use(VueI18n)

export const i18n = new VueI18n()

function loadLanguage () {
  let lang = navigator.language
  if (!lang) {
    lang = navigator.browserLanguage
  }
  axios.get(process.env.API_I18N + lang)
    .then(res => {
      i18n.locale = lang
      i18n.setLocaleMessage(lang, res.data)
    })
    .catch(res => {
      console.log(res)
    })
}

loadLanguage()
