/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-03-06 17:40:49
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-04 12:30:06
 */
package com.swithun.backend.controller;

import com.swithun.backend.dto.AddFileFileListDTO;
import com.swithun.backend.dto.StudentGetFileListDTO;
import com.swithun.backend.entity.CommentForFileEntity;
import com.swithun.backend.service.FileService;
import com.swithun.backend.service.StudentService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * StudentController
 */
@RestController
@CrossOrigin
public class StudentController {

    @Autowired
    private FileService fileService;

    @Autowired
    private StudentService studentS;

    /**
     * @description: 学生端上传论文
     * @param {*}
     * @return {*}
     */
    @PostMapping("/student/studentuploadpaper")
    public String studentUploadPaper(@RequestParam("file") MultipartFile file, Principal principal) {
        try {
            System.out.println("StudentController 学生姓名: " + principal.getName());
            fileService.store(file, principal.getName());
            return String.format("File << %s  >> uploaded succesfully", file.getOriginalFilename());
        } catch (Exception e) {
            return String.format("Cannot upload File << %s >> ", file.getOriginalFilename());
        }
    }

    /**
     * @description: 学生端获取所有文件 -- for 添加论文页面
     * @param {*}
     * @return {*}
     */
    @GetMapping(value = "/student/studentgetmypaper")
    public List<AddFileFileListDTO> getFileListForAddFile(Principal principal) {
        System.out.println("StudentController studetnGetMyPaper 学生姓名: " + principal.getName());
        return studentS.getFileListForAddFile(principal.getName());
    }

    /**
     * @description: 学生获取所有文件 -- for 我的论文页面
     * @param {*}
     * @return {*}
     */
    @GetMapping(value = "/student/studentGetMyPaperForMyPaper")
    public List<StudentGetFileListDTO> getFileListForMyFiles(Principal principal) {
        System.out.println("StudentController studetnGetMyPaper 学生姓名: " + principal.getName());
        return studentS.getFileListForMyFiles(principal.getName());
    }

    /**
     * @description: 学生下载选中文件
     * @param {*}
     * @return {*}
     */
    @GetMapping(value = "/student/downloadThisFile", produces = "application/pdf")
    public byte[] getMethodName(@RequestParam Integer fileId) {
        return fileService.downloadThisFile(fileId).getData();
    }

    /**
     * @description: 学生获取选中文件所有评论
     * @param {文件id}
     * @return {[{评论, 老师姓名}]}
     */
    @GetMapping(value = "/student/studentGetTeacherCommentOfMyFile")
    public List<CommentForFileEntity> studentGetTeacherCommentOfMyFile(@RequestParam Integer chosedFileId) {
        System.out.println("文件Id : " + chosedFileId);
        return studentS.getTeacherCommentOfMyFIle(chosedFileId);
    }

    @PostMapping(value="/student/addCommentForThisFile")
    public String addCommentForThisFile(@RequestBody Map<String, Object> mp, Principal principal) {
        Integer fileId = (Integer) mp.get("chosedFileId");
        String comment = (String) mp.get("comment");
        Integer parent_comment_id = (Integer) mp.get("chosedCommentId");
        System.out.println("student " + principal.getName() + " add comments [ " + comment + " ]\t for file " + fileId + "\treplay to comment [ " + parent_comment_id + " ]");
        studentS.addComment(fileId, comment, parent_comment_id, principal.getName());
        return "添加成功";
    }
    

}