<template>
  <v-main>
    <v-container fluid>
      <Header :text="'Persons'"></Header>
      <v-row>
        <v-spacer />
        <v-col cols="12" md="2" class="d-flex justify-end">
          <div>
            <v-btn class="mr-2 secondary" :to="{ name: 'PersonAdd' }" x-large data-cy="add">
              <v-icon left>mdi-plus-circle</v-icon>
              Person</v-btn
            >
          </div>
        </v-col>
      </v-row>
      <v-form ref="form" class="grey lighten-2 my-2">
        <v-row class="pl-2 pt-4" no-gutters>
          <v-col cols="12" md="4" class="d-flex">
            <div>
              <v-btn @click.prevent="triggerSearch" x-large data-cy="filter">
                <v-icon left>mdi-magnify</v-icon>
                Search/Filter</v-btn
              >
            </div>
            <SearchText
              class="ml-2"
              :text="'Last Name'"
              :value="'lastName'"
              @searchTextChanged="searchTextChanged"
              @keypress.enter.native.prevent="triggerSearch"
              data-cy="lastName"
            ></SearchText>
          </v-col>
          <v-col cols="12" md="2">
            <SearchSelect
              class="ml-2"
              :label="'Gender'"
              :value="'gender'"
              :urlApi="'/api/v1/persons/genders'"
              :itemText="'description'"
              :itemValue="'id'"
              :multiple="true"
              @searchSelectChanged="searchSelectChanged"
              data-cy="gender"
            >
            </SearchSelect>
          </v-col>
          <v-col cols="12" md="2">
            <SearchDate
              class="ml-2"
              :text="'Birth Date from'"
              :value="'birthDateFrom'"
              @searchTextChanged="searchDateChanged"
            ></SearchDate>
          </v-col>
          <v-col cols="12" md="2">
            <SearchDate
              class="ml-2"
              :text="'Birth Date to'"
              :value="'birthDateTo'"
              @searchTextChanged="searchDateChanged"
            ></SearchDate>
          </v-col>
        </v-row>
      </v-form>

      <v-row>
        <v-col cols="12" md="12">
          <Table
            ref="table"
            :headers="headers"
            :urlApi="'/api/v1/persons/elastic'"
            :search="search"
            :searchAttributes="['lastName', 'gender', 'birthDateFrom', 'birthDateTo']"
            :viewRouter="'PersonView'"
            :editRouter="'PersonAdd'"
          >
          </Table>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>

<script>
import Table from '@/components/Table.vue';
import SearchText from '@/components/search/SearchText.vue';
import SearchSelect from '@/components/search/SearchSelect.vue';
import SearchDate from '@/components/search/SearchDate.vue';
import Header from '@/components/helpers/Header';
import util from '@/mixins/util.js';

export default {
  name: 'Person',
  mixins: [util],
  components: {
    Table,
    SearchText,
    SearchSelect,
    SearchDate,
    Header,
  },
  data: () => ({
    search: {},
  }),
  methods: {
    triggerSearch() {
      this.$refs.table.triggerSearch();
    },
    searchTextChanged(obj) {
      console.log('[App] searchTextChanged');
      this.search[obj.value] = obj.search;
    },
    searchSelectChanged(obj) {
      console.log('[App] searchSelectChanged');
      this.search[obj.value] = obj.search;
    },
    searchDateChanged(obj) {
      console.log('[App] searchDateChanged');
      this.search[obj.value] = obj.search;
    },
  },
  computed: {
    headers() {
      let headers = [
        /*{ text: 'ID', value: 'id', sortable: true },*/
        { text: 'Last Name', value: 'lastName', sortable: true },
        { text: 'First Name', value: 'firstName', sortable: true },
        { text: 'Gender', value: 'gender', sortable: true, type: 'Enum' },
        { text: 'Birth Date', value: 'birthDate', sortable: true, type: 'Date' },
        { text: '', value: 'actions', sortable: false, align: 'end' },
      ];
      return headers;
    },
  },
};
</script>
