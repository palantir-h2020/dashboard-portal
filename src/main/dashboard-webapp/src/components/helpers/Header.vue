<template>
  <v-row>
    <v-col col="12" md="12"
      ><div class="text-h5 ml-2">
        <v-btn v-if="backArrow" :to="{ name: this.backArrow }" icon active-class="no-active">
          <v-icon>
            mdi-arrow-left
          </v-icon></v-btn
        >{{ text }}
        <v-btn v-if="editRouter" @click="onEditItem()" class="ma-2" tile outlined>
          <v-icon left>mdi-pencil</v-icon>
          Edit
        </v-btn>
      </div>
      <v-divider class="my-2"></v-divider
    ></v-col>
  </v-row>
</template>

<script>
export default {
  name: 'Header',
  props: {
    text: String,
    backArrow: String,
    editRouter: String,
    id: [String, Number],
  },
  methods: {
    onEditItem() {
      console.log('Click Edit ' + this.id);
      this.$router.push({
        name: this.editRouter,
        query: { id: this.id },
      });
    },
  },
};
</script>
