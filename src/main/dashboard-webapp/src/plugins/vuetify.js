import Vue from 'vue';
import Vuetify from 'vuetify/lib';

import LogoIcon from '@/components/icons/LogoIcon';
import BoxGridIcon from '@/components/icons/BoxGridIcon';
import BriefBoxIcon from '@/components/icons/BriefBoxIcon';
import LineChartIcon from '@/components/icons/LineChartIcon';
import PageCopyIcon from '@/components/icons/PageCopyIcon';
import RiskIndicatorIcon from '@/components/icons/RiskIndicatorIcon';
import ShapeOutlineIcon from '@/components/icons/ShapeOutlineIcon';
import SocialShareIcon from '@/components/icons/SocialShareIcon';
import TachometerIcon from '@/components/icons/TachometerIcon';

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
  // Custom Icons
  icons: {
    values: {
      // name of custom icon
      logo: {
        component: LogoIcon, // custom component
      },
      boxGrid: {
        component: BoxGridIcon,
      },
      briefBox: {
        component: BriefBoxIcon,
      },
      lineChart: {
        component: LineChartIcon,
      },
      pageCopy: {
        component: PageCopyIcon,
      },
      riskIndicator: {
        component: RiskIndicatorIcon,
      },
      shapeOutline: {
        component: ShapeOutlineIcon,
      },
      socialShare: {
        component: SocialShareIcon,
      },
      tachometer: {
        component: TachometerIcon,
      },
    },
  },
});
