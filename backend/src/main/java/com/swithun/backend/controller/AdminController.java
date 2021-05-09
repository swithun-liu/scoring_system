/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-06 21:35:12
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-09 17:03:55
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

    // 统计

    @GetMapping(value = "/admin/statistic/allScore")
    public List<List<Object>> statisticAllScore() {
        return adminS.statisticAllScore();
    }

    @GetMapping(value = "/admin/statistic/teacherTask")
    public List<List<Object>> statisticAllTeacherTasks() {
        return adminS.statisticTeacherTask();
    }

    // 获取所有

    @GetMapping(value="/admin/getAllStudent")
    public List<StudentEntity> getAllStudent() {
        return adminS.getAllStudent();
    }
    
    @GetMapping(value="/admin/getAllTeachers")
    public List<TeacherEntity> getAllTeachers() {
        return adminS.getAllTeachers();
    }

    // 编辑

    @PostMapping(value="/admin/editStudent")
    public String editStudent(@RequestBody Map<String, Object> mp) {
        adminS.editStudent(mp);
        return "修改成功";
    }

    @PostMapping(value="/admin/editTeacher")
    public String editTeacher(@RequestBody Map<String, Object> mp) {
        adminS.editTeacher(mp);
        return "修改成功";
    }
    

}
