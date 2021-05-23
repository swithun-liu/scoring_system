<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-27 10:35:15
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-23 09:48:38
-->

<template>
  <!-- 文件卡片 begin -->
  <div class="card-wrapper">
    <div class="card" v-for="(file, index) in tableData" :key="index">
      <div class="card-title-wrapper">
        <span class="card-title">{{file.name}}</span>
      </div>
      <br />
      <div class="info-wrapper">
        <span>id: </span> <span>{{file.id}}</span>
      </div>
      <div class="info-wrapper">
        <span class="card-abstract">摘要</span>
      </div>
      <br />
      <div class="card-abstract-conten-wrapper">
        <textarea class="card-abstract-content" name="text" rows="14" cols="10" wrap="soft" v-model="file.fileAbstract">
        </textarea>
      </div>
      <div class="card-general-wrapper">
        <el-progress :format="format" :text-inside="true" :stroke-width="26" :percentage="file.score"></el-progress>
      </div>
      <div class="card-footer">
          <el-button class="glass-btn-important card-btn" icon="el-icon-success" @click="handleEditInfo(file)"></el-button>
          <el-button class="glass-btn-important card-btn" icon="el-icon-download" size="small" @click="handleDownload(file.id, file.name)"></el-button>
          <el-button class="glass-btn-important card-btn" icon="el-icon-s-comment" size="small" @click="openCommentDialog(file.id)"></el-button>
          <input type="file" class="refresh-btn-input-file" value="" id="refreshFile" @change="handleRefreshFile($event, file.id)">
          <label for="refreshFile" class="glass-btn el-icon-refresh refresh-label"></label>
      </div>
    </div>
  </div>
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
  <el-dialog
    title="回复"
    v-model="commentDialogVisible"
    fullscreen: true
    width="40%"
    :before-close="handleCommentDialogClose"
  >
      <comment :loading="loading" :data="commentData" @handleReplay="handleReplay($event)" @refresh-data="flashComments" @test="test()"></comment>
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
  <!-- 回复 Dialog end -->
  <!-- </div> -->
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
      loading: true,
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
      'studentEditFileInfo',
      'studentRefreshFile'
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
      console.log('刷新评论');
      var chosedFileId = _this.chosedFileId
      this.studentGetAllComment({ chosedFileId }).then((res) => {
        _this.commentData = res.data.data
        console.log('刷新评论', _this.commentData)
        _this.$forceUpdate()
        _this.loading = false
      })
    },
    test() {
      console.log('outter test');
      this.replayWhichComment = '新建评论hhhh'
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
      console.log('回复评论', node);
      var student = node.data.studentByStudentId
      var teacher = node.data.teacherByTeacherId
      var username = null
      if (student != null) {
        username = student.name
      }
      if (teacher != null) {
        username = teacher.name
      }
      this.replayWhichComment = 'replay??? ' + username
      console.log('hahahahhaha');
      this.test()
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
    handleEditInfo(file) {
      this.studentEditFileInfo(file)
    },
    cancleChooseComment() {
      this.chosedCommentId = -1
      this.replayWhichComment = '新建评论'
    },
    handleRefreshFile(e, id) {
      const file = e.target.files[0]
      const param = new FormData()
      param.append('file', file)
      param.append('id', id)
      param.append('name', file.name)
      const config = {
        headers: { 'Content-Type': 'multipart/form-data' }
      }
      this.studentRefreshFile({ param: param, config: config }).then((res) => {
        console.log('refresh file : ' + id);
      })
    },
    format(percentage) {
      return `${percentage}分`
    },
  },
}
</script>

<style>
@import '../assets/css/card.css';
@import '../assets/css/el-dialog.css';
</style>
