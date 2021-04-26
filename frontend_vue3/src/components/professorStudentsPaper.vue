<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-17 14:26:03
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-26 16:52:18
-->

<template>
  <div>
    <el-table :data='tableData' style='width: 100%'>
      <el-table-column prop='id' label='文件id'></el-table-column>
      <el-table-column prop='name' label='文件名'></el-table-column>
      <el-table-column prop='studentId' label='学生id'></el-table-column>
      <el-table-column prop='studentName' label='学生名'></el-table-column>
      <el-table-column prop='score' label='分数'></el-table-column>
      <el-table-column lable='操作'>
        <template #default='scope'>
          <el-button
            size='small'
            @click='handleDownload(scope.row.id, scope.row.name)'
          >下载 {{scope.row.id}}</el-button>
          <el-button size='small'>更新</el-button>
          <el-button size='small' @click='openCommentDialog(scope.row.id)'>回复</el-button>
          <el-button size='small' @click='openScoreDialog(scope.row.id)'>评分</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title='评分' v-model='dialogVisible' width='30%' :before-close='handleClose'>
      <template #footer>
        <el-slider v-model='fileScore' show-input></el-slider>
        <span class='dialog-footer'>
          <el-button @click='dialogVisible = false'>取 消</el-button>
          <el-button type='primary' @click='handleScore()'>确 定</el-button>
        </span>
      </template>
    </el-dialog>
    <el-dialog title='回复' v-model='commentDialogVisible' width='80%' :before-close='handleClose'>
      <template #footer>
        <el-table :data='commentData' style='width: 100%'>
          <el-table-column prop='comment' label='评论'></el-table-column>
        </el-table>
        <el-form>
          <el-form-item>
            <el-input v-model='commentWatiForPush'></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click='handleAddComment()'>添加</el-button>
          </el-form-item>
        </el-form>
        <span class='dialog-footer'>
          <el-button @click='commentDialogVisible = false'>取 消</el-button>
          <el-button type='primary' @click='handleComment()'>确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { mapActions } from 'vuex';
import fileDownload from 'js-file-download';

export default {
  data() {
    return {
      tableData: [
      ],
      dialogVisible: false,
      commentDialogVisible: false,
      chosedFileId: 0,
      fileScore: 0,
      commentData: ['temp coment', 'temp coment2'],
      commentWatiForPush: ''
    };
  },
  mounted() {
    this.flashAllFileOfMyStudents();
  },
  methods: {
    ...mapActions('teacher', [
      'getAllFileOfMyStudents',
      'teacherGetThisFile',
      'teacherScoreThisFile',
      'teacherGetAllCommentsOfThisFile',
      'teacherAddCommentThisFile'
    ]),
    flashAllFileOfMyStudents() {
      var _this = this;
      this.getAllFileOfMyStudents().then((result) => {
        console.log(result.data.data);
        _this.tableData = result.data.data;
        _this.$forceUpdate();
      });
    },
    flashComments() {
      const _this = this;
      var chosedFileId = _this.chosedFileId;
      this.teacherGetAllCommentsOfThisFile({ chosedFileId }).then((res) => {
        console.log(res.data);
        _this.commentData = res.data.data;
        _this.$forceUpdate();
      });
    },
    handleDownload(id, name) {
      console.log('获取文件 id: ' + id);
      this.teacherGetThisFile({
        fileId: id,
      }).then((res) => {
        console.log(res.data);
        fileDownload(res.data, name);
      });
    },
    openScoreDialog(id) {
      console.log(id);
      this.chosedFileId = id;
      this.dialogVisible = true;
    },
    openCommentDialog(id) {
      console.log(id);
      this.chosedFileId = id;
      this.commentDialogVisible = true;
      this.flashComments();
    },
    handleScore() {
      const _this = this;
      const chosedFileId = this.chosedFileId;
      const fileScore = this.fileScore;
      console.log('handleScore');
      this.teacherScoreThisFile({ chosedFileId, fileScore }).then(() => {
        _this.flashAllFileOfMyStudents();
      });
    },
    handleComment() {
    },
    handleClose() {
      console.log('Dialog closed');
    },
    handleAddComment() {
      console.log(this.chosedFileId + ' ' + this.commentWatiForPush)
      var comment = this.commentWatiForPush;
      var chosedFileId = this.chosedFileId;
      var _this = this;
      this.teacherAddCommentThisFile({ chosedFileId, comment }).then((res) => {
        console.log(res);
        _this.commentWatiForPush = '';
        _this.flashComments();
      })
    }
  },
};
</script>

<style>
</style>
