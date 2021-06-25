/*
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-07 14:32:43
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-27 21:25:15
 */
import axios from 'axios'

const actions = {
  async getAllFile() {
    const response = await axios.get('http://localhost:8089/admin/statistic/allScore')
    return response
  },
  async statisticTeacherTask() {
    const response = await axios.get('http://localhost:8089/admin/statistic/teacherTask')
    return response
  },
  async getAllStudents() {
    const response = await axios.get('http://localhost:8089/admin/getAllStudent')
    return response
  },
  async getAllTeachers() {
    const response = await axios.get('http://localhost:8089/admin/getAllTeachers')
    return response
  },
  async editStudent({
    commmit
  }, payload) {
    const response = await axios.post('http://localhost:8089/admin/editStudent', payload)
    return response
  },
  async editTeacher({
    commmit
  }, payload) {
    const response = await axios.post('http://localhost:8089/admin/editTeacher', payload)
    return response
  },
  async setTeacher({
    commmit
  }, payload) {
    const response = await axios.post('http://localhost:8089/admin/setTeacher', payload)
    return response
  }
}

export default {
  namespaced: true,
  actions
}
