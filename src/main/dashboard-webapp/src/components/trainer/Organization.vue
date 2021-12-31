<template>
  <v-main>
    <v-container fluid>
      <teacher
        :text="
          'Get list of items from backend. Front end sorting, pagination, search/filter. Modal crud(add, edit, delete).'
        "
      ></teacher>
      <Header :text="'Organizations'"></Header>
      <v-row>
        <v-spacer />
        <v-col cols="12" md="2" class="d-flex justify-end">
          <div>
            <v-btn class="mr-2 secondary" x-large data-cy="add" @click.stop="dialog = true">
              <v-icon left>mdi-plus-circle</v-icon>
              Organization</v-btn
            >
          </div>
        </v-col>
      </v-row>

      <v-form ref="form" class="my-2">
        <v-row class="pl-2 pt-4" no-gutters>
          <v-col cols="12" class="d-flex">
            <v-text-field
              v-model="search"
              prepend-icon="mdi-magnify"
              label="Search/Filter"
              filled
              dense
              hide-details
            ></v-text-field>
          </v-col>
        </v-row>
      </v-form>

      <v-row>
        <v-col cols="12" md="12">
          <v-data-table
            :headers="headers"
            :items="items"
            sort-by="name"
            class="elevation-0"
            :search="search"
          >
            <template v-slot:item.actions="{ item }">
              <v-icon class="mr-2" @click="editItem(item)">
                mdi-file-document-edit-outline
              </v-icon>
              <v-icon @click="deleteItem(item)">
                mdi-delete-outline
              </v-icon>
            </template>
          </v-data-table>
          <v-dialog v-model="dialog" max-width="500px">
            <v-card>
              <v-card-title>
                <span class="headline">{{ formTitle }}</span>
              </v-card-title>
              <v-card-text>
                <v-container>
                  <v-form ref="form" v-model="valid" :lazy-validation="false">
                    <v-row>
                      <v-col cols="12">
                        <v-text-field
                          v-model="editedItem.name"
                          label="Name"
                          :rules="requiredRule()"
                          class="required"
                          :data-cy="'name'"
                        ></v-text-field>
                      </v-col>
                    </v-row>
                  </v-form>
                </v-container>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn text color="blue darken-1" @click="close">
                  Cancel
                </v-btn>
                <v-btn
                  text
                  color="success darken-1"
                  @click="save"
                  :disabled="!valid"
                  data-cy="save"
                >
                  Submit
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
          <v-dialog v-model="dialogDelete" max-width="500px">
            <v-card>
              <v-card-title class="headline">Delete</v-card-title>
              <v-card-text>
                Are you sure you want to delete this item?
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="closeDelete">Cancel</v-btn>
                <v-btn color="red darken-1" text @click="deleteItemConfirm">OK</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>

<script>
import Header from '@/components/helpers/Header';
import util from '@/mixins/util.js';
import EventBus from '@/helpers/event-bus.js';
import Teacher from '@/components/helpers/Teacher';

export default {
  name: 'Organization',
  mixins: [util],
  components: {
    Header,
    Teacher,
  },
  data: () => ({
    urlApi: '/api/v1/organizations',
    valid: false,
    dialog: false,
    dialogDelete: false,
    headers: [
      {
        text: 'Name',
        sortable: true,
        value: 'name',
      },
      { text: '', value: 'actions', sortable: false, align: 'end' },
    ],
    items: [],
    editedIndex: -1,
    editedItem: {
      name: null,
    },
    defaultItem: {
      name: null,
    },
    search: '',
  }),
  computed: {
    formTitle() {
      return this.editedIndex === -1 ? 'New' : 'Edit';
    },
  },
  watch: {
    dialog(val) {
      val || this.close();
    },
    dialogDelete(val) {
      val || this.closeDelete();
    },
  },
  created() {
    this.initialize();
  },
  methods: {
    initialize() {
      this.getDataFromApi().then(data => {
        this.items = data.items;
        this.totalItems = data.total;
      });
    },
    editItem(item) {
      this.editedIndex = this.items.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },
    deleteItem(item) {
      this.editedIndex = this.items.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogDelete = true;
    },
    deleteItemConfirm() {
      let value = this.editedItem;
      console.log('Click Delete ' + value.id);
      EventBus.$emit('waiting', true);
      this.axios
        .delete(this.urlApi + '/' + value.id)
        .then(() => {
          console.log('Success');
          EventBus.$emit('snackbar', {
            text: 'Delete successfully.',
          });
          this.items.splice(this.editedIndex, 1);
        })
        .catch(error => {
          console.log(error);
        })
        .finally(() => {
          EventBus.$emit('waiting', false);
          this.closeDelete();
        });
    },
    close() {
      this.dialog = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },
    closeDelete() {
      this.dialogDelete = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },
    save() {
      EventBus.$emit('waiting', true);
      this.axios
        .post(this.urlApi, this.editedItem)
        .then(() => {
          console.log('Success');
          EventBus.$emit('snackbar', {
            text: 'Organization has been created.',
          });
          if (this.editedIndex > -1) {
            Object.assign(this.items[this.editedIndex], this.editedItem);
          } else {
            this.items.push(this.editedItem);
          }
          this.close();
        })
        .catch(error => {
          console.log({ error });
        })
        .finally(() => {
          EventBus.$emit('waiting', false);
        });
    },
    // backend call - similar to Table.vue
    getDataFromApi() {
      console.log('[Table] Loading data');
      this.loading = true;
      EventBus.$emit('waiting', true);
      return new Promise(resolve => {
        this.axios
          .get(this.urlApi)
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
  },
};
</script>
