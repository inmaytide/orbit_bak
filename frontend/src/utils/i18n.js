import VueI18n from 'vue-i18n';
import Vue from 'vue';

Vue.use(VueI18n);

const locales = {
  'en': require('../langs/en.json'),
  'zh': require('../langs/zh.json')
};

// let lang = navigator.language;
// if (!lang) {
//   lang = navigator.browserLanguage;
// }

const lang = 'en';

const i18n = new VueI18n({
  locale: lang,
  messages: locales
});

export default i18n;
