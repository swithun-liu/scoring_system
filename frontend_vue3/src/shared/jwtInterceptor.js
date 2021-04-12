import axios from 'axios';
import store from '../store/index';

const jwtInterceptor = axios.create({});

jwtInterceptor.interceptors.request.use((config) => {
  const authData = store.getters['auth/getAuthData'];
  config.headers.common.Authorization = `bearer ${authData.token}`;
  return config;
});
export default jwtInterceptor;
