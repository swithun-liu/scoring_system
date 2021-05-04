/*
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-03-09 13:23:57
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-03 20:36:27
 */
import {
  jwtDecrypt,
  tokenAlive
} from '../../shared/jwtHelper'

import axios from 'axios'

const state = () => ({
  authData: {
    token: '',
    refreshToken: '',
    tokenExp: '',
    userId: '',
    userName: '',
    userType: []
  },
  loginStatus: '',
});

const getters = {
  getLoginStatus(state) {
    return state.loginStatus;
  },
  getAuthData(state) {
    return state.authData;
  },
  isTokenActive(state) {
    console.log('tokenExp : ' + state.authData.tokenExp)
    if (!state.authData.tokenExp) {
      return false;
    }
    return tokenAlive(state.authData.tokenExp);
  },
};

const actions = {
  /** 登陆 action */
  async login({
    commit
  }, payload) {
    console.log('payload: {' + payload.username + ', ' + payload.password + '}');
    const response = await axios
      .post('http://localhost:8089/authenticate', payload);
    console.log(response.data.data.token);
    /** 调用 mutations # 保存 token */
    commit('saveTokenData', {
      access_token: response.data.data.token,
      refresh_token: ''
    });
    commit('setLoginStatu', 'success');
  },
  async getMyPaper() {
    const response = await axios.get('http://localhost:8089/student/studentgetmypaper');
    return response;
  },
  async signOut({
    commit
  }) {
    commit('signOut')
  }
};

const mutations = {
  /** 用于保存 token */
  saveTokenData(state, data) {
    localStorage.setItem('access_token', data.access_token);
    localStorage.setItem('refresh_token', data.refresh_token);

    const jwtDecodedValue = jwtDecrypt(data.access_token);
    console.log('jwtDecodedValue')
    console.log(jwtDecodedValue)
    const newTokenData = {
      token: data.access_token,
      refreshToken: data.refresh_token,
      tokenExp: jwtDecodedValue.exp,
      userId: jwtDecodedValue.sub,
      userName: jwtDecodedValue.sub,
      userType: jwtDecodedValue.userType
    };
    state.authData = newTokenData;
  },
  setLoginStatu(state, value) {
    state.loginStatus = value;
  },
  signOut(state) {
    localStorage.removeItem('access_token')
    console.log('token exp = null')
    state.tokenExp = null
  }
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
