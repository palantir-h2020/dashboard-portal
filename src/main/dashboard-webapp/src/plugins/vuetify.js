import Vue from 'vue';
import Vuetify from 'vuetify/lib';

import LogoIcon from '@/components/icons/LogoIcon';

Vue.use(Vuetify);

export default new Vuetify({
  theme: {
    themes: {
      light: {
        primary: '#008CBF',
        secondary: '#00ADEE',
        error: '#F27E7E',
      },
    },
  },
  icons: {
    values: {
      logo: {
        // name of our custom icon
        component: LogoIcon, // our custom component
      },
    },
  },
});
