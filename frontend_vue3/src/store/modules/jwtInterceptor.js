/*
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-17 14:25:47
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-24 10:24:39
 */
import jwtInterceptor from '../../shared/jwtInterceptor';

const actions = {
  async getAllTodos({ commit }) {
    var response = await jwtInterceptor.get('http://localhost:3000/todos');
    if (response && response.data) {
      commit('setTodos', response.data);
    }
  }
};

export default {
  actions
}
