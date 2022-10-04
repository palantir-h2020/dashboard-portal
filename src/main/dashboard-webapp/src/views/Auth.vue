<template>
  <v-main>
    <v-img src="/IntroBackground.jpg" :max-height="windowHeight" :max-width="windowWidth">
      <v-container
        class="pa-0"
        fill-height
        fluid
        align-items-start
        v-bind:class="{
          bg: $vuetify.breakpoint.mdAndUp,
        }"
        style="align-items: start;"
      >
        <v-row class="pa-0">
          <v-col cols="3" class="pr-1 mt-4">
            <v-img
              src="/PalantirBanner.png"
              :max-height="windowHeight / 8"
              :max-width="windowWidth / 4"
              contain
            >
            </v-img>
          </v-col>
          <v-col cols="12" class="pa-0 mt-6 d-flex flex-column align-center">
            <!-- <div class="text-h4 font-weight-bold">WELCOME BACK!</div> -->
          </v-col>
          <v-col cols="12" class="pa-0">
            <router-view></router-view>
          </v-col>
        </v-row>
      </v-container>
    </v-img>
  </v-main>
</template>

<script>
export default {
  name: 'Auth',
  data() {
    return {
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
