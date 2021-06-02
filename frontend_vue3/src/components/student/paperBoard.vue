<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-27 10:35:15
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-06-02 20:39:20
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
          <el-button class="glass-btn-important card-btn" icon="el-icon-success" @click="handleFileEdit(file)"></el-button>
          <el-button class="glass-btn-important card-btn" icon="el-icon-download" size="small" @click="handleDownload(file.id, file.name)"></el-button>
          <el-button class="glass-btn-important card-btn" icon="el-icon-view" size="small" @click="openDialogPreview(file.id)"></el-button>
          <el-button class="glass-btn-important card-btn" icon="el-icon-s-comment" size="small" @click="openDialogComment(file.id)"></el-button>
          <el-button class="glass-btn-important card-btn" icon="el-icon-delete-solid" size="small" @click="openDialogDelete(file.id)"></el-button>
          <input type="file" class="refresh-btn-input-file" value="" id="refreshFile" @change="handleFileRefresh($event, file.id)">
          <label for="refreshFile" class="glass-btn el-icon-refresh refresh-label"></label>
      </div>
    </div>
  </div>
  <!-- 文件卡片 end-->
  <!-- 评分 Dialog begin -->
  <teleport to="body">
  <el-dialog title="评分" v-model="dialogVisibleScore" width="30%" :before-close="handleClose">
    <template #footer>
      <el-slider v-model="fileScore" show-input></el-slider>
      <span class="dialog-footer">
        <el-button @click="dialogVisibleScore = false">取 消</el-button>
        <el-button type="primary" @click="handleScore()">确 定</el-button>
      </span>
    </template>
  </el-dialog>
  </teleport>
  <!-- 评分 Dialog end -->
  <!-- 回复 Dialog begin -->
  <teleport to="body">
  <el-dialog
    title="回复"
    v-model="dialogVisibleComment"
    fullscreen: true
    width="40%"
    :before-close="handleCommentDialogClose"
  >
      <comment :loading="loading" :data="commentData" @handleReplay="handleReplay($event)" @refresh-data="getComments" @test="test()"></comment>
    <template #footer>
      <el-form>
        <el-form-item>
          <span>{{ replayWhichComment }}</span>
          <button v-if="replayWhichComment != '新建评论'" @click="cancleChooseComment()">取消</button>
          <el-input v-model="commentWaitForPush"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-position" class="el-btn-2-glass-btn" @click="handleCommentAdd()"></el-button>
        </el-form-item>
      </el-form>
    </template>
  </el-dialog>
  </teleport>
  <!-- 回复 Dialog end -->
  <!-- 删除文件 Dialog begin -->
  <teleport to="body">
    <el-dialog
    title="警告"
    v-model="dialogVisibleDelete"
    width="30%"
    :before-close="handleClose">
    <div class="delete-info-wrapper">
      <span>删除后无法恢复，确定删除？</span>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisibleDelete = false">取 消</el-button>
        <el-button type="primary" @click="handleFileDelete()">确 定</el-button>
      </span>
    </template>
    </el-dialog>
  </teleport>
  <!-- 删除文件 Dialog end -->
  <!-- 文件预览 Dialog begin -->
  <teleport to="body">
    <el-dialog
      id="dialogPreivewId"
      title="文件预览"
      v-model="dialogVisiblePreview"
      width="50%"
      :before-close="handleClose"
      custom-class="dialogPreivew"
      >
      <div style="height: 100%; width: 100%;">
        <web-viewer ref=myWebViewer :myBlob=myBlob :key=chosedFileId />
      </div>
    </el-dialog>
  </teleport>
  <!-- 文件预览 Dialog end -->
</template>

<script>
import { useStore } from 'vuex'
import fileDownload from 'js-file-download'
import { ref } from '@vue/reactivity'
import { onMounted, onUnmounted } from '@vue/runtime-core'
import comment from '../general/comment.vue'
import WebViewer from '../general/WebViewer.vue'
import { ElMessage } from 'element-plus'

