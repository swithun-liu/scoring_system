<template>
  <remote-script src="../assets/js/openMenuButtonScript.js"></remote-script>
  <el-container>
    <el-menu
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      :collapse="isCollapse"
      default-active="profile"
      :router="true"
    >
      <el-button @click="isCollapse = !isCollapse" class="glass-btn-important el-icon-caret-right"></el-button>
      <el-submenu index="1">
        <template #title>
          <i class="el-icon-location"></i>
          <span>论文管理</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="profile">profile</el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group v-if="type[0] === 'student'">
          <el-menu-item index="student_upload_paper">上传论文</el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group v-if="type[0] === 'student'">
          <el-menu-item index="student_my_papers">我的论文</el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group v-if="type[0] === 'teacher'">
          <el-menu-item index="professor_students_paper">学生论文</el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group v-if="type[0] === 'admin'">
          <el-menu-item index="/admin/score_statistic">得分总览</el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group v-if="type[0] === 'admin'">
          <el-menu-item index="/admin/teacher_task">教师任务</el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group v-if="type[0] === 'admin'">
          <el-menu-item index="/admin/tag_statistic">标签统计</el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group v-if="type[0] === 'admin'">
          <el-menu-item index="/admin/manage/students">学生管理</el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group v-if="type[0] === 'admin'">
          <el-menu-item index="/admin/manage/teachers">教师管理</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
    </el-menu>
    <el-container>
      <el-header>
        <div class="header-rapper">
          <el-button @click="handleSignOut()" class="el-btn-2-glass-btn">退出</el-button>
        </div>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
      <el-footer>Footer</el-footer>
    </el-container>
  </el-container>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  data() {
    return {
      isCollapse: true,
      type: ['student'],
    }
  },
  mounted() {
    this.type = this.getAuthData.userType
  },
  computed: {
    ...mapGetters('auth', ['getAuthData']),
  },
  methods: {
    ...mapActions('auth', ['signOut']),
    handleOpen(key, keyPath) {
      console.log(key, keyPath)
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath)
    },
    handleSignOut() {
      this.signOut().then(() => {
        location.reload()
      })
    },
    openSide() {
      console.log('openSide')
      if (this.isCollapse) {
        var key = ['1']
        this.handleOpen(key, 'keyPath')
      }
      this.isCollapse = !this.isCollapse
    },
  },
}
</script>

<style>
@import '../assets/css/dashboard.css';
</style>
