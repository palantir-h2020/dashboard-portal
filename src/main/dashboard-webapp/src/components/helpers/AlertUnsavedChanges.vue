<template>
  <v-row>
    <v-col col="12" md="12">
      <v-alert color="warning" outlined text type="info" dense>
        {{ text }}
      </v-alert>
    </v-col>
  </v-row>
</template>

<script>
export default {
  name: 'Teacher',
  props: {
    text: {
      type: String,
      default: 'You have unsaved changes',
    },
  },
};
</script>
