import {
  createRouter,
  createWebHashHistory
} from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../components/Login.vue'
import StudentHome from '../components/StudentHome.vue'
import StudentUploadPaper from '../components/StudentUploadPaper.vue';
import ProfessorStudentsPaper from '../components/professorStudentsPaper.vue';
import store from '../store/index';

const routes = [{
  path: '/',
  name: 'Home',
  component: Home,
  meta: {
    requiredAuth: true
  }
},
{
  path: '/about',
  name: 'About',
  // route level code-splitting
  // this generates a separate chunk (about.[hash].js) for this route
  // which is lazy-loaded when the route is visited.
  component: () => import(/* webpackChunkName: "about" */ '../views/About.vue'),
  meta: {
    requiredAuth: true
  }
},
{
  path: '/login',
  name: 'Login',
  component: Login,
  meta: {
    requiredAuth: false
  }
},
{
  path: '/student_home',
  name: 'StudentHome',
  component: StudentHome,
  children: [{
    path: '/student_upload_paper',
    name: 'StudentUploadPaper',
    component: StudentUploadPaper,
    meta: {
      requiredAuth: true
    }
  },
  {
    path: '/professor_students_paper',
    name: 'ProfessorStudentsPaper',
    component: ProfessorStudentsPaper,
    meta: {
      requiredAuth: true
    }
  }
  ],
  meta: {
    requiredAuth: true
  }
}
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  console.log(store.getters['auth/getAuthData'].token);
  /**
   * 检查是否 store 是否保存了 token
   */
  if (!store.getters['auth/getAuthData'].token) {
    /**
     * 如果没有保存, 从 localStorage获取, 并保存在 store
     */
    const access_token = localStorage.getItem('access_token');
    const refresh_token = localStorage.getItem('refresh_token');
    if (access_token) {
      const data = {
        access_token: access_token,
        refresh_token: refresh_token
      };
      store.commit('auth/saveTokenData', data);
    }
  }
  const auth = store.getters['auth/isTokenActive'];
  console.log(auth);

  if (to.fullPath === '/') {
    return next();
  } else if (auth && !to.meta.requiredAuth) {
    console.log('router: -> /dashboard ')
    return next({
      path: '/student_home'
    });
  } else if (!auth && to.meta.requiredAuth) {
    console.log('router: -> /login')
    return next({
      path: '/login'
    });
  }

  return next();
})

export default router
