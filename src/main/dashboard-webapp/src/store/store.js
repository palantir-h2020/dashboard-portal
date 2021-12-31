//you need to import both vue and vuex, as both are used here
import Vue from 'vue';
import Vuex from 'vuex';
import sharedMutations from 'vuex-shared-mutations';
//then you use Vuex
Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    fullName: null,
    avatar: null,
    roles: null,
    timezone: null,
    dateFormat: null,
  },
  mutations: {
    initialiseStore(state) {
      if (localStorage.avatar) {
        state.avatar = localStorage.avatar;
      }
      if (localStorage.roles) {
        state.roles = localStorage.roles;
      }
      if (localStorage.timezone) {
        state.timezone = localStorage.timezone;
      }
      if (localStorage.dateFormat) {
        state.dateFormat = localStorage.dateFormat;
      }
      if (localStorage.fullName) {
        state.fullName = localStorage.userfullname;
      }
    },
    setAvatar(state, avatar) {
      if (avatar) {
        localStorage.avatar = avatar;
        state.avatar = avatar;
      } else {
        localStorage.removeItem('avatar');
        state.avatar = null;
      }
    },
    setRoles(state, roles) {
      if (roles) {
        localStorage.roles = roles;
        state.roles = roles;
      } else {
        localStorage.removeItem('roles');
        state.roles = null;
      }
    },
    setTimezone(state, timezone) {
      if (timezone) {
        localStorage.timezone = timezone;
        state.timezone = timezone;
      } else {
        localStorage.removeItem('timezone');
        state.timezone = null;
      }
    },
    setDateFormat(state, dateFormat) {
      if (dateFormat) {
        localStorage.dateformat = dateFormat;
        state.dateFormat = dateFormat;
      } else {
        localStorage.removeItem('dateformat');
        state.dateFormat = null;
      }
    },
    setFullName(state, fullName) {
      if (fullName) {
        localStorage.fullName = fullName;
        state.fullName = fullName;
      } else {
        localStorage.removeItem('userfullname');
        state.fullName = null;
      }
    },
  },
  getters: {
    avatar: state => {
      return state.avatar ? state.avatar : '0';
    },
    roles: state => {
      return state.roles ? state.roles : [];
    },
    timezone: state => {
      return state.timezone ? state.timezone : 'Etc/UCT';
    },
    dateFormat: state => {
      return state.dateFormat ? state.dateFormat : 'YYYY-MM-DD';
    },
    fullName: state => {
      return state.fullName ? state.fullName : 'Undefined';
    },
  },
  plugins: [
    sharedMutations({
      predicate: ['setAvatar', 'setRoles', 'setTimezone', 'setDateFormat', 'setFullName'],
    }),
  ],
});
