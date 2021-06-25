/*
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-22 18:17:12
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-23 20:33:24
 */

import axios from 'axios';

const actions = {
  async getAllTags() {
    const response = await axios.get('http://localhost:8089/tag/getAll')
    const tags = response.data.data
    tags.forEach(element => {
      element.value = element.name
      delete element.name
    });
    return tags;
  },
  async tagStatistic() {
    const response = await axios.get('http://localhost:8089/tag/statistic')
    return response
  },
  async commentAddTag({
    commit
  }, payload) {
    console.log('js 评论添加标签', payload);
    const response = await axios.post('http://localhost:8089/tag/CommentAddTag', payload)
    return response
  }
}

export default {
  namespaced: true,
  actions
}
