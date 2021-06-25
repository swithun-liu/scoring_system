/*
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-11 20:29:38
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-11 20:57:15
 */

var data = {
  timeLimitedPlanSet: [{
    id: 3,
    expectedStartTimeBegin: 1620432597000,
    expectedStartTimeEnd: null,
    expectedEndTimeBegin: null,
    expectedEndTimeEnd: 1620439797000,
  }],
  orderLimitedPlanSet: [{
    id: 30,
    planName: null,
    repeatType: 0,
    expectedStartDate: null,
    expectedEndDate: null,
    expectedStartTimeBegin: null,
    expectedStartTimeEnd: null,
    expectedEndTimeBegin: null,
    expectedEndTimeEnd: null,
    practiceStartDateTime: null,
    practiceEndDateTime: null,
    x: 2811,
    y: 324,
    devotion: null,
    satisfaction: null,
    priority: null,
    expectedTime: null,
    time: null,
    note: null,
    typeList: [],
    taskType: null,
    subTasks: []
  }],
  planRelation: {
    o1: [
      't2'
    ]
  }
}

console.log(data)

function check(pre, back) {
  for (const key in data) {
    console.log(key);
    if (key === pre) {
      console.log(data[key]);
    }
  }
}

function checkItem()

check()
