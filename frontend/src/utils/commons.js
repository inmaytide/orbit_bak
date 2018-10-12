const __ORBIT_TOKEN_ = '__ORBIT_TOKEN_';
const __ORBIT_USER_ = '__ORBIT_USER_';

const capitalize = (str) => str.replace(/\b[a-z]/g, s => s.toUpperCase());

export default {
  clearAuthenticated () {
    localStorage.removeItem(__ORBIT_TOKEN_);
    localStorage.removeItem(__ORBIT_USER_);
  },
  storeToken (token) {
    localStorage.setItem(__ORBIT_TOKEN_, JSON.stringify(token));
  },
  getToken () {
    const stored = localStorage.getItem(__ORBIT_TOKEN_);
    if (!this.isBlank(stored)) {
      return JSON.parse(stored);
    }
    return null;
  },
  storeUser (user) {
    delete user.password;// earse password
    localStorage.setItem(__ORBIT_USER_, JSON.stringify(user));
  },
  getUser () {
    const stored = localStorage.getItem(__ORBIT_USER_);
    if (!this.isBlank(stored)) {
      return JSON.parse(stored);
    }
    return null;
  },
  getAuthorization () {
    const token = this.getToken();
    if (token !== null) {
      return capitalize(token.token_type) + ' ' + token.access_token;
    }
    return null;
  },
  getParamValue (name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || ['', ''])[1].replace(/\+/g, '%20')) || null;
  },
  transform (data, parent = {id: '0'}) {
    const filtered = data.filter(element => element.parent === parent.id);
    filtered.forEach(element => {
      element.parentObject = parent;
      element.children = this.transform(data, element);
    });
    return filtered;
  },
  transformOptions (data, parent = {id: '0'}, labelName = 'name', valueName = 'id') {
    const options = [];
    const filtered = data.filter(element => element.parent === parent.id);
    filtered.forEach(element => {
      options.push({
        label: element[labelName],
        value: element[valueName],
        children: this.transformOptions(data, element)
      });
    });
    return options;
  },
  index (elements, conditionValue, conditionKey = 'id') {
    const len = elements.length;
    for (let i = 0; i < len; i++) {
      if (elements[i][conditionKey] === conditionValue) {
        return i;
      }
    }
    return -1;
  },
  isEmptyArray (value) {
    return typeof value === 'undefined' || value == null || value.length === 0;
  },
  isBlank (value) {
    value = value + '';
    return typeof value === 'undefined' || value == null || value.trim() === '' || value === 'undefined';
  },
  isNull (value) {
    if (typeof value === 'undefined' || value == null) {
      return true;
    }
    try {
      return JSON.stringify(value) === '{}';
    } catch (e) {
      return false;
    }
  }
};
