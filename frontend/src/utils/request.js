import Vue from 'vue';
import axios from 'axios';
import commons from './commons';
import i18n from './i18n';

const CONTEXT_PATH = process.env.API_ROOT;
const VUE_INST = new Vue();
const ERROR_NAME_PREFIX = 'errors.';

function tokenExpired (config) {
  var token = commons.getToken();
  if (token === null) {
    location.href = '/login';
    return;
  }

  axios.get(CONTEXT_PATH + '/oauth/token?grant_type=refresh_token&refresh_token=' + token.refresh_token)
    .then(() => axios(config))
    .catch(() => (location.href = '/login'));
}

axios.interceptors.request.use(
  config => {
    const authorization = commons.getAuthorization();
    if (authorization != null) {
      config.headers.Authorization = authorization;
    }
    return config;
  },
  error => Promise.reject(error)
);

axios.interceptors.response.use(
  response => response.status === 200 ? response.data : response,
  error => {
    const res = error.response ? error.response.data : {};
    let name = ERROR_NAME_PREFIX + 'unexpected';
    if (res.hasOwnProperty('error_description')) {
      name = ERROR_NAME_PREFIX + res['error_description'];
    } else if (res.hasOwnProperty('code')) {
      if (res['code'] === 'expired_token') {
        return tokenExpired(error.config);
      }
      name = ERROR_NAME_PREFIX + res['code'];
    } else {
      console.log(error);
    }
    VUE_INST.$Message.error({
      duration: 3,
      content: i18n.t(name)
    });
    return Promise.reject(error);
  }
);

const request = {
  base: (config) => {
    config.url = CONTEXT_PATH + config.url;
    return axios.request(config);
  },
  get: (url, params) => {
    url = CONTEXT_PATH + url;
    return axios.get(url, {params: params});
  },
  delete: (url, params) => {
    url = CONTEXT_PATH + url;
    return axios.delete(url, {params: params});
  },
  head: (url, params) => {
    url = CONTEXT_PATH + url;
    return axios.head(url, {params: params});
  },
  post: (url, data, config) => {
    url = CONTEXT_PATH + url;
    return axios.post(url, data || {}, config || {});
  },
  put: (url, data, config) => {
    url = CONTEXT_PATH + url;
    return axios.put(url, data || {}, config || {});
  },
  patch: (url, data, config) => {
    url = CONTEXT_PATH + url;
    return axios.patch(url, data || {}, config || {});
  }
};

export default request;
