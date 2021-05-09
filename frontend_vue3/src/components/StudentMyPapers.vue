<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-27 10:35:15
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-09 17:14:52
-->

<template>
  <div class="main-wrapper">
    <!-- 文件列表 begin -->
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="id" label="文件id" width="90"></el-table-column>
      <el-table-column prop="name" label="文件名">
        <template #default="scope">
          <el-button
            icon="el-icon-download"
            size="small"
            @click="handleDownload(scope.row.id, scope.row.name)"
          ></el-button>
          {{scope.row.name}}
        </template>
      </el-table-column>
      <el-table-column prop="score" label="分数" width="90"></el-table-column>
      <el-table-column lable="操作">
        <template #default="scope">
          <el-button size="small">更新</el-button>
          <el-button size="small" @click="openCommentDialog(scope.row.id)">回复</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 文件列表 end -->
    <!-- 文件卡片 begin -->
    <!-- <div class="card-wrapper">
      <div class="card" v-for="(file, index) in tableData" :key="index">
        <span class="card-title">{{file.name}}</span>
        <br/>
        <span class="card-abstract">摘要</span>
      </div>
    </div> -->
    <!-- 文件卡片 end-->
    <!-- 评分 Dialog begin -->
    <el-dialog title="评分" v-model="dialogVisible" width="30%" :before-close="handleClose">
      <template #footer>
        <el-slider v-model="fileScore" show-input></el-slider>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleScore()">确 定</el-button>
        </span>
      </template>
    </el-dialog>
    <!-- 评分 Dialog end -->
    <!-- 回复 Dialog begin -->
    <el-dialog title="回复" v-model="commentDialogVisible" width="50%" :before-close="handleCommentDialogClose">
      <template #footer>
        <comment :loading="loading" :data="commentData" @handleReplay="handleReplay($event)"></comment>
        <el-form>
          <el-form-item>
            <span>{{ replayWhichComment }}</span>
            <button v-if="replayWhichComment != '新建评论'" @click="cancleChooseComment()">取消</button>
            <el-input v-model="commentWatiForPush"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click="handleAddComment()">添加</el-button>
          </el-form-item>
        </el-form>
      </template>
    </el-dialog>
    <!-- 回复 Dialog end -->
  </div>
</template>

<script>
import { mapActions } from 'vuex'
import fileDownload from 'js-file-download'
import comment from './comment.vue'

export default {
  components: { comment },
  data() {
    return {
      tableData: [],
      dialogVisible: false,
      commentDialogVisible: false,
      chosedFileId: 0,
      chosedCommentId: -1,
      replayWhichComment: '新建评论',
      fileScore: 0,
      commentData: [],
      commentWatiForPush: '',
      vuexComponentStudent: 'student',
      loading: true
    }
  },
  mounted() {
    this.flashAllFileOfMyStudents()
    this.getComments()
  },
  methods: {
    ...mapActions('student', [
      'studentGetAllMyFileForMyFilePage',
      'studentGetAllComment',
      'studentDownloadThisFile',
      'studentAddCommentForThisFile',
    ]),
    ...mapActions('comment', ['getComments']),
    // 刷新
    flashAllFileOfMyStudents() {
      var _this = this
      this.studentGetAllMyFileForMyFilePage().then((result) => {
        console.log(result.data.data)
        _this.tableData = result.data.data
        _this.$forceUpdate()
      })
    },
    flashComments() {
      const _this = this
      var chosedFileId = _this.chosedFileId
      this.studentGetAllComment({ chosedFileId }).then((res) => {
        _this.commentData = res.data.data
        console.log(_this.commentData)
        _this.$forceUpdate()
        _this.loading = false
      })
    },
    // 打开对话框
    openScoreDialog(id) {
      console.log(id)
      this.chosedFileId = id
      this.dialogVisible = true
    },
    openCommentDialog(id) {
      this.chosedCommentId = -1
      this.chosedFileId = id
      this.commentDialogVisible = true
      this.flashComments()
    },
    // 处理操作
    handleDownload(id, name) {
      this.studentDownloadThisFile({
        fileId: id,
      }).then((res) => {
        fileDownload(res.data, name)
      })
    },
    handleScore() {
      const _this = this
      const chosedFileId = this.chosedFileId
      const fileScore = this.fileScore
      console.log('handleScore')
      this.teacherScoreThisFile({ chosedFileId, fileScore }).then(() => {
        _this.flashAllFileOfMyStudents()
      })
    },
    handleReplay(node) {
      var student = node.data.studentByStudentId
      var teacher = node.data.teacherByTeacherId
      var username = null
      if (student != null) {
        username = student.name
      }
      if (teacher != null) {
        username = teacher.name
      }
      this.replayWhichComment = 'replay ' + username
      this.chosedCommentId = node.data.id
    },
    handleCommentDialogClose() {
      console.log('Dialog closed')
      this.commentDialogVisible = false
      this.loading = true
      this.commentData = []
    },
    handleAddComment() {
      var comment = this.commentWatiForPush
      var chosedFileId = this.chosedFileId
      var chosedCommentId = this.chosedCommentId
      var _this = this
      this.studentAddCommentForThisFile({
        chosedFileId,
        comment,
        chosedCommentId,
      }).then((res) => {
        console.log(res)
        _this.commentWatiForPush = ''
        _this.flashComments()
      })
    },
    cancleChooseComment() {
      this.chosedCommentId = -1
      this.replayWhichComment = '新建评论'
    },
  },
}
</script>

<style>
@import '../assets/css/card.css';
</style>
