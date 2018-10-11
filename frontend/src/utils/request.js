import Vue from 'vue';
import axios from 'axios';
import commons from './commons';
import i18n from './i18n';

const CONTEXT_PATH = process.env.API_ROOT;
const VUE_INST = new Vue();
const ERROR_NAME_PREFIX = 'errors.';

function displayError (name = 'unexpected') {
  name = ERROR_NAME_PREFIX + name;
  VUE_INST.$Message.error({
    duration: 3,
    content: i18n.t(name)
  });
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
  response => response.data,
  error => {
    const res = error.response ? error.response.data : {};
    if (error.response.status === 401 && !res.hasOwnProperty('error_description')) {
      location.href = '/login?m=expired_token';
      return;
    }

    let name = 'unexpected';
    if (res.hasOwnProperty('error_description')) {
      name = res['error_description'];
    } else {
      console.log(error);
    }
    displayError(name);
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
    if (data && commons.getUser()) {
      data.creator = commons.getUser().id;
    }
    return axios.post(url, data || {}, config || {});
  },
  put: (url, data, config) => {
    url = CONTEXT_PATH + url;
    if (data && commons.getUser()) {
      data.updater = commons.getUser().id;
    }
    return axios.put(url, data || {}, config || {});
  },
  patch: (url, data, config) => {
    url = CONTEXT_PATH + url;
    if (data && commons.getUser()) {
      data.updater = commons.getUser().id;
    }
    return axios.patch(url, data || {}, config || {});
  },
  error: displayError,
  getApiRoot () {
    return CONTEXT_PATH;
  }
};

export default request;
