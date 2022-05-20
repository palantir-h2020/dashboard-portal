<template>
  <v-main>
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
        <v-col cols="12" class="pa-0 mt-16 d-flex flex-column align-center">
          <!-- <div class="text-h4 font-weight-bold">WELCOME BACK!</div> -->
        </v-col>
        <v-col cols="12" class="pa-0">
          <router-view></router-view>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>

<script>
export default {
  name: 'Auth',
};
</script>
