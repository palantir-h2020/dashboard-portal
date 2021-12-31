<template>
  <v-container class="text-center">
    <v-row align="center">
      <v-col>
        <h1 class="display-2 primary--text">Error, 404</h1>

        <p>Page not found</p>

        <v-btn :to="{ name: 'Dashboard' }" color="primary" outlined>
          Go home
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>
