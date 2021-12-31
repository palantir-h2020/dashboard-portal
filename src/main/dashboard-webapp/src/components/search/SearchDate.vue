<template>
  <div>
    <v-menu v-model="menu" :close-on-content-click="false" offset-y min-width="290px">
      <template v-slot:activator="{ on }">
        <v-text-field
          :label="text"
          v-bind:value="searchFormatted"
          @blur="search = parseDate($event.target.value)"
          v-on="on"
          v-on:click:clear="search = null"
          clearable
          filled
          hide-details
          dense
          class="mb-2"
          autocomplete="off"
        ></v-text-field>
      </template>
      <v-date-picker v-model="search" @input="menu = false" no-title scrollable></v-date-picker>
    </v-menu>
  </div>
</template>

<script>
import util from '@/mixins/util.js';
import EventBus from '@/helpers/event-bus.js';

export default {
  name: 'SearchDate',
  mixins: [util],
  props: {
    text: String,
    value: String,
  },
  data: () => ({
    menu: false,
    search: null,
    firstLoad: true,
  }),
  created() {
    EventBus.$on('clear', this.clear);
  },
  beforeDestroy() {
    EventBus.$off('clear', this.clear);
  },
  mounted() {
    console.log('[SearchDate] Mounted');
    if (this.$route.query[this.value]) {
      this.search = this.$route.query[this.value];
    } else {
      this.firstLoad = false;
    }
  },
  methods: {
    clear() {
      this.search = null;
    },
  },
  computed: {
    searchFormatted() {
      return this.formatDate(this.search);
    },
  },
  watch: {
    search: {
      handler() {
        if (!this.firstLoad) {
          console.log('[SearchDate] Search watch');
          this.$emit('searchTextChanged', { value: this.value, search: this.search });
        }
        this.firstLoad = false;
      },
      deep: true,
    },
  },
};
</script>

<style scoped></style>
