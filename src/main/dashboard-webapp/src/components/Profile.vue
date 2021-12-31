<template>
  <v-main>
    <v-container fluid>
      <teacher
        :text="
          'Update user Avatar. Update user Timezone. Update user Date Format. These changes take place through keycloak and jwt attributes. Save state to vuex(if multiple tabs are open, the state will change across all tabs). All datetime are returned with utc format from the backend, so the front end handles the required transformations.'
        "
      ></teacher>
      <Header :text="'Profile Settings'"></Header>
      <div class="px-6">
        <v-row>
          <v-col cols="2">
            <v-subheader>
              Profile
            </v-subheader>
          </v-col>
          <v-col cols="10">
            <v-row>
              <v-col cols="12">
                <div class="font-weight-bold">Avatar</div>
                <v-avatar size="48px">
                  <img
                    alt="Avatar"
                    :src="require('@/assets/avatars/Frame-' + $store.getters.avatar + '.svg')"
                  />
                </v-avatar>
                <v-btn
                  color="secondary"
                  :to="{ name: 'Avatar', query: { redirect: 'Profile' } }"
                  small
                  class="ml-2"
                  >Change avatar</v-btn
                >
              </v-col>
              <v-col cols="12">
                <div class="font-weight-bold">Full name</div>
                <div>{{ $store.getters.fullName }}</div>
              </v-col>
              <v-col cols="12">
                <div class="font-weight-bold">Email</div>
                <div>{{ user.email }}</div>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
        <v-divider class="my-6"></v-divider>
        <v-row>
          <v-col cols="2">
            <v-subheader>
              Timezone and Region
            </v-subheader>
          </v-col>
          <v-col cols="10">
            <v-row>
              <v-col cols="12">
                <v-autocomplete
                  :value="this.$store.getters.timezone"
                  @change="updateTimezone($event)"
                  :items="getTimezones()"
                  label="Timezone"
                  filled
                  data-cy="timezone"
                ></v-autocomplete>
              </v-col>
              <v-col cols="12">
                <v-autocomplete
                  :value="this.$store.getters.dateFormat"
                  @change="updateDateFormat($event)"
                  :items="getDateFormats()"
                  label="Date Format"
                  filled
                  data-cy="dateFormat"
                ></v-autocomplete>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </div>
    </v-container>
  </v-main>
</template>

<script>
import util from '@/mixins/util.js';
import Header from '@/components/helpers/Header';
import EventBus from '@/helpers/event-bus.js';
import Teacher from '@/components/helpers/Teacher';

export default {
  name: 'Profile',
  mixins: [util],
  components: {
    Header,
    Teacher,
  },
  data: () => ({
    user: {
      email: null,
      timezone: null,
      dateFormat: null,
    },
  }),
  mounted() {
    this.axios.get('/api/v1/profile').then(res => (this.user = res.data));
  },
  methods: {
    updateTimezone(event) {
      console.log(event);
      this.loading = true;
      this.user.timezone = event;
      this.axios
        .post('/api/v1/profile/timezone', this.user)
        .then(() => {
          console.log('Success');
          EventBus.$emit('snackbar', {
            text: 'Timezone has been updated.',
          });
          this.$store.commit('setTimezone', this.user.timezone);
        })
        .catch(error => {
          console.log(error);
        })
        .finally(() => {
          this.loading = false;
        });
    },
    updateDateFormat(event) {
      console.log(event);
      this.loading = true;
      this.user.dateFormat = event;
      this.axios
        .post('/api/v1/profile/date-format', this.user)
        .then(() => {
          console.log('Success');
          EventBus.$emit('snackbar', {
            text: 'Date format has been updated.',
          });
          this.$store.commit('setDateFormat', this.user.dateFormat);
        })
        .catch(error => {
          console.log(error);
        })
        .finally(() => {
          this.loading = false;
        });
    },
  },
};
</script>
