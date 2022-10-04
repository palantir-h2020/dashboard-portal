<template>
  <v-main>
    <div id="financialDashboard">
      <v-row cols="12" justify="center" class="pa-5">
        <v-spacer></v-spacer>
        <v-spacer></v-spacer>
        <v-spacer></v-spacer>
        <v-card elevation="2" outlined>
          <v-card-actions class="justify-center">
            <h1 class="text--primary">{{ charges }}€</h1>
          </v-card-actions>
          <v-card-title>Current Charges</v-card-title>
        </v-card>
        <v-spacer></v-spacer>
        <v-card elevation="2" outlined>
          <v-card-actions class="justify-center">
            <h1 class="text--primary">{{ slaIncidents }}</h1>
          </v-card-actions>
          <v-card-title>SLA Incidents</v-card-title>
        </v-card>
        <v-spacer></v-spacer>
        <v-card elevation="2" outlined>
          <v-card-actions class="justify-center">
            <h1 class="text--primary">{{ subscribedOfferings }}</h1>
          </v-card-actions>
          <v-card-title>Subscribed Offerings</v-card-title>
        </v-card>
        <v-spacer></v-spacer>
        <v-spacer></v-spacer>
        <v-spacer></v-spacer>
      </v-row>
      <v-row cols="12" justify="center"> <v-spacer></v-spacer> </v-row>
      <v-row cols="12">
        <v-tabs v-model="tab" fixed-tabs>
          <v-tab v-for="item in items" :key="item.tab">
            {{ item.tab }}
          </v-tab>
        </v-tabs>
      </v-row>
      <v-spacer></v-spacer>
      <v-row cols="12">
        <v-col cols="12" md="12">
          <v-tabs-items v-model="tab" class="pa-15">
            <v-tab-item v-for="item in items" :key="item.tab">
              <Table
                ref="financialTable"
                :headers="item.headers"
                :dummyData="item.data"
                noDelete
                highlight
                singleHighlight
              />
            </v-tab-item>
          </v-tabs-items>
        </v-col>
      </v-row>
    </div>
  </v-main>
</template>

<script>
import Table from '@/components/Table';

export default {
  name: 'FinancialDashboard',
  components: {
    Table,
  },
  data() {
    return {
      windowHeight: window.innerHeight,
      windowWidth: window.innerWidth,
      charges: 0,
      slaIncidents: 0,
      subscribedOfferings: 0,
      tab: null,
      items: [
        {
          tab: 'Subscribed Offers',
          headers: [
            {
              text: 'Offer Id',
              value: 'id',
              sortable: false,
            },
            {
              text: 'Nature',
              value: 'nature',
              sortable: false,
            },
            {
              text: 'Motive',
              value: 'motive',
              sortable: false,
            },
            {
              text: 'Monthly Cost',
              value: 'monthlyCost',
              sortable: false,
            },
          ],
          data: {
            items: [
              {
                id: 1,
                nature: 'Mitigated risks (RFM)',
                motive: 'Operation of SC#1, SC#2, SC#3',
                monthlyCost: '70€',
              },
              {
                id: 2,
                nature: 'Breach response (TAR)',
                motive: 'Operation of SC#N',
                monthlyCost: '10€',
              },
              {
                id: 3,
                nature: 'Breach response (TAR)',
                motive: 'Operation of SC#P',
                monthlyCost: '10€',
              },
            ],
            total: 3,
          },
        },
        {
          tab: 'Billing Events',
          headers: [
            {
              text: 'Date',
              value: 'date',
              sortable: false,
            },
            {
              text: 'Nature',
              value: 'nature',
              sortable: false,
            },
            {
              text: 'Motive',
              value: 'motive',
              sortable: false,
            },
            {
              text: 'Offer Id',
              value: 'offerId',
              sortable: false,
            },
          ],
          data: {
            items: [
              {
                date: '03/10/22 18:35',
                nature: 'SLA',
                motive: 'SC#N restored (no SLA violation)',
                offerId: 2,
              },
              {
                date: '03/10/22 18:30',
                nature: 'SLA',
                motive: 'Fault detected in SC#N',
                offerId: 2,
              },
              {
                date: '02/10/22 12:44',
                nature: 'Instantiation',
                motive: 'Deploying SC#P',
                offerId: 3,
              },
              {
                date: '02/10/22 12:43',
                nature: 'Subscription',
                motive: 'Subscription to SC#P license',
                offerId: 3,
              },
              {
                date: '03/09/22 16:51',
                nature: 'Instantiation',
                motive: 'Deploying SC#N',
                offerId: 2,
              },
              {
                date: '03/09/22 16:49',
                nature: 'Subscription',
                motive: 'Subscription to SC#N license',
                offerId: 2,
              },
              {
                date: '01/08/22 09:31',
                nature: 'Instantiation',
                motive: 'Deploying SC#1, SC#2 and SC#3',
                offerId: 1,
              },
              {
                date: '01/08/22 09:30',
                nature: 'Subscription',
                motive: 'Subscription to SC#1, SC#2, SC#3 licenses',
                offerId: 1,
              },
            ],
            total: 8,
          },
        },
        {
          tab: 'Past Invoices',
          headers: [
            {
              text: 'Period',
              value: 'period',
              sortable: false,
            },
            {
              text: 'Charged Items',
              value: 'items',
              sortable: false,
            },
            {
              text: 'Cost',
              value: 'cost',
              sortable: false,
            },
          ],
          data: {
            items: [
              {
                period: 'October 2022',
                items: 'Offers 1, 2 and 3',
                cost: '90€',
              },
              {
                period: 'September 2022',
                items: 'Offers 1 and 2',
                cost: '80€',
              },
              {
                period: 'August 2022',
                items: 'Offer 1',
                cost: '70€',
              },
            ],
            total: 3,
          },
        },
      ],
    };
  },
  mounted() {
    this.$nextTick(() => {
      window.addEventListener('resize', this.onResize);
    });
    // DUMMY DATA!
    this.charges = 90;
    this.slaIncidents = 0;
    this.subscribedOfferings = 3;
  },

  beforeDestroy() {
    window.removeEventListener('resize', this.onResize);
  },

  methods: {
    onResize() {
      this.windowHeight = window.innerHeight;
      this.windowWidth = window.innerWidth;
    },
  },
};
</script>
