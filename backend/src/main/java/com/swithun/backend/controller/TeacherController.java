/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-23 08:44:19
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-26 16:51:45
 */
package com.swithun.backend.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.swithun.backend.dto.TeacherGetFileListDTO;
import com.swithun.backend.entity.StudentFileEntity;
import com.swithun.backend.entity.CommentForFileEntity;
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
    private TeacherService teacherS;

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
    public List<TeacherGetFileListDTO> getMethodName(Principal principal) {
        return teacherS.findStudentFileOfThisTeacher(principal.getName());
    }

    /**
     * @description: 老师下载选中文件
     * @param {*}
     * @return {*}
     */
    @GetMapping(value = "/teacher/teacherGetThisFile", produces = "application/pdf")
    public byte[] teacherGetThisFile(@RequestParam Integer fileId, HttpServletResponse response) throws IOException {
        StudentFileEntity studentFileEntity = fileservice.downloadThisFile(fileId);
        return studentFileEntity.getData();
    }

    @PostMapping(value = "/teacher/teacherScoreThisFile")
    public String score(@RequestBody Map<String, Object> mp) {
        teacherS.score(mp);
        return "修改成功";
    }

    @PostMapping(value = "/teacher/getAllCommentsOfThisFileOfMine")
    public List<CommentForFileEntity> getComments(@RequestBody Map<String, Object> mp, Principal principal) {
        return teacherS.getComments(mp, principal);
    }

    // 添加评论
    @PostMapping(value = "/teacher/AddCommentForThisFile")
    public String addComment(@RequestBody Map<String, Object> mp, Principal principal) {
        teacherS.addComment(mp, principal);
        return "评论成功";
    }

}
