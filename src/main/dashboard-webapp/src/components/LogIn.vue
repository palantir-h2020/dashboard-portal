<template>
  <v-container class="d-flex flex-column" fluid style="max-width: 429px">
    <v-container class="d-flex flex-column align-center">
      <p class="text-h4 font-weight-bold">Sign in</p>
    </v-container>
    <v-alert type="error" v-if="errorSummary !== null">{{ errorSummary }}</v-alert>
    <v-form ref="form" :lazy-validation="true" @keyup.native.enter="submit" fluid>
      <v-text-field
        label="Username"
        v-model="username"
        placeholder="Enter Username Here"
        type="text"
        :rules="requiredRule()"
        filled
        autofocus
        solo
        data-cy="username"
      ></v-text-field>

      <v-text-field
        label="Password"
        v-model="password"
        type="password"
        :rules="requiredRule()"
        filled
        solo
        placeholder="Enter Password Here"
        data-cy="password"
      ></v-text-field>
    </v-form>
    <v-btn block color="secondary" @click="submit" :disabled="loading" data-cy="login"
      >Log In</v-btn
    >
    <v-container class="d-flex flex-column align-center">
      <p class="mb-1"><a>Forgot Password?</a></p>
      <p class="primary3--text">
        No Account?
        <router-link :to="{ name: 'SignUp' }">Sign Up.</router-link>
      </p>
    </v-container>
  </v-container>
</template>

<script>
import util from '@/mixins/util.js';

export default {
  name: 'LogIn',
  mixins: [util],
  data: () => ({
    loading: false,
    username: null,
    password: null,
    rememberMe: true,
    responseStatus: null,
    errorSummary: null,
  }),
  mounted() {
    this.clearLocalStorage();
  },
  methods: {
    submit() {
      console.log('[LogIn] Trying to log in...');
      this.$refs.form.validate();
      let credentials = {};
      credentials.username = this.username;
      credentials.password = this.password;
      credentials.grant_type = 'password';
      this.loading = true;
      // Fallback username
      localStorage.userfullname = credentials.username;
      this.axios
        .post('/api/v1/auth/login', credentials)
        .then(res => {
          console.log('Success');
          this.setLocalStorage(res.data);
          this.$router.push({
            name: 'Dashboard',
          });
        })
        .catch(error => {
          if (error.request && error.response.status === 400) {
            this.errorSummary = error.response.data;
          }
        })
        .finally(() => {
          this.loading = false;
        });
    },
  },
};
</script>
