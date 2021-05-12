<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-11 19:25:50
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-11 20:08:10
-->
<template>
  <div class="profile-container">
    <div class="profile-name-wrapper">Hi~{{userName}}中文测试</div>
  </div>
</template>

<script>
import { onMounted, ref } from '@vue/runtime-core'
import { useStore } from 'vuex'
export default {
  setup(props) {
    const store = useStore()

    const userName = ref('username')

    onMounted(() => {
      const authInfo = store.getters['auth/getAuthData']
      console.log(authInfo)
      userName.value = authInfo.userName
    })

    return {
      userName,
    }
  },
}
</script>

<style scoped>
@import '../assets/css/profile.css';
</style>
