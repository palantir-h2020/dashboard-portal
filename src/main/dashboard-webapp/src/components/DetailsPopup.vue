<template>
  <popup-router-view label="details">
    <v-overlay slot="backdrop"></v-overlay>
    <popup-lightbox>
      <!-- Main content here... -->
      <v-banner> placeholder </v-banner>
    </popup-lightbox>
  </popup-router-view>
</template>

<script>
import PopupRouterView from '@/views/PopupRouterView';
import PopupLightbox from './PopupLightbox.vue';

export default {
  name: 'DetailsPopup',
  components: {
    PopupRouterView,
    PopupLightbox,
  },
};
</script>
