<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-06 20:21:26
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-23 18:52:35
-->
<template>
  <div ref="chart" style="width: 100%; height: 100%;" />
</template>

<script>
import { ref, watch, onMounted, reactive } from 'vue'
import { useStore } from 'vuex'
import { init } from 'echarts'

export default {
  setup(props, ctx) {
    const store = useStore()

    const text = ref(1)

    const chart = ref(null)
    let chartElement
    const option = reactive({
      dataset: [
        {
          source: [
            [22, 'Direct'],
            [10, 'Email'],
            [22, 'Ad Networks'],
            [22, 'Ad Networks'],
          ],
        },
      ],
      title: {
        text: 'Traffic Sources',
        left: 'center',
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)',
      },
      legend: {
        orient: 'vertical',
        left: 'left',
      },
      series: [
        {
          name: 'Traffic Sources',
          type: 'pie',
          radius: ['40%', '70%'],
          center: ['50%', '60%'],
          itemStyle: {
            borderRadius: 100,
            borderColor: '#fff',
            borderWidth: 2,
          },
          datasetIndex: 0,
          encode: { value: 0, itemName: 1 },
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)',
            },
          },
        },
      ],
    })
    const initChart = () => {
      chartElement = init(chart.value)
    }
    const mountData = () => {
      chartElement.setOption(option)
    }

    watch(option, () => {
      mountData()
    })

    onMounted(() => {
      initChart()
      // mountData()
      store.dispatch('admin/getAllFile').then((res) => {
        console.log('分数统计数据', res.data.data)
        option.dataset[0].source = res.data.data
      })
    })

    return {
      chart,
      text
    }
  }
}
</script>

<style scoped>
.chart {
  height: 100%;
}
</style>

<style>
body {
  margin: 0;
}
</style>
