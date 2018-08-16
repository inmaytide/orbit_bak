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
    if (typeof stored !== 'undefined' && stored != null && stored !== '') {
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
    if (typeof stored !== 'undefined' && stored != null) {
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
  transform (data, parent = '0') {
    const filtered = data.filter(element => element.parent === parent);
    filtered.forEach(element => (element.children = this.transform(data, element.id)));
    return filtered;
  }
};
