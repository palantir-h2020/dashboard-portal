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
import VueNativeSock from 'vue-native-websocket';
import { methods as utilMethods } from '@/mixins/util.js';

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

Vue.use(VueNativeSock, `ws://websocket/notifications-stream/${utilMethods.makeId(10)}`, {
  connectManually: false,
  store: store,
  reconnection: true,
  reconnectionAttempts: 5,
  reconnectionDelay: 3000,
});

new Vue({
  vuetify,
  router,
  store: store,
  beforeCreate() {
    this.$store.commit('initialiseStore');
  },
  render: h => h(App),
}).$mount('#app');
