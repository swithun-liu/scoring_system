<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-27 10:35:15
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-30 08:13:34
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
          <el-button class="glass-btn-important card-btn" icon="el-icon-view" size="small" @click="handlePreview(file.id, file.name)"></el-button>
          <el-button class="glass-btn-important card-btn" icon="el-icon-s-comment" size="small" @click="openCommentDialog(file.id)"></el-button>
          <el-button class="glass-btn-important card-btn" icon="el-icon-delete-solid" size="small" @click="openDelteDialog(file.id)"></el-button>
          <input type="file" class="refresh-btn-input-file" value="" id="refreshFile" @change="handleRefreshFile($event, file.id)">
          <label for="refreshFile" class="glass-btn el-icon-refresh refresh-label"></label>
      </div>
    </div>
  </div>
  <div style="height: 100%; width: 100%; background: red;">
    <!-- <web-viewer initialDoc='https://pdftron.s3.amazonaws.com/downloads/pl/demo-annotated.pdf' /> -->
    <web-viewer ref=myWebViewer :initialDoc=fileSrcURL :myBlob=myBlob />
  </div>
  <!-- 文件卡片 end-->
  <!-- 评分 Dialog begin -->
  <teleport to="body">
  <el-dialog title="评分" v-model="dialogVisible" width="30%" :before-close="handleClose">
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
  <!-- 回复 Dialog begin -->
  <teleport to="body">
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
          <el-button icon="el-icon-position" class="el-btn-2-glass-btn" @click="handleAddComment()"></el-button>
        </el-form-item>
      </el-form>
    </template>
  </el-dialog>
  </teleport>
  <!-- 回复 Dialog end -->
  <!-- 删除文件 Dialog begin -->
  <el-dialog
  title="警告"
  v-model="deleteDialogVisible"
  width="30%"
  :before-close="handleClose">
  <div class="delete-info-wrapper">
    <span>删除后无法恢复，确定删除？</span>
  </div>
  <template #footer>
    <span class="dialog-footer">
      <el-button @click="deleteDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="handleDeleteFile()">确 定</el-button>
    </span>
  </template>
</el-dialog>
  <!-- 删除文件 Dialog end -->
  <!-- </div> -->
</template>

<script>
import { useStore } from 'vuex'
import fileDownload from 'js-file-download'
import { ref } from '@vue/reactivity'
import { onMounted } from '@vue/runtime-core'
import comment from '../general/comment.vue'
import WebViewer from '../general/WebViewer.vue'

