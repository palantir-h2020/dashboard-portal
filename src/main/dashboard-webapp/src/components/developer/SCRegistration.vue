<template>
  <div>
    <div class="editor-container">
      <yaml-editor v-model="value" />
    </div>
  </div>
</template>
<script>
import YamlEditor from '@/components/inputs/YamlEditor.vue';

const yamlData =
  'hosts: all\nbecome: yes\nbecome_method: sudo\ngather_facts: no\n\ntasks:\n- name: "install { { package_name }}"\npackage:\nname: "{ { package_name }}"\nstate: "{ { state | default(\'present\') }}"';
export default {
  name: 'SCRegistration',
  components: {
    YamlEditor,
  },
  data() {
    return {
      value: yamlData,
    };
  },
};
</script>
<style scoped>
.editor-container {
  position: relative;
  height: 100%;
}
</style>
