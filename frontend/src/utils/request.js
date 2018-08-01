import axios from 'axios';
import commons from './commons.js';
import i18n from './i18n';

const CONTEXTPATH = process.env.API_ROOT;

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
    i18n.t('login.error.len.password');
    console.log(error);
    return Promise.reject(error);
  }
);

const request = {
  base: (config) => {
    config.url = CONTEXTPATH + config.url;
    return axios.request(config);
  },
  get: (url, params) => {
    url = CONTEXTPATH + url;
    return axios.get(url, {params: params});
  },
  delete: (url, params) => {
    url = CONTEXTPATH + url;
    return axios.delete(url, {params: params});
  },
  head: (url, params) => {
    url = CONTEXTPATH + url;
    return axios.head(url, {params: params});
  },
  post: (url, data, config) => {
    url = CONTEXTPATH + url;
    return axios.post(url, data || {}, config || {});
  },
  put: (url, data, config) => {
    url = CONTEXTPATH + url;
    return axios.put(url, data || {}, config || {});
  },
  patch: (url, data, config) => {
    url = CONTEXTPATH + url;
    return axios.patch(url, data || {}, config || {});
  }
};

export default request;
