/*
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-23 10:15:56
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-24 21:33:58
 */

import axios from 'axios';

const actions = {
  async getAllFileOfMyStudents() {
    const response = await axios.get('http://localhost:8089/teacher/getAllFileOfstudentsOfMine');
    return response;
  },
  async teacherGetThisFile({ commit }, payload) {
    const response = await axios.get('http://localhost:8089/teacher/teacherGetThisFile', { params: payload, responseType: 'arraybuffer' });
    return response
  }
}

export default {
  namespaced: true,
  actions
}
