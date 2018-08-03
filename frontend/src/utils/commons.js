export default {
  storeToken (token) {
    localStorage.setItem('token', token.token_type + ' ' + token.access_token);
  },
  storeUser (user) {
    delete user.password;
    localStorage.setItem('user', JSON.stringify(user));
  },
  getUser () {
    const stored = localStorage.getItem('user');
    if (typeof stored !== 'undefined' && stored != null) {
      return JSON.parse(stored);
    }
    return null;
  },
  getProfile () {
    return JSON.parse(localStorage.getItem('profile'));
  },
  getAuthorization () {
    const token = localStorage.getItem('token');
    if (typeof token !== 'undefined' && token != null && token !== '') {
      return token;
    }
    return null;
  }
};
