<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-17 14:26:03
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-24 21:58:33
-->

<template>
  <div>
    <el-table :data='tableData' style='width: 100%'>
      <el-table-column prop='id' label='文件id'></el-table-column>
      <el-table-column prop='name' label='文件名'></el-table-column>
      <el-table-column prop='studentId' label='学生id'></el-table-column>
      <el-table-column prop='studentName' label='学生名'></el-table-column>
      <el-table-column lable='操作'>
        <template #default='scope'>
          <el-button size='small' @click='handleDownload(scope.row.id, scope.row.name)'>下载 {{scope.row.id}}</el-button>
          <el-button size='small'>更新</el-button>
          <el-button size='small'>回复</el-button>
          <el-button size='small'>评分</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { mapActions } from 'vuex';
import fileDownload from 'js-file-download';

export default {
  data() {
    return {
      tableData: [
        {
          id: '2016-05-02',
          name: '王小虎',
          studentId: '',
          studentName: '',
        },
      ],
    };
  },
  mounted() {
    var _this = this;
    this.getAllFileOfMyStudents().then((result) => {
      console.log(result.data.data);
      _this.tableData = result.data.data;
    });
  },
  methods: {
    ...mapActions('teacher', ['getAllFileOfMyStudents', 'teacherGetThisFile']),
    handleDownload(id, name) {
      console.log('获取文件 id: ' + id);
      this.teacherGetThisFile({
        fileId: id,
      }).then((res) => {
        console.log(res.data);
        fileDownload(res.data, name)
      });
    },
  },
};
</script>

<style>
</style>
