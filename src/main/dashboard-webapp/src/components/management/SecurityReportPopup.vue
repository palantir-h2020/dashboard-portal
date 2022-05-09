<template>
  <details-popup
    :title="title"
    baseAPI="/api/v1/events"
    :collection="collection"
    :detailsId="$route.query.id"
    :importantProps="importantProps"
  ></details-popup>
</template>

<script>
import DetailsPopup from '@/components/DetailsPopup';

export default {
  name: 'SecurityReportPopup',
  components: {
    DetailsPopup,
  },
  beforeMount() {
    const reportId = this.$route.query.id;
    const cachedData = this.$store.getters.cachedData('securityReports');
    const reportType = cachedData[reportId].type;
    if (this.$store.getters.dataCollectionTypes.includes(reportType)) {
      this.collection = this.$store.getters.dataCollections[reportType];
    }
  },
  data: () => ({
    title: 'Details',
    collection: 'incident',
    importantProps: ['description'],
  }),
};
</script>
