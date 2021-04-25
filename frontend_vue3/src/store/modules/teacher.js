/*
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-23 10:15:56
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-25 15:14:12
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
  },
  async teacherScoreThisFile({ commit }, payload) {
    console.log(payload)
    const reponse = await axios.post('http://localhost:8089/teacher/teacherScoreThisFile', payload);
    return reponse
  }
}

export default {
  namespaced: true,
  actions
}
