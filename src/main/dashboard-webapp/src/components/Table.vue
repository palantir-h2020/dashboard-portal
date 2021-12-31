<template>
  <div>
    <v-data-table
      :headers="headers"
      :items="items"
      class="elevation-0"
      :options.sync="options"
      :server-items-length="totalItems"
      :loading="loading"
      :multi-sort="true"
      :footer-props="{
        showFirstLastPage: false,
        itemsPerPageOptions: [10, 15, 25, 50, 100, 150, 200],
      }"
      item-class="properties"
      :dense="false"
    >
      <template v-if="totalItems === 2147483647" #footer.page-text="props">
        {{ props.pageStart }} - {{ props.pageStop }}
      </template>
      <template v-slot:item="{ item }">
        <tr>
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
              getDescendantProp(item, header.value + '.description')
            }}</span>
            <span v-else-if="header.type == 'List'">{{
              getDescendantProp(item, header.value)
                .map(function(item) {
                  return item[header.property];
                })
                .join(', ')
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
                <span>View</span>
              </v-tooltip>
              <v-tooltip top v-if="editRouter">
                <template v-slot:activator="{ on }">
                  <v-icon v-on="on" @click="onEditItem(item)" class="mr-2">
                    mdi-file-document-edit-outline
                  </v-icon>
                </template>
                <span>Edit</span>
              </v-tooltip>
              <v-tooltip top>
                <template v-slot:activator="{ on }">
                  <v-icon
                    v-on="on"
                    @click="(itemToBeDeleted = item), (deleteDialog = true)"
                    class="mr-2"
                  >
                    mdi-delete-outline
                  </v-icon>
                </template>
                <span>Delete</span>
              </v-tooltip>
            </v-layout>
          </td>
        </tr>
      </template>
    </v-data-table>

    <v-dialog v-if="itemToBeDeleted !== null" v-model="deleteDialog" max-width="500">
      <v-card>
        <v-card-title class="headline">Delete</v-card-title>
        <v-card-text>
          Are you sure you want to delete this item?
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="secondary" text @click="deleteDialog = false">
            Cancel
          </v-btn>
          <v-btn color="red darken-1" text @click="onDeleteItem" :disabled="disableDelete">
            Delete
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
    headers: Array,
    url: String,
    urlApi: String,
    search: Object,
    searchAttributes: Array,
    editRouter: String,
    viewRouter: String,
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
  mounted() {
    console.log('[Table] Mounted');
    if (this.firstLoad) {
      /* Populate table from route */
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
        .delete(this.urlApi + '/' + value.id)
        .then(() => {
          console.log('Success');
          EventBus.$emit('snackbar', {
            text: 'Delete successfully.',
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
  },
};
</script>
