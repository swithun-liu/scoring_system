<template>
  <div class="container0">
    <el-container class="container1">
      <el-menu
        default-active="1-4-1"
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose"
        :collapse="isCollapse"
        :router="true"
      >
        <el-button type="success" icon="el-icon-check" circle @click="isCollapse = !isCollapse"></el-button>
        <el-submenu index="1">
          <template #title>
            <i class="el-icon-location"></i>
            <span>论文管理</span>
          </template>
          <el-menu-item-group>
            <el-menu-item index="student_upload_paper">上传论文</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group>
            <el-menu-item index="professor_students_paper">学生论文</el-menu-item>
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
  background-color: #b3c0d1;
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
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  line-height: 160px;
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
</style>
