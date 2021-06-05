/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-03-06 17:40:49
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-06-05 21:27:59
 */
package com.swithun.backend.controller;

import com.swithun.backend.dto.AddFileFileListDTO;
import com.swithun.backend.entity.CommentForFileEntity;
import com.swithun.backend.entity.StudentFileEntity;
import com.swithun.backend.service.FileService;
import com.swithun.backend.service.StudentService;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * StudentController
 */
@RestController
@CrossOrigin
public class StudentController {

    @Autowired
    private FileService fileS;

    @Autowired
    private StudentService studentS;

    Logger logger = LoggerFactory.getLogger(StudentController.class);

    // 学生端上传论文
    @PostMapping("/student/studentuploadpaper")
    public String studentUploadPaper(@RequestParam("file") MultipartFile file, Principal principal) {
        try {
            System.out.println("StudentController 学生姓名: " + principal.getName());
            fileS.store(file, principal.getName());
            return String.format("File << %s  >> uploaded succesfully", file.getOriginalFilename());
        } catch (Exception e) {
            return String.format("Cannot upload File << %s >> ", file.getOriginalFilename());
        }
    }

    // 获取所有文件 -- for 添加论文页面
    @GetMapping(value = "/student/studentgetmypaper")
    public List<AddFileFileListDTO> getFileListForAddFile(Principal principal) {
        System.out.println("StudentController studetnGetMyPaper 学生姓名: " + principal.getName());
        return studentS.getFileListForAddFile(principal.getName());
    }

    // 获取所有文件 -- for 我的论文页面
    @GetMapping(value = "/student/studentGetMyPaperForMyPaper")
    public List<StudentFileEntity> getFileListForMyFiles(Principal principal) {
        logger.info("学生: " + principal.getName());
        return studentS.getFileListForMyFiles(principal.getName());
    }

    // 学生下载选中文件
    @GetMapping(value = "/student/downloadThisFile", produces = "application/pdf")
    public byte[] getMethodName(@RequestParam Integer fileId) {
        return fileS.downloadThisFile(fileId).getData();
    }

    // 学生获取选中文件所有评论
    @GetMapping(value = "/student/studentGetTeacherCommentOfMyFile")
    public List<CommentForFileEntity> studentGetTeacherCommentOfMyFile(@RequestParam Integer chosedFileId) {
        System.out.println("文件Id : " + chosedFileId);
        return studentS.getComments(chosedFileId);
    }

    // 学生 添加 文章评论
    @PostMapping(value = "/student/addCommentForThisFile")
    public String addCommentForThisFile(@RequestBody Map<String, Object> mp, Principal principal) {
        Integer fileId = (Integer) mp.get("chosedFileId");
        String comment = (String) mp.get("comment");
        Integer parent_comment_id = (Integer) mp.get("chosedCommentId");
        logger.info("student " + principal.getName() + " add comments [ " + comment + " ]\t for file " + fileId
                + "\treplay to comment [ " + parent_comment_id + " ]");
        studentS.addComment(fileId, comment, parent_comment_id, principal.getName());
        return "添加成功";
    }

    // 给 文章 添加 摘要
    @PostMapping(value = "/student/addAbstract")
    public String addAbstract(@RequestBody StudentFileEntity file) {
        studentS.addAbstract(file);
        return "添加成功";
    }

    // 刷新文件
    @PostMapping(value = "/student/refreshFile")
    public String refreshFile(@RequestParam("file") MultipartFile file, @RequestParam("id") Integer id, @RequestParam("name") String name) {
        logger.info("使用 " + file.getOriginalFilename() + " 更新 " + id);
        try {
            studentS.refreshFile(file, id, name);
        } catch (IOException e) {
            return "更新失败";
        }
        return "更新成功";
    }

    @DeleteMapping(value = "/student/deleteFile/{id}")
    public String deleteFile(@PathVariable("id") Integer id, Principal principal) {
        logger.info("请求删除文件 " + id);
        if (studentS.deleteFile(id, principal)) {
            return "删除成功";
        }
        return "删除失败";
    }
}