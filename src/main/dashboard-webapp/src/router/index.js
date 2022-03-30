import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

const routes = [
  {
    // Generic Views / Components
    path: '/',
    component: () => import('../views/Dashboard.vue'),
    meta: { title: '', requiresAuth: true },
    children: [
      {
        name: 'Profile',
        path: 'profile',
        meta: { title: 'Profile' },
        component: () => import('../components/Profile.vue'),
      },
      {
        name: 'Dashboard',
        path: 'dashboard',
        redirect: () => {
          let dashboardType = '/';
          const userRole = localStorage.roles;
          if (userRole === 'network_operator' || userRole === 'sme_manager') {
            dashboardType = `${dashboardType}management`;
          } else if (userRole === 'sc_developer') {
            dashboardType = `${dashboardType}developer`;
          }
          return { path: `${dashboardType}/dashboard` };
        },
      },
    ],
  },
  {
    // Network Operator or SME Manager
    path: '/management',
    component: () => import('../views/Dashboard.vue'),
    children: [
      {
        name: 'ThreatsDashboard',
        path: 'dashboard',
        meta: {
          title: 'Threats Dashboard',
          requiresAuth: true,
          authorize: ['network_operator', 'sme_manager'],
        },
        component: () => import('../components/management/DashboardContent.vue'),
      },
      {
        name: 'SecurityReports',
        path: 'reports',
        meta: {
          title: 'Security Reports',
          requiresAuth: true,
          authorize: ['network_operator', 'sme_manager'],
        },
        component: () => import('../components/management/SecurityReports.vue'),
      },
      {
        name: 'FinancialDashboard',
        path: 'sme/financial',
        meta: {
          title: 'Financial Dashboard',
          requiresAuth: true,
          authorize: ['sme_manager'],
        },
        component: () => import('../components/management/sme/FinancialDashboard.vue'),
      },
      {
        name: 'FinancialStatus',
        path: 'net/financial',
        meta: {
          title: 'Financial Status',
          requiresAuth: true,
          authorize: ['network_operator'],
        },
        component: () => import('../components/management/net/FinancialStatus.vue'),
      },
      {
        // Redirects to appropriate based on role
        name: 'Financial',
        path: 'financial',
        redirect: () => {
          let redirectTarget = '/management/';
          const userRole = localStorage.roles;
          if (userRole === 'network_operator') {
            redirectTarget = `${redirectTarget}net/financial`;
          } else if (userRole === 'sme_manager') {
            redirectTarget = `${redirectTarget}sme/financial`;
          }
          return { path: redirectTarget };
        },
      },
      {
        name: 'SCStatus',
        path: 'sme/capabilities',
        meta: {
          title: 'Security Capabilities Status',
          requiresAuth: true,
          authorize: ['sme_manager'],
        },
        component: () => import('../components/management/sme/SCStatus.vue'),
      },
      {
        name: 'SCManagement',
        path: 'net/capabilities',
        meta: {
          title: 'Security Capabilities Management',
          requiresAuth: true,
          authorize: ['network_operator'],
        },
        component: () => import('../components/management/net/SCManagement.vue'),
      },
      {
        // Redirects to appropriate based on role
        name: 'SecurityCapabilities',
        path: 'capabilities',
        redirect: () => {
          let redirectTarget = '/management/';
          const userRole = localStorage.roles;
          if (userRole === 'network_operator') {
            redirectTarget = `${redirectTarget}net/capabilities`;
          } else if (userRole === 'sme_manager') {
            redirectTarget = `${redirectTarget}sme/capabilities`;
          }
          return { path: redirectTarget };
        },
      },
      {
        name: 'SCMarketplace',
        path: 'capabilities/marketplace',
        meta: {
          title: 'Security Capabilities Marketplace',
          requiresAuth: true,
          authorize: ['network_operator', 'sme_manager'],
        },
        component: () => import('../components/management/SecurityCapabilitiesMarketplace.vue'),
      },
      {
        name: 'RiskAssessment',
        path: 'risk',
        meta: {
          title: 'Risk Assessment',
          requiresAuth: true,
          authorize: ['network_operator', 'sme_manager'],
        },
        component: () => import('../components/management/RiskAssessment.vue'),
      },
      {
        name: 'ThreatSharing',
        path: 'capabilities/marketplace',
        meta: {
          title: 'Threat Sharing',
          requiresAuth: true,
          authorize: ['network_operator', 'sme_manager'],
        },
        component: () => import('../components/management/ThreatSharing.vue'),
      },
    ],
  },
  {
    // SC Developer
    path: '/developer',
    component: () => import('../views/Dashboard.vue'),
    meta: { requiresAuth: true, authorize: ['sc_developer'] },
    children: [
      {
        name: 'DevDashboard',
        path: 'dashboard',
        meta: {
          title: 'Developer Dashboard',
        },
        component: () => import('../components/developer/DashboardContent.vue'),
      },
      {
        name: 'SCRegistration',
        path: 'registration',
        meta: {
          title: 'Security Capability Registration',
        },
        component: () => import('../components/developer/SCRegistration.vue'),
      },
      {
        name: 'SecurityCapabilities',
        path: 'capabilities',
        meta: {
          title: 'Security Capabilities',
        },
        component: () => import('../components/developer/SecurityCapabilities.vue'),
      },
    ],
  },
  {
    path: '/auth',
    component: () => import('../views/Auth.vue'),
    children: [
      {
        name: 'LogIn',
        path: 'login',
        meta: { title: 'Log In' },
        component: () => import('../components/LogIn.vue'),
      },
      {
        name: 'SignUp',
        path: 'sign-up',
        meta: { title: 'Sign Up' },
        component: () => import('../components/SignUp.vue'),
      },
    ],
    meta: { title: 'Log In' },
  },
  {
    path: '/avatar',
    component: () => import('../views/Plain.vue'),
    children: [
      {
        name: 'Avatar',
        path: '',
        meta: { title: 'Avatar', requiresAuth: true },
        component: () => import('../components/Avatar.vue'),
      },
    ],
  },
  {
    path: '/page-401',
    name: 'Page401',
    component: () => import('../views/Page401.vue'),
  },
  {
    path: '/page-403',
    name: 'Page403',
    component: () => import('../views/Page403.vue'),
  },
  {
    path: '*',
    component: () => import('../views/Page404.vue'),
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  // Title
  let globalTitle = process.env.VUE_APP_TITLE;
  if (to.meta.title) {
    document.title = to.meta.title + ' · ' + globalTitle;
  } else {
    document.title = globalTitle;
  }
  // Prevent access to routes;
  if (to.matched.some(record => record.meta.requiresAuth) && !loggedIn()) {
    return next({
      path: process.env.VUE_APP_LOGINPATH,
    });
  }
  // check if route is restricted by role
  let userRole = localStorage.roles;
  if (userRole && to.meta.authorize && !to.meta.authorize.includes(userRole)) {
    if (userRole === 'network_operator' || userRole === 'sme_manager') {
      return next({ name: 'ThreatsDashboard' });
    } else if (userRole === 'sc_developer') {
      return next({ name: 'DevDashboard' });
    }
  }
  // Start websocket if it is not up
  // TODO
  let currentPathName = to.name;
  if (localStorage[currentPathName] && localStorage[currentPathName] !== to.fullPath) {
    next({
      path: localStorage[currentPathName],
    });
  }
  next();
});

function loggedIn() {
  if (localStorage.token) {
    if (localStorage.expires_in_timestamp) {
      let date_now = Date.now();
      let temp = (localStorage.expires_in_timestamp - date_now) / 1000;
      if (temp > 0) {
        return true;
      } else {
        console.log('Refresh token has expired');
        return false;
      }
    } else {
      return false;
    }
  } else {
    return false;
  }
}

export default router;
