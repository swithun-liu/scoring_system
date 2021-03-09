import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import installElementPlus from './plugins/element'// 导入element plust
import './assets/css/global.css'// 导入全局样式表
// axios in vue
import axios from 'axios'
import VueAxios from 'vue-axios'
// import vuex store
import store from './store/index'

const app = createApp(App).use(store)
installElementPlus(app)
app.use(router).mount('#app')
app.use(VueAxios, axios)
