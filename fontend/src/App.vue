<template>
  <a-locale-provider :locale="zh_CN">
    <div id="app">
      根组件
      <router-view></router-view>
    </div>
  </a-locale-provider>
</template>

<script>
import zh_CN from "ant-design-vue/lib/locale-provider/zh_CN";

export default {
  name: "app",
  data() {
    return {
      zh_CN,
    };
  },
};
</script>

<style lang="less" scoped>
#app {
  background-color: brown;
}
</style>
