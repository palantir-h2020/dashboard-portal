<template>
  <div id="app">
    <left-sidebar ref="drawer" />
    <top-menu @toggle-drawer="$refs.drawer.drawer = !$refs.drawer.drawer" :logoutTimer="false" />
    <v-main>
      <v-container fluid class="pa-4 overflow-y-auto" over>
        <vue-snotify></vue-snotify>
        <router-view></router-view>
        <PortalTarget name="popup" />
      </v-container>
    </v-main>
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
