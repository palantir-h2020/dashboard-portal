<template>
  <v-text-field v-model="search" :label="text" filled dense class="mb-2" clearable></v-text-field>
</template>

<script>
import EventBus from '@/helpers/event-bus.js';
export default {
  name: 'SearchText',
  props: {
    text: String,
    value: String,
  },
  data: () => ({
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
    console.log('[SearchText] Mounted');
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
  watch: {
    search: {
      handler() {
        if (!this.firstLoad) {
          console.log('[SearchText] Search watch');
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
