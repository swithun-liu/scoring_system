/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-06 21:35:12
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-27 21:28:56
 */
package com.swithun.backend.controller;

import java.util.List;
import java.util.Map;

import com.swithun.backend.entity.StudentEntity;
import com.swithun.backend.entity.TeacherEntity;
import com.swithun.backend.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminS;

    // 统计 论文得分
    @GetMapping(value = "/admin/statistic/allScore")
    public List<List<Object>> statisticScore() {
        return adminS.statisticAllScore();
    }

    // 统计 教师任务
    @GetMapping(value = "/admin/statistic/teacherTask")
    public List<List<Object>> statisticTeacherTasks() {
        return adminS.statisticTeacherTask();
    }

    // 获取学生列表
    @GetMapping(value = "/admin/getAllStudent")
    public List<StudentEntity> getStudents() {
        return adminS.getStudents();
    }

    // 获取教师列表
    @GetMapping(value = "/admin/getAllTeachers")
    public List<TeacherEntity> getTeachers() {
        return adminS.getTeachers();
    }

    // 编辑学生
    @PostMapping(value = "/admin/editStudent")
    public String editStudent(@RequestBody Map<String, Object> mp) {
        adminS.editStudent(mp);
        return "修改成功";
    }

    // 编辑教师
    @PostMapping(value = "/admin/editTeacher")
    public String editTeacher(@RequestBody Map<String, Object> mp) {
        adminS.editTeacher(mp);
        return "修改成功";
    }

    // 设置教师
    @PostMapping(value = "/admin/setTeacher")
    public String setTeacher(@RequestBody Map<String, Integer> mp) {
        if (adminS.setTeacher(mp)) {
            return "ok";
        } else {
            return "error";
        }
    }

}
