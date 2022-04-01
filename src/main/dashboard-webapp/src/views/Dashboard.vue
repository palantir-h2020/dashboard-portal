<template>
  <div>
    <top-menu
      app
      @toggle-drawer="$refs.drawer.drawer = !$refs.drawer.drawer"
      :logoutTimer="false"
    />
    <left-sidebar ref="drawer" />
    <v-container fluid class="pa-4 overflow-y-auto" over>
      <router-view></router-view>
    </v-container>
  </div>
</template>

<script>
import TopMenu from '@/components/TopMenu';
import LeftSidebar from '@/components/LeftSidebar';

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
  beforeDestroy() {
    // Disconnect WebSocket.
    this.$disconnect();
  },
};
</script>
