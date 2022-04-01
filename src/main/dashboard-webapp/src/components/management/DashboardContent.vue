<template>
  <div id="dashboardContent">
    <v-row justify="center">
      <img :src="image" />
    </v-row>
  </div>
</template>

<script>
import image from '../../assets/DummyDashboard.png';

export default {
  name: 'DashboardContent',
  data() {
    return {
      image: image,
    };
  },
};
</script>
