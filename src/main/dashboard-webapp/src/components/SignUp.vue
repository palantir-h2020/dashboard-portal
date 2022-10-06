<template>
  <v-container class="d-flex flex-column" fluid style="max-width: 429px">
    <v-container class="d-flex flex-column align-center">
      <p class="text-h4 font-weight-bold">
        <v-btn :to="{ name: 'LogIn' }" icon>
          <v-icon>
            mdi-arrow-left
          </v-icon></v-btn
        >
        SIGN UP
      </p>
    </v-container>
    <v-alert type="error" v-if="errorSummary !== null" class="white--text">{{
      errorSummary
    }}</v-alert>
    <v-form v-model="valid" ref="form" :lazy-validation="false" @keyup.native.enter="submit" fluid>
      <v-text-field
        label="Username"
        v-model="user.username"
        placeholder="Enter Your Nickname"
        type="text"
        solo
        :rules="usernameRules"
        data-cy="username"
      ></v-text-field>

      <v-text-field
        label="Email"
        v-model="user.email"
        placeholder="Enter Email"
        type="email"
        solo
        :rules="emailRules"
        data-cy="email"
      ></v-text-field>

      <v-text-field
        label="Password"
        v-model="user.password"
        type="password"
        solo
        placeholder="Enter Password"
        :rules="passwordRules"
        data-cy="password"
      ></v-text-field>

      <v-text-field
        label="Confirm Password"
        v-model="user.confirmPassword"
        type="password"
        solo
        placeholder="Confirm Password"
        :rules="confirmPasswordRules.concat(passwordConfirmationRule)"
        data-cy="confirmPassword"
      ></v-text-field>
      <v-radio-group v-model="user.realmRole" mandatory>
        <template v-slot:label>
          <h3 class="text--primary">What kind of user are you?</h3>
        </template>
        <v-radio label="Network Operator" value="network_operator" color="primary"></v-radio>
        <v-radio label="SME Manager" value="sme_manager" color="primary"></v-radio>
        <v-radio
          label="Security Capabilities Developer"
          value="sc_developer"
          color="primary"
        ></v-radio>
      </v-radio-group>
    </v-form>
    <v-btn block color="secondary" @click="submit" :disabled="!valid || loading" data-cy="register"
      >Register</v-btn
    >
  </v-container>
</template>

<script>
import util from '@/mixins/util.js';
import EventBus from '@/helpers/event-bus.js';

export default {
  name: 'SignUp',
  mixins: [util],
  data: () => ({
    loading: false,
    valid: false,
    user: {
      username: null,
      email: null,
      password: null,
      confirmPassword: null,
      realmRole: 'sme_manager',
      realmRoles: [],
      // organization: null,
    },
    usernameRules: [
      v => !!v || 'Nickname is required',
      v =>
        /^(?=.{4})[a-z\d]*_?[a-z\d]+$/i.test(v) ||
        'Nickname must contain at least 4 characters long, must contain only letters, numbers and an optional underscore',
    ],
    emailRules: [
      v => !!v || 'E-mail is required',
      v => /.+@.+\..+/.test(v) || 'Email must be valid',
    ],
    passwordRules: [v => !!v || 'Password is required'],
    confirmPasswordRules: [v => !!v || 'Confirm password is required'],
    responseStatus: null,
    errorSummary: null,
  }),
  mounted() {
    this.clearLocalStorage();
  },
  methods: {
    submit() {
      console.log('[LogIn] Trying to sign up...');
      this.user.realmRoles.push(this.user.realmRole);
      this.user.role = this.user.realmRole;
      this.loading = true;
      EventBus.$emit('waiting', true);
      this.axios
        .post('/api/v1/auth/sign-up', this.user)
        .then(() => {
          console.log('Success');
          EventBus.$emit('snackbar', {
            text: 'You have been registered successfully.',
            color: 'success',
          });
          this.$router.push(
            this.$route.query.redirect || {
              name: 'LogIn',
            },
          );
        })
        .catch(error => {
          console.log(error);
          this.errorSummary = 'Something went wrong. Try again.';
          if (error.response && error.response.status === 409) {
            EventBus.$emit('snackbar', {
              text: error.response.data,
              color: 'error',
            });
          }
        })
        .finally(() => {
          this.loading = false;
          EventBus.$emit('waiting', false);
        });
    },
  },
  computed: {
    passwordConfirmationRule() {
      return () => this.user.password === this.user.confirmPassword || 'Password must match';
    },
  },
};
</script>
