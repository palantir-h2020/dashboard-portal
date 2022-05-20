<template>
  <v-main>
    <div id="dashboardContent">
      <v-row cols="12" justify="center">
        <img :src="image" />
      </v-row>
    </div>
  </v-main>
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
