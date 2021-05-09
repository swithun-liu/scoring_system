/*
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-28 15:06:41
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-07 20:19:46
 */
import {
  createApp
} from 'vue'
import App from './App.vue'
import router from './router'
import installElementPlus from './plugins/element' // 导入element plust
import './assets/css/global.css' // 导入全局样式表
// axios in vue
import axios from 'axios'
import VueAxios from 'vue-axios'
// import vuex store
import store from './store/index'
// 全局引入 echarts
// import 'echarts'

axios.interceptors.request.use((config) => {
  const authData = store.getters['auth/getAuthData'];
  config.headers.common.Authorization = `bearer ${authData.token}`;
  return config;
});

const app = createApp(App).use(store)
installElementPlus(app)
app.use(router).mount('#app')
app.use(VueAxios, axios)
