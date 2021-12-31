<template>
  <v-autocomplete
    v-if="!multiple"
    v-model="search"
    :label="label"
    :multiple="multiple"
    :items="itemsInn"
    :clearable="clearable"
    :itemText="itemText"
    :item-value="itemValue"
    filled
    hide-details
    dense
    class="mb-2"
  ></v-autocomplete>
  <v-autocomplete
    v-else
    v-model="searchMultiple"
    :label="label"
    :multiple="multiple"
    :items="itemsInn"
    :clearable="clearable"
    :itemText="itemText"
    :item-value="itemValue"
    filled
    hide-details
    dense
    class="mb-2"
  ></v-autocomplete>
</template>

<script>
import EventBus from '@/helpers/event-bus.js';
export default {
  name: 'SearchSelect',
  props: {
    /**
     * The text of the label
     */
    label: {
      type: String,
      default: 'Search',
    },
    /**
     * The representation of the backend model .e.g. firstName
     */
    value: String,
    /**
     * Changes select to multiple.
     */
    multiple: {
      type: Boolean,
      default: false,
    },
    /**
     * Set the options list
     */
    items: Array,
    itemsSelected: Array,
    /**
     * The server side rendering path for dynamic options list.
     * Overrides the items prop
     */
    urlApi: String,
    /**
     * Set property of items's text value
     */
    itemText: {
      type: [String, Array, Function],
      default: 'text',
    },
    /**
     * Set property of items's value - must be primitive. Dot notation is supported.
     */
    itemValue: {
      type: [String, Array, Function],
      default: 'value',
    },
    /**
     * Set property of items's value type.
     * This is needed when options value is Number
     * @values String, Number
     */
    itemValueType: {
      default: 'String',
    },
    clearable: {
      default: true,
    },
  },
  data: () => ({
    search: {
      type: [String, Number],
      default: null,
    },
    searchMultiple: [],
    itemsInn: [],
    firstLoad: true,
  }),
  created() {
    EventBus.$on('clear', this.clear);
  },
  beforeDestroy() {
    EventBus.$off('clear', this.clear);
  },
  mounted() {
    console.log('[SearchSelect] Mounted');
    if (typeof this.urlApi !== 'undefined') {
      this.getDataFromApi();
    } else {
      this.itemsInn = this.items;
    }
    if (this.$route.query[this.value]) {
      if (this.itemValueType === 'Number') {
        this.search = Number(this.$route.query[this.value]);
        this.searchMultiple = this.$route.query[this.value].split(',').map(Number);
      } else {
        this.search = this.$route.query[this.value];
        this.searchMultiple = this.$route.query[this.value].split(',');
      }
    } else if (this.itemsSelected) {
      this.search = this.itemsSelected[0];
      this.searchMultiple = this.itemsSelected;
    } else {
      this.firstLoad = false;
    }
  },
  watch: {
    search: {
      handler() {
        if (!this.firstLoad) {
          console.log('[SearchSelect] Search watch');
          this.$emit('searchSelectChanged', { value: this.value, search: this.search });
        }
        this.firstLoad = false;
      },
    },
    searchMultiple: {
      handler() {
        if (!this.firstLoad) {
          console.log('[SearchSelect] Search watch');
          this.$emit('searchSelectChanged', {
            value: this.value,
            search: this.searchMultiple.join(),
          });
        }
        this.firstLoad = false;
      },
      deep: true,
    },
  },
  methods: {
    getDataFromApi() {
      console.log('[SearchSelect] Loading data');
      this.axios.get(this.urlApi).then(res => (this.itemsInn = res.data.content));
    },
    clear() {
      if (this.multiple) {
        this.searchMultiple = [];
      } else {
        this.search = null;
      }
    },
  },
};
</script>

<style scoped></style>
