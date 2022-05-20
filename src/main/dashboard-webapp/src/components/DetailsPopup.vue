<template>
  <popup-router-view label="details">
    <v-overlay slot="backdrop" />
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
        <v-divider class="mx-4" />
        <v-card-text>
          <v-row
            v-for="property in importantProps"
            :key="property"
            align="center"
            class="mx-0 text--primary"
          >
            {{ keysTransformer(property) }}: {{ detailsData[property] }} <br /><br />
          </v-row>
          <v-row v-for="property in secondaryProps" :key="property" align="center" class="mx-0">
            {{ keysTransformer(property) }}: {{ detailsData[property] }} <br /><br />
          </v-row>
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
import util from '@/mixins/util.js';

export default {
  name: 'DetailsPopup',
  mixins: [util],
  props: {
    title: {
      type: String,
      default: 'Details',
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
      type: [String, Number],
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
    keysTransformer: {
      // Invoked on render
      type: Function,
      // USE THIS default AS BASIS for override!
      default: property => {
        return util.methods.addSpaces(util.methods.uppercaseFirstLetters(property)); // The default is best to be just this!
      },
    },
    dataTransformer: {
      // Invoked after fetch.
      type: Function,
      // USE THIS default AS BASIS for override!
      default: (propertyName, propertyValue, local) => {
        if (util.methods.isEmptyObject(local) || util.methods.isEmptyString(propertyName)) {
          console.error('Empty data transformer input. Input:', propertyName, propertyValue, local);
          return ''; // Always has to return some value!
        }
        return propertyValue; // The default should be just this!
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
    loading: false,
  }),
  mounted() {
    this.$store.commit('freezeUnderlayState');
    console.log('[DetailsPopup] Mounted');
    this.getDataFromApi().then(detailsData => {
      console.log(detailsData);
      this.detailsData = detailsData;
      let allKeys = Object.keys(detailsData);
      this.secondaryProps = allKeys.filter(element => {
        return !this.importantProps.includes(element) && !this.excludedProps.includes(element);
      });
    });
  },
  destroyed() {
    this.$store.commit('unfreezeUnderlayState');
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
            // Invoke data transformer
            for (const key in detailsData) {
              detailsData[key] = this.dataTransformer(key, detailsData[key], this);
            }
            resolve(detailsData);
          });
        });
      });
    },
  },
};
</script>
