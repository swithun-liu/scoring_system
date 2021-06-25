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
      <el-submenu index="1">
        <template #title>
          <i class="el-icon-more"></i>
          <span>论文管理</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="profile">
            <span class="el-icon-user"></span>
            <span>个人信息</span>
          </el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group v-if="type[0] === 'student'">
          <el-menu-item index="student_upload_paper">
            <span class="el-icon-upload"></span>
            <span>上传论文</span>
          </el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group v-if="type[0] === 'student'">
          <el-menu-item index="student_my_papers">
            <span class="el-icon-document"></span>
            <span>我的论文</span>
          </el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group v-if="type[0] === 'teacher'">
          <el-menu-item index="professor_students_paper">
            <span class="el-icon-document"></span>
            <span>学生论文</span>
          </el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group v-if="type[0] === 'admin'">
          <el-menu-item index="/admin/score_statistic">
            <span class="el-icon-pie-chart"></span>
            <span>得分总览</span>
          </el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group v-if="type[0] === 'admin'">
          <el-menu-item index="/admin/teacher_task">
            <span class="el-icon-s-claim"></span>
            <span>教师任务</span>
          </el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group v-if="type[0] === 'admin'">
          <el-menu-item index="/admin/tag_statistic">
            <span class="el-icon-price-tag"></span>
            <span>标签统计</span>
          </el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group v-if="type[0] === 'admin'">
          <el-menu-item index="/admin/manage/students">
            <span class="el-icon-lollipop"></span>
            <span>学生管理</span>
          </el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group v-if="type[0] === 'admin'">
          <el-menu-item index="/admin/manage/teachers">
            <span class="el-icon-coffee-cup"></span>
            <span>教师管理</span>
          </el-menu-item>
        </el-menu-item-group>
      </el-submenu>
    </el-menu>
    <el-container>
      <el-header>
        <div class="header-rapper">
          <el-button @click="isCollapse = !isCollapse" class="header-btn el-icon-menu"></el-button>
          <el-button @click="handleSignOut()" class="header-btn el-icon-error"></el-button>
        </div>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
      <el-footer>
        <div class="footer-wrapper">
          <span>论文评审评分系统</span>
        </div>
      </el-footer>
    </el-container>
  </el-container>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  data() {
    return {
      isCollapse: false,
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
