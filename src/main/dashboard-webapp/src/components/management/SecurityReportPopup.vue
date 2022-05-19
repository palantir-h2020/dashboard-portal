<template>
  <details-popup
    :title="title"
    baseAPI="/api/v1/events"
    :collection="collection"
    :detailsId="$route.query.id"
    :importantProps="importantProps"
    :dataTransformer="dataTransformer"
    :keysTransformer="keysTransformer"
  ></details-popup>
</template>

<script>
import DetailsPopup from '@/components/DetailsPopup';
import util from '@/mixins/util.js';

export default {
  name: 'SecurityReportPopup',
  mixins: [util],
  components: {
    DetailsPopup,
  },
  beforeMount() {
    const reportId = this.$route.query.id;
    const cachedData = this.$store.getters.cachedData('securityReports')[reportId];
    const reportType = cachedData.type;
    if (this.$store.getters.dataCollectionTypes.includes(reportType)) {
      this.collection = this.$store.getters.dataCollections[reportType];
    }

    const typeParts = cachedData.type.split(':');
    const type = typeParts[0];
    const subtype = typeParts.length > 1 ? typeParts[1] : '';
    this.completeReportType = util.methods.uppercaseFirstLetters(`${subtype} ${type}`.trim());

    this.title = `Details of ${this.completeReportType} ${reportId}`;
  },
  data: () => ({
    title: 'Details',
    completeReportType: 'Threat',
    collection: 'incident',
    importantProps: ['description', 'location', 'createdTimestamp'],
  }),
  methods: {
    dataTransformer(propertyName, propertyValue, local) {
      if (this.isEmptyObject(local) || this.isEmptyString(propertyName)) {
        console.error('Empty data transformer input. Input:', propertyName, propertyValue, local);
        return '';
      }

      // Property transformations are added here!
      const propertyTransform = {
        type: () => {
          return `${this.completeReportType} (${propertyValue})`;
        },
        name: () => {
          return `${this.uppercaseFirstLetters(propertyValue)}`;
        },
        createdTimestamp: () => {
          return this.formatDateTime(propertyValue);
        },
      };

      if (propertyName in propertyTransform) {
        return propertyTransform[propertyName]();
      } else return propertyValue;
    },
    keysTransformer(property) {
      const propertyTransform = {
        createdTimestamp: 'Happened on',
      };

      if (property in propertyTransform) {
        return propertyTransform[property];
      } else return this.addSpaces(this.uppercaseFirstLetters(property));
    },
  },
};
</script>
