import jwtInterceptor from '../../shared/jwtInterceptor';

const actions = {
  async getAllTodos({ commit }) {
    var response = await jwtInterceptor.get('http://localhost:3000/todos');
    if (response && response.data) {
      commit('setTodos', response.data);
    }
  }
};
