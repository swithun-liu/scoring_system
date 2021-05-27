/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-06 21:38:54
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-27 21:27:31
 */
package com.swithun.backend.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.swithun.backend.dao.StudentFileRepository;
import com.swithun.backend.dao.StudentRepository;
import com.swithun.backend.dao.TeacherRepository;
import com.swithun.backend.entity.StudentEntity;
import com.swithun.backend.entity.StudentFileEntity;
import com.swithun.backend.entity.TeacherEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private StudentFileRepository fileR;
    @Autowired
    private TeacherRepository teacherR;
    @Autowired
    private StudentRepository studentR;

    Logger logger = LoggerFactory.getLogger(AdminService.class);

    // 统计

    public List<List<Object>> statisticAllScore() {
        List<StudentFileEntity> files = fileR.findAll();
        int[] sums = new int[7];
        String[] names = { "95-100", "90-95", "80-90", "70-80", "60-70", "0-60", "尚未评分" };
        for (StudentFileEntity file : files) {
            Integer score = file.getScore();
            if (score != null) {
                if (score >= 95) {
                    sums[0]++;
                } else if (score >= 90) {
                    sums[1]++;
                } else if (score >= 80) {
                    sums[2]++;
                } else if (score >= 70) {
                    sums[3]++;
                } else if (score >= 60) {
                    sums[4]++;
                } else {
                    sums[5]++;
                }
            } else {
                sums[6]++;
            }
        }
        List<List<Object>> data = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            data.add(Arrays.asList(sums[i], names[i]));
        }
        return data;
    }

    public List<List<Object>> statisticTeacherTask() {
        List<TeacherEntity> teachers = teacherR.findAll();
        List<List<Object>> res = new ArrayList<>();
        List<Object> names = new ArrayList<>();
        List<Object> student_num = new ArrayList<>();
        List<Object> has_scored_file_num = new ArrayList<>();
        List<Object> wait_score_file_num = new ArrayList<>();
        for (TeacherEntity teacher : teachers) {
            // 1.
            names.add(teacher.getName() == null ? "null" : teacher.getName());
            Collection<StudentEntity> students = teacher.getStudentsById();
            // 2.
            student_num.add(students.size());
            Integer has_score = 0;
            Integer wait_score = 0;
            for (StudentEntity student : students) {
                Collection<StudentFileEntity> files = student.getStudentFilesById();
                for (StudentFileEntity file : files) {
                    if (file.getScore() != null) {
                        has_score++;
                    } else {
                        wait_score++;
                    }
                }
            }
            // 3.
            has_scored_file_num.add(has_score);
            // 4.
            wait_score_file_num.add(wait_score);
        }
        res.add(names);
        res.add(student_num);
        res.add(has_scored_file_num);
        res.add(wait_score_file_num);
        return res;
    }

    // 获取学生列表
    public List<StudentEntity> getStudents() {
        List<StudentEntity> students = studentR.findAll();
        for (StudentEntity student : students) {
            student.setTemperTeacher(student.getTeacherByTeacherId());
            student.getTemperTeacher().setStudentsById(null);
        }
        return students;
    }

    // 获取教师列表
    public List<TeacherEntity> getTeachers() {
        return teacherR.findAll();
    }

    // 编辑学生
    public void editStudent(Map<String, Object> mp) {

        Integer id = (Integer) mp.get("id");
        String name = (String) mp.get("name");
        String password = (String) mp.get("password");

        StudentEntity student = studentR.findById(id).get();

        student.setName(name);

        if (password != "") {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            student.setPassword(encoder.encode(password));
        }
        studentR.save(student);
    }

    public void editTeacher(Map<String, Object> mp) {

        logger.info("教师修改信息");

        Integer id = (Integer) mp.get("id");
        String name = (String) mp.get("name");
        String password = (String) mp.get("password");

        logger.info("id " + id + "   name " + name + "   password " + password);

        TeacherEntity teacher = teacherR.findById(id).get();

        teacher.setName(name);

        if (password != "") {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            teacher.setPassword(encoder.encode(password));
        }
        teacherR.save(teacher);
    }

    public boolean setTeacher(Map<String, Integer> mp) {
        Integer teacherId = mp.get("teacherId");
        Integer studentId = mp.get("studentId");

        logger.info("teacherId " + teacherId);
        logger.info("studentId " + studentId);

        StudentEntity student = studentR.findById(studentId).get();
        TeacherEntity teacher = teacherR.findById(teacherId).get();

        if (student == null || teacher == null) {
            return false;
        }
        logger.info(student.getId() + " " + student.getName());
        logger.info(teacher.getId() + " " + teacher.getName());
        student.setTeacherByTeacherId(teacher);
        studentR.save(student);
        return true;
    }

}