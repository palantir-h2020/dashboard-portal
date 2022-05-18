<template>
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
          highlight
          singleHighlight
          cacheName="securityReports"
        />
      </v-col>
    </v-row>
    <router-view />
  </v-container>
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
      {
        text: 'Time (YYYY-MM-DD hh:mm)',
        value: 'createdTimestamp',
        sortable: false,
        type: 'DateTime',
      },
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
