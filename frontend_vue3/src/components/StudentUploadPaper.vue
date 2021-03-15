<template>
  <div class="student_upload_paper_container0 container0">
    <el-upload
      class="upload-demo"
      action="http://localhost:8088/studentuploadpaper/"
      :on-success="handleSuccess"
      :on-preview="handlePreview"
      :on-remove="handleRemove"
      :before-upload="beforeUpload"
      :before-remove="beforeRemove"
      multiple
      :limit="3"
      :on-exceed="handleExceed"
      :file-list="fileList"
      :data="userName"
      :headers="myHeaders"
      :auto-upload="true"
    >
      <el-button size="small" type="primary" class="test">点击上传</el-button>
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
    beforeUpload(file) {
      console.log("before upload");
      const fd = new FormData();
      fd.append("file", file);
      this.$http.post("http://localhost:8088/studentuploadpaper", fd, {
        headers: {
          test: "test",
        },
      });
      return false; // false就是不自动上传，我后来试了发现都一样，都不会自动上传
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleExceed(files, fileList) {
      console.log("handleExceed");
      this.$message.warning(
        `当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
        } 个文件`
      );
    },
    handleSuccess(response, file, fileList) {
      console.log("handle success");
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
  background: rgba(255, 255, 255, 0.25)  !important;
  box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37) !important;
  backdrop-filter: blur(4px) !important;
  -webkit-backdrop-filter: blur(4px) !important;
  border-radius: 10px !important;
  border: 1px solid rgba(255, 255, 255, 0.18) !important;
}
</style>
