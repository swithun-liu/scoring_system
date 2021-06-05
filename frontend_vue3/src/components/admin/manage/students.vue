<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-08 14:32:00
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-06-05 19:48:08
-->
<template>
  <!-- 学生列表 -->
  <el-table :data="tableData" style="width: 100%">
    <el-table-column type="expand">
      <template #default="props">
        <el-form label-position="left" inline class="demo-table-expand">
          <el-form-item label="所有论文">
            <div v-for="(file, index) in props.row.studentFilesById" :key="index">
              <div class="file-wrapper">
                <div style="display: flex; flex-direction: row; justify-content: flex-begin;">
                  <div class="el-icon-document file-icon"></div>
                  <div>{{file.name}}</div>
                </div>
                <div style="display: flex; flex-direction: row; width: 6em;">
                  <el-divider direction="vertical" style="font-size: 3em;"></el-divider>
                  <div class="score-wrapper">{{file.score == null ? '暂未评' : file.score}}分</div>
                </div>
              </div>
            </div>
          </el-form-item>
          <el-form-item label="指导教师">
            <el-select
              v-model="props.row.temperTeacher.id"
              clearable
              placeholder="请选择"
              @change="handleSetTeacher($event, props.row.id)"
            >
              <el-option
                v-for="item in teachers"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </template>
    </el-table-column>
    <el-table-column label="学生 ID" prop="id" width="100"></el-table-column>
    <el-table-column label="学生编号" prop="name"></el-table-column>
    <el-table-column label="操作" width="200">
      <template #default="scope">
        <el-button
          class="el-btn-2-glass-btn"
          icon="el-icon-edit"
          @click="openEditDialog(scope.row.id, scope.row.name)"
        ></el-button>
      </template>
    </el-table-column>
  </el-table>

  <!-- 编辑学生信息 Dialog -->
  <el-dialog title="编辑学生信息" v-model="editDialogVisible" :before-close="handleClose" width="50%">
    <el-form :model="editForm" label-width="100px">
      <el-form-item label="学生id" prop="id">
        <el-input v-model="editForm.id"></el-input>
      </el-form-item>
      <el-form-item label="编号" prop="name">
        <el-input v-model="editForm.name"></el-input>
      </el-form-item>
      <el-form-item label="昵称" prop="nickName">
        <el-input v-model="editForm.nickName"></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="password">
        <el-input v-model="editForm.password" placeholder="不更改"></el-input>
      </el-form-item>
      <el-from-item>
        <el-button @click="handleEdit()" class="el-btn-2-glass-btn">修改</el-button>
      </el-from-item>
    </el-form>
  </el-dialog>
</template>

<script>
import { onMounted, reactive, ref } from '@vue/runtime-core'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'
export default {
  setup(props) {
    const store = useStore()

    const editDialogVisible = ref(false)

    const editForm = reactive({
      id: 100,
      name: 'student',
      nickName: 'nickNamehahah',
      password: '',
    })

    const tableData = reactive([
      // {
      //   id: 1,
      //   name: 'student0001',
      //   studentFilesById: [
      //     {
      //       id: 1,
      //       name: '考虑安全的博士论文评审系统的研究和实现.pdf',
      //       type: 'application/pdf',
      //       score: 89,
      //     },
      //     {
      //       id: 2,
      //       name: '国内外高校教务管理系统的研究现状.pdf',
      //       type: 'application/pdf',
      //       score: 86,
      //     },
      //   ],
      // },
    ])

    const teachers = ref([])

    // get teacher list
    const getTeachers = () => {
      console.log('开始打印')
      store.dispatch('admin/getAllTeachers').then((res) => {
        teachers.value = res.data.data
        console.log('所有教师', teachers)
      })
    }
    // get student list
    const getStudents = () => {
      console.log('开始打印')
      store.dispatch('admin/getAllStudents').then((res) => {
        res.data.data.forEach((element) => {
          console.log(element)
          if (element.temperTeacher == null) {
            element.temperTeacher = {
              id: null,
            }
          }
        })
        tableData.splice(0)
        tableData.push(...res.data.data)
        console.log(tableData)
      })
    }

    const openEditDialog = (id, name) => {
      console.log('编辑学生 ' + id)
      editDialogVisible.value = true
      editForm.id = id
      editForm.name = name
      // editForm.nickName = nickName
    }

    const handleEdit = () => {
      store.dispatch('admin/editStudent', editForm).then((res) => {
        console.log(res)
        editDialogVisible.value = false
        getStudents()
      })
    }

    const handleSetTeacher = (teacherId, studentId) => {
      console.log('teacherId', teacherId, 'studentId', studentId)
      store
        .dispatch('admin/setTeacher', {
          teacherId: teacherId,
          studentId: studentId,
        })
        .then((res) => {
          console.log(res)
          if (res.data.data === 'ok') {
            ElMessage.success({
              message: '修改成功',
              type: 'success',
            })
          }
        })
    }

    onMounted(() => {
      getStudents()
      getTeachers()
    })

    return {
      tableData,
      openEditDialog,
      editForm,
      editDialogVisible,
      handleEdit,
      handleSetTeacher,
      teachers,
    }
  },
}
</script>

<style lang="less"  scoped>
@import url('../../../assets/css/el-selector.css');
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
.file-wrapper {
  /* 样式 */
  background: rgba(255, 255, 255, 0);
  box-shadow: 0 1px 6px 0 rgba(31, 38, 135, 0.37);
  backdrop-filter: blur(3px);
  -webkit-backdrop-filter: blur(3px);
  border-radius: 10px;
  border: 1px solid rgba(255, 255, 255, 0.18);
  /* 结构 */
  display: flex;
  flex-direction: row;
  align-content: center;
  justify-content: space-between;
  margin: 10px 0;
  padding: 5px;
  width: 100%;
}
.file-icon {
  align-self: center;
  padding: 5px;
  font-size: large;
  cursor: pointer;
}
/deep/.el-form-item__content {
  width: 76% !important  ;
}
.score-wrapper {
  /* 样式 */
}
</style>
