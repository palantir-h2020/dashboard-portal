<template>
  <v-navigation-drawer
    v-model="drawer"
    :mini-variant="mini"
    app
    dark
    id="left-sidebar"
    class="primary"
    :expand-on-hover="mini"
    width="340"
    mini-variant-width="80"
  >
    <v-list-item>
      <v-list-item-content>
        <v-list-item-title class="title d-flex align-end">
          <div class="d-inline"><v-icon size="40px">$vuetify.icons.logo</v-icon></div>
          <div class="ml-10 d-inline">PALANTIR</div>
        </v-list-item-title>
      </v-list-item-content>
    </v-list-item>
    <v-list nav>
      <template v-for="item in items">
        <v-row v-if="item.heading" :key="item.heading" align="center">
          <v-col cols="6">
            <v-subheader v-if="item.heading">
              {{ !mini ? item.heading : '' }}
            </v-subheader>
          </v-col>
        </v-row>
        <v-list-group
          v-else-if="item.children"
          :key="item.text"
          v-model="item.model"
          :prepend-icon="item.icon"
          :append-icon="'mdi-chevron-down'"
          color="inherit"
        >
          <template v-slot:activator>
            <v-list-item-content>
              <v-list-item-title v-bind:data-cy="item.text">
                {{ item.text }}
              </v-list-item-title>
            </v-list-item-content>
          </template>
          <v-list-item
            v-for="(child, i) in item.children"
            :key="i"
            link
            :to="child.to"
            class="pl-10"
          >
            <v-list-item-action v-if="child.icon">
              <v-icon>{{ child.icon }}</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title v-bind:data-cy="child.text">
                {{ child.text }}
              </v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-group>
        <v-list-item v-else-if="item.text === 'Logout'" :key="item.text" @click="logout">
          <v-list-item-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title v-bind:data-cy="item.text">
              {{ item.text }}
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item v-else :key="item.text" link :to="item.to">
          <v-list-item-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title v-bind:data-cy="item.text">
              {{ item.text }}
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </template>
    </v-list>
    <template v-slot:append>
      <div class="pa-2" @click.stop="switchMini()">
        <v-btn tile block text>
          <v-icon left class="ml-1">
            {{ mini ? 'mdi-chevron-double-right' : 'mdi-chevron-double-left' }}
          </v-icon>
          <div class="ml-6">
            {{ mini ? 'Expand sidebar' : 'Collapse sidebar' }}
          </div>
        </v-btn>
      </div>
    </template>
  </v-navigation-drawer>
</template>

<script>
import util from '@/mixins/util.js';

export default {
  name: 'LeftSidebar',
  mixins: [util],
  data() {
    return {
      drawer: true,
      mini: true,
    };
  },
  computed: {
    items() {
      const currentRole = this.$store.getters.roles;

      if (currentRole === 'network_operator' || currentRole === 'sme_manager') {
        return [
          {
            icon: '$vuetify.icons.tachometer',
            text: 'Threats Dashboard',
            to: {
              name: 'ThreatsDashboard',
            },
          },
          {
            icon: '$vuetify.icons.pageCopy',
            text: 'Security Incident Reports',
            to: {
              name: 'SecurityReports',
            },
          },
          {
            icon: '$vuetify.icons.briefBox',
            text: 'Security Capabilities Marketplace',
            to: {
              name: 'SCMarketplace',
            },
          },
          {
            icon: '$vuetify.icons.lineChart',
            text: currentRole === 'sme_manager' ? 'Financial Dashboard' : 'Financial Status',
            to: {
              name: currentRole === 'sme_manager' ? 'FinancialDashboard' : 'FinancialStatus',
            },
          },
          {
            icon: '$vuetify.icons.shapeOutline',
            text:
              currentRole === 'sme_manager'
                ? 'Security Capabilities Status'
                : 'Security Capabilities Management',
            to: {
              name: currentRole === 'sme_manager' ? 'SCStatus' : 'SCManagement',
            },
          },
          {
            icon: '$vuetify.icons.riskIndicator',
            text: 'Risk Assessment Wizard',
            to: {
              name: 'RiskAssessment',
            },
          },
          {
            icon: '$vuetify.icons.socialShare',
            text: 'Threat Sharing',
            to: {
              name: 'ThreatSharing',
            },
          },
        ];
      } else if (this.$store.getters.roles === 'sc_developer') {
        return [
          {
            icon: '$vuetify.icons.tachometer',
            text: 'Dev Dashboard',
            to: {
              name: 'DevDashboard',
            },
          },
          {
            icon: '$vuetify.icons.boxGrid',
            text: 'Security Capabilities',
            to: {
              name: 'DevSecurityCapabilities',
            },
          },
          {
            icon: '$vuetify.icons.pageCopy',
            text: 'Security Capability Registration',
            to: {
              name: 'SCRegistration',
            },
          },
        ];
      } else {
        return [{ divider: true }, { heading: 'Empty' }];
      }
    },
  },
  methods: {
    switchMini() {
      let needsReset = false;

      if (!this.mini) needsReset = true;

      let mini = !this.mini;

      if (needsReset) {
        this.mini = mini;
        this.drawer = false;
        setTimeout(() => {
          this.mini = mini;
          this.drawer = true;
        }, 300);
      } else {
        this.mini = mini;
      }
    },
  },
};
</script>

<style scoped>
#left-sidebar
  >>> .v-list-group--active
  > .v-list-group__header
  .v-list-group__header__prepend-icon
  .v-icon {
  color: inherit;
}
</style>
