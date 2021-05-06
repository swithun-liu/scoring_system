/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-23 08:44:19
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-03 16:37:04
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
import com.swithun.backend.tools.convert.Entity2DTO;

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

    @Autowired
    private Entity2DTO converter;

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
        return teacherService.findStudentFileOfThisTeacher(principal.getName());
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
    public String teacherSetFileScore(@RequestBody Map<String, Object> mp) {
        Integer id = (Integer) mp.get("chosedFileId");
        Integer score = (Integer) mp.get("fileScore");
        System.out.println(id + " " + score);
        teacherService.scoreThisPaper(id, score);
        return "修改成功";
    }

    @PostMapping(value = "/teacher/getAllCommentsOfThisFileOfMine")
    public List<CommentForFileEntity> getAllCommentsOfThisFileOfMine(@RequestBody Map<String, Object> mp,
            Principal principal) {

        Integer fileId = (Integer) mp.get("chosedFileId"); // 文件Id
        String teacherName = principal.getName(); // 教师姓名

        System.out.println("teacherController getAllCommentsOfThisFileOfMine" + fileId + " " + teacherName);

        List<CommentForFileEntity> teacherComments = teacherService
                .findAllCommnetsOfThisFileOfThisTeacher(fileId, teacherName);
        return teacherComments;
    }

    /**
     * 老师为文章添加评论
     * @param mp
     * @param principal
     * @return
     */
    @PostMapping(value = "/teacher/AddCommentForThisFile")
    public String teacherAddCommentsForThisFile(@RequestBody Map<String, Object> mp, Principal principal) {
        Integer fileId = (Integer) mp.get("chosedFileId");
        String comment = (String) mp.get("comment");
        Integer parent_comment_id = (Integer)mp.get("chosedCommentId");
        System.out.println("teacherController AddCommentForThisFile" + fileId + " " + comment);
        teacherService.addComment(fileId, comment, parent_comment_id ,principal.getName());
        return "评论成功";
    }

}
