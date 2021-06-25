<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-03-06 17:40:49
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-06-05 20:05:25
-->
<template>
  <div class="container0">
    <div class="upload-wrapper">
      <el-upload
        class="upload-demo"
        action="http://localhost:8089/student/studentuploadpaper/"
        :on-success="handleSuccess"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :before-remove="beforeRemove"
        multiple
        :on-exceed="handleExceed"
        :file-list="fileList"
        :data="{userName}"
        :headers="myHeaders"
        :auto-upload="true"
        list-type="text"
      >
        <div class="upload-button-wrapper">
          <button class="custom-btn btn-12 uploadButton">
            <span>Click!</span>
            <span>UPLOAD</span>
          </button>
        </div>
        <template #tip>
          <div class="el-upload__tip"></div>
        </template>
      </el-upload>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

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
        // {
        //   name: 'food.jpeg',
        //   url:
        //     'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
        // },
      ],
      userName: 'username0001',
      myHeaders: {
        authorization: 1,
      },
    }
  },
  mounted() {
    this.myHeaders.authorization = 'bearer ' + this.gettersAuthData.token
    console.log(this.myHeaders.Authoriztion)
    var _this = this
    this.getMyPaper().then((res) => {
      console.log(res.data.data)
      _this.fileList = res.data.data
    })
  },
  methods: {
    ...mapActions('auth', ['getMyPaper']),
    handleRemove(file, fileList) {
      console.log(file, fileList)
    },
    handlePreview(file) {
      console.log(file)
    },
    handleExceed(files, fileList) {
      console.log('handleExceed')
      this.$message.warning(
        `当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
        } 个文件`
      )
    },
    handleSuccess(response, file, fileList) {
      console.log('handle success')
      console.log(response)
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },
  },
}
</script>

<style>
@import '../../assets/css/el-upload.css';

.uploadButton {
  font-family: fantasy !important;
  font-size: 25px !important;
  width: 200px;
  height: 50px;
  margin: 0px !important;
}
.upload-wrapper {
  height: 100%;
  display: flex;
  justify-content: center;
  align-content: center;
}
</style>
