<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-29 14:22:45
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-29 16:55:51
-->
<template>
  <div id="webviewer" ref="viewer"></div>
</template>

<script>
import { ref, onMounted } from 'vue'
import WebViewer from '@pdftron/webviewer'

export default {
  name: 'WebViewer',
  props: { initialDoc: { type: String }, myBlob: { type: Blob } },
  setup(props) {
    const viewer = ref(null)

    const renderFile = () => {
      const path = `${process.env.BASE_URL}webviewer`
      console.log('初始化兄台各地1: ', path)
      WebViewer({ path: path }, viewer.value).then((instance) => {
        instance.loadDocument(props.myBlob, { filename: 'myfile.pdf' })

        const { docViewer } = instance
        docViewer.on('documentLoaded', () => {
          // perform document operations
        })

        console.log('初始化兄台各地2: ', path, instance)
      })
      console.log('初始化兄台各地3: ', path)
    }
    onMounted(() => {
      renderFile()
    })
    return {
      viewer,
      renderFile,
    }
  },
}
</script>

<style>
#webviewer {
  height: 800px;
}
</style>
