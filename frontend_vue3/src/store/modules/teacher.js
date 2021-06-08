/*
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-23 10:15:56
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-06-07 15:36:24
 */

import axios from 'axios';

const actions = {
  // get file list
  async getAllFileOfMyStudents() {
    const response = await axios.get('http://localhost:8089/teacher/getAllFileOfstudentsOfMine');
    return response;
  },
  // get a file date by file id
  async teacherGetThisFile({ commit }, payload) {
    const response = await axios.get('http://localhost:8089/teacher/teacherGetThisFile', { params: payload, responseType: 'blob' });
    return response;
  },
  // score a file
  async teacherScoreThisFile({ commit }, payload) {
    console.log(payload)
    const reponse = await axios.post('http://localhost:8089/teacher/teacherScoreThisFile', payload);
    return reponse;
  },
  // get comments of a file
  async teacherGetAllCommentsOfThisFile({ commit }, payload) {
    const response = await axios.post('http://localhost:8089/teacher/getAllCommentsOfThisFileOfMine', payload);
    return response;
  },
  // add comment for a file
  async teacherAddCommentThisFile({ commit }, payload) {
    const response = await axios.post('http://localhost:8089/teacher/AddCommentForThisFile', payload);
    return response;
  },
  // update file
  async updateFile({
    commit,
  }, payload) {
    console.log('studentRefreshFile')
    console.log(payload);
    console.log(payload.param)
    console.log(payload.config)
    const response = await axios.post('http://localhost:8089/teacher/updateFile', payload.param, payload.config)
    return response
  },
}

export default {
  namespaced: true,
  actions
}
