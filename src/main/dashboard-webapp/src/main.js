import 'intersection-observer';
import Vue from 'vue';
import './plugins/axios';
import App from './App.vue';
import vuetify from './plugins/vuetify';
import router from './router';
import store from './store/store';
// import plugin
import { TiptapVuetifyPlugin } from 'tiptap-vuetify';
// don't forget to import CSS styles
import 'tiptap-vuetify/dist/main.css';
//leaflet css
import 'leaflet/dist/leaflet.css';
import { Icon } from 'leaflet';
import '@mdi/font/css/materialdesignicons.min.css';
import 'typeface-roboto/index.css';
import './styles/app.scss';
// Internationalization
import i18n from './i18n';
// WebSocket connector
import VueNativeSock from 'vue-native-websocket';
// Notifications utility
import Snotify, { SnotifyPosition } from 'vue-snotify';
// Portal Component, renders DOME outside of a component, anywhere. (useful for popups)
import PortalVue from 'portal-vue';
// For some reason this directive is not included, so it is now fixed.
import vClickOutside from 'v-click-outside';
// Utilities
import util from './mixins/util.js';

//fixes issue with marker not being visible
delete Icon.Default.prototype._getIconUrl;
Icon.Default.mergeOptions({
  iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png'),
  iconUrl: require('leaflet/dist/images/marker-icon.png'),
  shadowUrl: require('leaflet/dist/images/marker-shadow.png'),
});

Vue.config.productionTip = false;

// use this package's plugin
Vue.use(TiptapVuetifyPlugin, {
  // the next line is important! You need to provide the Vuetify Object to this place.
  vuetify, // same as "vuetify: vuetify"
  // optional, default to 'md' (default vuetify icons before v2.0.0)
  iconsGroup: 'mdi',
});

Vue.use(
  VueNativeSock,
  `ws://${util.methods.currentIP()}:8081/websocket/notifications-stream/${util.methods.makeId(10)}`,
  {
    connectManually: true,
    store: store,
    reconnection: true,
    reconnectionAttempts: 5,
    reconnectionDelay: 3000,
  },
);

Vue.use(Snotify, {
  toast: {
    position: SnotifyPosition.rightTop,
    titleMaxLength: 300,
    bodyMaxLength: 3000,
    buttons: [
      { text: 'Ok', action: null, bold: false },
      { text: 'Check', action: null, bold: false },
    ],
  },
});

Vue.use(PortalVue);

Vue.use(vClickOutside);

new Vue({
  i18n,
  vuetify,
  router,
  store: store,
  beforeCreate() {
    this.$store.commit('initialiseStore');
  },
  render: h => h(App),
}).$mount('#app');
