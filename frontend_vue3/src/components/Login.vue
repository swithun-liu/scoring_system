
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
    <el-form-item label="确认密码" prop="password">
      <el-input type="password" v-model="LoginForm.password" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm('LoginForm')">提交</el-button>
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
        username: "",
        password: "",
      },
    };
  },
  computed: {
    ...mapGetters('auth',{
      getterLoginStatus:'getLoginStatus'
    })
  },
  methods: {
    ...mapActions('auth',{
      actionLogin:'login'
    }),
    async login(){
      await this.actionLogin({username:this.username,password:this.password});
      if(this.getterLoginStatus == 'success'){
        this.$router.push('/student_home');
      }else{
        alert('failed to login');
      }
    },
    submitForm(formName) {
      console.log(this.LoginForm);
      var _this = this;
      this.$http
        .post("http://localhost:8088/login", this.LoginForm)
        .then((response) => {
          console.log(response);
          if (response.data.succ) {
            _this.$notify.success({
              message: "登陆成功",
            });
            window.sessionStorage.setItem(
              "token",
              response.data.data.pass_word
            );
            if (response.data.data.user_type === 0) {
              this.$router.push("/student_home");
            }
          } else {
            _this.$notify.error({
              message: "登陆失败",
            });
          }
        });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>

<style>
</style>
