import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

const routes = [
  {
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
        meta: { title: 'Dashboard', requiresAuth: true, authorize: ['trainee'] },
        component: () => import('../components/trainee/DashboardContent.vue'),
      },
    ],
  },
  {
    path: '/trainer',
    component: () => import('../views/Dashboard.vue'),
    children: [
      {
        name: 'DashboardTrainer',
        path: 'dashboard',
        meta: { title: 'Dashboard', requiresAuth: true, authorize: ['trainer'] },
        component: () => import('../components/trainer/DashboardContent.vue'),
      },
      {
        name: 'Person',
        path: 'person',
        meta: { title: 'Person', requiresAuth: true, authorize: ['trainer'] },
        component: () => import('../components/trainer/Person.vue'),
      },
      {
        name: 'PersonElastic',
        path: 'person-elastic',
        meta: { title: 'Person', requiresAuth: true, authorize: ['trainer'] },
        component: () => import('../components/trainer/PersonElastic.vue'),
      },
      {
        name: 'PersonAdd',
        path: 'person/add',
        meta: { title: 'Person', requiresAuth: true, authorize: ['trainer'] },
        component: () => import('../components/trainer/PersonAdd.vue'),
      },
      {
        name: 'PersonView',
        path: 'person/view',
        meta: { title: 'Person', requiresAuth: true, authorize: ['trainer'] },
        component: () => import('../components/trainer/PersonView.vue'),
      },
      {
        name: 'Organization',
        path: 'organization',
        meta: { title: 'Organization', requiresAuth: true, authorize: ['trainer'] },
        component: () => import('../components/trainer/Organization.vue'),
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
    // role not authorised so redirect to home page
    if (userRole === 'trainee') {
      return next({ name: 'Dashboard' });
    } else if (userRole === 'trainer') {
      return next({ name: 'DashboardTrainer' });
    }
  }
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
