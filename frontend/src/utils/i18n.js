import VueI18n from 'vue-i18n';
import Vue from 'vue';
import en from '../langs/en/';
import zh from '../langs/zh/';

Vue.use(VueI18n);

const locales = {
  'en': en,
  'zh-CN': zh
};

// let lang = navigator.language;
// if (!lang) {
//   lang = navigator.browserLanguage;
// }

const lang = 'zh-CN';

const i18n = new VueI18n({
  locale: lang,
  messages: locales
});

export default i18n;
