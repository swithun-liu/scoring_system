<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-28 15:06:41
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-01 16:28:10
-->
<template>
  <div id="app" style="height: 100%">
    test
    <div class='radio-checked'>
      <div class='radio-checked_highlight'></div>
      <div class='radio-checked_container'>
        <input
          checked='checked'
          class='radio-checked_input'
          id='on'
          name='status'
          type='radio'
          value='on'
        />
        <label class='radio-checked_label radio-checked_label--on' for='on'>On</label>
        <input class='radio-checked_input' id='off' name='status' type='radio' value='off' />
        <label class='radio-checked_label radio-checked_label--off' for='off'>Off</label>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'demo',
}
</script>

<style scoped>
</style>
