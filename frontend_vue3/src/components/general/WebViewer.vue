<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-29 14:22:45
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-06-01 21:14:18
-->
<template>
  <div id="webviewer" ref="viewer"></div>
</template>

<script>
import { ref, onMounted, defineComponent, onUnmounted } from 'vue'
import WebViewer from '@pdftron/webviewer'

export default defineComponent({
  name: 'WebViewer',
  props: { myBlob: { type: Blob } },
  setup(props) {
    const viewer = ref(null)
    const myInstance = ref(null)

    const renderFile = () => {
      const path = `${process.env.BASE_URL}webviewer`
      console.log('初始化兄台各地1: ', path)
      WebViewer({ path: path }, viewer.value).then((instance) => {
        myInstance.value = instance
        instance.loadDocument(props.myBlob, { filename: 'myfile.pdf' })
      })
      console.log('初始化兄台各地3: ', path)
    }
    onMounted(() => {
      console.log('重开');
      renderFile()
    })

    onUnmounted(() => {
      console.log('webviewer死掉');
    })
    return {
      viewer,
      renderFile,
    }
  },
})
</script>

<style>
#webviewer {
  height: 800px;
}
</style>
