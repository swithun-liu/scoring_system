<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-29 14:22:45
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-06-02 20:07:32
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

    const renderFile = () => {
      const path = `${process.env.BASE_URL}webviewer`
      WebViewer({ path: path }, viewer.value).then((instance) => {
        instance.loadDocument(props.myBlob, { filename: 'myfile.pdf' })
      })
    }
    onMounted(() => {
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

<style lang="less" scoped>
#webviewer {
  height: 100%;
}

.DocumentContainer {
  background-color: transparent !important;
}
</style>
