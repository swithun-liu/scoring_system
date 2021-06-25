<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-11 19:25:50
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-26 19:58:57
-->
<template>
  <div class="profile-container">
    <div class="profile-wrapper profile-wrapper-style">
      <div>
        <div class="profile-name-wrapper">Hi~{{userName}}</div>
        <div class="my-avatar-wrapper">
          <el-avatar
            :size="150"
            src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
          ></el-avatar>
        </div>
      </div>
      <!-- <div class="my-info-wrapper">
        <div class="my-info-list">haha</div>
      </div> -->
      <div class="quote-wrapper">
        <div class="quote">{{quote.quote}}</div>
      </div>
    </div>
    <div class="info-card-wrapper profile-wrapper-style">
      <info-card></info-card>
    </div>
  </div>
</template>

<script>
import { onMounted, ref } from '@vue/runtime-core'
import { useStore } from 'vuex'
import infoCard from '../profile/infoCard.vue'
export default {
  components: { infoCard },
  setup(props) {
    const store = useStore()

    const userName = ref('username')

    const quote = ref('')

    onMounted(() => {
      const authInfo = store.getters['auth/getAuthData']
      store.dispatch('profile/getQuote').then((res) => {
        console.log('quote', res)
        quote.value = res.data.contents.quotes[0]
      })
      console.log(authInfo)
      userName.value = authInfo.userName
    })

    return {
      userName,
      quote,
    }
  },
}
</script>

<style scoped>
@import '../../assets/css/profile.css';
</style>
