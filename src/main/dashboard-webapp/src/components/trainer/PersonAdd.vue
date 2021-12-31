<template>
  <v-main>
    <Loader :loading="loading"></Loader>
    <v-container fluid>
      <Header :text="'Person'" :backArrow="'Person'"></Header>
      <v-form ref="form" v-model="valid" :lazy-validation="false">
        <v-subheader>
          Attributes
        </v-subheader>
        <v-row>
          <v-col cols="6">
            <v-text-field
              v-model="person.lastName"
              label="Last Name"
              filled
              :rules="requiredRule()"
              class="required"
              data-cy="lastName"
            ></v-text-field>
          </v-col>
          <v-col cols="6">
            <v-text-field
              v-model="person.firstName"
              label="First Name"
              filled
              :rules="requiredRule()"
              class="required"
              data-cy="firstName"
            ></v-text-field>
          </v-col>
          <v-col cols="6">
            <v-autocomplete
              v-model="person.gender"
              :items="genders"
              label="Gender"
              :item-value="'id'"
              :item-text="'description'"
              return-object
              filled
              clearable
              data-cy="gender"
            ></v-autocomplete>
          </v-col>
          <v-col cols="12">
            <input-date
              :label="'Birth Date'"
              v-model="person.birthDate"
              :data-cy="'birthDate'"
              :max="new Date().toISOString().slice(0, 10)"
            >
            </input-date>
          </v-col>
          <v-col cols="12">
            <input-editor
              :label="'Description'"
              v-model="person.description"
              :placeholder="'Description'"
            ></input-editor>
          </v-col>
        </v-row>
      </v-form>
      <v-row justify="end">
        <v-col class="text-right">
          <v-btn color="success" @click="add()" align-self="end" :disabled="!valid" data-cy="submit"
            >Submit</v-btn
          >
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>

<script>
import Header from '@/components/helpers/Header';
import EventBus from '@/helpers/event-bus.js';
import util from '@/mixins/util.js';
import Loader from '@/components/helpers/Loader.vue';
import InputEditor from '@/components/inputs/InputEditor.vue';
import InputDate from '@/components/inputs/InputDate';

export default {
  name: 'PersonAdd',
  mixins: [util],
  components: {
    Header,
    Loader,
    InputEditor,
    InputDate,
  },
  data() {
    return {
      valid: false,
      loadingComponents: -1,
      person: {
        lastName: null,
        firstName: null,
        gender: null,
        birthDate: null,
        description: null,
      },
      genders: [],
      menuBirthDate: false,
    };
  },
  mounted() {
    this.axios
      .get('/api/v1/persons/genders')
      .then(res => (this.genders = res.data.content))
      .finally(() => {
        this.loadingComponents++;
      });
  },
  methods: {
    add() {
      EventBus.$emit('waiting', true);
      this.axios
        .post('/api/v1/persons', this.person)
        .then(() => {
          console.log('Success');
          EventBus.$emit('snackbar', {
            text: 'Person has been created.',
          });
          this.$router.push({ name: 'Person' });
        })
        .catch(error => {
          console.log({ error });
        })
        .finally(() => {
          EventBus.$emit('waiting', false);
        });
    },
  },
  computed: {
    loading() {
      return this.loadingComponents !== 0;
    },
    birthDateFormatted() {
      return this.formatDate(this.person.birthDate);
    },
  },
  watch: {
    loading(value) {
      let id = this.$route.query.id;
      if (value === false && id) {
        console.log('Loading id: ' + id);
        EventBus.$emit('waiting', true);
        this.axios
          .get('/api/v1/persons/' + id)
          .then(res => {
            this.person = res.data;
          })
          .catch(error => {
            console.log({ error });
            this.$router.push({ name: 'Person' });
          })
          .finally(() => {
            EventBus.$emit('waiting', false);
          });
      }
    },
  },
};
</script>
