<template>
  <v-menu v-model="menu" :close-on-content-click="false" offset-y top min-width="290px">
    <template v-slot:activator="{ on }">
      <v-text-field
        class="mb-4"
        :label="label"
        :placeholder="placeholder ? placeholder : placeholderDefault"
        append-outer-icon="mdi-calendar-month-outline"
        v-bind:value="dateFormatted"
        @blur="dateValue = parseDate($event.target.value)"
        v-on="on"
        v-on:click:clear="dateValue = null"
        clearable
        filled
        dense
        hide-details
        :data-cy="dataCy"
        :class="[{ required: required }]"
        :rules="rules"
        autocomplete="off"
      ></v-text-field>
    </template>
    <v-date-picker
      v-model="dateValue"
      @input="menu = false"
      no-title
      scrollable
      :allowed-dates="allowedDates"
      :min="min"
      :max="max"
    ></v-date-picker>
  </v-menu>
</template>

<script>
import util from '@/mixins/util.js';
export default {
  name: 'InputDate',
  mixins: [util],
  props: {
    value: String,
    label: String,
    placeholder: {
      type: String,
    },
    dataCy: String,
    /**
     * Shows required asterisk
     */
    required: {
      type: Boolean,
      default: false,
    },
    rules: {
      type: Array,
      default: () => [],
    },
    allowedDates: {
      type: Function,
      default: null,
    },
    min: {
      type: String,
      default: undefined,
    },
    max: {
      type: String,
      default: undefined,
    },
  },
  data() {
    return {
      menu: false,
      firstMount: true,
      dateValue: null,
    };
  },
  computed: {
    dateFormatted() {
      return this.formatDate(this.dateValue);
    },
    placeholderDefault() {
      return this.$store.getters.dateFormat;
    },
  },
  watch: {
    value(val) {
      this.firstMount = false;
      this.dateValue = val;
    },
    dateValue(val) {
      this.$emit('input', val);
    },
  },
};
</script>
