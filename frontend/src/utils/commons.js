export default {
  clearAuthenticated () {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  },
  storeToken (token) {
    localStorage.setItem('token', JSON.stringify(token));
  },
  getToken () {
    const stored = localStorage.getItem('token');
    if (!this.isBlank(stored)) {
      return JSON.parse(stored);
    }
    return null;
  },
  storeUser (user) {
    delete user.password;// earse password
    localStorage.setItem('user', JSON.stringify(user));
  },
  getUser () {
    const stored = localStorage.getItem('user');
    if (!this.isBlank(stored)) {
      return JSON.parse(stored);
    }
    return null;
  },
  getAuthorization () {
    const token = this.getToken();
    if (token !== null) {
      return token.token_type + ' ' + token.access_token;
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
