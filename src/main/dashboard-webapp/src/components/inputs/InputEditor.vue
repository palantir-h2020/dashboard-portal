<template>
  <div>
    <div :class="[{ required: required }]">
      <v-label>{{ label }}</v-label>
    </div>
    <tiptap-vuetify
      v-model="tiptapValue"
      :extensions="extensions"
      :placeholder="placeholder"
      :card-props="{ flat: true, outlined: true }"
      class="custom"
    >
    </tiptap-vuetify>
    <v-row>
      <v-col cols="12" md="2">
        <VMessages v-if="!firstMount" :value="errorBucket" color="error" />
      </v-col>
      <v-spacer></v-spacer>
      <v-col cols="12" md="3">
        <div class="text-caption mb-8 v-messages" v-text="counterMessage"></div>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import VInput from 'vuetify/lib/components/VInput/VInput.js';
import {
  Bold,
  Heading,
  History,
  Image,
  Italic,
  Link,
  Paragraph,
  TiptapVuetify,
  Underline,
} from 'tiptap-vuetify';

export default {
  name: 'InputEditor',
  components: { TiptapVuetify },
  extends: VInput,
  props: {
    value: String,
    label: String,
    placeholder: String,
    /**
     * Shows required asterisk
     */
    required: {
      type: Boolean,
      default: false,
    },
    /**
     * Shows a limit for characters
     */
    counter: { type: Number, default: -1 },
  },
  data() {
    return {
      extensions: [
        Bold,
        Italic,
        Underline,
        Link,
        [
          Heading,
          {
            options: {
              levels: [1, 2, 3],
            },
          },
        ],
        Paragraph,
        History,
        Image,
      ],
      tiptapValue: null,
      firstMount: true,
    };
  },
  computed: {
    counterMessage() {
      if (this.counter === -1) {
        return '';
      } else if (!this.value) {
        return '0' + ' / ' + this.counter;
      } else {
        return this.value.length - 7 + ' / ' + this.counter;
      }
    },
  },
  watch: {
    value(val) {
      this.firstMount = false;
      this.tiptapValue = val;
    },
    tiptapValue(val) {
      this.$emit('input', val);
    },
  },
};
</script>
