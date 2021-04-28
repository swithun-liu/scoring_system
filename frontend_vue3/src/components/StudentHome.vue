<template>
  <div class='container0'>
    <remote-script src='../assets/js/openMenuButtonScript.js'></remote-script>
    <el-container class='container1'>
      <el-menu
        default-active='1-4-1'
        class='el-menu-vertical-demo gray1'
        @open='handleOpen'
        @close='handleClose'
        :collapse='isCollapse'
        :router='true'
      >
        <el-button @click='isCollapse = !isCollapse' class="custom-btn btn-16 menu-btn">>>></el-button>
        <el-submenu index='1'>
          <template #title>
            <i class='el-icon-location'></i>
            <span>论文管理</span>
          </template>
          <el-menu-item-group>
            <el-menu-item index='student_upload_paper' v-if="type == 'student'">上传论文</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group>
            <el-menu-item index='student_my_papers' v-if="type == 'student'">我的论文</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group>
            <el-menu-item index='professor_students_paper' v-if="type == 'teacher'">学生论文</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
      </el-menu>
      <el-container>
        <el-header>Header</el-header>
        <el-main>
          <router-view></router-view>
        </el-main>
        <el-footer>Footer</el-footer>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isCollapse: true,
      type: 'student'
    };
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log('handleOpen');
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log('handleColse');
      console.log(key, keyPath);
    },
  },
};
</script>

<style>
/* layout */
.container1 {
  height: 100%;
}
.el-header,
.el-footer {
  background-color: var(--color-gray1);
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #d3dce6;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  background-color: var(--color-gray0);
  background-image: linear-gradient(315deg, #d6fffa 0%, var(--color-gray0) 18%);
  color: #333;
  text-align: center;
  border: 1px solid rgba(255, 255, 255, 0.18);
  box-shadow: 0 8px 20px 0 rgba(31, 38, 135, 0.37);
  border-radius: 30px;
  border: 1px solid rgba(255, 255, 255, 0.18);
  margin: 25px;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}

/* Aside */
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}

.el-menu {
  border-right: 0px !important;
}

</style>
