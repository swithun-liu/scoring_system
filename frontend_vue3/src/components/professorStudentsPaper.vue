<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-17 14:26:03
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-24 20:49:16
-->

<template>
  <!-- 文件列表 begin -->
  <el-table :data="tableData" style="width: 100%">
    <el-table-column prop="id" label="文件id" width="90"></el-table-column>
    <el-table-column prop="name" label="文件名"></el-table-column>
    <el-table-column prop="studentId" label="学生id" width="90"></el-table-column>
    <el-table-column prop="studentName" label="学生名" width="120"></el-table-column>
    <el-table-column prop="score" label="分数" width="90"></el-table-column>
    <el-table-column lable="操作" width="300">
      <template #default="scope">
        <el-button
          icon="el-icon-download"
          size="small"
          class="el-btn-2-glass-btn table-btn"
          @click="handleDownload(scope.row.id, scope.row.name)"
        ></el-button>
        <el-button size="small" icon="el-icon-refresh" class="el-btn-2-glass-btn table-btn"></el-button>
        <el-button
          size="small"
          icon="el-icon-s-comment"
          class="el-btn-2-glass-btn table-btn"
          @click="openCommentDialog(scope.row.id)"
        ></el-button>
        <el-button
          size="small"
          icon="el-icon-edit-outline"
          class="el-btn-2-glass-btn table-btn"
          @click="openScoreDialog(scope.row.id)"
        ></el-button>
      </template>
    </el-table-column>
  </el-table>
  <!-- 评分 Dialog begin -->
  <teleport to="body">
    <el-dialog
      title="评分"
      v-model="dialogVisible"
      width="30%"
      :before-close="handleCommentDialogClose"
    >
      <template #footer>
        <el-slider v-model="fileScore" show-input></el-slider>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleScore()">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </teleport>
  <!-- 评分 Dialog end -->
  <!-- 文件列表 end-->
  <!-- 回复 Dialog begin -->
  <teleport to="body">
    <el-dialog
      title="回复"
      v-model="commentDialogVisible"
      width="80%"
      :before-close="handleClose"
      custom-class="replay-dialog"
    >
      <comment
        :loading="loading"
        :data="commentData"
        @handleReplay="handleReplay($event)"
        @refresh-data="flashComments"
      ></comment>
      <template #footer>
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
  </teleport>
  <!-- 回复 Dialog end-->
  <button class="custom-btn btn-13">
    <span>Read More</span>
  </button>
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
      commentOriginData: [],
      commentWatiForPush: '',
      loading: true,
    }
  },
  mounted() {
    this.flashAllFileOfMyStudents()
  },
  methods: {
    ...mapActions('teacher', [
      'getAllFileOfMyStudents',
      'teacherGetThisFile',
      'teacherScoreThisFile',
      'teacherGetAllCommentsOfThisFile',
      'teacherAddCommentThisFile',
    ]),
    // 刷新
    flashAllFileOfMyStudents() {
      var _this = this
      this.getAllFileOfMyStudents().then((result) => {
        console.log(result.data.data)
        _this.tableData = result.data.data
        _this.$forceUpdate()
      })
    },
    flashComments() {
      const _this = this
      var chosedFileId = _this.chosedFileId
      this.teacherGetAllCommentsOfThisFile({ chosedFileId }).then((res) => {
        _this.commentData = res.data.data
        console.log(_this.commentData)
        console.log(_this.testData)
        _this.$forceUpdate()
        _this.loading = false
      })
    },
    // 打开对话框
    openScoreDialog(id) {
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
      // 下载文件
      this.teacherGetThisFile({
        fileId: id,
      }).then((res) => {
        console.log(res.data)
        fileDownload(res.data, name)
      })
    },
    handleScore() {
      // 文章打分
      const _this = this
      const chosedFileId = this.chosedFileId
      const fileScore = this.fileScore
      this.teacherScoreThisFile({ chosedFileId, fileScore }).then(() => {
        _this.flashAllFileOfMyStudents()
      })
    },
    handleReplay(node) {
      // 评论回复
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
      this.teacherAddCommentThisFile({
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

<style scoped>
@import '../assets/css/el-dialog.css';
@import '../assets/css/el-table.css';
.table-btn {
  margin: 0 4px !important;
}
</style>
