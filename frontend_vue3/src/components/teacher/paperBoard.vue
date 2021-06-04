<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-17 14:26:03
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-06-04 19:21:03
-->

<template>
  <!-- 文件列表 begin -->
  <el-table :data="dTableDate" style="width: 100%">
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
        <el-button
          icon="el-icon-view"
          size="small"
          class="el-btn-2-glass-btn table-btn"
          @click="openDialogPreview(scope.row.id)"
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
      v-model="dDialogVisibleScore"
      width="30%"
      :before-close="handleCommentDialogClose"
    >
      <template #footer>
        <el-slider v-model="dFileScore" show-input></el-slider>
        <span class="dialog-footer">
          <el-button @click="dDialogVisibleScore = false">取 消</el-button>
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
      v-model="dDialogVisibleComment"
      width="80%"
      :before-close="handleClose"
      custom-class="replay-dialog"
    >
      <comment
        :loading="dLoading"
        :data="dCommentData"
        @handleReplay="handleReplay($event)"
        @refresh-data="flashComments"
      ></comment>
      <template #footer>
        <el-form>
          <el-form-item>
            <span>{{ dReplayWhichComment }}</span>
            <button v-if="dReplayWhichComment != '新建评论'" @click="cancleChooseComment()">取消</button>
            <el-input v-model="dCommentWatiForPush"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="el-icon-position" @click="handleAddComment()"></el-button>
          </el-form-item>
        </el-form>
      </template>
    </el-dialog>
  </teleport>
  <!-- 回复 Dialog end-->
  <!-- 文件预览 Dialog begin -->
  <teleport to="body">
    <el-dialog
      id="dialogPreivewId"
      title="文件预览"
      v-model="dDialogVisiblePreview"
      width="50%"
      :before-close="handleClose"
      custom-class="dialogPreivew"
    >
      <div style="height: 100%; width: 100%;">
        <web-viewer ref="dMyWebViewer" :myBlob="dMyBlob" :key="dChosedFileId" />
      </div>
    </el-dialog>
  </teleport>
  <!-- 文件预览 Dialog end -->
  <button class="custom-btn btn-13">
    <span>Read More</span>
  </button>
</template>

<script>
import fileDownload from 'js-file-download'
import { ElMessage } from 'element-plus'
import { ref } from '@vue/reactivity'
import { onMounted } from '@vue/runtime-core'
import { useStore } from 'vuex'
import comment from '../general/comment.vue'
import WebViewer from '../general/WebViewer.vue'

export default {
  components: { comment, WebViewer },
  setup(props) {
    const dTableDate = ref([])
    const dDialogVisibleScore = ref(false)
    const dDialogVisibleComment = ref(false)
    const dDialogVisiblePreview = ref(false)
    const dChosedFileId = ref(0)
    const dChosedCommentId = ref(-1)
    const dReplayWhichComment = ref('新建评论')
    const dFileScore = ref(0)
    const dCommentData = ref([])
    const dCommentOriginData = ref([])
    const dCommentWatiForPush = ref('')
    const dLoading = ref(true)
    const dMyBlob = ref({ type: Blob })
    const dMyWebViewer = ref('myWebViewer')

    const store = useStore()

    // 获取学生列表
    const flashAllFileOfMyStudents = () => {
      store.dispatch('teacher/getAllFileOfMyStudents').then((res) => {
        console.log('学生列表', res.data.data)
        dTableDate.value = res.data.data
      })
    }

    // 获取评论
    const flashComments = () => {
      store
        .dispatch('teacher/teacherGetAllCommentsOfThisFile', {
          chosedFileId: dChosedFileId.value,
        })
        .then((res) => {
          dCommentData.value = res.data.data
          dLoading.value = false
          console.log('获取到评论 ', dCommentData.value)
        })
    }

    // 打开评分对话框
    const openScoreDialog = (id) => {
      dChosedFileId.value = id
      dDialogVisibleScore.value = true
    }
    // 打开评论对话框
    const openCommentDialog = (id) => {
      dChosedCommentId.value = -1
      dChosedFileId.value = id
      dDialogVisibleComment.value = true
      flashComments()
    }
    // 打开预览对话框
    const openDialogPreview = (id) => {
      console.log('企图预览文件' + id)
      dChosedFileId.value = id
      handlePreview(id)
      dDialogVisiblePreview.value = true
    }
    const handlePreview = (id) => {
      store
        .dispatch('teacher/teacherGetThisFile', {
          fileId: id,
        })
        .then((res) => {
          dMyBlob.value = res.data
          console.log('获取到的pdf blob为', dMyBlob.value)
          dMyWebViewer.value.renderFileItem()
        })
    }
    // 处理下载文件
    const handleDownload = (id, name) => {
      store
        .dispatch('teacher/teacherGetThisFile', {
          fileId: id,
        })
        .then((res) => {
          console.log('获取到文件', res.data)
          fileDownload(res.data, name)
        })
    }
    // 处理评分
    const handleScore = () => {
      store
        .dispatch('teacher/teacherScoreThisFile', {
          chosedFileId: dChosedFileId.value,
          fileScore: dFileScore.value,
        })
        .then((res) => {
          console.log('评分结果', res.data.data)
          flashAllFileOfMyStudents()
          if (res.data.data === '修改成功') {
            ElMessage({
              showClose: true,
              message: '修改成功',
              type: 'success',
            })
          } else {
            ElMessage({
              showClose: true,
              message: '修改失败',
              type: 'error',
            })
          }
          dDialogVisibleScore.value = false
        })
    }
    // 处理评论
    const handleReplay = (node) => {
      var student = node.data.studentByStudentId
      var teacher = node.data.teacherByTeacherId
      var username = null
      if (student != null) {
        username = student.name
      }
      if (teacher != null) {
        username = teacher.name
      }
      dReplayWhichComment.value = '回复 ' + username
      dChosedCommentId.value = node.data.id
    }
    // 处理添加评论
    const handleAddComment = () => {
      store
        .dispatch('teacher/teacherAddCommentThisFile', {
          chosedFileId: dChosedFileId.value,
          comment: dCommentWatiForPush.value,
          chosedCommentId: dChosedCommentId.value,
        })
        .then((res) => {
          console.log(res)
          dCommentWatiForPush.value = ''
          flashComments()
        })
    }
    // 取消选择评论
    const cancleChooseComment = () => {
      dChosedCommentId.value = -1
      dReplayWhichComment.value = '新建评论'
    }
    // 关闭评论对话框
    const handleCommentDialogClose = () => {
      console.log('评论对话框关闭')
      dDialogVisibleComment.value = false
      dLoading.value = true
      dCommentData.value = []
    }

    onMounted(() => {
      flashAllFileOfMyStudents()
    })

    return {
      dTableDate,
      dDialogVisibleScore,
      dDialogVisibleComment,
      dDialogVisiblePreview,
      dChosedFileId,
      dChosedCommentId,
      dReplayWhichComment,
      dFileScore,
      dCommentData,
      dCommentOriginData,
      dCommentWatiForPush,
      dLoading,
      dMyBlob,
      dMyWebViewer,
      flashAllFileOfMyStudents,
      flashComments,
      openScoreDialog,
      openCommentDialog,
      handleDownload,
      handleScore,
      handleReplay,
      openDialogPreview,
      handleCommentDialogClose,
      handleAddComment,
      cancleChooseComment,
    }
  },
}

