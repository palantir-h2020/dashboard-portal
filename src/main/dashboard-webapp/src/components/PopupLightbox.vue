<template>
  <div class="PopupLightbox">
    <div class="PopupLightbox__content">
      <slot />
    </div>
    <div class="PopupLightbox__header">
      <v-btn color="primary" elevation="2" @click="$parent.$emit('close')">Close</v-btn>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PopupLightbox',
};
</script>

<style>
.PopupLightbox {
  margin: auto;
  max-width: 80%;
}

.PopupLightbox__header {
  text-align: right;
}

.PopupLightbox__content {
  margin-top: 0.5em;
}
</style>
