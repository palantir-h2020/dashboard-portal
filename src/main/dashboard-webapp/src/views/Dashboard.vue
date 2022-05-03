<template>
  <div id="app">
    <top-menu
      app
      @toggle-drawer="$refs.drawer.drawer = !$refs.drawer.drawer"
      :logoutTimer="false"
    />
    <left-sidebar ref="drawer" />
    <v-app>
      <v-container fluid class="pa-4 overflow-y-auto" over>
        <vue-snotify></vue-snotify>
        <router-view></router-view>
      </v-container>
      <PortalTarget name="popup" />
    </v-app>
  </div>
</template>

<script>
import TopMenu from '@/components/TopMenu';
import LeftSidebar from '@/components/LeftSidebar';
import EventBus from '../helpers/event-bus';

export default {
  name: 'Dashboard',
  components: {
    TopMenu,
    LeftSidebar,
  },
  beforeMount() {
    // Connect WebSocket once user is logged in / dashboard is loaded.
    this.$connect();
  },
  created() {
    // Set basic notification handler when this view is active.
    EventBus.$on('newEvent', event => {
      if (event.type == 'incident') {
        const {
          incidentType,
          detectedIncident,
          incidentLocation,
          incidentDescription,
        } = event.incident;
        this.$snotify.warning(
          `Incident ${detectedIncident} found at ${incidentLocation}. ${incidentDescription}`,
          `Incident ${incidentType} found!`,
        );
      } else if (event.type == 'action') {
        const {
          actionDescription,
          actionName,
          componentIP,
          componentId,
          componentType,
          onIps,
        } = event.action;
        this.$snotify.info(
          `${actionDescription}${onIps.length > 0 ? ' Action on IP addresses: ' + onIps : ''}.`,
          `Component ${componentType} ${componentId} ${componentIP} did ${actionName} action.`,
        );
      }
    });
  },
  beforeDestroy() {
    // Disconnect WebSocket.
    this.$disconnect();
  },
};
</script>
