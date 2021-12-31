<template>
  <v-container class="text-center">
    <v-row align="center">
      <v-col>
        <h1 class="display-2 primary--text">Error, 403</h1>

        <p>
          Forbidden
        </p>
        <p>
          Access to this resource is denied
        </p>

        <v-btn :to="{ name: 'Dashboard' }" color="primary" outlined class="mr-2">
          Go back home
        </v-btn>

        <v-btn @click="logout" color="primary" outlined>
          Logout
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import util from '@/mixins/util.js';

export default {
  name: 'Page403',
  mixins: [util],
};
</script>
