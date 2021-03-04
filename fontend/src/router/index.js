import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login.vue'
import StudentHome from '../components/StudentHome.vue'

Vue.use(VueRouter)

const routes = [{
  path: '/login',
  name: 'Login',
  component: Login
},
{
  path: '/student_home',
  name: 'StudentHome',
  component: StudentHome
}
]

const router = new VueRouter({
  routes
})

export default router
