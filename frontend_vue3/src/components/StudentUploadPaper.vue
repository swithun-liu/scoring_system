<template>
  <div class="student_upload_paper_container0 container0">
    <el-upload
      class="upload-demo"
      action="http://localhost:8088/student_upload_paper"
      :on-success="handleSuccess"
      :on-preview="handlePreview"
      :on-remove="handleRemove"
      :before-remove="beforeRemove"
      multiple
      :limit="3"
      :on-exceed="handleExceed"
      :file-list="fileList"
      :data="userName"
      :headers="myHeaders"
    >
      <el-button size="small" type="primary">点击上传</el-button>
      <template #tip>
        <div class="el-upload__tip">fff--{{gettersAuthData.userName}}</div>
      </template>
    </el-upload>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  data() {
    return {
      fileList: [],
      userName: "username0001",
      myHeaders: {
        Authoriztion:
          "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VybmFtZTAwMDEiLCJleHAiOjE2MTUzODY3NjgsImlhdCI6MTYxNTM2ODc2OH0.GViaYzVLDCcGTYFOrH25x7LhmOeXXbJ6T5TrLyhdIERr4vmwg5BWLkL6X8V-PoYD55xMIvUFdqVMzZIr5GXIZw",
      },
    };
  },
  computed: {
    ...mapGetters("auth", {
      gettersAuthData: "getAuthData",
    }),
    ...mapGetters("auth", {
      getterLoginStatus: "getLoginStatus",
    }),
  },
  methods: {
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
        } 个文件`
      );
    },
    handleSuccess(response, file, fileList) {
      console.log(response);
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    beforeupload(file) {
      const fd = new FormData();
      this.$http.post("http://localhost:8088/student_upload_paper", fd, {
        headers: {
          test: "test",
        },
      });
      return false; // false就是不自动上传，我后来试了发现都一样，都不会自动上传
    },
  },
};
</script>

<style>
.student_upload_paper_container0 {
  background-color: rgb(201, 253, 223);
}
</style>
