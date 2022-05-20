<template>
  <div class="yaml-editor">
    <textarea ref="textarea" />
  </div>
</template>
<script>
import CodeMirror from 'codemirror';
import 'codemirror/addon/lint/lint.css';
import 'codemirror/lib/codemirror.css';
import 'codemirror/theme/monokai.css';
import 'codemirror/mode/yaml/yaml';
import 'codemirror/addon/lint/lint';
import 'codemirror/addon/lint/yaml-lint';
window.jsyaml = require('js-yaml'); // yaml syntax checking core support
export default {
  name: 'YamlEditor',
  // eslint-disable-next-line vue/require-prop-types
  props: ['value'],
  data() {
    return {
      yamlEditor: false,
    };
  },
  watch: {
    value(value) {
      const editorValue = this.yamlEditor.getValue();
      if (value !== editorValue) {
        this.yamlEditor.setValue(this.value);
      }
    },
  },
  mounted() {
    this.yamlEditor = CodeMirror.fromTextArea(this.$refs.textarea, {
      lineNumbers: true, // According to the line Numbers
      mode: 'text/x-yaml', // grammar model
      gutters: ['CodeMirror-lint-markers'], // Grammar checker
      theme: 'monokai', // Editor theme
      lint: true, // Turn on syntax checking
    });
    this.yamlEditor.setValue(this.value);
    this.yamlEditor.on('change', cm => {
      this.$emit('changed', cm.getValue());
      this.$emit('input', cm.getValue());
    });
  },
  methods: {
    getValue() {
      return this.yamlEditor.getValue();
    },
  },
};
</script>
<style scoped>
.yaml-editor {
  height: 100%;
  position: relative;
}
.yaml-editor >>> .CodeMirror {
  height: auto;
  min-height: 300px;
}
.yaml-editor >>> .CodeMirror-scroll {
  min-height: 300px;
}
.yaml-editor >>> .cm-s-rubyblue span.cm-string {
  color: #f08047;
}
</style>
