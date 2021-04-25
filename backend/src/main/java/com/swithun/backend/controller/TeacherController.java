/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-23 08:44:19
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-25 15:18:11
 */
package com.swithun.backend.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.swithun.backend.dto.studentFileDTO;
import com.swithun.backend.entity.StudentFileEntity;
import com.swithun.backend.service.FileService;
import com.swithun.backend.service.TeacherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private FileService fileservice;

    @GetMapping(value = "/testprincipal")
    public String testPrincipal(Principal principal) {
        return principal.getName();
    }

    @PostMapping(value = "/testprincipal/post")
    public String testPrincipalPost(Principal principal) {
        return principal.getName();
    }

    @GetMapping(value = "/teacher/getAllFileOfstudentsOfMine")
    public List<studentFileDTO> getMethodName(Principal principal) {
        return teacherService.findStudentFileOfThisTeacher(principal.getName());
    }

    @GetMapping(value = "/teacher/teacherGetThisFile", produces = "application/pdf")
    public byte[] teacherGetThisFile(@RequestParam Integer fileId, HttpServletResponse response) throws IOException {
        StudentFileEntity studentFileEntity = fileservice.teacherGetThisFile(fileId);
        return studentFileEntity.getData();
    }

    @PostMapping(value="/teacher/teacherScoreThisFile")
    public String teacherSetFileScore(@RequestBody Map<String, Object> mp) {
        Integer id = (Integer) mp.get("chosedFileId");
        Integer score = (Integer) mp.get("fileScore");
        System.out.println(id + " " + score);
        teacherService.scoreThisPaper(id, score);
        return "修改成功";
    }

}
