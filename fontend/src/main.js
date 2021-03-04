import Vue from 'vue'
import App from './App.vue'
import router from './router'

// 引入全局样式表
import './assets/css/global.css'
// 引入ant-design UI框架
import './plugins/ant-design-vue.js'

import { notification } from 'ant-design-vue'

// 在vue中使用axios
import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.prototype.$notify = notification;

Vue.use(VueAxios, axios)

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
