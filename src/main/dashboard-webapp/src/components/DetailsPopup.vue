<template>
  <popup-router-view label="details">
    <v-overlay slot="backdrop"></v-overlay>
    <popup-lightbox>
      <!-- Main content here... -->
      <v-card elevation="1" :loading="loading" ma-5 class="mx-auto my-12">
        <template slot="progress">
          <v-progress-linear color="deep-purple" height="10" indeterminate></v-progress-linear>
        </template>
        <slot name="header" />
        <!-- Extra content goes here. -->
        <v-divider class="mx-4" />
        <v-card-title>{{ title }}</v-card-title>
        <v-card-text>
          <v-divider class="mx-4" />
          <!-- TODO -->
          <!-- CONTINUE WITH MAIN CONTENT HERE!!! Highlight the important/top content. -->
          <!-- render values of importantProps -->
          <!-- render values of secondaryProps -->
          <slot> <!-- Custom main content / text --> </slot>
        </v-card-text>
        <v-divider class="mx-4" />
        <slot name="footer" />
      </v-card>
    </popup-lightbox>
  </popup-router-view>
</template>

<script>
import PopupRouterView from '@/views/PopupRouterView';
import PopupLightbox from './PopupLightbox.vue';

export default {
  name: 'DetailsPopup',
  props: {
    title: {
      type: String,
      default: 'Details',
    },
    loading: {
      type: Boolean,
      default: false,
    },
    baseAPI: {
      type: String,
      default: null,
    },
    collection: {
      type: String,
      default: null,
    },
    detailsId: {
      type: String,
      default: null,
    },
    importantProps: {
      type: Array,
      default() {
        return [];
      },
    },
    excludedProps: {
      type: Array,
      default() {
        return [];
      },
    },
  },
  components: {
    PopupRouterView,
    PopupLightbox,
  },
  data: () => ({
    detailsData: {},
    secondaryProps: [],
  }),
  mounted() {
    console.log('[DetailsPopup] Mounted');
    this.getDataFromApi().then(detailsData => {
      this.detailsData = detailsData;
      let allKeys = detailsData.keys();
      this.secondaryProps = allKeys.filter(element => {
        return !this.importantProps.includes(element) || !this.excludedProps.includes(element);
      });
    });
  },
  methods: {
    getDataFromApi() {
      console.log('[DetailsPopup] Loading data');
      this.loading = true;
      return new Promise(resolve => {
        this.axios.get(`${this.baseAPI}/${this.collection}/${this.detailsId}`).then(res => {
          let detailsData = {};
          if (res.status === 200) {
            detailsData = res.data;
          }
          setTimeout(() => {
            this.loading = false;
            resolve(detailsData);
          });
        });
      });
    },
  },
};
</script>
