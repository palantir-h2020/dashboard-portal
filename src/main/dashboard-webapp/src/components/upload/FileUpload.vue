<template>
  <div>
    <div v-if="progressInfos">
      <div class="mb-2" v-for="(progressInfo, index) in progressInfos" :key="index">
        <span>{{ progressInfo.fileName }}</span>
        <v-progress-linear v-model="progressInfo.percentage" height="25" reactive>
          <strong>{{ progressInfo.percentage }} %</strong>
        </v-progress-linear>
      </div>
    </div>

    <v-row no-gutters justify="center" align="center">
      <v-col cols="8">
        <v-file-input
          v-model="files"
          multiple
          show-size
          label="Select Files"
          @change="selectFiles"
        ></v-file-input>
      </v-col>

      <v-col cols="4" class="pl-2">
        <v-btn color="success" dark small @click="uploadFiles">
          Upload
          <v-icon right dark>mdi-cloud-upload</v-icon>
        </v-btn>
      </v-col>
    </v-row>

    <v-alert v-if="message" outlined class="multi-line" color="error">
      {{ message }}
    </v-alert>

    <v-card v-if="fileInfos.length > 0" class="mx-auto">
      <v-list>
        <v-subheader>List of Files</v-subheader>
        <v-list>
          <v-list-item v-for="(file, index) in fileInfos" :key="index" dense>
            <v-list-item-content>
              <a @click="downloadFile(url, file)">{{ file }}</a>
            </v-list-item-content>
            <v-list-item-icon>
              <v-tooltip top>
                <template v-slot:activator="{ on }">
                  <v-icon v-on="on" @click="(itemToBeDeleted = file), (deleteDialog = true)" small>
                    mdi-delete-outline
                  </v-icon>
                </template>
                <span>Delete</span>
              </v-tooltip>
            </v-list-item-icon>
          </v-list-item>
        </v-list>
      </v-list>
    </v-card>

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
import EventBus from '@/helpers/event-bus';

export default {
  name: 'FileUpload',
  props: {
    url: String,
  },
  data() {
    return {
      selectedFiles: [],
      progressInfos: [],
      message: '',
      fileInfos: [],
      files: [],
      itemToBeDeleted: null,
      deleteDialog: false,
      disableDelete: false,
    };
  },
  methods: {
    selectFiles(files) {
      this.progressInfos = [];
      this.selectedFiles = files;
    },
    upload(idx, file) {
      this.progressInfos[idx] = { percentage: 0, fileName: file.name };

      this.uploadFile(this.url, file, event => {
        this.progressInfos[idx].percentage = Math.round((100 * event.loaded) / event.total);
      })
        .then(response => {
          if (response.status === 200) {
            EventBus.$emit('snackbar', {
              text: 'File has been uploaded.',
            });
          } else {
            let prevMessage = this.message ? this.message + '\n' : '';
            this.message = prevMessage + response.data.message;
          }
          return this.getFiles(this.url);
        })
        .then(files => {
          this.fileInfos = files.data.content;
        })
        .catch(() => {
          this.progressInfos[idx].percentage = 0;
          let prevMessage = this.message ? this.message + '\n' : '';
          this.message = prevMessage + 'Could not upload the file:' + file.name;
        });
    },
    uploadFiles() {
      this.message = '';
      for (let i = 0; i < this.selectedFiles.length; i++) {
        this.upload(i, this.selectedFiles[i]);
      }
    },
    initialize() {
      this.message = '';
      this.progressInfos = [];
      this.selectedFiles = [];
      this.fileInfos = [];
      this.files = [];
      this.getFiles(this.url).then(response => {
        this.fileInfos = response.data.content;
      });
    },
    uploadFile(url, file, onUploadProgress) {
      let formData = new FormData();
      console.log({ file });
      formData.append('file', file);
      formData.append('name', file.name);
      formData.append('type', file.type);
      formData.append('size', file.size);
      return this.axios.post(url, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
        onUploadProgress,
      });
    },
    downloadFile(url, file) {
      this.axios({
        url: url + '/' + file,
        method: 'GET',
        responseType: 'blob', // important
      }).then(response => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', file);
        document.body.appendChild(link);
        link.click();
      });
    },
    getFiles(url) {
      return this.axios.get(url);
    },
    onDeleteItem() {
      this.disableDelete = true;
      let value = this.itemToBeDeleted;
      console.log('Click Delete ' + value);
      EventBus.$emit('waiting', true);
      this.axios
        .delete(this.url + '/' + value)
        .then(() => {
          console.log('Success');
          EventBus.$emit('snackbar', {
            text: 'Delete successfully.',
          });
          this.deleteDialog = false;
          this.getFiles(this.url).then(response => {
            this.fileInfos = response.data.content;
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
  mounted() {
    this.initialize();
  },
  watch: {
    url(val) {
      if (val) {
        this.initialize();
      }
    },
  },
};
</script>

<style>
.multi-line {
  white-space: pre-line;
}
</style>
