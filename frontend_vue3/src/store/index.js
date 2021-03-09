import { createStore } from 'vuex'
import authModule from './modules/auth';

const store = createStore({
  state: {},
  mutations: {},
  actions: {},
  modules: {
    auth: authModule
  }
})

export default store;
