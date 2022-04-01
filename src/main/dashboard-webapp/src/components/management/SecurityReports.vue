<template>
  <div id="dashboardContent">
    <v-row justify="center">
      <img :src="image" />
    </v-row>
  </div>
</template>

<script>
import image from '../../assets/DummyIncidents.png';

export default {
  name: 'SecurityReports',
  data() {
    return {
      image: image,
    };
  },
};
</script>
