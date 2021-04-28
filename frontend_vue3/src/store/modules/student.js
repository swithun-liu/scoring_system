/*
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-27 10:38:13
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-27 16:29:41
 */

import axios from 'axios';

const actions = {
  async studentGetAllMyFile() {
    const response = await axios.get('http://localhost:8089/teacher/studentGetAllMyFile');
    return response;
  },
  async studentGetAllMyFileForMyFilePage() {
    const response = await axios.get('http://localhost:8089/student/studentGetMyPaperForMyPaper');
    return response;
  },
  async studentGetAllComment({ commit }, payload) {
    const response = await axios.get('http://localhost:8089/student/studentGetTeacherCommentOfMyFile', { params: payload });
    return response;
  },
  async studentDownloadThisFile({ commit }, payload) {
    const response = await axios.get('http://localhost:8089/student/downloadThisFile', { params: payload, responseType: 'arraybuffer' });
    return response;
  }
}

export default {
  namespaced: true,
  actions
}
