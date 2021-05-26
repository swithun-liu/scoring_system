/*
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-24 16:29:38
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-25 16:08:27
 */

import axios from 'axios';

const actions = {
  async getProfile() {
    const response = await axios.get('http://localhost:8089/getProfile')
    return response;
  },
  async getQuote() {
    const response = await axios.get('https://quotes.rest/qod')
    return response;
  }
}

export default {
  namespaced: true,
  actions
}
