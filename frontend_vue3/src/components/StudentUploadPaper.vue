<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-03-06 17:40:49
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-12 21:02:37
-->
<template>
  <div class='student_upload_paper_container0 container0'>
    <el-upload
      class='upload-demo'
      action='http://localhost:8088/studentuploadpaper/'
      :on-success='handleSuccess'
      :on-preview='handlePreview'
      :on-remove='handleRemove'
      :before-remove='beforeRemove'
      multiple
      :on-exceed='handleExceed'
      :file-list='fileList'
      :data='{userName}'
      :headers='myHeaders'
      :auto-upload='true'
    >
      <el-button size='small' type='primary' class='test'>点击上传</el-button>
      <template #tip>
        <div class='el-upload__tip'>fff--{{gettersAuthData.userName}}</div>
      </template>
    </el-upload>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';

export default {
  computed: {
    ...mapGetters('auth', {
      gettersAuthData: 'getAuthData',
    }),
    ...mapGetters('auth', {
      getterLoginStatus: 'getLoginStatus',
    }),
  },
  data() {
    return {
      fileList: [
      ],
      fileList2: [
        {
          name: 'paper3',
        },
        {
          name: 'paper4',
        },
      ],
      userName: 'username0001',
      myHeaders: {
        authorization: 1,
      },
    };
  },
  mounted() {
    this.myHeaders.authorization = 'bearer ' + this.gettersAuthData.token;
    console.log(this.myHeaders.Authoriztion);
    var _this = this;
    this.getMyPaper().then((res) => {
      console.log(res.data.data);
      _this.fileList = res.data.data;
    });
  },
  methods: {
    ...mapActions('auth', ['getMyPaper']),
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleExceed(files, fileList) {
      console.log('handleExceed');
      this.$message.warning(
        `当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
        } 个文件`
      );
    },
    handleSuccess(response, file, fileList) {
      console.log('handle success');
      console.log(response);
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
  },
};
</script>

<style>
.student_upload_paper_container0 {
  background-color: rgb(201, 253, 223);
}
.test {
  color: rgb(51, 51, 51) !important;
  background: rgba(255, 255, 255, 0.25) !important;
  box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37) !important;
  backdrop-filter: blur(4px) !important;
  -webkit-backdrop-filter: blur(4px) !important;
  border-radius: 10px !important;
  border: 1px solid rgba(255, 255, 255, 0.18) !important;
}
</style>
