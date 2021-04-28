/*
 * @Descripttion:
 * @version:
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-28 10:00:16
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-28 10:55:30
 */
// #cpc-on-off #codepenchallenge
const root = document.documentElement;
// var animateDur = '1.4s';

window.onload = function () {
  console.log('funtion onload');
  const activeChecked = document.querySelector(
    '.radio-checked_input:checked + .radio-checked_label'
  );
  const left = activeChecked.offsetLeft;
  const width = activeChecked.offsetWidth;
  animation(left, width);
};

document
  .querySelectorAll('.radio-checked_label')
  .forEach((t) => t.addEventListener('click', onChecked));

function onChecked(e) {
  console.log('funtion onChecked');
  const left = e.target.offsetLeft;
  const width = e.target.offsetWidth;
  animation(left, width);
}
// var bool = true;

function animation(left, width) {
  console.log('funtion animation');
  root.style.setProperty('--pagination-width', width + 'px');
  root.style.setProperty('--highlight-left', left + 'px');
}
