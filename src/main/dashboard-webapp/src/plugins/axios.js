'use strict';

import Vue from 'vue';
import axios from 'axios';
import EventBus from '@/helpers/event-bus.js';
import Util from '@/mixins/util.js';

// Full config:  https://github.com/axios/axios#request-config
// axios.defaults.baseURL = process.env.baseURL || process.env.apiUrl || '';
// axios.defaults.headers.common['Authorization'] = AUTH_TOKEN;
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

let config = {
  baseURL: process.env.VUE_APP_AXIOSBASE,
  // timeout: 60 * 1000, // Timeout
  // withCredentials: true, // Check cross-site Access-Control
};

const _axios = axios.create(config);

_axios.interceptors.request.use(
  function(config) {
    // Do something before request is sent
    if (!config.url.includes('/api/v1/auth')) {
      let token = localStorage.token;
      // add token to header
      if (token) {
        config.headers.Authorization = 'Bearer ' + token;
      }
    }
    return config;
  },
  function(error) {
    // Do something with request error
    return Promise.reject(error);
  },
);

// Add a response interceptor
_axios.interceptors.response.use(
  function(response) {
    // Do something with response data
    if (!response.config.url.includes('/api/v1/auth') && response.config.url.includes('/api/v1')) {
      if (localStorage.tokenIsRefreshing === 'false') {
        localStorage.tokenIsRefreshing = true;
        _axios
          .post('/api/v1/auth/refresh', localStorage.refresh_token, {
            headers: { 'Content-Type': 'application/json' },
          })
          .then(res => {
            console.log('Token refreshed');
            Util.methods.setLocalStorage(res.data);
          })
          .catch(error => {
            console.log(error);
          })
          .finally(() => {
            localStorage.tokenIsRefreshing = false;
          });
      }
    }
    return response;
  },
  function(error) {
    // Do something with response error
    console.log(error.response.config.url);
    if (error.response.status === 500) {
      EventBus.$emit('snackbar', {
        text: 'HTTP 500 - Internal server error. Please contact site admin.',
        color: 'error',
      });
    } else if (error.response.status === 404) {
      EventBus.$emit('snackbar', {
        text: 'Entity was not found.',
        color: 'warning',
      });
    } else if (error.response.status === 403) {
      window.location.href = '/page-403';
    } else if (error.response.status === 401) {
      if (error.response.config.url.includes('/api/v1/auth')) {
        window.location.href = process.env.VUE_APP_LOGINPATH;
      } else {
        window.location.href = '/page-401';
      }
    } else if (error.response.status === 400) {
      EventBus.$emit('snackbar', {
        text: 'Bad request. Please check the values of the form.',
        color: 'warning',
      });
    }
    return Promise.reject(error);
  },
);

/* eslint-disable no-unused-vars */
Plugin.install = function(Vue, options) {
  Vue.axios = _axios;
  window.axios = _axios;
  Object.defineProperties(Vue.prototype, {
    axios: {
      get() {
        return _axios;
      },
    },
    $axios: {
      get() {
        return _axios;
      },
    },
  });
};

Vue.use(Plugin);

export default Plugin;
