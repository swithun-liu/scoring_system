import {
  jwtDecrypt,
  tokenAlive
} from "../../shared/jwtHelper"

import axios from 'axios'

const state = () => ({
  authData: {
    token: "",
    refreshToken: "",
    tokenExp: "",
    userId: "",
    userName: "",
  },
  loginStatus: "",
});

const getters = {
  getLoginStatus(state) {
    return state.loginStatus;
  },
  getAuthData(state) {
    return state.authData;
  },
  isTokenActive(state) {
    console.log("tokenExp : " + state.authData.tokenExp)
    if (!state.authData.tokenExp) {
      return false;
    }
    return tokenAlive(state.authData.tokenExp);
  },
};

const actions = {
  async login({
    commit
  }, payload) {
    console.log("payload: " + payload);
    const response = await axios
      .post("http://localhost:8088/authenticate", payload);
    console.log(response.data.data.token);
    commit('saveTokenData', {
      access_token: response.data.data.token,
      refresh_token: ""
    });
    commit('setLoginStatu', 'success');
  },
};

const mutations = {
  saveTokenData(state, data) {
    localStorage.setItem("access_token", data.access_token);
    localStorage.setItem("refresh_token", data.refresh_token);

    const jwtDecodedValue = jwtDecrypt(data.access_token);
    console.log("username: " + jwtDecodedValue)
    const newTokenData = {
      token: data.access_token,
      refreshToken: data.refresh_token,
      tokenExp: jwtDecodedValue.exp,
      userId: jwtDecodedValue.sub,
      userName: jwtDecodedValue.username,
    };
    state.authData = newTokenData;
  },
  setLoginStatu(state, value) {
    state.loginStatus = value;
  }
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
