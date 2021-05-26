/*
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-17 14:25:47
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-24 16:32:57
 */
import {
  createStore
} from 'vuex'
import authModule from './modules/auth'
import teacherModule from './modules/teacher'
import studentModule from './modules/student'
import commentModule from './modules/comment'
import adminMoudule from './modules/admin'
import tagMoudule from './modules/tag';
import profileModule from './modules/profile'

const store = createStore({
  state: {},
  mutations: {},
  actions: {},
  modules: {
    auth: authModule,
    teacher: teacherModule,
    student: studentModule,
    comment: commentModule,
    admin: adminMoudule,
    tag: tagMoudule,
    profile: profileModule
  }
})

export default store;
