<template>
  <v-app-bar elevate-on-scroll>
    <v-app-bar-nav-icon @click.stop="$emit('toggle-drawer')"></v-app-bar-nav-icon>
    <v-spacer></v-spacer>
    <v-toolbar-title class="font-weight-bold primary--text">{{
      this.$route.meta.title
    }}</v-toolbar-title>
    <v-spacer></v-spacer>
    <v-menu offset-y min-width="600" max-width="1000">
      <template v-slot:activator="{ on, attrs }">
        <v-btn x-large text tile v-bind="attrs" v-on="on" data-cy="notificationsMenu">
          <notification-bell
            :size="33"
            :count="latestNotifications.length"
            :ding="true"
            :upperLimit="50"
            counterLocation="upperLeft"
            counterStyle="roundRectangle"
            counterBackgroundColor="#FF0000"
            counterTextColor="#FFFFFF"
            iconColor="#000000"
            :animated="true"
          />
        </v-btn>
      </template>
      <v-list v-if="latestNotifications.length > 0" three-line>
        <v-list-item class="h5">
          Latest notifications:
        </v-list-item>
        <v-list-item
          v-for="(item, index) in latestNotifications.slice(-8).reverse()"
          :key="index"
          link
        >
          <!-- Add :to destination link -->
          <v-list-item-title class="title" data-cy="notificationsList">
            {{ uppercaseFirstLetters(item.type) }} -
            {{
              item.type == 'incident'
                ? item.incident.incidentType
                : `${item.action.actionName} by ${item.action.componentType} ${item.action.componentId}`
            }}
            <!-- {{
              item.type == 'incident'
                ? item.incident.detectedIncident
                : item.action.componentType + ' with id' + item.action.componentId
            }} -->
          </v-list-item-title>
          <br />
          <v-list-item-subtitle
            v-html="item[item.type][item.type + 'Description']"
          ></v-list-item-subtitle>
        </v-list-item>
      </v-list>
      <v-list v-else>
        <v-list-item><v-list-item-subtitle>No notifications</v-list-item-subtitle></v-list-item>
      </v-list>
    </v-menu>
    <v-tooltip bottom v-if="logoutTimer">
      <template v-slot:activator="{ on, attrs }">
        <div v-on="on">
          <v-btn text v-bind="attrs" v-on="on" @click="refreshToken">
            <v-icon left>mdi-progress-clock</v-icon>
            {{ countDownFormatted }}
          </v-btn>
        </div>
      </template>
      <span>Click for refresh</span>
    </v-tooltip>
    <v-menu offset-y min-width="240" max-width="500">
      <template v-slot:activator="{ on, attrs }">
        <v-btn x-large text tile v-bind="attrs" v-on="on" data-cy="userMenu">
          <v-avatar size="40px">
            <img
              alt="Avatar"
              :src="require('@/assets/avatars/Frame-' + $store.getters.avatar + '.svg')"
            />
          </v-avatar>
          <v-icon>mdi-chevron-down</v-icon>
        </v-btn>
      </template>
      <v-list class="pl-2">
        <v-list-item>
          <v-list-item-title class="title" data-cy="userFullName">{{
            $store.getters.fullName
          }}</v-list-item-title>
        </v-list-item>
        <v-list-item>
          <v-list-item-title data-cy="userRole">
            Role:
            <b
              >{{
                uppercaseWords(uppercaseFirstLetters($store.getters.roles.replace('_', ' ')), 3)
              }}
            </b>
          </v-list-item-title>
        </v-list-item>
        <v-divider></v-divider>
      </v-list>
      <v-list class="pl-2">
        <v-list-item link :to="{ name: 'Profile' }">
          <v-list-item-title>Profile</v-list-item-title>
        </v-list-item>
        <v-list-item @click="logout">
          <v-list-item-title data-cy="logout">Logout</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
  </v-app-bar>
</template>

<script>
import util from '@/mixins/util.js';
import { mapGetters } from 'vuex';
import NotificationBell from 'vue-notification-bell';

export default {
  name: 'TopMenu',
  mixins: [util],
  components: {
    NotificationBell,
  },
  props: {
    /**
     * Shows a count down timer based on refresh token expiration time
     */
    logoutTimer: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      countDown: 0,
      helpUrl: process.env.VUE_APP_HELP_URL,
    };
  },
  created() {
    this.countDownTimer();
  },
  methods: {
    countDownTimer() {
      if (localStorage.expires_in_timestamp) {
        let date_now = Date.now();
        let temp = (localStorage.expires_in_timestamp - date_now) / 1000;
        if (temp > 0) {
          setTimeout(() => {
            temp -= 1;
            this.countDown = temp;
            this.countDownTimer();
          }, 1000);
        } else {
          console.log('Refresh token has expired - go to logout');
          this.logout();
        }
      }
    },
    refreshToken() {
      this.axios.get('/api/v1/service/dummy');
    },
  },
  computed: {
    countDownFormatted() {
      return new Date(this.countDown * 1000).toISOString().substr(11, 8);
    },
    ...mapGetters({
      latestNotifications: 'notificationList',
    }),
  },
};
</script>
