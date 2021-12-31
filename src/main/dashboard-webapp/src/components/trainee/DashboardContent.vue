<template>
  <v-main>
    <v-container fluid>
      <Header :text="'Dashboard'"></Header>
    </v-container>
  </v-main>
</template>

<script>
import Header from '@/components/helpers/Header';
export default {
  name: 'DashboardContent-Trainee',
  components: {
    Header,
  },
};
</script>
