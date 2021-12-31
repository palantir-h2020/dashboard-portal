<template>
  <v-main>
    <v-container class="fill-height pa-0" fluid>
      <v-row class="fill-height pa-0 mx-0 my-auto">
        <v-col cols="12" class="pa-0 align-center">
          <router-view></router-view>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>

<script>
export default {
  name: 'Plain',
};
</script>
