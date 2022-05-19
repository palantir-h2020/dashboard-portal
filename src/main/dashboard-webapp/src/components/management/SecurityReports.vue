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
import util from '@/mixins/util.js';

export default {
  name: 'SecurityReports',
  components: {
    Table,
  },
  data: () => ({
    search: {},
    headers: [
      {
        text: 'Type',
        value: 'type',
        sortable: false,
        transform: val => {
          const typeParts = val.split(':');
          const type = typeParts[0];
          const subtype = typeParts.length > 1 ? typeParts[1] : '';
          return util.methods.uppercaseFirstLetters(`${subtype} ${type}`.trim());
        },
      },
      {
        text: 'Name',
        value: 'name',
        sortable: false,
        transform: val => {
          return util.methods.uppercaseFirstLetters(val);
        },
      },
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
