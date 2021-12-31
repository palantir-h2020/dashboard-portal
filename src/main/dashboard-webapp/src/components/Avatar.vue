<template>
  <v-container class="d-flex flex-column align-center" fluid>
    <div style="max-width: 1000px">
      <div class="d-flex justify-center">
        <v-btn v-if="this.$route.query.redirect" :to="{ name: this.$route.query.redirect }" icon>
          <v-icon>
            mdi-arrow-left
          </v-icon></v-btn
        >
        <h2 class="mb-6">Please select your avatar</h2>
      </div>
      <div
        v-for="n in avatarsSize"
        class="d-inline-block pa-2 avatar"
        :key="n"
        @click="user.avatar = n"
        v-bind:class="{
          selected: user.avatar === n,
        }"
      >
        <img alt="Avatar" :src="require('../assets/avatars/Frame-' + n + '.svg')" />
      </div>
      <v-btn
        block
        class="mt-2"
        color="secondary"
        @click="submit"
        :disabled="!this.user.avatar || loading"
        >Next</v-btn
      >
    </div>
  </v-container>
</template>

<script>
import util from '@/mixins/util.js';

export default {
  name: 'Avatar',
  mixins: [util],
  data: () => ({
    loading: false,
    avatarsSize: parseInt(process.env.VUE_APP_AVATARS_SIZE),
    user: {
      avatar: null,
    },
  }),

  methods: {
    submit() {
      this.loading = true;
      this.axios
        .post('/api/v1/profile/avatar', this.user)
        .then(() => {
          console.log('Success');
          this.$store.commit('setAvatar', this.user.avatar);
          let redirect = this.$route.query.redirect;
          if (redirect) {
            this.$router.push({
              name: redirect,
            });
          } else {
            this.$router.push({
              name: 'Dashboard',
            });
          }
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

<style scoped>
.avatar:hover {
  box-shadow: 0 0 11px white;
  cursor: pointer;
}
.avatar.selected {
  box-shadow: 0 0 11px #4bace9;
  opacity: 0.8;
}
</style>