// export default {
//   components: { comment },
//   data() {
//     return {
//       tableData: [],
//       dialogVisible: false,
//       commentDialogVisible: false,
//       chosedFileId: 0,
//       chosedCommentId: -1,
//       replayWhichComment: '新建评论',
//       fileScore: 0,
//       commentData: [],
//       commentOriginData: [],
//       commentWatiForPush: '',
//       loading: true,
//     }
//   },
//   mounted() {
//     this.flashAllFileOfMyStudents()
//   },
//   methods: {
//     ...mapActions('teacher', [
//       'getAllFileOfMyStudents',
//       'teacherGetThisFile',
//       'teacherScoreThisFile',
//       'teacherGetAllCommentsOfThisFile',
//       'teacherAddCommentThisFile',
//     ]),
//     // 刷新
//     flashAllFileOfMyStudents() {
//       var _this = this
//       this.getAllFileOfMyStudents().then((result) => {
//         console.log(result.data.data)
//         _this.tableData = result.data.data
//         _this.$forceUpdate()
//       })
//     },
//     flashComments() {
//       const _this = this
//       var chosedFileId = _this.chosedFileId
//       this.teacherGetAllCommentsOfThisFile({ chosedFileId }).then((res) => {
//         _this.commentData = res.data.data
//         console.log(_this.commentData)
//         console.log(_this.testData)
//         _this.$forceUpdate()
//         _this.loading = false
//       })
//     },
//     // 打开对话框
//     openScoreDialog(id) {
//       this.chosedFileId = id
//       this.dialogVisible = true
//     },
//     openCommentDialog(id) {
//       this.chosedCommentId = -1
//       this.chosedFileId = id
//       this.commentDialogVisible = true
//       this.flashComments()
//     },
//     // 处理操作
//     handleDownload(id, name) {
//       // 下载文件
//       this.teacherGetThisFile({
//         fileId: id,
//       }).then((res) => {
//         console.log(res.data)
//         fileDownload(res.data, name)
//       })
//     },
//     handleScore() {
//       // 文章打分
//       const _this = this
//       const chosedFileId = this.chosedFileId
//       const fileScore = this.fileScore
//       this.teacherScoreThisFile({ chosedFileId, fileScore }).then(() => {
//         _this.flashAllFileOfMyStudents()
//       })
//     },
//     handleReplay(node) {
//       // 评论回复
//       var student = node.data.studentByStudentId
//       var teacher = node.data.teacherByTeacherId
//       var username = null
//       if (student != null) {
//         username = student.name
//       }
//       if (teacher != null) {
//         username = teacher.name
//       }
//       this.replayWhichComment = 'replay ' + username
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
//       this.teacherAddCommentThisFile({
//         chosedFileId,
//         comment,
//         chosedCommentId,
//       }).then((res) => {
//         console.log(res)
//         _this.commentWatiForPush = ''
//         _this.flashComments()
//       })
//     },
//     cancleChooseComment() {
//       this.chosedCommentId = -1
//       this.replayWhichComment = '新建评论'
//     },
//   },
// }
</script>

<style scoped>
@import '../../assets/css/el-dialog.css';
@import '../../assets/css/el-table.css';
.table-btn {
  margin: 0 4px !important;
}
</style>
