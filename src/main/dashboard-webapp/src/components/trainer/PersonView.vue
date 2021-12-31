<template>
  <v-main>
    <Loader :loading="loading"></Loader>
    <v-container fluid>
      <Header
        :text="'Person - ' + person.lastName"
        :backArrow="'Person'"
        :editRouter="'PersonAdd'"
        :id="person.id"
      ></Header>
      <div class="px-6">
        <v-row>
          <v-col cols="6">
            <div class="font-weight-bold">Last Name</div>
            <div>{{ this.person.lastName }}</div>
          </v-col>
          <v-col cols="6">
            <div class="font-weight-bold">Last Name</div>
            <div>{{ this.person.lastName }}</div>
          </v-col>
          <v-col cols="6">
            <div class="font-weight-bold">Gender</div>
            <div>{{ this.person.gender ? this.person.gender.description : '' }}</div>
          </v-col>
          <v-col cols="6">
            <div class="font-weight-bold">Birth Date</div>
            <div>{{ formatDate(this.person.birthDate) }}</div>
          </v-col>
          <v-col cols="12">
            <div class="font-weight-bold">Description</div>
            <div v-html="person.description"></div>
          </v-col>
        </v-row>
      </div>
    </v-container>
  </v-main>
</template>

<script>
import Header from '@/components/helpers/Header';
import util from '@/mixins/util.js';
import Loader from '@/components/helpers/Loader.vue';

export default {
  name: 'PersonView',
  mixins: [util],
  components: {
    Header,
    Loader,
  },
  data() {
    return {
      loadingComponents: -1,
      person: {
        lastName: null,
        firstName: null,
        gender: null,
        birthDate: null,
        description: null,
      },
    };
  },
  mounted() {
    let id = this.$route.query.id;
    this.axios
      .get('/api/v1/persons/' + id)
      .then(res => (this.person = res.data))
      .finally(() => {
        this.loadingComponents++;
      });
  },
  computed: {
    loading() {
      return this.loadingComponents !== 0;
    },
  },
};
</script>
