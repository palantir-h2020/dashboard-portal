<template>
  <v-main>
    <div id="dashboardContent">
      <v-row cols="12" justify="center">
        <v-img
          :src="image"
          contain
          :max-height="(windowHeight * 9) / 10"
          :max-width="windowWidth"
        />
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
      windowHeight: window.innerHeight,
      windowWidth: window.innerWidth,
    };
  },
  mounted() {
    this.$nextTick(() => {
      window.addEventListener('resize', this.onResize);
    });
  },

  beforeDestroy() {
    window.removeEventListener('resize', this.onResize);
  },

  methods: {
    onResize() {
      this.windowHeight = window.innerHeight;
      this.windowWidth = window.innerWidth;
    },
  },
};
</script>
