<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-27 10:35:15
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-04 14:00:17
-->

<template>
  <div>
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
    <el-dialog title="回复" v-model="commentDialogVisible" width="80%" :before-close="handleClose">
      <template #footer>
        <el-table :data="commentData" style="width: 100%">
          <el-table-column prop="comment" label="评论"></el-table-column>
        </el-table>
        <comment :data="commentData"></comment>
        <el-form>
          <el-form-item>
            <el-input v-model="commentWatiForPush"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click="handleAddComment()">添加</el-button>
          </el-form-item>
        </el-form>
        <span class="dialog-footer">
          <el-button @click="commentDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleComment()">确 定</el-button>
        </span>
      </template>
    </el-dialog>
    <!-- 回复 Dialog end -->
    <comment></comment>
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
      fileScore: 0,
      commentData: [],
      commentWatiForPush: '',
    }
  },
  mounted() {
    this.flashAllFileOfMyStudents()
  },
  methods: {
    ...mapActions('student', [
      'studentGetAllMyFileForMyFilePage',
      'studentGetAllComment',
      'studentDownloadThisFile',
    ]),
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
        console.log(res.data)
        _this.commentData = res.data.data
        _this.$forceUpdate()
      })
    },
    // 处理操作
    handleDownload(id, name) {
      console.log('获取文件 id: ' + id)
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
    handleComment() {},
    handleClose() {
      console.log('Dialog closed')
    },
    handleAddComment() {
      console.log(this.chosedFileId + ' ' + this.commentWatiForPush)
      var comment = this.commentWatiForPush
      var chosedFileId = this.chosedFileId
      var _this = this
      this.teacherAddCommentThisFile({ chosedFileId, comment }).then((res) => {
        console.log(res)
        _this.commentWatiForPush = ''
        _this.flashComments()
      })
    },
    // 打开对话框
    openScoreDialog(id) {
      console.log(id)
      this.chosedFileId = id
      this.dialogVisible = true
    },
    openCommentDialog(id) {
      console.log(id)
      this.chosedFileId = id
      this.commentDialogVisible = true
      this.flashComments()
    },
  },
}
</script>

<style>
</style>
