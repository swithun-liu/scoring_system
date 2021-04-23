/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-23 08:44:19
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-23 09:42:22
 */
package com.swithun.backend.controller;

import java.security.Principal;
import java.util.List;

import com.swithun.backend.dto.studentFileDTO;
import com.swithun.backend.service.TeacherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

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

}
