<!--
 * @Descripttion: 评论组件
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-04 10:58:35
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-04 11:41:13
-->
<template>
  <div>
    <el-tree
      :data="data"
      :props="defaultProps"
      node-key="id"
      default-expand-all
      :expand-on-click-node="false"
    >
      <template #default="{ node }">
        <span class="custom-tree-node">
          <span
            :class="node.data.studentByStudentId == null ? 'teacher-tag' : 'student-tag'"
          >{{ node.data.studentByStudentId == null ? node.data.teacherByTeacherId.name : node.data.studentByStudentId.name }}</span>
          <br />
          <span>{{ node.label }}</span>
          <br />
          <span>
            <a @click="$emit('handleReplay', node)" class="replay-button">Replay</a>
          </span>
        </span>
      </template>
    </el-tree>
  </div>
</template>

<script>
export default {
  props: {
    data: [],
  },
  data() {
    return {
      defaultProps: {
        children: 'commentForFilesById',
        label: 'comments',
      },
    }
  },
}
</script>

<style>
</style>
