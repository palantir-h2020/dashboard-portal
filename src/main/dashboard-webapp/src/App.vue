<template>
  <v-app>
    <router-view></router-view>
    <v-snackbar
      v-model="snackbar"
      :bottom="y === 'bottom'"
      :color="snackbarColor"
      :left="x === 'left'"
      :multi-line="mode === 'multi-line'"
      :right="x === 'right'"
      :timeout="timeout"
      :top="y === 'top'"
      :vertical="mode === 'vertical'"
    >
      {{ snackbarText }}

      <template v-slot:action="{ attrs }">
        <v-btn dark text v-bind="attrs" @click="snackbar = false">
          Close
        </v-btn>
      </template>
    </v-snackbar>
    <Loader :loading="wait"></Loader>
  </v-app>
</template>

<script>
import EventBus from '@/helpers/event-bus.js';
import Loader from '@/components/helpers/Loader.vue';

export default {
  name: 'App',
  components: {
    Loader,
  },
  data() {
    return {
      snackbar: false,
      snackbarColor: '',
      mode: '',
      snackbarText: '',
      timeout: 6000,
      x: null,
      y: 'bottom',
      wait: false,
    };
  },
  created() {
    EventBus.$on('snackbar', this.snackbarChanged);
    EventBus.$on('waiting', this.waiting);
    this.axios.get('/api/v1/service/dummy');
  },
  beforeDestroy() {
    EventBus.$off('snackbar', this.snackbarChanged);
    EventBus.$off('waiting', this.waiting);
  },
  methods: {
    snackbarChanged(params) {
      this.snackbarText = params.text;
      this.snackbarColor = params.color;
      this.snackbar = true;
    },
    waiting(value) {
      this.wait = value;
    },
  },
};
</script>

<style>
a {
  text-decoration: none;
}

.required label::after {
  content: ' *';
}

.theme--dark.v-list {
  color: #ffffff;
}

.v-btn--active.no-active:not(:hover)::before {
  opacity: 0 !important;
}

.tiptap-vuetify-editor.custom header.v-toolbar {
  background-color: inherit !important;
}
</style>
