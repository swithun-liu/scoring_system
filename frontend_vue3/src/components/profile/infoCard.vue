<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-24 11:06:33
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-24 21:29:39
-->
<template>
  <div class="info-card" v-for="item in infoCardData" :key="item">
    <div class="info-card-icon-wrapper">
      <i :class="item.icon" class="info-card-icon"></i>
    </div>
    <span class="info-card-value">{{item.value}}</span>
    <span class="info-card-title">{{item.title}}</span>
  </div>
</template>

<script>
import { ref } from '@vue/reactivity'
import { onMounted } from '@vue/runtime-core'
import { useStore } from 'vuex'
export default {
  setup(props) {
    const store = useStore()
    const infoCardData = ref([
      {
        icon: 'el-icon-sunny',
        title: '学生数量',
        value: 222,
      },
      {
        icon: 'el-icon-sunny',
        title: '学生数量',
        value: 222,
      },
      {
        icon: 'el-icon-sunny',
        title: '学生数量',
        value: 222,
      },
      {
        icon: 'el-icon-sunny',
        title: '学生数量',
        value: 222,
      },
      {
        icon: 'el-icon-sunny',
        title: '学生数量',
        value: 222,
      },
    ])
    const mounteData = () => {
      store.dispatch('profile/getProfile').then((res) => {
        console.log(res)
        infoCardData.value = res.data.data
        console.log('infoCardData', infoCardData)
      })
    }
    onMounted(() => {
      mounteData()
    })
    return {
      infoCardData,
    }
  },
}
</script>

<style>
@import '../../assets/css/infoCard.css';
</style>
