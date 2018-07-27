export default {
  storeUser (token) {
    const profileStr = decodeURIComponent(escape(atob(token.access_token.split('.')[1])));
    const profile = JSON.parse(profileStr);
    localStorage.setItem('token', JSON.stringify(token));
    localStorage.setItem('profile', JSON.stringify({
      avatar: profile.avatar,
      name: profile.name,
      username: profile.user_name
    }));
  },
  getProfile () {
    return JSON.parse(localStorage.getItem('profile'));
  },
  getAuthorization () {
    const stored = localStorage.getItem('token');
    if (stored != null) {
      const token = JSON.parse(stored);
      if (token.access_token) {
        return 'Bearer ' + token.access_token;
      }
    }
    return null;
  },
  errorHandler (error) {
    return Promise.reject(error);
  }
};
