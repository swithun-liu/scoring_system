<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-17 14:26:03
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-23 14:32:01
-->

<template>
  <el-table :data='tableData' style='width: 100%'>
    <el-table-column prop='id' label='文件id' width='180'></el-table-column>
    <el-table-column prop='name' label='文件名' width='180'></el-table-column>
    <el-table-column prop='studentId' label='学生id'></el-table-column>
    <el-table-column prop='studentName' label='学生名'></el-table-column>
  </el-table>
</template>

<script>
import { mapActions } from 'vuex';

export default {
  data() {
    return {
      tableData: [
        {
          id: '2016-05-02',
          name: '王小虎',
          studentId: '',
          studentName: ''
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
    ...mapActions('teacher', ['getAllFileOfMyStudents']),
  },
};
</script>

<style>
</style>
