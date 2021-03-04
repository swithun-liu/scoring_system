<template>
  <div class="login_container">
    <a-form layout="inline" :model="formInline" @submit="handleSubmit" @submit.native.prevent>
      <a-form-item>
        <a-input v-model="formInline.user" placeholder="Username">
          <template #prefix>
            <UserOutlined style="color: rgba(0, 0, 0, 0.25)" />
          </template>
        </a-input>
      </a-form-item>
      <a-form-item>
        <a-input v-model="formInline.password" type="password" placeholder="Password">
          <template #prefix>
            <LockOutlined style="color: rgba(0, 0, 0, 0.25)" />
          </template>
        </a-input>
      </a-form-item>
      <a-form-item>
        <a-button
          type="primary"
          html-type="submit"
          :disabled="formInline.user === '' || formInline.password === ''"
        >Log in</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
import { UserOutlined, LockOutlined } from "@ant-design/icons-vue";
// import { notification } from "ant-design-vue";

export default {
  components: {
    UserOutlined,
    LockOutlined,
  },
  data() {
    return {
      // the data binding object fro login
      formInline: {
        user: "",
        password: "",
      },
    };
  },
  methods: {
    // for login
    handleSubmit(e) {
      console.log(this.formInline);
      var _this = this;
      this.$http
        .post("http://localhost:8088/login", this.formInline)
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
  },
};
</script>

<style lang="less" scoped>
.login_container {
  background-color: aquamarine;
}
</style>
