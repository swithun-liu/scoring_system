<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-03-06 17:40:49
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-05 10:24:58
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
        <input class="my-input" type="username" v-model="LoginForm.username" autocomplete="off" />
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <input class="my-input" type="password" v-model="LoginForm.password" autocomplete="off" />
      </el-form-item>
      <el-form-item>
        <div class="radio-container">
          <div class="tabs">
            <input
              type="radio"
              id="radio-1"
              name="tabs"
              checked
              v-model="LoginForm.usertype"
              value="0"
            />
            <label class="tab" for="radio-1">student</label>
            <input type="radio" id="radio-2" name="tabs" v-model="LoginForm.usertype" value="1" />
            <label class="tab" for="radio-2">teacher</label>
            <span class="glider"></span>
          </div>
        </div>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="login()">提交</el-button>
        <el-button @click="resetForm('LoginForm')">重置</el-button>
      </el-form-item>
    </el-form>
    <span>{{usertype}}</span>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

export default {
  data() {
    return {
      LoginForm: {
        username: '',
        password: '',
        usertype: -1,
      },
    }
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
        usertype: this.LoginForm.usertype,
      })
      if (this.getterLoginStatus === 'success') {
        this.$router.push('/student_home')
      } else {
        alert('failed to login')
      }
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
  },
}
</script>

<style lang="less" scoped>
@import '../assets/css/radioStyle.css';
.login-form-wrapper {
  width: 40%;
  height: 100%;
  margin: 0px auto;
  background: var(--color-gray2);
  box-shadow: 0 8px 32px 0 var(--color-gray2);
  backdrop-filter: blur(8px);
  -webkit-backdrop-filter: blur(8px);
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 0 50px;
}
.my-input {
  border: 0px;
  width: 90%;
  border-radius: 16px !important;
  background: #e0e0e0 !important;
  background-color: #e0e0e0 !important;
  box-shadow: 5px 5px 14px #898989, -5px -5px 14px #ffffff !important;
  padding: 20px;
}

/deep/ .el-form-item__content {
  margin-right: 100px;
  display: flex;
  justify-content: center;
}
</style>
