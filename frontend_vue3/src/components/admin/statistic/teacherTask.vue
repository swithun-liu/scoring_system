<!--
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-08 09:13:41
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-08 14:10:47
-->
<template>
  <div ref="chart" style="width: 100%; height: 100%;"/>
</template>

<script>
import { reactive, ref } from '@vue/reactivity'
import { init } from 'echarts'
import { onMounted, watch } from '@vue/runtime-core'
import { useStore } from 'vuex'
export default {
  setup(props) {
    const store = useStore()
    const chart = ref(null)
    let chartElement
    const option = reactive({
      dataset: [
        {
          source: [
            [220, 182]
          ]
        }
      ],
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          // Use axis to trigger tooltip
          type: 'shadow', // 'shadow' as default; can also be 'line' or 'shadow'
        },
      },
      legend: {
        data: ['正在管理的学生数量', '已经打分的论文', '尚未打分的论文'],
      },
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true,
      },
      xAxis: {
        type: 'value',
      },
      yAxis: {
        type: 'category',
        data: ['老师1', '老师2', '老师3'],
      },
      series: [
        {
          name: '正在管理的学生数量',
          type: 'bar',
          stack: 'total',
          label: {
            show: true,
          },
          emphasis: {
            focus: 'series',
          },
          data: [220, 182],
        },
        {
          name: '已经打分的论文',
          type: 'bar',
          stack: 'total',
          label: {
            show: true,
          },
          emphasis: {
            focus: 'series',
          },
          data: [120, 132],
        },
        {
          name: '尚未打分的论文',
          type: 'bar',
          stack: 'total',
          label: {
            show: true,
          },
          emphasis: {
            focus: 'series',
          },
          data: [120, 132],
        },
      ],
    })
    const initChart = () => {
      chartElement = init(chart.value)
    }
    const mountData = () => {
      chartElement.setOption(option)
    }

    const renewOption = () => {
      store.dispatch('admin/statisticTeacherTask').then((res) => {
        var info = res.data.data
        option.yAxis.data = info[0]
        option.series[0].data = info[1]
        option.series[1].data = info[2]
        option.series[2].data = info[3]
      })
    }

    watch(option, () => {
      mountData()
    })

    onMounted(() => {
      initChart()
      mountData()
      renewOption()
    })

    return {
      chart,
    }
  },
}
</script>
