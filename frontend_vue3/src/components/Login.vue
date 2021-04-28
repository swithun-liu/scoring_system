<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-03-06 17:40:49
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-28 08:59:42
-->

<template>
  <div class="login-form-wrapper">
    <el-form
      :model="LoginForm"
      status-icon
      :rules="rules"
      ref="LoginForm"
      label-width="100px"
      class="demo-LoginForm"
    >
      <el-form-item label="账户" prop="username">
        <el-input type="username" v-model="LoginForm.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="LoginForm.password" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="用户类型" prop="usertype">
        <el-input v-model="LoginForm.usertype" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="login()">提交</el-button>
        <el-button @click="resetForm('LoginForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';

export default {
  data() {
    return {
      LoginForm: {
        username: '',
        password: '',
        usertype: 0,
      },
    };
  },
  /** 将 auth 中的 getLoginStatus 映射为 getterLoginStatus */
  computed: {
    ...mapGetters('auth', {
      getterLoginStatus: 'getLoginStatus',
    }),
  },
  /** 将 auth 中的 login 映射为 actionLogin */
  methods: {
    ...mapActions('auth', {
      actionLogin: 'login',
    }),
    async login() {
      await this.actionLogin({
        username: this.LoginForm.username,
        password: this.LoginForm.password,
        usertype: this.LoginForm.usertype
      });
      if (this.getterLoginStatus === 'success') {
        this.$router.push('/student_home');
      } else {
        alert('failed to login');
      }
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>

<style>
.login-form-wrapper {
  width: 40%;
  height: 30%;
  margin: 0px auto;
  background: rgba( 255, 255, 255, 0.45 );
  box-shadow: 0 8px 32px 0 rgba( 31, 38, 135, 0.37 );
  backdrop-filter: blur( 8.0px );
  -webkit-backdrop-filter: blur( 8.0px );
  border-radius: 10px;
  border: 1px solid rgba( 255, 255, 255, 0.18 );
  position: absolute;
  top: 160px;
  left: 0px;
  right: 0px;
  padding: 50px;
}
</style>
