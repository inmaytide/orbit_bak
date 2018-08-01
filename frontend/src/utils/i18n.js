import VueI18n from 'vue-i18n';
import Vue from 'vue';

Vue.use(VueI18n);

const locales = {
  'en-US': require('../langs/en.json')
};

// let lang = navigator.language;
// if (!lang) {
//   lang = navigator.browserLanguage;
// }

const lang = 'en-US';

const i18n = new VueI18n({
  locale: lang,
  messages: locales
});

export default i18n;
