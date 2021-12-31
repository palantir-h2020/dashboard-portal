<template>
  <v-navigation-drawer
    v-model="drawer"
    :mini-variant="mini"
    app
    dark
    id="left-sidebar"
    class="primary"
    :expand-on-hover="mini"
  >
    <v-list-item>
      <v-list-item-content>
        <v-list-item-title class="title d-flex align-end">
          <div class="d-inline"><v-icon>$vuetify.icons.logo</v-icon></div>
          <div class="ml-2 d-inline">Ubitech</div>
        </v-list-item-title>
      </v-list-item-content>
    </v-list-item>
    <v-list nav>
      <v-divider class="my-2"></v-divider>
      <template v-for="item in items">
        <v-row v-if="item.heading" :key="item.heading" align="center">
          <v-col cols="6">
            <v-subheader v-if="item.heading">
              {{ !mini ? item.heading : '' }}
            </v-subheader>
          </v-col>
        </v-row>
        <v-divider v-else-if="item.divider" :key="item.heading" class="my-2"></v-divider>
        <v-list-group
          v-else-if="item.children"
          :key="item.text"
          v-model="item.model"
          :prepend-icon="item.icon"
          :append-icon="'mdi-chevron-down'"
          color="inherit"
        >
          <template v-slot:activator>
            <v-list-item-content>
              <v-list-item-title v-bind:data-cy="item.text">
                {{ item.text }}
              </v-list-item-title>
            </v-list-item-content>
          </template>
          <v-list-item
            v-for="(child, i) in item.children"
            :key="i"
            link
            :to="child.to"
            class="pl-10"
          >
            <v-list-item-action v-if="child.icon">
              <v-icon>{{ child.icon }}</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title v-bind:data-cy="child.text">
                {{ child.text }}
              </v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-group>
        <v-list-item v-else-if="item.text === 'Logout'" :key="item.text" @click="logout">
          <v-list-item-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title v-bind:data-cy="item.text">
              {{ item.text }}
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item v-else :key="item.text" link :to="item.to">
          <v-list-item-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title v-bind:data-cy="item.text">
              {{ item.text }}
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </template>
    </v-list>
    <template v-slot:append>
      <v-divider></v-divider>
      <div class="pa-2" @click.stop="mini = !mini">
        <v-btn tile block text>
          <v-icon left class="mr-6">
            {{ mini ? 'mdi-chevron-double-right' : 'mdi-chevron-double-left' }}
          </v-icon>
          {{ mini ? 'Expand sidebar' : 'Collapse sidebar' }}
        </v-btn>
      </div>
    </template>
  </v-navigation-drawer>
</template>

<script>
import util from '@/mixins/util.js';

export default {
  name: 'LeftSidebar',
  mixins: [util],
  data() {
    return {
      drawer: true,
      mini: null,
    };
  },
  computed: {
    items() {
      if (this.$store.getters.roles === 'trainee') {
        return [
          {
            icon: 'mdi-view-dashboard',
            text: 'Dashboard',
            to: {
              name: 'Dashboard',
            },
          },
        ];
      } else if (this.$store.getters.roles === 'trainer') {
        return [
          {
            icon: 'mdi-view-dashboard',
            text: 'Dashboard',
            to: {
              name: 'DashboardTrainer',
            },
          },
          {
            icon: 'mdi-office-building-outline',
            text: 'Organizations',
            to: {
              name: 'Organization',
            },
          },
          {
            icon: 'mdi-account-group-outline',
            text: 'Persons',
            model: true,
            children: [
              {
                text: 'Persons - RDBMS',
                to: {
                  name: 'Person',
                },
              },
              {
                text: 'Persons - Elastic',
                to: {
                  name: 'PersonElastic',
                },
              },
            ],
          },
        ];
      } else {
        return [{ divider: true }, { heading: 'Empty' }];
      }
    },
  },
};
</script>

<style scoped>
#left-sidebar
  >>> .v-list-group--active
  > .v-list-group__header
  .v-list-group__header__prepend-icon
  .v-icon {
  color: inherit;
}
</style>