export default {
  components: { comment, WebViewer },
  setup(props) {
    const store = useStore()
    const chosedFileId = ref(0)
    const chosedCommentId = ref(-1)
    const tableData = ref([])
    const commentData = ref('')
    const loading = ref(false)
    const dialogVisibleScore = ref(false)
    const dialogVisibleComment = ref(false)
    const dialogVisibleDelete = ref(false)
    const myBlob = ref({ type: Blob })
    const replayWhichComment = ref('')
    const commentWaitForPush = ref('')

    // 获取文件列表
    const getFiles = () => {
      store.dispatch('student/studentGetAllMyFileForMyFilePage').then((res) => {
        tableData.value = res.data.data
        console.log('文件列表', tableData.value);
      })
    }

    // 获取文件评论
    const getComments = () => {
      store.dispatch('student/studentGetAllComment', {
        chosedFileId: chosedFileId
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

    // 处理文件下载
    const handleDownload = (id, name) => {
      store.dispatch('student/studentDownloadThisFile', {
        fileId: id,
      }).then((res) => {
        fileDownload(res.data, name);
      })
    }

    // 处理预览文件
    const handlePreivew = (id) => {
      store.dispatch('student/studentDownloadThisFile', {
        fileId: id
      }).then((res) => {
        myBlob.value = res.data
        console.log('获取到的pdf blob为', myBlob);
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
      })
    }

    // 处理文件编辑
    const handleFileEdit = (file) => {
      store.dispatch('student/studentFileInfo', file)
    }

    // 关闭评论对话框
    const closeCommentDialog = () => {
      dialogVisibleComment.value = false
      loading.value = true
      commentData.value = []
    }

    onMounted(() => {
      getFiles()
    })

    return {
      openDialogScore,
      openDialogComment,
      openDialogDelete,
      handlePreivew,
      handleReplay,
      handleCommentAdd,
      handleFileDelete,
      handleFileEdit,
      handleDownload,
      closeCommentDialog,
    }
  }
}

// export default {
//   components: { comment, WebViewer },
//   data() {
//     return {
// tableData: [],
//       dialogVisible: false,
//       commentDialogVisible: false,
//       deleteDialogVisible: false,
//       chosedFileId: 0,
//       chosedCommentId: -1,
//       replayWhichComment: '新建评论',
//       fileScore: 0,
//       commentData: [],
//       commentWatiForPush: '',
//       vuexComponentStudent: 'student',
//       loading: true,
//       fileSrcURL: '../../assets/pdf/test.pdf',
//       viewer: null,
//       testURL: '../../assets/pdf/test.pdf',
//       myBlob: { type: Blob },
//       myWebViewer: 'myWebViewer',
//     }
//   },
//   mounted() {
//     this.flashAllFileOfMyStudents()
//     this.getComments()
//     this.tempHandlePreview(12)
//   },
//   methods: {
//     ...mapActions('student', [
//       'studentGetAllMyFileForMyFilePage',
//       'studentGetAllComment',
//       'studentDownloadThisFile',
//       'studentAddCommentForThisFile',
//       'studentEditFileInfo',
//       'studentRefreshFile',
//       'deleteFile'
//     ]),
//     ...mapActions('comment', ['getComments']),
//     // 刷新
//     flashAllFileOfMyStudents() {
//       var _this = this
//       this.studentGetAllMyFileForMyFilePage().then((result) => {
//         console.log(result.data.data)
//         _this.tableData = result.data.data
//         _this.$forceUpdate()
//       })
//     },
//     flashComments() {
//       const _this = this
//       console.log('刷新评论');
//       var chosedFileId = _this.chosedFileId
//       this.studentGetAllComment({ chosedFileId }).then((res) => {
//         _this.commentData = res.data.data
//         console.log('刷新评论', _this.commentData)
//         _this.$forceUpdate()
//         _this.loading = false
//       })
//     },
//     test() {
//       console.log('outter test');
//       this.replayWhichComment = '新建评论hhhh'
//     },
//     // 打开对话框
//     openScoreDialog(id) {
//       console.log(id)
//       this.chosedFileId = id
//       this.dialogVisible = true
//     },
//     openCommentDialog(id) {
//       this.chosedCommentId = -1
//       this.chosedFileId = id
//       this.commentDialogVisible = true
//       this.flashComments()
//     },
//     openDelteDialog(id) {
//       this.chosedFileId = id
//       this.deleteDialogVisible = true
//     },
//     // 文件下载
//     handleDownload(id, name) {
//       this.studentDownloadThisFile({
//         fileId: id,
//       }).then((res) => {
//         fileDownload(res.data, name)
//       })
//     },
//     // 文件预览
//     handlePreview(id) {
//       const _this = this
//       this.studentDownloadThisFile({
//         fileId: id,
//       }).then((res) => {
//         const blob = res.data
//         _this.myBlob = res.data
//         const url = URL.createObjectURL(blob)
//         console.log('文件url为：', url);
//         _this.fileSrcURL = url
//         console.log('fileSrcURL', _this.fileSrcURL)
//       })
//     },
//     // 文件预览
//     tempHandlePreview(id) {
//       var _this = this
//       this.studentDownloadThisFile({
//         fileId: id,
//       }).then((res) => {
//         const blob = res.data
//         _this.myBlob = res.data
//         console.log('文件blob为', blob)
//         const url = URL.createObjectURL(blob)
//         console.log('onMounted 文件url为：', url);
//         _this.fileSrcURL = url
//         console.log('onMounted fileSrcURL', _this.fileSrcURL)
//       })
//     },
//     // 文件评分
//     handleScore() {
//       const _this = this
//       const chosedFileId = this.chosedFileId
//       const fileScore = this.fileScore
//       console.log('handleScore')
//       this.teacherScoreThisFile({ chosedFileId, fileScore }).then(() => {
//         _this.flashAllFileOfMyStudents()
//       })
//     },
//     handleReplay(node) {
//       console.log('回复评论', node);
//       var student = node.data.studentByStudentId
//       var teacher = node.data.teacherByTeacherId
//       var username = null
//       if (student != null) {
//         username = student.name
//       }
//       if (teacher != null) {
//         username = teacher.name
//       }
//       this.replayWhichComment = 'replay??? ' + username
//       console.log('hahahahhaha');
//       this.test()
//       this.chosedCommentId = node.data.id
//     },
//     handleCommentDialogClose() {
//       console.log('Dialog closed')
//       this.commentDialogVisible = false
//       this.loading = true
//       this.commentData = []
//     },
//     handleAddComment() {
//       var comment = this.commentWatiForPush
//       var chosedFileId = this.chosedFileId
//       var chosedCommentId = this.chosedCommentId
//       var _this = this
//       this.studentAddCommentForThisFile({
//         chosedFileId,
//         comment,
//         chosedCommentId,
//       }).then((res) => {
//         console.log(res)
//         _this.commentWatiForPush = ''
//         _this.flashComments()
//       })
//     },
//     handleDeleteFile() {
//       console.log('删除文件', this.chosedFileId);
//       this.deleteFile(this.chosedFileId).then(() => {
//         this.deleteDialogVisible = false
//         this.flashAllFileOfMyStudents()
//       })
//     },
//     handleEditInfo(file) {
//       this.studentEditFileInfo(file)
//     },
//     cancleChooseComment() {
//       this.chosedCommentId = -1
//       this.replayWhichComment = '新建评论'
//     },
//     handleRefreshFile(e, id) {
//       const file = e.target.files[0]
//       const param = new FormData()
//       param.append('file', file)
//       param.append('id', id)
//       param.append('name', file.name)
//       const config = {
//         headers: { 'Content-Type': 'multipart/form-data' }
//       }
//       this.studentRefreshFile({ param: param, config: config }).then((res) => {
//         console.log('refresh file : ' + id);
//       })
//     },
//     format(percentage) {
//       return `${percentage}分`
//     },
//   },
// }
</script>

<style>
@import '../../assets/css/card.css';
@import '../../assets/css/el-dialog.css';
</style>
