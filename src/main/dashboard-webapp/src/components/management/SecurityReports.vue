<template>
  <v-main>
    <v-container fluid>
      <v-row>
        <v-col cols="12" md="12">
          <Table
            ref="incidentsTable"
            :headers="headers"
            :urlApi="urlApi"
            :search="search"
            :searchAttributes="[]"
            :viewRouter="viewRouter"
            noDelete
          />
        </v-col>
      </v-row>
    </v-container>
    <router-view />
  </v-main>
</template>

<script>
import EventBus from '@/helpers/event-bus.js';
import Table from '@/components/Table';

export default {
  name: 'SecurityReports',
  components: {
    Table,
  },
  data: () => ({
    search: {},
    headers: [
      { text: 'Type', value: 'type', sortable: false },
      { text: 'Name', value: 'name', sortable: false },
    ],
    viewRouter: 'SecurityReportDetails',
    urlApi: '/api/v1/events/incident', // Data source
  }),
  created() {
    // Event handler for updating the incidents.
    EventBus.$on('newEvent', event => {
      if (event.type == 'incident') {
        this.$refs.incidentsTable.updateTable();
      }
    });
  },
};
</script>
