<template>
  <!-- MAKE SURE THAT THIS COMPONENT IS WITHIN A v-main -->
  <div>
    <v-data-table
      :headers="dataTableHeaders"
      :items="items"
      class="elevation-0"
      :options.sync="options"
      :server-items-length="totalItems"
      :loading="loading"
      :multi-sort="true"
      :hide-default-footer="true"
      item-class="properties"
      :dense="false"
      item-key="id"
    >
      <template v-slot:top="{ pagination, options, updateOptions }">
        <v-data-footer
          :items-per-page-options="[10, 15, 25, 50, 100, 150, 200]"
          :pagination="pagination"
          :options="options"
          @update:options="updateOptions"
          items-per-page-text="$vuetify.dataTable.itemsPerPageText"
          showFirstLastPage
          firstIcon="mdi-arrow-collapse-left"
          lastIcon="mdi-arrow-collapse-right"
          prevIcon="mdi-arrow-left"
          nextIcon="mdi-arrow-right"
        />
      </template>
      <template v-slot:item="{ item, index: itemIndex }">
        <tr
          :class="selectedRows.indexOf(item.id) > -1 ? 'light-blue lighten-2' : ''"
          @click="rowClicked(item)"
        >
          <td>
            {{ options.itemsPerPage * (options.page - 1) + itemIndex + 1 }}
          </td>
          <td
            class="d-block d-sm-table-cell"
            v-for="(header, index) in headers"
            :key="index"
            @click="onViewItem(item)"
          >
            <span v-if="header.type === 'Date'">{{
              formatDate(getDescendantProp(item, header.value))
            }}</span>
            <span v-else-if="header.type === 'DateTime'">{{
              formatDateTime(getDescendantProp(item, header.value))
            }}</span>
            <span v-else-if="header.type === 'DateTimeLocale'">{{
              formatDateTimeLocale(getDescendantProp(item, header.value))
            }}</span>
            <span v-else-if="header.type == 'Enum'">{{
              $t(getDescendantProp(item, header.value + '.id'))
            }}</span>
            <span v-else-if="header.type == 'List'">{{
              getDescendantProp(item, header.value)
                .map(function(item) {
                  return item[header.property];
                })
                .join(', ')
            }}</span>
            <span v-else-if="header.type == 'EnumEntity'">{{
              $t(getDescendantProp(item, header.value + '.description'))
            }}</span>
            <span v-else>{{ getDescendantProp(item, header.value) }}</span>
          </td>
          <td>
            <v-layout justify-end>
              <v-tooltip top v-if="viewRouter">
                <template v-slot:activator="{ on }">
                  <v-icon v-on="on" @click="onViewItem(item)" class="mr-2">
                    mdi-file-find-outline
                  </v-icon>
                </template>
                <span>{{ $t('view') }}</span>
              </v-tooltip>
              <v-tooltip top v-if="editRouter">
                <template v-slot:activator="{ on }">
                  <v-icon v-on="on" @click="onEditItem(item)" class="mr-2">
                    mdi-file-document-edit-outline
                  </v-icon>
                </template>
                <span>{{ $t('edit') }}</span>
              </v-tooltip>
              <v-tooltip top v-if="!noDelete">
                <template v-slot:activator="{ on }">
                  <v-icon
                    v-on="on"
                    @click="(itemToBeDeleted = item), (deleteDialog = true)"
                    class="mr-2"
                  >
                    mdi-delete-outline
                  </v-icon>
                </template>
                <span>{{ $t('delete.message') }}</span>
              </v-tooltip>
            </v-layout>
          </td>
        </tr>
      </template>
    </v-data-table>

    <v-dialog v-if="itemToBeDeleted !== null" v-model="deleteDialog" max-width="500">
      <v-card>
        <v-card-title class="headline">{{ $t('delete.message') }}</v-card-title>
        <v-card-text>
          {{ $t('delete.confirm') }}
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="secondary" text @click="deleteDialog = false">
            {{ $t('cancel') }}
          </v-btn>
          <v-btn color="red darken-1" text @click="onDeleteItem" :disabled="disableDelete">
            {{ $t('delete.message') }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import EventBus from '@/helpers/event-bus.js';
import util from '@/mixins/util.js';

export default {
  name: 'Table',
  mixins: [util],
  props: {
    headers: Array, // Table headers as list.
    urlApi: String, // REST API endpoint as Data source.
    /*
    if you do not want to use generic urlApi, use urlApiDelete
     */
    urlApiDelete: String, // Alternate REST API endpoint for delete.
    search: Object,
    searchAttributes: Array,
    editRouter: String, // Route for edit.
    viewRouter: String, // Route for view.
    noDelete: {
      type: Boolean,
      default: false,
    }, // If true, no delete allowed.
    cacheName: {
      type: String,
      default: '',
    }, // Key for cached data fetched by table.
    highlight: {
      type: Boolean,
      default: false,
    }, // If true, clicked item is toggled highlighted.
    singleHighlight: {
      type: Boolean,
      default: false,
    }, // If true and 'highlight' true, ONLY clicked item is toggled highlighted.
  },
  data: () => ({
    firstLoad: true,
    totalItems: 0,
    items: [],
    loading: true,
    options: {},
    itemToBeDeleted: null,
    deleteDialog: false,
    disableDelete: false,
    dataTableHeaders: [{ text: '#' }],
    tableId: '',
    selectedRows: [],
  }),
  watch: {
    options: {
      handler() {
        console.log('[Table] Options watch');
        if (!this.firstLoad) {
          this.changeOptions();
          this.getDataFromApi().then(data => {
            this.items = data.items;
            this.totalItems = data.total;
          });
        }
      },
      deep: true,
    },
  },
  beforeMount() {
    let dataTableHeaders = [{ text: '#', sortable: false, align: 'start' }].concat(this.headers);
    if (this.viewRouter || this.editRouter || !this.noDelete) {
      dataTableHeaders = dataTableHeaders.concat([
        { text: 'Actions', sortable: false, align: 'end' },
      ]);
    }
    this.dataTableHeaders = dataTableHeaders;
    this.tableId = 'table:' + this.makeId(10);
  },
  mounted() {
    console.log('[Table] Mounted');
    if (this.firstLoad) {
      this.options.page = this.$route.query.index ? Number(this.$route.query.index) + 1 : 1;
      this.options.itemsPerPage = this.$route.query.size ? Number(this.$route.query.size) : 10;
      this.options.sortBy = [];
      this.options.sortDesc = [];
      if (this.$route.query.sort) {
        const sortList = this.$route.query.sort.split(',');
        for (let i = 0; i < sortList.length; i += 1) {
          this.options.sortBy.push(sortList[i].split(' ')[0]);
          if (sortList[i].split(' ')[1] === 'asc') {
            this.options.sortDesc.push(false);
          } else {
            this.options.sortDesc.push(true);
          }
        }
      }
      for (let i = 0; i < this.searchAttributes.length; i += 1) {
        const attr = this.searchAttributes[i];
        if (this.$route.query[attr]) {
          this.$set(this.search, attr, this.$route.query[attr]);
        }
      }
    }
    this.getDataFromApi().then(data => {
      this.items = data.items;
      this.totalItems = data.total;
      this.firstLoad = false;
    });
  },
  beforeDestroy() {
    this.$store.commit('clearCachedData', this.cacheName ? this.cacheName : this.tableId);
  },
  methods: {
    triggerSearch() {
      console.log('[Table] Search watch');
      if (!this.firstLoad) {
        this.options.page = 1;
        this.changeOptions();
        this.getDataFromApi().then(data => {
          this.items = data.items;
          this.totalItems = data.total;
        });
      }
    },
    changeOptions() {
      this.$router.push({
        path: this.$router.path,
        query: this.getParams(),
      });
      let currentPathName = this.$router.currentRoute.name;
      localStorage[currentPathName] = this.$router.currentRoute.fullPath;
    },
    getDataFromApi() {
      console.log('[Table] Loading data');
      this.loading = true;
      EventBus.$emit('waiting', true);
      return new Promise(resolve => {
        this.axios
          .get(`${this.urlApi}?`, {
            params: this.getParams(),
          })
          .then(res => {
            let items = [];
            let total = 0;
            if (res.status === 200) {
              items = res.data.content;
              total = res.data.total;
            }
            setTimeout(() => {
              if (res.status === 200) this.cacheTableData(items);
              this.loading = false;
              resolve({
                items,
                total,
              });
            });
          })
          .finally(() => {
            EventBus.$emit('waiting', false);
          });
      });
    },
    getParams() {
      const { sortBy, sortDesc, page, itemsPerPage } = this.options;
      const query = {
        index: page - 1,
        size: itemsPerPage,
      };
      let sort = '';
      for (let i = 0; i < sortBy.length; i += 1) {
        if (i !== 0) {
          sort += ',';
        }
        let dir = 'asc';
        if (sortDesc[i]) {
          dir = 'desc';
        }
        sort += `${sortBy[i]} ${dir}`;
      }
      if (sort !== '') {
        query.sort = sort;
      }
      for (let i = 0; i < this.searchAttributes.length; i += 1) {
        const attr = this.searchAttributes[i];
        if (this.search[attr]) {
          query[attr] = this.search[attr];
        }
      }
      return query;
    },
    onViewItem(value) {
      console.log('Click View ' + value.id);
      this.$router.push({
        name: this.viewRouter,
        query: { id: value.id },
      });
    },
    onEditItem(value) {
      console.log('Click Edit ' + value.id);
      this.$router.push({
        name: this.editRouter,
        query: { id: value.id },
      });
    },
    onDeleteItem() {
      this.disableDelete = true;
      let value = this.itemToBeDeleted;
      console.log('Click Delete ' + value.id);
      EventBus.$emit('waiting', true);
      this.axios
        .delete((this.urlApiDelete ? this.urlApiDelete : this.urlApi) + '/' + value.id)
        .then(() => {
          console.log('Success');
          EventBus.$emit('snackbar', {
            text: this.$i18n.t('delete.success'),
          });
          this.deleteDialog = false;
          this.getDataFromApi().then(data => {
            this.items = data.items;
            this.totalItems = data.total;
          });
          this.disableDelete = false;
        })
        .catch(error => {
          console.log(error);
          this.disableDelete = false;
        })
        .finally(EventBus.$emit('waiting', false));
    },
    updateTable() {
      this.getDataFromApi().then(data => {
        this.items = data.items;
        this.totalItems = data.total;
      });
    },
    cacheTableData(data) {
      let cachedItems = {};
      for (const item of data) {
        cachedItems[item.id] = item;
      }
      this.$store.commit('setCachedData', {
        cachedData: cachedItems,
        cacheId: this.cacheName ? this.cacheName : this.tableId,
      });
    },
    rowClicked(row) {
      if (!this.highlight) return;
      if (this.singleHighlight) {
        let isOn = this.selectedRows.includes(row.id);
        this.selectedRows = [];
        if (!isOn) this.selectedRows.push(row.id);
      } else this.toggleSelection(row.id);
    },
    toggleSelection(keyID, off = false) {
      if (this.selectedRows.includes(keyID)) {
        this.selectedRows = this.selectedRows.filter(selectedKeyID => selectedKeyID !== keyID);
      } else if (!off) {
        this.selectedRows.push(keyID);
      }
    },
  },
};
</script>
