<template>
  <Portal to="popup">
    <popup :label="label" @close="$router.back()">
      <slot name="backdrop" slot="backdrop" />
      <!-- Use this slot to add a v-overlay or equivalent component -->
      <slot />
    </popup>
  </Portal>
</template>

<script>
import Popup from '../components/Popup.vue';

export default {
  name: 'PopupRouterView',
  props: {
    label: {
      required: true,
      type: String,
    },
  },
  components: {
    Popup,
  },
};
</script>
