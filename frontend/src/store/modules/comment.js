/*
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-04 17:58:19
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-04 20:29:02
 */

// import axios from 'axios'
import store from '../../store/index';

const actions = {
  async getComments({
    commit
  }, payload) {
    const auth = store.getters['auth/getAuthData']
    const userType = auth.userType[0]
    console.log(userType)
    const userName = auth.userName
    console.log(userName)
    if (userType === 0) {
      store.dispatch('student/studentGetAllComment', payload).then((res) => {
        console.log('dispatch student')
        console.log(res)
      })
    } else if (userType === 1) {
      store.dispatch('teacher/teacherGetAllCommentsOfThisFile', payload).then((res) => {
        console.log('dispatch teacher')
        console.log(res)
      })
    }
  }
}

export default {
  namespaced: true,
  actions
}