export default {
  components: { comment, WebViewer },
  setup(props) {
    const store = useStore()
    const chosedFileId = ref(0)
    const chosedCommentId = ref(-1)
    const myWebViewer = ref('myWebViewer')
    const tableData = ref([])
    const commentData = ref('')
    const loading = ref(false)
    const dialogVisibleScore = ref(false)
    const dialogVisibleComment = ref(false)
    const dialogVisibleDelete = ref(false)
    const dialogVisiblePreview = ref(false)
    const myBlob = ref({ type: Blob })
    const replayWhichComment = ref('')
    const commentWaitForPush = ref('')

    // 获取文件列表
    const getFiles = () => {
      store.dispatch('student/studentGetAllMyFileForMyFilePage').then((res) => {
        tableData.value = res.data.data
        console.log('文件列表', tableData);
      })
    }

    // 获取文件评论
    const getComments = () => {
      store.dispatch('student/studentGetAllComment', {
        chosedFileId: chosedFileId.value
      }).then((res) => {
        commentData.value = res.data.data
        loading.value = false
        console.log('文件评论', commentData.value);
      })
    }

    // 打开评分对话框
    const openDialogScore = (id) => {
      console.log('为文件' + id + '评分');
      chosedFileId.value = id
      dialogVisibleScore.value = true
    }

    // 打开评论对话框
    const openDialogComment = (id) => {
      console.log('为文件' + id + '评论');
      chosedFileId.value = id
      dialogVisibleComment.value = true
      getComments()
    }

    // 打开删除对话框
    const openDialogDelete = (id) => {
      console.log('企图删除文件' + id);
      chosedFileId.value = id
      dialogVisibleDelete.value = true
    }

    // 打开预览对话框
    const openDialogPreview = (id) => {
      console.log('企图预览文件' + id);
      chosedFileId.value = id
      handlePreview(id)
      dialogVisiblePreview.value = true
    }

    // 处理文件下载
    const handleDownload = (id, name) => {
      store.dispatch('student/studentDownloadThisFile', {
        fileId: id,
      }).then((res) => {
        fileDownload(res.data, name);
      })
    }

    // 处理文件预览
    const handlePreview = (id) => {
      store.dispatch('student/studentDownloadThisFile', {
        fileId: id
      }).then((res) => {
        myBlob.value = res.data
        console.log('获取到的pdf blob为', myBlob.value);
        myWebViewer.value.renderFileItem()
      })
    }

    // 处理评论回复
    const handleReplay = (node) => {
      var student = node.data.studentByStudentId
      var teacher = node.data.teacherByTeacherId
      var username = 'null'
      if (student != null) {
        username = student.name
      }
      if (teacher != null) {
        username = teacher.name
      }
      chosedCommentId.value = node.data.id
      replayWhichComment.value = 'replay ' + username
      console.log('回复' + username + '的评论' + chosedCommentId.value)
    }

    // 处理添加评论
    const handleCommentAdd = () => {
      store.dispatch('student/studentAddCommentForThisFile', {
        chosedFileId: chosedFileId.value,
        comment: commentWaitForPush.value,
        chosedCommentId: chosedCommentId.value
      }).then((res) => {
        console.log('添加评论结果 ' + res);
        commentWaitForPush.value = ''
        getComments()
      })
    }

    // 处理文件删除
    const handleFileDelete = () => {
      console.log('删除文件', chosedFileId.value);
      store.dispatch('student/deleteFile', chosedFileId.value).then(() => {
        dialogVisibleDelete.value = false
        getFiles()
      })
    }

    // 处理文件编辑
    const handleFileEdit = (file) => {
      store.dispatch('student/studentEditFileInfo', file).then((res) => {
        console.log(res.data.data);
        if (res.data.data === '添加成功') {
          ElMessage.success({
            message: '修改成功',
            type: 'success'
          })
        } else {
          ElMessage.success({
            message: '修改失败',
            type: 'error'
          })
        }
      })
    }

    // 文件更新
    const handleFileRefresh = (event, id) => {
      const file = event.target.files[0]
      const param = new FormData()
      param.append('file', file)
      param.append('id', id)
      param.append('name', file.name)
      const config = {
        headers: { 'Content-Type': 'multipart/form-data' }
      }
      store.dispatch('student/studentRefreshFile', {
        param: param,
        config: config
      }).then((res) => {
        console.log('更新文件 ' + id)
      })
    }

    // 关闭评论对话框
    const closeCommentDialog = () => {
      dialogVisibleComment.value = false
      loading.value = true
      commentData.value = []
    }

    const format = (percentage) => {
      return percentage + '分'
    }

    onMounted(() => {
      getFiles()
      handlePreview(14)
    })

    onUnmounted(() => {
      console.log('paperBoard死掉')
    })

    return {
      tableData,
      myWebViewer,
      myBlob,
      commentData,
      loading,
      getComments,
      replayWhichComment,
      commentWaitForPush,
      chosedFileId,
      openDialogScore,
      openDialogComment,
      openDialogDelete,
      openDialogPreview,
      dialogVisibleScore,
      dialogVisibleComment,
      dialogVisibleDelete,
      dialogVisiblePreview,
      handlePreview,
      handleReplay,
      handleCommentAdd,
      handleFileDelete,
      handleFileEdit,
      handleDownload,
      handleFileRefresh,
      closeCommentDialog,
      format
    }
  }
}
</script>

<style lang="less" scoped>
@import '../../assets/css/card.css';
@import '../../assets/css/el-dialog.css';
</style>

<style lang="less">
.dialogPreivew {
  height: 95% !important;
  .el-dialog__body {
    height: 100% !important;
  }
}
#dialogPreivewId {
  background: red !important;
}
</style>
