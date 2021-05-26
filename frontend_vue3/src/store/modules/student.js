/*
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-27 10:38:13
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-26 14:43:40
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
  async studentGetAllComment({
    commit
  }, payload) {
    const response = await axios.get('http://localhost:8089/student/studentGetTeacherCommentOfMyFile', {
      params: payload
    });
    return response;
  },
  async studentDownloadThisFile({
    commit
  }, payload) {
    const response = await axios.get('http://localhost:8089/student/downloadThisFile', {
      params: payload,
      responseType: 'arraybuffer'
    });
    return response;
  },
  async studentAddCommentForThisFile({
    commit
  }, payload) {
    const reponse = await axios.post('http://localhost:8089/student/addCommentForThisFile', payload)
    return reponse
  },
  async studentEditFileInfo({
    commit
  }, payload) {
    const response = await axios.post('http://localhost:8089/student/addAbstract', payload)
    return response
  },
  async studentRefreshFile({
    commit,
  }, payload) {
    console.log('studentRefreshFile')
    console.log(payload);
    console.log(payload.param)
    console.log(payload.config)
    const response = await axios.post('http://localhost:8089/student/refreshFile', payload.param, payload.config)
    return response
  },
  async deleteFile({
    commit
  }, payload) {
    console.log('student.js', payload);
    const response = await axios.delete('http://localhost:8089/student/deleteFile/' + payload)
    return response
  }
}

export default {
  namespaced: true,
  actions
}
