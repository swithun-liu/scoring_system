<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-03-06 17:40:49
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-21 15:30:15
-->

<template>
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
</style>
