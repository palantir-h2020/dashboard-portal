<template>
  <v-row>
    <v-col col="12" md="12">
      <v-alert color="purple" icon="mdi-teach" outlined text type="info">
        {{ text }}
      </v-alert>
    </v-col>
  </v-row>
</template>

<script>
export default {
  name: 'Teacher',
  props: {
    text: String,
  },
};
</script>
