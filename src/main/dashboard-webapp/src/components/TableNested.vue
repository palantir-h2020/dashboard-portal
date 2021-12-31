<template>
  <v-data-table :headers="headers" :items="items">
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title></v-toolbar-title>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px">
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              color="primary"
              dark
              class="mb-2"
              v-bind="attrs"
              v-on="on"
              @click="$refs.form ? $refs.form.resetValidation() : null"
            >
              New {{ label }}
            </v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="headline">{{ preTitle + ' ' + label }}</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-form ref="form" v-model="valid" :lazy-validation="false">
                  <slot name="form" :editedItem="editedItem"></slot>
                </v-form>
              </v-container>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="secondary" text @click="close">
                Cancel
              </v-btn>
              <v-btn color="blue darken-1" :disabled="!valid" text @click="save">
                Save
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
              <v-btn color="secondary" text @click="closeDelete">Cancel</v-btn>
              <v-btn color="blue darken-1" text @click="deleteItemConfirm">OK</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog v-if="uploadUrl" v-model="dialogFiles" max-width="500px">
          <v-card>
            <v-card-title class="headline">Files</v-card-title>
            <v-card-text>
              <file-upload
                :url="editedItem.id ? uploadUrl.replace('{id}', editedItem.id) : null"
              ></file-upload>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="secondary" text @click="closeUploadFiles">Close</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>
    <template v-slot:item="{ item }">
      <tr>
        <td class="d-block d-sm-table-cell" v-for="(header, index) in headers" :key="index">
          <span v-if="header.type === 'Date'">{{
            formatDate(getDescendantProp(item, header.value))
          }}</span>
          <span v-else-if="header.type === 'DateTime'">{{
            formatDateTime(getDescendantProp(item, header.value))
          }}</span>
          <span v-else-if="header.type == 'Enum'">{{
            getDescendantProp(item, header.value + '.description')
          }}</span>
          <span v-else-if="header.type == 'Boolean'">{{
            getDescendantProp(item, header.value) ? 'Yes' : 'No'
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
            <v-icon
              small
              class="mr-2"
              @click="uploadFiles(item)"
              v-if="uploadUrl"
              :disabled="!item.id"
            >
              mdi-file-document
            </v-icon>
            <v-icon small class="mr-2" @click="editItem(item)">
              mdi-pencil
            </v-icon>
            <v-icon small @click="deleteItem(item)">
              mdi-delete
            </v-icon>
          </v-layout>
        </td>
      </tr></template
    >
  </v-data-table>
</template>

<script>
import util from '@/mixins/util.js';
import FileUpload from '@/components/upload/FileUpload';

export default {
  name: 'TableNested',
  mixins: [util],
  components: {
    FileUpload,
  },
  props: {
    headers: Array,
    value: Array,
    label: {
      type: String,
      default: 'Item',
    },
    defaultItem: {
      type: Object,
      default: () => {},
    },
    uploadUrl: String,
  },
  data: () => ({
    valid: false,
    items: [],
    firstMount: true,
    dialog: false,
    dialogDelete: false,
    dialogFiles: false,
    editedIndex: -1,
    editedItem: {},
  }),
  watch: {
    value: {
      handler: function(val) {
        console.log('[Table Nested] Value changed');
        this.items = val;
      },
      deep: true,
    },
    items(val) {
      this.$emit('input', val);
    },
  },
  mounted() {
    console.log('[Table Nested] Mounted');
    if (this.firstMount) {
      this.firstMount = false;
      this.items = this.value;
      this.editedItem = Object.assign({}, this.defaultItem);
    }
  },
  methods: {
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
      this.items.splice(this.editedIndex, 1);
      this.$emit('delete', this.editedItem.id, this.editedItem.index);
      this.closeDelete();
    },
    uploadFiles(item) {
      this.editedIndex = this.items.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogFiles = true;
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
    closeUploadFiles() {
      this.dialogFiles = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },
    save() {
      if (this.editedIndex > -1) {
        Object.assign(this.items[this.editedIndex], this.editedItem);
        this.$emit('edit', this.editedItem.id, this.editedItem.index);
      } else {
        this.editedItem.createdTimestamp = this.getNowUtc();
        this.items.push(this.editedItem);
        this.$emit('add', this.editedItem.id, this.editedItem.index);
      }
      this.close();
    },
  },
  computed: {
    preTitle() {
      return this.editedIndex === -1 ? 'New' : 'Edit';
    },
  },
};
</script>
