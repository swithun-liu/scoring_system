/*
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-27 14:59:59
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-27 15:01:16
 */

import axios from 'axios';

const actions = {
  async getThisFile({ commit }, payload) {
    const response = await axios.get('http://localhost:8089/teacher/teacherGetThisFile', { params: payload, responseType: 'arraybuffer' });
    return response;
  }
}

export default {
  namespace: true,
  actions
}
