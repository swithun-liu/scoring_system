<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-29 14:22:45
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-06-07 15:28:23
-->
<template>
  <div id="webviewer" ref="viewer"></div>
</template>

<script>
import { ref, onMounted, defineComponent, onUnmounted } from 'vue'
import WebViewer from '@pdftron/webviewer'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'

export default defineComponent({
  name: 'WebViewer',
  props: {
    myBlob: { type: Blob },
    refreshURL: { type: String },
    fileId: { type: Number },
    fileName: { type: String },
    file: { type: Object },
  },
  setup(props) {
    const viewer = ref(null)

    const store = useStore()

    const renderFile = () => {
      const path = `${process.env.BASE_URL}webviewer`
      WebViewer({ path: path }, viewer.value).then((instance) => {
        console.log('传入的文件为', props.file)
        instance.loadDocument(props.myBlob, { filename: props.file.name })

        var annotManager = instance.docViewer.getAnnotationManager()
        // Add a save button on header
        instance.setHeaderItems(function (header) {
          header.push({
            type: 'actionButton',
            img: '<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"><path d="M0 0h24v24H0z" fill="none"/><path d="M17 3H5c-1.11 0-2 .9-2 2v14c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V7l-4-4zm-5 16c-1.66 0-3-1.34-3-3s1.34-3 3-3 3 1.34 3 3-1.34 3-3 3zm3-10H5V5h10v4z"/></svg>',
            onClick: function () {
              // Update the document when button is clicked
              saveDocument('demo.pdf').then(function () {
                alert('Annotations saved to the document.')
              })
            },
          })
        })

        const saveDocument = (filename) => {
          return new Promise(function (resolve) {
            annotManager.exportAnnotations().then(function (xfdfString) {
              instance.docViewer
                .getDocument()
                .getFileData({ xfdfString })
                .then(function (data) {
                  var arr = new Uint8Array(data)
                  const file = new Blob([arr], {
                    type: 'application/pdf',
                  })

                  console.log('企图更新文件' + props.fileId)
                  console.log('文件类型', typeof file)
                  const param = new FormData()
                  param.append('file', file)
                  param.append('id', props.file.id)
                  param.append('name', props.file.name)
                  const config = {
                    headers: { 'Content-Type': 'multipart/form-data' },
                  }
                  store
                    .dispatch(props.refreshURL, {
                      param: param,
                      config: config,
                    })
                    .then((res) => {
                      console.log('文件刷新结果', res)
                      if (res.data.data === 'ok') {
                        ElMessage.success({
                          message: '修改成功',
                          type: 'success',
                        })
                      } else {
                        ElMessage.success({
                          message: '修改失败',
                          type: 'error',
                        })
                      }
                    })
                })
            })
          })
        }
      })
    }

    onMounted(() => {
      renderFile()
    })

    onUnmounted(() => {
      console.log('webviewer死掉')
    })
    return {
      viewer,
      renderFile,
    }
  },
})
</script>

<style lang="less" scoped>
#webviewer {
  height: 100%;
}

.DocumentContainer {
  background-color: transparent !important;
}
</style>
