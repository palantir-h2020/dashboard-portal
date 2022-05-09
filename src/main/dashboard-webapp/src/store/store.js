//you need to import both vue and vuex, as both are used here
import Vue from 'vue';
import Vuex from 'vuex';
import sharedMutations from 'vuex-shared-mutations';
import EventBus from '../helpers/event-bus.js';
//then you use Vuex
Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    fullName: null,
    avatar: null,
    roles: null,
    timezone: null,
    dateFormat: null,
    socket: {
      isConnected: false,
      lastMessage: '',
      reconnectError: false,
    },
    notificationCollections: {
      action: {},
      incident: {},
    },
    // Standard mappings type -> collection
    dataCollections: {
      'failed_attestation:fsm': 'attestation_incident',
      'threat:netflow': 'netflow_incident',
      anomaly: 'netflow_incident',
      'threat:syslog': 'syslog_threat_incidents',
    },
    // Cached data from current path
    cachedData: {},
    notificationList: [],
  },
  mutations: {
    // Socket mutations
    SOCKET_ONOPEN(state, event) {
      Vue.prototype.$socket = event.currentTarget;
      state.socket.isConnected = true;
      console.log('Socket opened', event);
    },
    SOCKET_ONCLOSE(state, event) {
      state.socket.isConnected = false;
      console.log('Socket closed', event);
    },
    SOCKET_ONERROR(state, event) {
      console.error(state, event);
    },
    // default handler called for all methods
    SOCKET_ONMESSAGE(state, message) {
      console.log('Received message', message);
      message = JSON.parse(message.data);
      state.socket.lastMessage = message;
      console.log('Received JSON:', message);
      EventBus.$emit('newEvent', message);
      // Push notification to temporary notifications list.
      state.notificationList.push(message);
      // Index notification in collections.
      if (!state.notificationCollections[message.type][message.collection]) {
        state.notificationCollections[message.type][message.collection] = {};
      }
      state.notificationCollections[message.type][message.collection][message.id] = message;
      // TODO LATER... Clean up old from temp list and index.
    },
    // mutations for reconnect methods
    SOCKET_RECONNECT(state, count) {
      console.info(state, count);
    },
    SOCKET_RECONNECT_ERROR(state) {
      state.socket.reconnectError = true;
    },
    // Basic mutations
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
    // Careful! Data for a Cache ID is meant for a specific purpose.
    setCachedData(state, payload) {
      let { cachedData, cacheId } = payload;
      state.cachedData[cacheId] = cachedData;
    },
    clearCachedData(state, cacheId) {
      delete state.cachedData[cacheId];
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
    notificationCollections: state => {
      return state.notificationCollections;
    },
    notificationList: state => {
      return state.notificationList;
    },
    dataCollections: state => {
      return state.dataCollections;
    },
    dataCollectionTypes: state => {
      return Object.keys(state.dataCollections);
    },
    cachedData: state => cacheId => {
      return state.cachedData[cacheId];
    },
  },
  plugins: [
    sharedMutations({
      predicate: ['setAvatar', 'setRoles', 'setTimezone', 'setDateFormat', 'setFullName'],
    }),
  ],
});
