<!--
 * @Descripttion: 评论组件
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-04 10:58:35
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-28 10:48:24
-->
<template>
  <el-tree
    v-loading="loading"
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
        <div class="tags-wrapper">
          <!-- tag 区域  begin-->
          <el-tag
            :key="tagComment "
            v-for="tagComment in node.data.tagCommentsById"
            closable
            :disable-transitions="false"
            @close="handleClose(tag)"
          >{{tagComment.tagByTagId == null ? 'null' : tagComment.tagByTagId.name}}</el-tag>
          <el-autocomplete
            v-if="inputVisible"
            v-model="inputValue"
            :fetch-suggestions="querySearchAsync"
            placeholder="请输入内容"
            @select="handleInputConfirm(node.data)"
            @keyup.enter="handleInputConfirm(node.data)"
          ></el-autocomplete>
          <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
          <!-- tag 区域  end-->
        </div>
        <span class="replay-button-wrapper">
          <a @click="$emit('handleReplay', node)" class="replay-button">Replay</a>
        </span>
      </span>
    </template>
  </el-tree>
</template>

<script>
import { reactive, ref } from '@vue/reactivity'
import { onMounted } from '@vue/runtime-core'
import { useStore } from 'vuex'
export default {
  props: {
    data: [],
    loading: Boolean,
  },
  emits: ['handleReplay', 'refresh-data', 'test'],
  setup(props, { emit }) {
    const store = useStore()
    // tag标签
    const defaultProps = reactive({
      children: 'commentForFilesById',
      label: 'comments',
    })
    const inputVisible = ref(false)
    const inputValue = ref('')
    const handleClose = (tag) => {
      this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1)
    }
    const InnerRefreshData = () => {
      console.log('内部尝试刷新数据')
      console.log('context emit', emit)
      console.log('old', props.data)
      emit('refresh-data')
      console.log('new', props.data)
      console.log('内部尝试刷新数据完毕')
    }
    const showInput = () => {
      inputVisible.value = true
      this.$nextTick((_) => {
        this.$refs.saveTagInput.$refs.input.focus()
      })
    }
    // 输入tag点击回车
    const handleInputConfirm = (data) => {
      console.log(
        '输入tag点击回车',
        '评论id ' + data.id,
        '标签名' + inputValue.value
      )
      store
        .dispatch('tag/commentAddTag', {
          tagName: inputValue.value,
          commentId: data.id,
        })
        .then(() => {
          InnerRefreshData()
          // 结构控制
          inputVisible.value = false
          inputValue.value = ''
        })
    }
    // 输入框相关
    const tags = ref([
      {
        id: 1,
        value: 'tagName',
      },
    ])

    const loadAllItem = () => {
      const res = store.dispatch('tag/getAllTags').then((res) => {
        console.log('loadAllItem', res)
        tags.value = res
      })
      return res
    }

    // 输入东西，过滤列表的
    const querySearchAsync = (queryString, cb) => {
      console.log(queryString)
      var results = queryString
        ? tags.value.filter(createFilter(queryString))
        : tags.value

      console.log(results)
      cb(results)
    }
    const createFilter = (queryString) => {
      return (restaurant) => {
        return (
          restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) ===
          0
        )
      }
    }
    // 点击选择的
    const handleSelect = (item) => {
      console.log(item)
    }
    const InnerTest = () => {
      console.log('inner test')
      // emit('test')
      emit('refresh-data')
    }
    onMounted(() => {
      loadAllItem()
      console.log('loadAllItem', tags.value)
    })
    return {
      defaultProps,
      // tag相关
      inputVisible,
      inputValue,
      handleClose,
      showInput,
      handleInputConfirm,
      InnerTest,
      // input相关
      tags,
      state: ref(''),
      querySearchAsync,
      createFilter,
      // loadAll,
      loadAllItem,
      handleSelect,
      InnerRefreshData,
    }
  },
}
</script>

<style scoped>
@import '../../assets/css/comment.css';

.el-tag + .el-tag {
  margin-left: 10px;
}
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>
