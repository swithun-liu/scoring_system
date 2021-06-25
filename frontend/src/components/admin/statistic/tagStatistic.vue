<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-23 11:57:22
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-25 17:46:08
-->
<template>
  <div ref="chart" style="width: 100%; height: 100%;" />
</template>

<script>
import { reactive, ref } from '@vue/reactivity'
import { init } from 'echarts'
import { useStore } from 'vuex'
import { onMounted, watch } from '@vue/runtime-core'

export default {
  setup(props) {
    const store = useStore()
    const chart = ref(null)
    let chartElement
    const initChart = () => {
      chartElement = init(chart.value)
    }
    const mountData = () => {
      chartElement.setOption(option)
    }
    const option = reactive({
      dataset: [
        {
          dimensions: ['tagName', 'num'],
          source: [],
        },
        {
          transform: {
            type: 'sort',
            config: { dimension: 'num', order: 'desc' },
          },
        },
      ],
      xAxis: {
        type: 'category',
        axisLabel: { interval: 0, rotate: 30 },
      },
      yAxis: {},
      series: {
        type: 'bar',
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2,
          color: '#19351655',
        },
        encode: { x: 'tagName', y: 'num' },
        datasetIndex: 1,
      },
    })
    watch(option, () => {
      mountData()
    })
    onMounted(() => {
      initChart()
      store.dispatch('tag/tagStatistic').then((res) => {
        console.log('获取问题标签统计', res)
        option.dataset[0].source = res.data.data
      })
    })
    return {
      chart,
    }
  },
}
</script>

<style>
</style>
