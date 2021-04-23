
/*
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-23 10:15:56
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-23 14:29:25
 */

import axios from 'axios';

const actions = {
  async getAllFileOfMyStudents() {
    const response = await axios.get('http://localhost:8089/teacher/getAllFileOfstudentsOfMine');
    console.log('getAllFileOfMyStudents')
    return response;
  }
}

export default {
  namespaced: true,
  actions
}
