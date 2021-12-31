<template>
  <v-dialog v-model="loading" persistent width="300">
    <v-card align="center" class="pb-8 pt-8">
      <v-card-text class="mb-4">
        Please wait...
      </v-card-text>
      <v-progress-circular :size="40" :width="4" color="amber" indeterminate></v-progress-circular>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: 'Loader.vue',

  props: {
    loading: Boolean,
  },
};
</script>
