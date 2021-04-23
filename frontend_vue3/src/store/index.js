/*
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-17 14:25:47
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-23 10:39:43
 */
import { createStore } from 'vuex'
import authModule from './modules/auth';
import teacherModule from './modules/teacher';

const store = createStore({
  state: {},
  mutations: {},
  actions: {},
  modules: {
    auth: authModule,
    teacher: teacherModule
  }
})

export default store;
