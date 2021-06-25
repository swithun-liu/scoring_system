<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-09 12:26:21
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-06-06 17:18:01
-->
<template>
  <!-- 学生列表 -->
  <el-table :data="tableData" style="width: 100%">
    <el-table-column type="expand">
      <template #default="props">
        <el-form label-position="left" inline class="demo-table-expand">
          <el-form-item label="所有学生">
            <div v-for="(student, index) in props.row.studentsById" :key="index">
              <div>
                <span>{{student.name}}</span>
              </div>
            </div>
          </el-form-item>
        </el-form>
      </template>
    </el-table-column>
    <el-table-column label="教师 ID" prop="id" width="100"></el-table-column>
    <el-table-column label="教师编号" prop="name"></el-table-column>
    <el-table-column label="操作" width="200">
      <template #default="scope">
        <el-button
          class="el-btn-2-glass-btn"
          icon="el-icon-edit"
          @click="openEditDialog(scope.row.id, scope.row.name, scope.row.nickname)"
        ></el-button>
      </template>
    </el-table-column>
  </el-table>

  <!-- 编辑学生信息 Dialog -->
  <teleport to="body">
    <el-dialog title="编辑学生信息" v-model="editDialogVisible" :before-close="handleClose" width="50%">
      <el-form :model="editForm" label-width="100px">
        <el-form-item label="教师id" prop="id">
          <el-input v-model="editForm.id"></el-input>
        </el-form-item>
        <el-form-item label="编号" prop="name">
          <el-input v-model="editForm.name"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="editForm.nickname"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="password">
          <el-input v-model="editForm.password" placeholder="不更改"></el-input>
        </el-form-item>
        <el-from-item style="display: flex; justify-content: flex-end;">
          <el-button
            @click="handleEdit()"
            class="el-btn-2-glass-btn"
            style="margin: 2px !important;"
          >修改</el-button>
        </el-from-item>
      </el-form>
    </el-dialog>
  </teleport>
</template>

<script>
import { onMounted, reactive, ref } from '@vue/runtime-core'
import { useStore } from 'vuex'
export default {
  setup(props) {
    const store = useStore()

    const editDialogVisible = ref(false)

    const editForm = reactive({
      id: 100,
      name: 'student',
      nickname: 'nickNamehahah',
      password: '',
    })

    const tableData = reactive([
      // {
      //   id: 1,
      //   name: 'teacher0001',
      //   studentsById: [
      //     {
      //       id: 1,
      //       name: 'student0001',
      //       studentFilesById: [
      //         {
      //           id: 1,
      //           name: '考虑安全的博士论文评审系统的研究和实现.pdf',
      //           type: 'application/pdf',
      //           score: 89,
      //         },
      //         {
      //           id: 2,
      //           name: '国内外高校教务管理系统的研究现状.pdf',
      //           type: 'application/pdf',
      //           score: 86,
      //         },
      //         {
      //           id: 3,
      //           name: '考虑安全的博士论文评审系统的研究和实现.pdf',
      //           type: 'application/pdf',
      //           score: 56,
      //         },
      //       ],
      //     },
      //   ],
      // },
    ])

    // get teacher list
    const refreshInfo = () => {
      console.log('开始打印')
      store.dispatch('admin/getAllTeachers').then((res) => {
        console.log(res.data.data)
        tableData.splice(0)
        tableData.push(...res.data.data)
      })
    }

    const openEditDialog = (id, name, nickname) => {
      console.log('编辑学生 ' + id)
      editDialogVisible.value = true
      editForm.id = id
      editForm.name = name
      editForm.nickname = nickname
    }

    const handleEdit = () => {
      store.dispatch('admin/editTeacher', editForm).then((res) => {
        console.log(res)
        editDialogVisible.value = false
        refreshInfo()
      })
    }

    onMounted(() => {
      refreshInfo()
    })

    return {
      tableData,
      openEditDialog,
      editForm,
      editDialogVisible,
      handleEdit,
    }
  },
}
</script>

<style lang="less" scoped>
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
</style>
